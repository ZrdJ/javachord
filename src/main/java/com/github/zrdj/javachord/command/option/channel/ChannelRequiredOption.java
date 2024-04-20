package com.github.zrdj.javachord.command.option.channel;

import com.github.zrdj.javachord.command.option.RequiredOption;
import org.javacord.api.entity.channel.ChannelType;
import org.javacord.api.entity.channel.ServerChannel;
import org.javacord.api.interaction.SlashCommandOptionBuilder;
import org.javacord.api.interaction.SlashCommandOptionType;

import java.util.List;

public class ChannelRequiredOption extends RequiredOption<ServerChannel> {
    private final List<ChannelType> _channelTypes;

    public ChannelRequiredOption(final String name, final String description, final List<ChannelType> channelTypes) {
        super(name, description, SlashCommandOptionType.CHANNEL, (n, e) -> e.getArgumentChannelValueByName(n).orElseThrow());
        _channelTypes = channelTypes;
    }

    @Override
    protected final SlashCommandOptionBuilder buildSlashCommandOption(final SlashCommandOptionBuilder builder) {
        return builder.setChannelTypes(_channelTypes);
    }
}
