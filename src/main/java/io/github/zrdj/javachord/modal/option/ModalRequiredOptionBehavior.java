package io.github.zrdj.javachord.modal.option;

import org.javacord.api.entity.message.component.TextInputBuilder;
import org.javacord.api.interaction.ModalInteraction;

import java.util.function.BiFunction;

public abstract class ModalRequiredOptionBehavior<Type> extends ModalOptionBehavior<Type> {
    public ModalRequiredOptionBehavior(final TextInputBuilder componentBuilder, final BiFunction<String, ModalInteraction, Type> mapper) {
        super(componentBuilder.setRequired(true).build(), mapper);
    }
}
