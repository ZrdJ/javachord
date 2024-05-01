package com.github.zrdj.javachord.component;

import com.github.zrdj.javachord.Javachord;
import org.javacord.api.event.interaction.MessageComponentCreateEvent;
import org.javacord.api.interaction.MessageComponentInteraction;
import org.javacord.api.listener.interaction.MessageComponentCreateListener;

import java.util.function.Predicate;

public abstract class MessageComponentBehavior implements MessageComponent, MessageComponentCreateListener {
    private final Predicate<String> _identify;
    protected final String _identifier;
    protected boolean _disabled = false;

    public MessageComponentBehavior(final String identifier, final Predicate<String> identify) {
        _identify = identify;
        _identifier = identifier;
        Javachord.Constraint.ensureIdentifier(identifier);
        Javachord.Instance.Get.addListener(this);
    }

    public MessageComponentBehavior(final String identifier) {
        this(identifier, identifier::equalsIgnoreCase);
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
        var customId = event.getMessageComponentInteraction().getCustomId();
        if (_identify.test(customId)) {
            onInteraction(event.getMessageComponentInteraction());
        }
    }

    protected abstract void onInteraction(final MessageComponentInteraction event);
}
