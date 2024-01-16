package io.github.zrdj.javachord.command.option;

import org.javacord.api.entity.channel.ChannelType;
import org.javacord.api.entity.channel.ServerChannel;
import org.javacord.api.interaction.SlashCommandOption;
import org.javacord.api.interaction.SlashCommandOptionType;

import java.util.List;

public final class ApplicationCommandOptionChannelOptional extends ApplicationCommandOptionOptionalBehavior<ServerChannel> {
    private final List<ChannelType> _channelTypes;

    public ApplicationCommandOptionChannelOptional(final String name, final String description, final List<ChannelType> _channelTypes) {
        super(name, description, SlashCommandOptionType.CHANNEL, (n, e) -> e.getArgumentChannelValueByName(n));
        this._channelTypes = _channelTypes;
    }
    @Override
    public SlashCommandOption toSlashCommandOption() {
        return SlashCommandOption.createChannelOption(
                _name,
                _description,
                false,
                _channelTypes
        );
    }
}
