package com.github.zrdj.javachord.component.select;

import com.github.zrdj.javachord.component.MessageComponentBehavior;
import org.javacord.api.entity.message.component.ComponentType;
import org.javacord.api.entity.message.component.LowLevelComponent;
import org.javacord.api.entity.message.component.SelectMenuBuilder;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.interaction.MessageComponentInteraction;
import org.javacord.api.interaction.SelectMenuInteraction;

import java.util.List;

public abstract class SelectMenuRoleComponent extends MessageComponentBehavior {
    public SelectMenuRoleComponent(final String identifier) {
        super(identifier);
    }

    @Override
    protected void onInteraction(final MessageComponentInteraction event) {
        var selectMenuInteraction = event.asSelectMenuInteraction().get();
        onRolesSelected(selectMenuInteraction, selectMenuInteraction.getSelectedRoles());
    }

    protected abstract void onRolesSelected(final SelectMenuInteraction interaction, final List<Role> roles);

    @Override
    public LowLevelComponent component() {
        return new SelectMenuBuilder(ComponentType.SELECT_MENU_ROLE, _identifier)
                .setDisabled(_disabled)
                .build();
    }
}
