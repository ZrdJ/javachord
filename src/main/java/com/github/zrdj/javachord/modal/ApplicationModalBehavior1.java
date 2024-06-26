package com.github.zrdj.javachord.modal;

import org.javacord.api.event.interaction.ModalSubmitEvent;

import java.util.List;

public abstract class ApplicationModalBehavior1<E> extends ApplicationModalBehavior {
    protected final ApplicationModalOption<E> _option1;

    protected ApplicationModalBehavior1(final String identifier, final String name, final ApplicationModalOption<E> option1) {
        super(identifier, name, List.of(option1));
        _option1 = option1;
    }

    @Override
    protected final void onModalInteractionTriggered(final ModalSubmitEvent event) {
        onModalInteraction(event, _option1.toOptionValue(event.getModalInteraction()));
    }

    protected abstract void onModalInteraction(final ModalSubmitEvent interaction, final E value1);
}
