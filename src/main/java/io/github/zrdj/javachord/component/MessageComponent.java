package io.github.zrdj.javachord.component;

import org.javacord.api.entity.message.component.LowLevelComponent;

public interface MessageComponent {
    LowLevelComponent component();

    void enable();

    void disabled();
}
