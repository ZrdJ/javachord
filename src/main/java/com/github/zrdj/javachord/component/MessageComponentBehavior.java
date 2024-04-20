package com.github.zrdj.javachord.component;

import com.github.zrdj.javachord.Javachord;
import org.javacord.api.event.interaction.MessageComponentCreateEvent;
import org.javacord.api.interaction.MessageComponentInteraction;
import org.javacord.api.listener.interaction.MessageComponentCreateListener;

public abstract class MessageComponentBehavior implements MessageComponent, MessageComponentCreateListener {
    protected final String _identifier;
    protected boolean _disabled = false;

    public MessageComponentBehavior(final String identifier) {
        Javachord.Constraint.ensureIdentifier(identifier);
        _identifier = identifier;
        Javachord.Instance.Get.addListener(this);
    }

    @Override
    public final void enable() {
        _disabled = false;
    }

    @Override
    public final void disabled() {
        _disabled = true;
    }

    @Override
    public final void onComponentCreate(final MessageComponentCreateEvent event) {
        event.getMessageComponentInteractionWithCustomId(_identifier)
                .ifPresent(this::onInteraction);
    }

    protected abstract void onInteraction(final MessageComponentInteraction event);
}
