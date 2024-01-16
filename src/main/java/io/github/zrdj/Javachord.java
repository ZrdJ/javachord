package io.github.zrdj;

import io.github.zrdj.command.option.ChannelOptionalOption;
import io.github.zrdj.command.option.ChannelRequiredOption;
import io.github.zrdj.command.option.OptionalOptionBehavior;
import io.github.zrdj.command.option.RequiredOptionBehavior;
import io.github.zrdj.modal.option.ModalTextInputOptionalOption;
import io.github.zrdj.modal.option.ModalTextInputRequiredOption;
import org.javacord.api.entity.Mentionable;
import org.javacord.api.entity.channel.ChannelType;
import org.javacord.api.entity.channel.ServerChannel;
import org.javacord.api.entity.message.component.TextInputBuilder;
import org.javacord.api.entity.message.component.TextInputStyle;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.user.User;
import org.javacord.api.interaction.SlashCommandOptionType;

import java.util.List;

public interface Javachord {
    interface Modal {
        interface Option {
            interface Required {
                static ModalTextInputRequiredOption textOption(final String name, final String identifier, final boolean small) {
                    return new ModalTextInputRequiredOption(new TextInputBuilder(small? TextInputStyle.SHORT : TextInputStyle.PARAGRAPH, identifier, name));
                }
            }

            interface Optional {
                static ModalTextInputOptionalOption textOption(final String name, final String identifier, final boolean small) {
                    return new ModalTextInputOptionalOption(new TextInputBuilder(small? TextInputStyle.SHORT : TextInputStyle.PARAGRAPH, identifier, name));
                }
            }
        }
    }
    interface Command {
        interface Option {
            interface Optional {
                static OptionalOptionBehavior<String> stringOption(final String name, final String description) {
                    return new OptionalOptionBehavior<>(name, description, SlashCommandOptionType.STRING, (n, e) -> e.getArgumentStringValueByName(n));
                }

                static OptionalOptionBehavior<String> booleanOption(final String name, final String description) {
                    return new OptionalOptionBehavior<>(name, description, SlashCommandOptionType.BOOLEAN, (n, e) -> e.getArgumentStringValueByName(n));
                }

                static OptionalOptionBehavior<String> longOption(final String name, final String description) {
                    return new OptionalOptionBehavior<>(name, description, SlashCommandOptionType.LONG, (n, e) -> e.getArgumentStringValueByName(n));
                }

                static OptionalOptionBehavior<String> decimalOption(final String name, final String description) {
                    return new OptionalOptionBehavior<>(name, description, SlashCommandOptionType.DECIMAL, (n, e) -> e.getArgumentStringValueByName(n));
                }

                static OptionalOptionBehavior<ServerChannel> channelOption(final String name, final String description, final List<ChannelType> channelTypes) {
                    return new ChannelOptionalOption(name, description, channelTypes);
                }

                static OptionalOptionBehavior<User> userOption(final String name, final String description) {
                    return new OptionalOptionBehavior<>(name, description, SlashCommandOptionType.USER, (n, e) -> e.getArgumentUserValueByName(n));
                }

                static OptionalOptionBehavior<Mentionable> mentionableOption(final String name, final String description) {
                    return new OptionalOptionBehavior<>(name, description, SlashCommandOptionType.MENTIONABLE, (n, e) -> e.getArgumentMentionableValueByName(n));
                }

                static OptionalOptionBehavior<Role> roleOption(final String name, final String description) {
                    return new OptionalOptionBehavior<>(name, description, SlashCommandOptionType.ROLE, (n, e) -> e.getArgumentRoleValueByName(n));
                }
            }
            interface Required {
                static RequiredOptionBehavior<ServerChannel> channelOption(final String name, final String description, final List<ChannelType> channelTypes) {
                    return new ChannelRequiredOption(name, description, channelTypes);
                }

                static RequiredOptionBehavior<User> userOption(final String name, final String description) {
                    return new RequiredOptionBehavior<>(name, description, SlashCommandOptionType.USER, (n, e) -> e.getArgumentUserValueByName(n).orElseThrow());
                }

                static RequiredOptionBehavior<Mentionable> mentionableOption(final String name, final String description) {
                    return new RequiredOptionBehavior<>(name, description, SlashCommandOptionType.MENTIONABLE, (n, e) -> e.getArgumentMentionableValueByName(n).orElseThrow());
                }

                static RequiredOptionBehavior<Role> roleOption(final String name, final String description) {
                    return new RequiredOptionBehavior<>(name, description, SlashCommandOptionType.ROLE, (n, e) -> e.getArgumentRoleValueByName(n).orElseThrow());
                }

                static RequiredOptionBehavior<String> stringOption(final String name, final String description) {
                    return new RequiredOptionBehavior<>(name, description, SlashCommandOptionType.STRING, (n, e) -> e.getArgumentStringValueByName(n).orElseThrow());
                }

                static RequiredOptionBehavior<String> booleanOption(final String name, final String description) {
                    return new RequiredOptionBehavior<>(name, description, SlashCommandOptionType.BOOLEAN, (n, e) -> e.getArgumentStringValueByName(n).orElseThrow());
                }

                static RequiredOptionBehavior<String> longOption(final String name, final String description) {
                    return new RequiredOptionBehavior<>(name, description, SlashCommandOptionType.LONG, (n, e) -> e.getArgumentStringValueByName(n).orElseThrow());
                }

                static RequiredOptionBehavior<String> decimalOption(final String name, final String description) {
                    return new RequiredOptionBehavior<>(name, description, SlashCommandOptionType.DECIMAL, (n, e) -> e.getArgumentStringValueByName(n).orElseThrow());
                }

            }


        }
    }

}
