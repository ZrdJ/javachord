package io.github.zrdj.modal;

import io.github.zrdj.ApplicationModal;
import org.javacord.api.entity.message.component.TextInput;
import org.javacord.api.event.interaction.ModalSubmitEvent;

abstract class ApplicationModal1 implements ApplicationModal {

    abstract void onInteraction(final ModalSubmitEvent interaction, final String value1);
}
