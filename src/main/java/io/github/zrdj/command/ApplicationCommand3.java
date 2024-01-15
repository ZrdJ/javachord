package io.github.zrdj.command;

import io.github.zrdj.ApplicationCommand;
import io.github.zrdj.ApplicationCommandOption;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.interaction.SlashCommandInteraction;

import java.util.List;

public interface ApplicationCommand3<O1, O2, O3> extends ApplicationCommand {
    ApplicationCommandOption<O1> option1();

    ApplicationCommandOption<O2> option2();

    ApplicationCommandOption<O3> option3();

    void onInteraction(final SlashCommandInteraction interaction, final O1 o1, final O2 o2, final O3 o3);

    abstract class Of<O1, O2, O3> extends ApplicationCommand.Abstract implements ApplicationCommand3<O1, O2, O3> {
        protected final ApplicationCommandOption<O1> _option1;
        protected final ApplicationCommandOption<O2> _option2;
        protected final ApplicationCommandOption<O3> _option3;

        protected Of(
                final String name,
                final String description,
                final ApplicationCommandGroup parentCommand,
                final ApplicationCommandOption<O1> option1,
                final ApplicationCommandOption<O2> option2,
                final ApplicationCommandOption<O3> option3
        ) {
            super(name, description, parentCommand, List.of(option1, option2, option3));
            _option1 = option1;
            _option2 = option2;
            _option3 = option3;
        }

        protected Of(
                final String name,
                final String description,
                final ApplicationCommandOption<O1> option1,
                final ApplicationCommandOption<O2> option2,
                final ApplicationCommandOption<O3> option3
        ) {
            super(name, description, List.of(option1, option2, option3));
            _option1 = option1;
            _option2 = option2;
            _option3 = option3;
        }

        @Override
        protected final void onSlashCommandTriggered(final SlashCommandCreateEvent event) {
            onInteraction(
                    event.getSlashCommandInteraction(),
                    _option1.toOptionValue(event.getSlashCommandInteraction()),
                    _option2.toOptionValue(event.getSlashCommandInteraction()),
                    _option3.toOptionValue(event.getSlashCommandInteraction())
            );
        }

        @Override
        public final ApplicationCommandOption<O3> option3() {
            return _option3;
        }

        @Override
        public final ApplicationCommandOption<O2> option2() {
            return _option2;
        }

        @Override
        public final ApplicationCommandOption<O1> option1() {
            return _option1;
        }
    }
}
