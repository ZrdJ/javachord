package io.github.zrdj.modal.option;

import org.javacord.api.entity.message.component.TextInputBuilder;

public class ModalTextInputOptionalOption extends ModalOptionalOptionBehavior<String> {
    public ModalTextInputOptionalOption(final TextInputBuilder componentBuilder) {
        super(componentBuilder, (id, interaction) -> interaction.getTextInputValueByCustomId(id));
    }
}
