package io.github.zrdj.javachord.usage;

import io.github.zrdj.javachord.Javachord;
import io.github.zrdj.javachord.modal.ApplicationModal1;
import org.javacord.api.event.interaction.ModalSubmitEvent;

public class MyModal extends ApplicationModal1<String> {

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
