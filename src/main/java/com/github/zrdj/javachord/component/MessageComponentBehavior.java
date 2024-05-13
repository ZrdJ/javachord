package com.github.zrdj.javachord.component;

import com.github.zrdj.javachord.Javachord;
import com.github.zrdj.javachord.component.plugin.MessageComponentTriggerPlugin;
import com.github.zrdj.javachord.error.JavachordConstraintError;
import org.javacord.api.event.interaction.MessageComponentCreateEvent;
import org.javacord.api.interaction.MessageComponentInteraction;
import org.javacord.api.listener.interaction.MessageComponentCreateListener;

import java.util.Optional;

public abstract class MessageComponentBehavior implements MessageComponent, MessageComponentCreateListener {
    protected final String _identifier;
    protected boolean _disabled = false;
    private MessageComponentTriggerPlugin _triggerPlugin;

    public MessageComponentBehavior(final String identifier) {
        _identifier = identifier;
        Javachord.Constraint.ensureIdentifier(identifier);
        Javachord.Instance.Get.addListener(this);
    }

    protected MessageComponentTriggerPlugin triggerPlugin() {
        return MessageComponentTriggerPlugin.defaultPlugin(_identifier);
    }

    private void ensurePlugins() {
        if (_triggerPlugin != null) {
            return;
        }

        _triggerPlugin = triggerPlugin();

        if (_triggerPlugin == null) {
            throw new JavachordConstraintError("Trigger plugin has not been defined. Did you forget to provide a valid Plugin while overriding triggerPlugin()?");
        }
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
        ensurePlugins();
        if (_triggerPlugin.triggered(event.getMessageComponentInteraction())) {
            onInteraction(event.getMessageComponentInteraction());
        }
    }

    protected abstract void onInteraction(final MessageComponentInteraction event);
}
