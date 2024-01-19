package io.github.zrdj.javachord.component;

import org.javacord.api.entity.channel.ServerChannel;
import org.javacord.api.entity.message.component.ComponentType;
import org.javacord.api.entity.message.component.LowLevelComponent;
import org.javacord.api.entity.message.component.SelectMenuBuilder;
import org.javacord.api.interaction.MessageComponentInteraction;
import org.javacord.api.interaction.SelectMenuInteraction;

import java.util.List;

public abstract class SelectMenuChannelComponent extends MessageComponentBehavior {
    public SelectMenuChannelComponent(final String identifier) {
        super(identifier);
    }

    @Override
    protected void onInteraction(final MessageComponentInteraction event) {
        var selectMenuInteraction = event.asSelectMenuInteraction().get();
        onChannelsSelected(selectMenuInteraction, selectMenuInteraction.getSelectedChannels());
    }

    protected abstract void onChannelsSelected(final SelectMenuInteraction interaction, final List<ServerChannel> channels);

    @Override
    public LowLevelComponent component() {
        return new SelectMenuBuilder(ComponentType.SELECT_MENU_CHANNEL, _identifier)
                .build();
    }
}
