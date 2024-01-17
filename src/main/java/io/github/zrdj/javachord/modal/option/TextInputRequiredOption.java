package io.github.zrdj.javachord.modal.option;

import org.javacord.api.entity.message.component.TextInputBuilder;

public class TextInputRequiredOption extends RequiredOption<String> {
    public TextInputRequiredOption(final TextInputBuilder componentBuilder) {
        super(componentBuilder, (id, interaction) -> interaction.getTextInputValueByCustomId(id).orElseThrow());
    }
}
