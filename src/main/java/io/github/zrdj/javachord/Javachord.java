package io.github.zrdj.javachord;

import io.github.zrdj.javachord.command.option.ApplicationCommandOptionChannelOptional;
import io.github.zrdj.javachord.command.option.ApplicationCommandOptionChannelRequired;
import io.github.zrdj.javachord.command.option.ApplicationCommandOptionOptionalBehavior;
import io.github.zrdj.javachord.command.option.ApplicationCommandOptionRequiredBehavior;
import io.github.zrdj.javachord.modal.option.ApplicationModalTextInputOptionalOption;
import io.github.zrdj.javachord.modal.option.ApplicationModalTextInputRequiredOption;
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
                static ApplicationModalTextInputRequiredOption textOption(final String name, final String identifier, final boolean paragraph) {
                    return new ApplicationModalTextInputRequiredOption(new TextInputBuilder(paragraph ? TextInputStyle.PARAGRAPH : TextInputStyle.SHORT, identifier, name));
                }
            }

            interface Optional {
                static ApplicationModalTextInputOptionalOption textOption(final String name, final String identifier, final boolean paragraph) {
                    return new ApplicationModalTextInputOptionalOption(new TextInputBuilder(paragraph ? TextInputStyle.PARAGRAPH : TextInputStyle.SHORT, identifier, name));
                }
            }
        }
    }

    interface Command {
        interface Option {
            interface Optional {
                static ApplicationCommandOptionOptionalBehavior<String> stringOption(final String name, final String description) {
                    return new ApplicationCommandOptionOptionalBehavior<>(name, description, SlashCommandOptionType.STRING, (n, e) -> e.getArgumentStringValueByName(n));
                }

                static ApplicationCommandOptionOptionalBehavior<String> booleanOption(final String name, final String description) {
                    return new ApplicationCommandOptionOptionalBehavior<>(name, description, SlashCommandOptionType.BOOLEAN, (n, e) -> e.getArgumentStringValueByName(n));
                }

                static ApplicationCommandOptionOptionalBehavior<String> longOption(final String name, final String description) {
                    return new ApplicationCommandOptionOptionalBehavior<>(name, description, SlashCommandOptionType.LONG, (n, e) -> e.getArgumentStringValueByName(n));
                }

                static ApplicationCommandOptionOptionalBehavior<String> decimalOption(final String name, final String description) {
                    return new ApplicationCommandOptionOptionalBehavior<>(name, description, SlashCommandOptionType.DECIMAL, (n, e) -> e.getArgumentStringValueByName(n));
                }

                static ApplicationCommandOptionOptionalBehavior<ServerChannel> channelOption(final String name, final String description, final List<ChannelType> channelTypes) {
                    return new ApplicationCommandOptionChannelOptional(name, description, channelTypes);
                }

                static ApplicationCommandOptionOptionalBehavior<User> userOption(final String name, final String description) {
                    return new ApplicationCommandOptionOptionalBehavior<>(name, description, SlashCommandOptionType.USER, (n, e) -> e.getArgumentUserValueByName(n));
                }

                static ApplicationCommandOptionOptionalBehavior<Mentionable> mentionableOption(final String name, final String description) {
                    return new ApplicationCommandOptionOptionalBehavior<>(name, description, SlashCommandOptionType.MENTIONABLE, (n, e) -> e.getArgumentMentionableValueByName(n));
                }

                static ApplicationCommandOptionOptionalBehavior<Role> roleOption(final String name, final String description) {
                    return new ApplicationCommandOptionOptionalBehavior<>(name, description, SlashCommandOptionType.ROLE, (n, e) -> e.getArgumentRoleValueByName(n));
                }
            }

            interface Required {
                static ApplicationCommandOptionRequiredBehavior<ServerChannel> channelOption(final String name, final String description, final List<ChannelType> channelTypes) {
                    return new ApplicationCommandOptionChannelRequired(name, description, channelTypes);
                }

                static ApplicationCommandOptionRequiredBehavior<User> userOption(final String name, final String description) {
                    return new ApplicationCommandOptionRequiredBehavior<>(name, description, SlashCommandOptionType.USER, (n, e) -> e.getArgumentUserValueByName(n).orElseThrow());
                }

                static ApplicationCommandOptionRequiredBehavior<Mentionable> mentionableOption(final String name, final String description) {
                    return new ApplicationCommandOptionRequiredBehavior<>(name, description, SlashCommandOptionType.MENTIONABLE, (n, e) -> e.getArgumentMentionableValueByName(n).orElseThrow());
                }

                static ApplicationCommandOptionRequiredBehavior<Role> roleOption(final String name, final String description) {
                    return new ApplicationCommandOptionRequiredBehavior<>(name, description, SlashCommandOptionType.ROLE, (n, e) -> e.getArgumentRoleValueByName(n).orElseThrow());
                }

                static ApplicationCommandOptionRequiredBehavior<String> stringOption(final String name, final String description) {
                    return new ApplicationCommandOptionRequiredBehavior<>(name, description, SlashCommandOptionType.STRING, (n, e) -> e.getArgumentStringValueByName(n).orElseThrow());
                }

                static ApplicationCommandOptionRequiredBehavior<String> booleanOption(final String name, final String description) {
                    return new ApplicationCommandOptionRequiredBehavior<>(name, description, SlashCommandOptionType.BOOLEAN, (n, e) -> e.getArgumentStringValueByName(n).orElseThrow());
                }

                static ApplicationCommandOptionRequiredBehavior<String> longOption(final String name, final String description) {
                    return new ApplicationCommandOptionRequiredBehavior<>(name, description, SlashCommandOptionType.LONG, (n, e) -> e.getArgumentStringValueByName(n).orElseThrow());
                }

                static ApplicationCommandOptionRequiredBehavior<String> decimalOption(final String name, final String description) {
                    return new ApplicationCommandOptionRequiredBehavior<>(name, description, SlashCommandOptionType.DECIMAL, (n, e) -> e.getArgumentStringValueByName(n).orElseThrow());
                }

            }


        }
    }
}
