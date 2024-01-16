package io.github.zrdj.modal;

import io.github.zrdj.ApplicationModalOption;
import org.javacord.api.event.interaction.ModalSubmitEvent;

public abstract class ApplicationModal1<E> extends ApplicationModalBehavior {
    protected final ApplicationModalOption<E> _option1;

    protected ApplicationModal1(final String identifier, final String name, final ApplicationModalOption<E> option1) {
        super(identifier, name);
        _option1 = option1;
    }

    @Override
    protected final void onModalInteractionTriggered(final ModalSubmitEvent event) {
        onModalInteraction(event, _option1.toOptionValue(event.getModalInteraction()));
    }

    abstract void onModalInteraction(final ModalSubmitEvent interaction, final E value1);
}
