package io.github.zrdj.javachord.modal.option;

import org.javacord.api.entity.message.component.TextInputBuilder;
import org.javacord.api.interaction.ModalInteraction;

import java.util.Optional;
import java.util.function.BiFunction;

abstract class ApplicationModalOptionalOptionBehavior<Type> extends ApplicationModalOptionBehavior<Optional<Type>> {
    ApplicationModalOptionalOptionBehavior(final TextInputBuilder componentBuilder, final BiFunction<String, ModalInteraction, Optional<Type>> mapper) {
        super(componentBuilder.setRequired(false).build(), mapper);
    }
}
