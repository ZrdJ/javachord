package io.github.zrdj.javachord.component.button;

import io.github.zrdj.javachord.component.MessageComponentBehavior;
import org.javacord.api.entity.message.component.ButtonBuilder;
import org.javacord.api.entity.message.component.LowLevelComponent;
import org.javacord.api.interaction.ButtonInteraction;
import org.javacord.api.interaction.MessageComponentInteraction;

public abstract class ButtonComponent extends MessageComponentBehavior {
    private String _label;

    public ButtonComponent(final String identifier, final String label) {
        super(identifier);
        this._label = label;
    }

    protected abstract ButtonBuilder configureComponent(final ButtonBuilder builder);

    @Override
    protected final void onInteraction(final MessageComponentInteraction event) {
        onButtonClicked(event.asButtonInteraction().get());
    }

    public void updateLabel(final String label) {
        _label = label;
    }
    protected abstract void onButtonClicked(final ButtonInteraction event);

    @Override
    public final LowLevelComponent component() {
        return configureComponent(new ButtonBuilder())
                .setCustomId(_identifier)
                .setLabel(_label)
                .setDisabled(_disabled)
                .build();
    }
}
