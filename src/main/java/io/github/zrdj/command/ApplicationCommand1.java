package io.github.zrdj.command;

import io.github.zrdj.option.ApplicationCommandOption;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.interaction.SlashCommandInteraction;

import java.util.List;

public interface ApplicationCommand1<O1> extends ApplicationCommandBase {
    ApplicationCommandOption<O1> option1();

    void onInteraction(final SlashCommandInteraction interaction, final O1 o1);

    abstract class SubCommand<O1> extends ApplicationCommandBase.Abstract implements ApplicationCommand1<O1> {
        protected final ApplicationCommandOption<O1> _option1;

        protected SubCommand(
                final String name,
                final String description,
                final ApplicationCommand parentCommand,
                final ApplicationCommandOption<O1> option1
        ) {
            super(name, description, parentCommand, List.of(option1));
            _option1 = option1;
        }

        protected SubCommand(
                final String name,
                final String description,
                final ApplicationCommandOption<O1> option1
        ) {
            super(name, description, List.of(option1));
            _option1 = option1;
        }

        @Override
        protected final void onSlashCommandTriggered(final SlashCommandCreateEvent event) {
            onInteraction(
                    event.getSlashCommandInteraction(),
                    _option1.toOptionValue(event.getSlashCommandInteraction())
            );
        }

        @Override
        public ApplicationCommandOption<O1> option1() {
            return _option1;
        }
    }
}
