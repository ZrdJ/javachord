package com.github.zrdj.javachord.modal;

import org.javacord.api.event.interaction.ModalSubmitEvent;

import java.util.List;

public abstract class ApplicationModalBehavior2<E> extends ApplicationModalBehavior {
    protected final ApplicationModalOption<E> _option1;
    protected final ApplicationModalOption<E> _option2;

    protected ApplicationModalBehavior2(
            final String identifier,
            final String name,
            final ApplicationModalOption<E> option1,
            final ApplicationModalOption<E> option2
    ) {
        super(identifier, name, List.of(option1, option2));
        _option1 = option1;
        _option2 = option2;
    }

    @Override
    protected final void onModalInteractionTriggered(final ModalSubmitEvent event) {
        onModalInteraction(event, _option1.toOptionValue(event.getModalInteraction()), _option2.toOptionValue(event.getModalInteraction()));
    }

    protected abstract void onModalInteraction(final ModalSubmitEvent interaction, final E value1, final E value2);
}
