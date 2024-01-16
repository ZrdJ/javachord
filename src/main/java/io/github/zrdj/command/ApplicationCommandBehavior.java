package io.github.zrdj.command;

import io.github.zrdj.ApplicationCommand;
import io.github.zrdj.ApplicationCommandGroup;
import io.github.zrdj.ApplicationCommandOption;
import org.javacord.api.DiscordApi;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.interaction.SlashCommand;
import org.javacord.api.interaction.SlashCommandBuilder;
import org.javacord.api.interaction.SlashCommandOption;
import org.javacord.api.listener.interaction.SlashCommandCreateListener;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

abstract class ApplicationCommandBehavior implements ApplicationCommand, SlashCommandCreateListener {
    protected final String _name;
    protected final String _description;
    protected final Optional<ApplicationCommandGroup> _parentCommand;
    private final List<ApplicationCommandOption<?>> _options;
    protected DiscordApi _discordApi;

    ApplicationCommandBehavior(final String name, final String description, ApplicationCommandGroup parentCommand, List<ApplicationCommandOption<?>> options) {
        _name = name.contains(" ") ? name.replaceAll(" ", "-") : name;
        _description = description;
        _parentCommand = Optional.ofNullable(parentCommand);
        _options = options;
        options.forEach(o -> o.optionOf(this));
    }

    ApplicationCommandBehavior(final String name, final String description, List<ApplicationCommandOption<?>> options) {
        this(name, description, null, options);
    }

    @Override
    public final String fqdn() {
        return parentCommand()
                .map(p -> p.fqdn() + " " + _name)
                .orElse(_name);
    }

    @Override
    public final void register(final DiscordApi discordApi) {
        discordApi.addListener(this);
        subCommand().ifPresent(sc -> sc.register(discordApi));
        onRegisterInternal(discordApi);
    }

    protected abstract void onRegisterInternal(DiscordApi discordApi);

    @Override
    public final SlashCommandOption toSlashSubCommand(final DiscordApi discordApi) {
        _discordApi = discordApi;
        var slashCommandOptions = _options.stream().map(ApplicationCommandOption::toSlashCommandOption).collect(Collectors.toList());
        return SlashCommandOption.createSubcommand(_name, _description, slashCommandOptions);
    }

    @Override
    public final SlashCommandBuilder toSlashCommand(final DiscordApi discordApi) {
        _discordApi = discordApi;
        var builder = SlashCommand.with(_name, _description);
        for (final ApplicationCommandOption<?> option : _options) {
            builder.addOption(option.toSlashCommandOption());
        }
        subCommand().ifPresent(c -> builder.addOption(c.toSlashSubCommand(discordApi)));
        return builder;
    }

    @Override
    public final void onSlashCommandCreate(final SlashCommandCreateEvent event) {
        if (!event.getSlashCommandInteraction().getFullCommandName().equalsIgnoreCase(fqdn())) {
            return;
        }
        onSlashCommandTriggered(event);
    }

    protected abstract void onSlashCommandTriggered(final SlashCommandCreateEvent event);

    @Override
    public final Optional<ApplicationCommandGroup> parentCommand() {
        return _parentCommand;
    }

    @Override
    public final Optional<ApplicationCommand> subCommand() {
        return Optional.ofNullable(subCommandInternal());
    }

    protected ApplicationCommand subCommandInternal() {
        return null;
    }

}
