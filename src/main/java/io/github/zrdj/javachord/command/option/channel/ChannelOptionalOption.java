package io.github.zrdj.javachord.command.option.channel;

import io.github.zrdj.javachord.command.option.OptionalOption;
import org.javacord.api.entity.channel.ChannelType;
import org.javacord.api.entity.channel.ServerChannel;
import org.javacord.api.interaction.SlashCommandOption;
import org.javacord.api.interaction.SlashCommandOptionBuilder;
import org.javacord.api.interaction.SlashCommandOptionType;

import java.util.List;

public class ChannelOptionalOption extends OptionalOption<ServerChannel> {
    private final List<ChannelType> _channelTypes;

    public ChannelOptionalOption(final String name, final String description, final List<ChannelType> _channelTypes) {
        super(name, description, SlashCommandOptionType.CHANNEL, (n, e) -> e.getArgumentChannelValueByName(n));
        this._channelTypes = _channelTypes;
    }

    @Override
    protected final SlashCommandOptionBuilder buildSlashCommandOption(final SlashCommandOptionBuilder builder) {
        return builder.setChannelTypes(_channelTypes);
    }
}
