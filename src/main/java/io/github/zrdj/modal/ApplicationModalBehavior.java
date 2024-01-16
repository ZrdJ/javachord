package io.github.zrdj.modal;

import io.github.zrdj.ApplicationModal;
import org.javacord.api.event.interaction.ModalSubmitEvent;
import org.javacord.api.listener.interaction.ModalSubmitListener;

abstract class ApplicationModalBehavior implements ApplicationModal, ModalSubmitListener {
    private final String _identifier;
    private final String _name;

    ApplicationModalBehavior(String identifier, String name) {
        _identifier = identifier;
        _name = name;
    }

    @Override
    public void onModalSubmit(ModalSubmitEvent event) {
        if (!event.getModalInteraction().getCustomId().equalsIgnoreCase(_identifier)) {
            return;
        }
        onModalInteractionTriggered(event);
    }

    protected abstract void onModalInteractionTriggered(ModalSubmitEvent event);

    @Override
    public String identifier() {
        return _identifier;
    }

    @Override
    public String getName() {
        return _name;
    }
}
