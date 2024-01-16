package io.github.zrdj.command;

import io.github.zrdj.ApplicationCommandOption;
import io.github.zrdj.ApplicationCommand;
import io.github.zrdj.ApplicationCommandGroup;
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

    protected ApplicationCommandBehavior(final String name, final String description, ApplicationCommandGroup parentCommand, List<ApplicationCommandOption<?>> options) {
        _name = name.contains(" ") ? name.replaceAll(" ", "-") : name;
        _description = description;
        _parentCommand = Optional.ofNullable(parentCommand);
        _options = options;
        options.forEach(o -> o.optionOf(this));
    }

    protected ApplicationCommandBehavior(final String name, final String description, List<ApplicationCommandOption<?>> options) {
        this(name, description, null, options);
    }

    @Override
    public final String fqdn() {
        return parentCommand()
                .map(p -> p.fqdn() + " " + getName())
                .orElse(getName());
    }

    @Override
    public final void registerListeners(final DiscordApi discordApi) {
        discordApi.addListener(this);
        subCommand().ifPresent(sc -> sc.registerListeners(discordApi));
    }

    @Override
    public final SlashCommandOption toSlashSubCommand(final DiscordApi discordApi) {
        _discordApi = discordApi;
        var slashCommandOptions = _options.stream().map(ApplicationCommandOption::toSlashCommandOption).collect(Collectors.toList());
        return SlashCommandOption.createSubcommand(getName(), description(), slashCommandOptions);
    }

    @Override
    public final SlashCommandBuilder toSlashCommand(final DiscordApi discordApi) {
        _discordApi = discordApi;
        var builder = SlashCommand.with(getName(), description());
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
        return Optional.ofNullable(internalSubCommand());
    }

    protected ApplicationCommand internalSubCommand() {
        return null;
    }

    @Override
    public final String getName() {
        return _name;
    }

    @Override
    public final String description() {
        return _description;
    }

}
