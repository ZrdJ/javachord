package io.github.zrdj.javachord.command;

import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.interaction.SlashCommandInteraction;

import java.util.List;

public abstract class ApplicationCommandBehavior1<O1> extends ApplicationCommandBehavior {
    protected final ApplicationCommandOption<O1> _option1;

    protected ApplicationCommandBehavior1(
            final String name,
            final String description,
            final ApplicationCommandGroupBehavior parentCommand,
            final ApplicationCommandOption<O1> option1
    ) {
        super(name, description, parentCommand, List.of(option1));
        _option1 = option1;
    }

    protected ApplicationCommandBehavior1(
            final String name,
            final String description,
            final ApplicationCommandOption<O1> option1
    ) {
        super(name, description, List.of(option1));
        _option1 = option1;
    }

    protected abstract void onInteraction(final SlashCommandInteraction interaction, final O1 o1);
    @Override
    protected final void onSlashCommandTriggered(final SlashCommandCreateEvent event) {
        onInteraction(
                event.getSlashCommandInteraction(),
                _option1.toOptionValue(event.getSlashCommandInteraction())
        );
    }
}
