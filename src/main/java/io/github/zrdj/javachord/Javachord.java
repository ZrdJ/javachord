package io.github.zrdj.javachord;

import io.github.zrdj.javachord.command.option.ChannelOptionalOption;
import io.github.zrdj.javachord.command.option.ChannelRequiredOption;
import io.github.zrdj.javachord.command.option.OptionalOptionBehavior;
import io.github.zrdj.javachord.command.option.RequiredOptionBehavior;
import io.github.zrdj.javachord.modal.option.ModalTextInputOptionalOption;
import io.github.zrdj.javachord.modal.option.ModalTextInputRequiredOption;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.Mentionable;
import org.javacord.api.entity.channel.ChannelType;
import org.javacord.api.entity.channel.ServerChannel;
import org.javacord.api.entity.message.component.TextInputBuilder;
import org.javacord.api.entity.message.component.TextInputStyle;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.interaction.SlashCommandOptionType;
import org.javacord.api.listener.GloballyAttachableListener;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public interface Javachord {
    enum Instance implements Javachord {
        Get;

        private final List<ApplicationCommand> _commands = new ArrayList<>();
        private final List<GloballyAttachableListener> _listeners = new ArrayList<>();

        public void addCommand(final ApplicationCommand command) {
            _commands.add(command);
        }
        public void addListener(final GloballyAttachableListener listener) {
            _listeners.add(listener);
        }
        public void register(final DiscordApi discordApi) {
            var commandSet = _commands.stream().map(ApplicationCommand::toSlashCommand).collect(Collectors.toSet());
            discordApi.bulkOverwriteGlobalApplicationCommands(commandSet);
            _listeners.forEach(discordApi::addListener);
        }
        public void register(final DiscordApi discordApi, final Server server) {
            var commandSet = _commands.stream().map(ApplicationCommand::toSlashCommand).collect(Collectors.toSet());
            discordApi.bulkOverwriteServerApplicationCommands(server, commandSet);
            _listeners.forEach(discordApi::addListener);
        }
    }

    interface Modal {
        interface Option {
            interface Required {
                static ModalTextInputRequiredOption textOption(final String name, final String identifier, final boolean paragraph) {
                    return new ModalTextInputRequiredOption(new TextInputBuilder(paragraph ? TextInputStyle.PARAGRAPH : TextInputStyle.SHORT, identifier, name));
                }
            }

            interface Optional {
                static ModalTextInputOptionalOption textOption(final String name, final String identifier, final boolean paragraph) {
                    return new ModalTextInputOptionalOption(new TextInputBuilder(paragraph ? TextInputStyle.PARAGRAPH : TextInputStyle.SHORT, identifier, name));
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
