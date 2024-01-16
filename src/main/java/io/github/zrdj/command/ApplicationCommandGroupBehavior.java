package io.github.zrdj.command;

import io.github.zrdj.ApplicationCommandGroup;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;

import java.util.Collections;

public abstract class ApplicationCommandGroupBehavior extends ApplicationCommandBehavior implements ApplicationCommandGroup {
    protected ApplicationCommandGroupBehavior(
            final String name,
            final String description
    ) {
        super(name, description, Collections.emptyList());
    }

    @Override
    protected final void onSlashCommandTriggered(SlashCommandCreateEvent event) {
        // NOOP
    }
}
