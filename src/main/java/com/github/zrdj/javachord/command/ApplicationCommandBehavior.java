package com.github.zrdj.javachord.command;

import com.github.zrdj.javachord.Javachord;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.server.Server;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.interaction.SlashCommand;
import org.javacord.api.interaction.SlashCommandBuilder;
import org.javacord.api.interaction.SlashCommandOption;
import org.javacord.api.listener.interaction.SlashCommandCreateListener;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

abstract class ApplicationCommandBehavior implements ApplicationCommand, SlashCommandCreateListener {
    protected final String _name;
    protected final String _description;
    protected final Optional<ApplicationCommandGroup> _parentCommand;
    private final List<ApplicationCommandOption<?>> _options;

    ApplicationCommandBehavior(final String name, final String description, ApplicationCommandGroup parentCommand, List<ApplicationCommandOption<?>> options) {
        _name = name.contains(" ") ? name.replaceAll(" ", "-") : name;
        _description = description;
        _parentCommand = Optional.ofNullable(parentCommand);
        _options = options;
        _parentCommand.ifPresent(p -> p.registerSubCommand(this));
        options.forEach(o -> o.optionOf(this));
        Javachord.Instance.Get.addListener(this);
        // Only add "root" commands, that are either a command group ..
        if (this instanceof ApplicationCommandGroupBehavior) {
            Javachord.Instance.Get.addCommand(this);
            // .. or a standalone command (without a parent)
        } else if(_parentCommand.isEmpty()) {
            Javachord.Instance.Get.addCommand(this);
        }
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
    public final SlashCommandOption toSlashSubCommand() {
        var slashCommandOptions = _options.stream().map(ApplicationCommandOption::toSlashCommandOption).collect(Collectors.toList());
        return SlashCommandOption.createSubcommand(_name, _description, slashCommandOptions);
    }

    @Override
    public final SlashCommandBuilder toSlashCommand() {
        var builder = SlashCommand.with(_name, _description);
        for (final ApplicationCommandOption<?> option : _options) {
            builder.addOption(option.toSlashCommandOption());
        }
        subCommands().forEach(c -> builder.addOption(c.toSlashSubCommand()));
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
    public List<ApplicationCommand> subCommands() {
        return Collections.emptyList();
    }

}
