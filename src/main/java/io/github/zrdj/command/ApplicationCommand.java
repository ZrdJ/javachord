package io.github.zrdj.command;

import org.javacord.api.event.interaction.SlashCommandCreateEvent;

import java.util.Collections;

public interface ApplicationCommand extends ApplicationCommandBase {
    abstract class ParentCommand extends ApplicationCommandBase.Abstract implements ApplicationCommand {
        protected ParentCommand(
                final String name,
                final String description
        ) {
            super(name, description, Collections.emptyList());
        }

        @Override
        protected void onSlashCommandTriggered(final SlashCommandCreateEvent event) {
            // NOOP
        }
    }
}
