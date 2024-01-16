package io.github.zrdj.modal.option;

import org.javacord.api.entity.message.component.TextInputBuilder;

public class ModalTextInputRequiredOption extends ModalRequiredOptionBehavior<String> {
    public ModalTextInputRequiredOption(final TextInputBuilder componentBuilder) {
        super(componentBuilder, (id, interaction) -> interaction.getTextInputValueByCustomId(id).orElseThrow());
    }
}
