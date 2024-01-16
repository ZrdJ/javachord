package io.github.zrdj.option;

import org.javacord.api.entity.channel.ChannelType;
import org.javacord.api.entity.channel.ServerChannel;
import org.javacord.api.interaction.SlashCommandOption;
import org.javacord.api.interaction.SlashCommandOptionType;

import java.util.List;

public interface ChannelRequiredOption extends RequiredOption<ServerChannel> {
    List<ChannelType> channelTypes();

    final class Channel extends Abstract<ServerChannel> implements ChannelRequiredOption {
        private final List<ChannelType> _channelTypes;

        public Channel(final String name, final String description, final List<ChannelType> channelTypes) {
            super(name, description, SlashCommandOptionType.CHANNEL, (n, e) -> e.getArgumentChannelValueByName(n).orElseThrow());
            _channelTypes = channelTypes;
        }

        @Override
        public List<ChannelType> channelTypes() {
            return _channelTypes;
        }

        @Override
        public SlashCommandOption toSlashCommandOption() {
            return SlashCommandOption.createChannelOption(
                    getName(),
                    description(),
                    true,
                    _channelTypes
            );
        }
    }
}
