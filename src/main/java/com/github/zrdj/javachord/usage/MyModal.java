package com.github.zrdj.javachord.usage;

import com.github.zrdj.javachord.Javachord;
import com.github.zrdj.javachord.modal.ApplicationModalBehavior1;
import org.javacord.api.event.interaction.ModalSubmitEvent;

public class MyModal extends ApplicationModalBehavior1<String> {
    protected MyModal() {
        super(
                "modal"
                , "my modal"
                , Javachord.Modal.Option.Required.textOption("name", "modal-text", false)
        );
    }

    @Override
    protected void onModalInteraction(ModalSubmitEvent interaction, String value1) {
        System.out.println("modal value is " + value1);
        interaction.getModalInteraction().createImmediateResponder().setContent("Thanks!").respond();
    }
}
