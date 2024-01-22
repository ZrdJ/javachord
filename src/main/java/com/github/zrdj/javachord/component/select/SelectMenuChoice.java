package com.github.zrdj.javachord.component.select;

import org.javacord.api.entity.message.component.SelectMenuOption;

public interface SelectMenuChoice<T> {
    String name();

    String description();

    String value();

    boolean isDefault();

    T valueObject();

    default SelectMenuOption toSelectMenuOption() {
        return SelectMenuOption.create(name(), value(), description(), isDefault());
    }
}
