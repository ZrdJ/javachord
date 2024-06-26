package com.github.zrdj.javachord.modal.option;

import com.github.zrdj.javachord.modal.ApplicationModalOption;
import org.javacord.api.entity.message.component.LowLevelComponent;
import org.javacord.api.entity.message.component.TextInput;
import org.javacord.api.interaction.ModalInteraction;

import java.util.function.BiFunction;

abstract class Option<Type> implements ApplicationModalOption<Type> {
    protected final BiFunction<String, ModalInteraction, Type> _mapper;
    protected final LowLevelComponent _component;
    protected final String _identifier;

    Option(final TextInput component, final BiFunction<String, ModalInteraction, Type> mapper) {
        _identifier = component.getCustomId();
        _component = component;
        _mapper = mapper;
    }

    @Override
    public LowLevelComponent component() {
        return _component;
    }

    @Override
    public Type toOptionValue(ModalInteraction interaction) {
        return _mapper.apply(_identifier, interaction);
    }
}
