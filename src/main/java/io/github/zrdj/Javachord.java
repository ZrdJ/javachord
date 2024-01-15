package io.github.zrdj;

import io.github.zrdj.option.ChannelOptionalOption;
import io.github.zrdj.option.ChannelRequiredOption;
import io.github.zrdj.option.OptionalOption;
import io.github.zrdj.option.RequiredOption;
import org.javacord.api.entity.Mentionable;
import org.javacord.api.entity.channel.ChannelType;
import org.javacord.api.entity.channel.ServerChannel;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.user.User;
import org.javacord.api.interaction.SlashCommandOptionType;

import java.util.List;

public interface Javachord {
    interface Option {
        static RequiredOption<ServerChannel> channelRequired(final String name, final String description, final List<ChannelType> channelTypes) {
            return new ChannelRequiredOption.Channel(name, description, channelTypes);
        }

        static RequiredOption<User> userRequired(final String name, final String description) {
            return new RequiredOption.Primitive<>(name, description, SlashCommandOptionType.USER, (n, e) -> e.getArgumentUserValueByName(n).orElseThrow());
        }

        static RequiredOption<Mentionable> mentionableRequired(final String name, final String description) {
            return new RequiredOption.Primitive<>(name, description, SlashCommandOptionType.MENTIONABLE, (n, e) -> e.getArgumentMentionableValueByName(n).orElseThrow());
        }

        static RequiredOption<Role> roleRequired(final String name, final String description) {
            return new RequiredOption.Primitive<>(name, description, SlashCommandOptionType.ROLE, (n, e) -> e.getArgumentRoleValueByName(n).orElseThrow());
        }

        static RequiredOption<String> stringRequired(final String name, final String description) {
            return new RequiredOption.Primitive<>(name, description, SlashCommandOptionType.STRING, (n, e) -> e.getArgumentStringValueByName(n).orElseThrow());
        }

        static RequiredOption<String> booleanRequired(final String name, final String description) {
            return new RequiredOption.Primitive<>(name, description, SlashCommandOptionType.BOOLEAN, (n, e) -> e.getArgumentStringValueByName(n).orElseThrow());
        }

        static RequiredOption<String> longRequired(final String name, final String description) {
            return new RequiredOption.Primitive<>(name, description, SlashCommandOptionType.LONG, (n, e) -> e.getArgumentStringValueByName(n).orElseThrow());
        }

        static RequiredOption<String> decimalRequired(final String name, final String description) {
            return new RequiredOption.Primitive<>(name, description, SlashCommandOptionType.DECIMAL, (n, e) -> e.getArgumentStringValueByName(n).orElseThrow());
        }

        static OptionalOption<String> stringOptional(final String name, final String description) {
            return new OptionalOption.Primitive<>(name, description, SlashCommandOptionType.STRING, (n, e) -> e.getArgumentStringValueByName(n));
        }

        static OptionalOption<String> booleanOptional(final String name, final String description) {
            return new OptionalOption.Primitive<>(name, description, SlashCommandOptionType.BOOLEAN, (n, e) -> e.getArgumentStringValueByName(n));
        }

        static OptionalOption<String> longOptional(final String name, final String description) {
            return new OptionalOption.Primitive<>(name, description, SlashCommandOptionType.LONG, (n, e) -> e.getArgumentStringValueByName(n));
        }

        static OptionalOption<String> decimalOptional(final String name, final String description) {
            return new OptionalOption.Primitive<>(name, description, SlashCommandOptionType.DECIMAL, (n, e) -> e.getArgumentStringValueByName(n));
        }

        static OptionalOption<ServerChannel> channelOptional(final String name, final String description, final List<ChannelType> channelTypes) {
            return new ChannelOptionalOption.Channel(name, description, channelTypes);
        }

        static OptionalOption<User> userOptional(final String name, final String description) {
            return new OptionalOption.Primitive<>(name, description, SlashCommandOptionType.USER, (n, e) -> e.getArgumentUserValueByName(n));
        }

        static OptionalOption<Mentionable> mentionableOptional(final String name, final String description) {
            return new OptionalOption.Primitive<>(name, description, SlashCommandOptionType.MENTIONABLE, (n, e) -> e.getArgumentMentionableValueByName(n));
        }

        static OptionalOption<Role> roleOptional(final String name, final String description) {
            return new OptionalOption.Primitive<>(name, description, SlashCommandOptionType.ROLE, (n, e) -> e.getArgumentRoleValueByName(n));
        }
    }
}
