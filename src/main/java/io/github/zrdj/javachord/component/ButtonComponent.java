package io.github.zrdj.javachord.component;

import org.javacord.api.entity.message.component.ButtonBuilder;
import org.javacord.api.entity.message.component.LowLevelComponent;
import org.javacord.api.interaction.ButtonInteraction;
import org.javacord.api.interaction.MessageComponentInteraction;

public abstract class ButtonComponent extends MessageComponentBehavior {
    public ButtonComponent(final String identifier, final String label) {
        super(identifier);
    }

    protected abstract ButtonBuilder buildComponent();

    @Override
    protected final void onInteraction(final MessageComponentInteraction event) {
        onButtonClicked(event.asButtonInteraction().get());
    }

    protected abstract void onButtonClicked(final ButtonInteraction event);

    @Override
    public final LowLevelComponent component() {
        return buildComponent()
                .setCustomId(_identifier)
                .build();
    }
}
