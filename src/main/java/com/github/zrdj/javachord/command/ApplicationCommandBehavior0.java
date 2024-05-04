package com.github.zrdj.javachord.command;

import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.interaction.SlashCommandInteraction;

import java.util.List;

public abstract class ApplicationCommandBehavior0 extends ApplicationCommandBehavior {

    protected ApplicationCommandBehavior0(
            final String name,
            final String description,
            final ApplicationCommandGroupBehavior parentCommand
    ) {
        super(name, description, parentCommand, List.of());
    }

    protected ApplicationCommandBehavior0(
            final String name,
            final String description
    ) {
        super(name, description, List.of());
    }

    protected abstract void onInteraction(final SlashCommandInteraction interaction);
    @Override
    protected final void onSlashCommandTriggered(final SlashCommandCreateEvent event) {
        onInteraction(
                event.getSlashCommandInteraction()
        );
    }
}
