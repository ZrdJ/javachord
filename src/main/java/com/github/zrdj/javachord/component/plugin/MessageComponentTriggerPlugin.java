package com.github.zrdj.javachord.component.plugin;

import org.javacord.api.interaction.MessageComponentInteraction;

public interface MessageComponentTriggerPlugin {
    boolean triggered(final MessageComponentInteraction interaction);

    static MessageComponentTriggerPlugin defaultPlugin(final String customId) {
        return new CustomIdTrigger(customId);
    }

    final class CustomIdTrigger implements MessageComponentTriggerPlugin {
        private final String _customId;

        public CustomIdTrigger(final String customId) {
            _customId = customId;
        }

        @Override
        public boolean triggered(final MessageComponentInteraction interaction) {
            return interaction.getCustomId().equalsIgnoreCase(_customId);
        }
    }
}
