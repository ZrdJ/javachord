package io.github.zrdj.option;

import org.javacord.api.entity.channel.ChannelType;
import org.javacord.api.entity.channel.ServerChannel;
import org.javacord.api.interaction.SlashCommandOption;
import org.javacord.api.interaction.SlashCommandOptionType;

import java.util.List;
import java.util.Optional;

public interface ChannelOptionalOption extends OptionalOption<ServerChannel> {
    List<ChannelType> channelTypes();

    final class Channel extends Abstract<Optional<ServerChannel>> implements ChannelOptionalOption {
        private final List<ChannelType> _channelTypes;

        public Channel(final String name, final String description, final List<ChannelType> _channelTypes) {
            super(name, description, SlashCommandOptionType.CHANNEL, (n, e) -> e.getArgumentChannelValueByName(n));
            this._channelTypes = _channelTypes;
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
                    false,
                    _channelTypes
            );
        }
    }
}
