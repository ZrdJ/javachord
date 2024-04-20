package com.github.zrdj.javachord.modal;

import org.javacord.api.event.interaction.ModalSubmitEvent;

import java.util.List;

public abstract class ApplicationModalBehavior5<E> extends ApplicationModalBehavior {
    protected final ApplicationModalOption<E> _option1;
    protected final ApplicationModalOption<E> _option2;
    protected final ApplicationModalOption<E> _option3;
    protected final ApplicationModalOption<E> _option4;
    protected final ApplicationModalOption<E> _option5;

    protected ApplicationModalBehavior5(
            final String identifier,
            final String name,
            final ApplicationModalOption<E> option1,
            final ApplicationModalOption<E> option2,
            final ApplicationModalOption<E> option3,
            final ApplicationModalOption<E> option4,
            final ApplicationModalOption<E> option5
    ) {
        super(identifier, name, List.of(option1, option2, option3, option4));
        _option1 = option1;
        _option2 = option2;
        _option3 = option3;
        _option4 = option4;
        _option5 = option5;
    }

    @Override
    protected final void onModalInteractionTriggered(final ModalSubmitEvent event) {
        onModalInteraction(
                event,
                _option1.toOptionValue(event.getModalInteraction()),
                _option2.toOptionValue(event.getModalInteraction()),
                _option3.toOptionValue(event.getModalInteraction()),
                _option4.toOptionValue(event.getModalInteraction()),
                _option5.toOptionValue(event.getModalInteraction())
        );
    }

    protected abstract void onModalInteraction(
            final ModalSubmitEvent interaction,
            final E value1,
            final E value2,
            final E value3,
            final E value4,
            final E value5
    );
}
