package io.github.zrdj;

import org.javacord.api.entity.message.component.LowLevelComponent;
import org.javacord.api.interaction.ModalInteraction;

public interface ApplicationModalOption<Type> {
    Type toOptionValue(final ModalInteraction interaction);
    LowLevelComponent component();
}
