package io.github.zrdj.javachord.modal.option;

import org.javacord.api.entity.message.component.TextInputBuilder;

public class ApplicationModalTextInputOptionalOption extends ApplicationModalOptionalOptionBehavior<String> {
    public ApplicationModalTextInputOptionalOption(final TextInputBuilder componentBuilder) {
        super(componentBuilder, (id, interaction) -> interaction.getTextInputValueByCustomId(id));
    }
}
