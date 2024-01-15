package io.github.zrdj.command;

import io.github.zrdj.ApplicationCommand;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;

import java.util.Collections;

public interface ApplicationCommandGroup extends ApplicationCommand {
    abstract class Of extends ApplicationCommand.Abstract implements ApplicationCommandGroup {
        protected Of(
                final String name,
                final String description
        ) {
            super(name, description, Collections.emptyList());
        }

        @Override
        protected final void onSlashCommandTriggered(final SlashCommandCreateEvent event) {
            // NOOP as this command is only used as parent command without execution
        }
    }
}
