package io.github.zrdj.javachord.component;

import io.github.zrdj.javachord.Javachord;
import io.github.zrdj.javachord.MessageComponent;
import org.javacord.api.event.interaction.MessageComponentCreateEvent;
import org.javacord.api.interaction.MessageComponentInteraction;
import org.javacord.api.listener.interaction.MessageComponentCreateListener;

abstract class MessageComponentBehavior implements MessageComponent, MessageComponentCreateListener {
    protected final String _identifier;

    public MessageComponentBehavior(final String identifier) {
        _identifier = identifier;
        Javachord.Instance.Get.addListener(this);
    }

    @Override
    public final void onComponentCreate(final MessageComponentCreateEvent event) {
        event.getMessageComponentInteractionWithCustomId(_identifier)
                .ifPresent(this::onInteraction);
    }

    protected abstract void onInteraction(final MessageComponentInteraction event);
}
