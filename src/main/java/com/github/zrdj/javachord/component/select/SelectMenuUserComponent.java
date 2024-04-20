package com.github.zrdj.javachord.component.select;

import com.github.zrdj.javachord.component.MessageComponentBehavior;
import org.javacord.api.entity.message.component.ComponentType;
import org.javacord.api.entity.message.component.LowLevelComponent;
import org.javacord.api.entity.message.component.SelectMenuBuilder;
import org.javacord.api.entity.user.User;
import org.javacord.api.interaction.MessageComponentInteraction;
import org.javacord.api.interaction.SelectMenuInteraction;

import java.util.List;

public abstract class SelectMenuUserComponent extends MessageComponentBehavior {
    public SelectMenuUserComponent(final String identifier) {
        super(identifier);
    }

    @Override
    protected void onInteraction(final MessageComponentInteraction event) {
        var selectMenuInteraction = event.asSelectMenuInteraction().get();
        onUsersSelected(selectMenuInteraction, selectMenuInteraction.getSelectedUsers());
    }

    protected abstract void onUsersSelected(final SelectMenuInteraction interaction, final List<User> users);

    @Override
    public final LowLevelComponent component() {
        return new SelectMenuBuilder(ComponentType.SELECT_MENU_USER, _identifier)
                .setDisabled(_disabled)
                .build();
    }
}
