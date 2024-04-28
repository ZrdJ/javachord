package com.github.zrdj.javachord.command;

import org.javacord.api.event.interaction.SlashCommandCreateEvent;

import java.util.*;

public abstract class ApplicationCommandGroupBehavior extends ApplicationCommandBehavior implements ApplicationCommandGroup {
    private final Set<ApplicationCommand> _subCommands = new HashSet<>();

    protected ApplicationCommandGroupBehavior(
            final String name,
            final String description
    ) {
        super(name, description, Collections.emptyList());
    }

    @Override
    public final List<ApplicationCommand> subCommands() {
        return new ArrayList<>(_subCommands);
    }

    @Override
    public final void registerSubCommand(final ApplicationCommand command) {
        _subCommands.add(command);
    }

    @Override
    protected final void onSlashCommandTriggered(SlashCommandCreateEvent event) {
        // NOOP
    }
}
