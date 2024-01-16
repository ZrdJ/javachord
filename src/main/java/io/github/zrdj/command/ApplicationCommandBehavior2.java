package io.github.zrdj.command;

import io.github.zrdj.ApplicationCommandOption;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.interaction.SlashCommandInteraction;

import java.util.List;

public abstract class ApplicationCommandBehavior2<O1,O2> extends ApplicationCommandBehavior {
    protected final ApplicationCommandOption<O1> _option1;
    protected final ApplicationCommandOption<O2> _option2;

    protected ApplicationCommandBehavior2(
            final String name,
            final String description,
            final ApplicationCommandGroupBehavior parentCommand,
            final ApplicationCommandOption<O1> option1,
            final ApplicationCommandOption<O2> option2
    ) {
        super(name, description, parentCommand, List.of(option1, option2));
        _option1 = option1;
        _option2 = option2;
    }

    protected ApplicationCommandBehavior2(
            final String name,
            final String description,
            final ApplicationCommandOption<O1> option1,
            final ApplicationCommandOption<O2> option2
    ) {
        super(name, description, List.of(option1, option2));
        _option1 = option1;
        _option2 = option2;
    }

    protected abstract void onInteraction(final SlashCommandInteraction interaction, final O1 o1, final O2 o2);
    @Override
    protected final void onSlashCommandTriggered(final SlashCommandCreateEvent event) {
        onInteraction(
                event.getSlashCommandInteraction(),
                _option1.toOptionValue(event.getSlashCommandInteraction()),
                _option2.toOptionValue(event.getSlashCommandInteraction())
        );
    }
}
