package io.github.zrdj.javachord.component;

import org.javacord.api.entity.Mentionable;
import org.javacord.api.entity.message.component.ComponentType;
import org.javacord.api.entity.message.component.LowLevelComponent;
import org.javacord.api.entity.message.component.SelectMenuBuilder;
import org.javacord.api.interaction.MessageComponentInteraction;
import org.javacord.api.interaction.SelectMenuInteraction;

import java.util.List;

public abstract class SelectMenuMentionableComponent extends MessageComponentBehavior {
    public SelectMenuMentionableComponent(final String identifier) {
        super(identifier);
    }

    @Override
    protected void onInteraction(final MessageComponentInteraction event) {
        var selectMenuInteraction = event.asSelectMenuInteraction().get();
        onMentionableSelected(selectMenuInteraction, selectMenuInteraction.getSelectedMentionables());
    }

    protected abstract void onMentionableSelected(final SelectMenuInteraction interaction, final List<Mentionable> mentionables);

    @Override
    public LowLevelComponent component() {
        return new SelectMenuBuilder(ComponentType.SELECT_MENU_MENTIONABLE, _identifier)
                .build();
    }
}
