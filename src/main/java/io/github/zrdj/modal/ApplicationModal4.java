package io.github.zrdj.modal;

import io.github.zrdj.ApplicationModal;
import org.javacord.api.entity.message.component.TextInput;
import org.javacord.api.event.interaction.ModalSubmitEvent;

public interface ApplicationModal4 extends ApplicationModal {
    TextInput slot1();
    TextInput slot2();
    TextInput slot3();
    TextInput slot4();

    void onInteraction(final ModalSubmitEvent interaction, final String value1, final String value2, final String value3, final String value4);
}
