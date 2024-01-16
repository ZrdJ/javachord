package io.github.zrdj;

import org.javacord.api.interaction.ModalInteraction;

public interface ApplicationModalOption<Type> {
    Type toOptionValue(final ModalInteraction interaction);
}
