package io.github.zrdj.javachord.modal.option;

import org.javacord.api.entity.message.component.TextInputBuilder;

public class ApplicationModalTextInputRequiredOption extends ApplicationModalRequiredOptionBehavior<String> {
    public ApplicationModalTextInputRequiredOption(final TextInputBuilder componentBuilder) {
        super(componentBuilder, (id, interaction) -> interaction.getTextInputValueByCustomId(id).orElseThrow());
    }
}
