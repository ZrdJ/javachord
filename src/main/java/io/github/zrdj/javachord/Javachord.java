package io.github.zrdj.javachord;

import io.github.zrdj.javachord.command.option.autocomplete.*;
import io.github.zrdj.javachord.command.option.channel.ChannelOptionalOption;
import io.github.zrdj.javachord.command.option.channel.ChannelRequiredOption;
import io.github.zrdj.javachord.command.option.OptionalOption;
import io.github.zrdj.javachord.command.option.RequiredOption;
import io.github.zrdj.javachord.command.option.choice.Choice;
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
import org.javacord.api.interaction.SlashCommandInteractionOption;
import org.javacord.api.interaction.SlashCommandOptionChoice;
import org.javacord.api.interaction.SlashCommandOptionType;
import org.javacord.api.listener.GloballyAttachableListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
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
            interface Autocomplete {
                interface Optional {
                    static OptionalOption<String> stringOption(final String name, final String description, final Function<java.util.Optional<String>, List<Choice<String>>> autocomplete) {
                        return new StringAutocompleteOptionalOption(name, description) {

                            @Override
                            protected List<Choice<String>> onAutocompleteChoice(final java.util.Optional<String> option) {
                                return autocomplete.apply(option);
                            }
                        };
                    }

                    static OptionalOption<Long> longOption(final String name, final String description, final Function<java.util.Optional<Long>, List<Choice<Long>>> autocomplete) {
                        return new LongAutocompleteOptionalOption(name, description) {
                            @Override
                            protected List<Choice<Long>> onAutocompleteChoice(final java.util.Optional<Long> option) {
                                return autocomplete.apply(option);
                            }
                        };
                    }

                }
                interface Required {
                    static RequiredOption<String> stringOption(final String name, final String description, final Function<java.util.Optional<String>, List<Choice<String>>> autocomplete) {
                        return new StringAutocompleteRequiredOption(name, description) {
                            @Override
                            protected List<Choice<String>> onAutocompleteChoice(final java.util.Optional<String> option) {
                                return autocomplete.apply(option);
                            }
                        };
                    }

                    static RequiredOption<Long> longOption(final String name, final String description, final Function<java.util.Optional<Long>, List<Choice<Long>>> autocomplete) {
                        return new LongAutocompleteRequiredOption(name, description) {
                            @Override
                            protected List<Choice<Long>> onAutocompleteChoice(final java.util.Optional<Long> option) {
                                return autocomplete.apply(option);
                            }
                        };
                    }

                }
            }
            interface Optional {
                static OptionalOption<String> stringOption(final String name, final String description) {
                    return new OptionalOption<>(name, description, SlashCommandOptionType.STRING, (n, e) -> e.getArgumentStringValueByName(n));
                }

                static OptionalOption<String> booleanOption(final String name, final String description) {
                    return new OptionalOption<>(name, description, SlashCommandOptionType.BOOLEAN, (n, e) -> e.getArgumentStringValueByName(n));
                }

                static OptionalOption<String> longOption(final String name, final String description) {
                    return new OptionalOption<>(name, description, SlashCommandOptionType.LONG, (n, e) -> e.getArgumentStringValueByName(n));
                }

                static OptionalOption<String> decimalOption(final String name, final String description) {
                    return new OptionalOption<>(name, description, SlashCommandOptionType.DECIMAL, (n, e) -> e.getArgumentStringValueByName(n));
                }

                static OptionalOption<ServerChannel> channelOption(final String name, final String description, final List<ChannelType> channelTypes) {
                    return new ChannelOptionalOption(name, description, channelTypes);
                }

                static OptionalOption<User> userOption(final String name, final String description) {
                    return new OptionalOption<>(name, description, SlashCommandOptionType.USER, (n, e) -> e.getArgumentUserValueByName(n));
                }

                static OptionalOption<Mentionable> mentionableOption(final String name, final String description) {
                    return new OptionalOption<>(name, description, SlashCommandOptionType.MENTIONABLE, (n, e) -> e.getArgumentMentionableValueByName(n));
                }

                static OptionalOption<Role> roleOption(final String name, final String description) {
                    return new OptionalOption<>(name, description, SlashCommandOptionType.ROLE, (n, e) -> e.getArgumentRoleValueByName(n));
                }
            }

            interface Required {
                static RequiredOption<ServerChannel> channelOption(final String name, final String description, final List<ChannelType> channelTypes) {
                    return new ChannelRequiredOption(name, description, channelTypes);
                }

                static RequiredOption<User> userOption(final String name, final String description) {
                    return new RequiredOption<>(name, description, SlashCommandOptionType.USER, (n, e) -> e.getArgumentUserValueByName(n).orElseThrow());
                }

                static RequiredOption<Mentionable> mentionableOption(final String name, final String description) {
                    return new RequiredOption<>(name, description, SlashCommandOptionType.MENTIONABLE, (n, e) -> e.getArgumentMentionableValueByName(n).orElseThrow());
                }

                static RequiredOption<Role> roleOption(final String name, final String description) {
                    return new RequiredOption<>(name, description, SlashCommandOptionType.ROLE, (n, e) -> e.getArgumentRoleValueByName(n).orElseThrow());
                }

                static RequiredOption<String> stringOption(final String name, final String description) {
                    return new RequiredOption<>(name, description, SlashCommandOptionType.STRING, (n, e) -> e.getArgumentStringValueByName(n).orElseThrow());
                }

                static RequiredOption<String> booleanOption(final String name, final String description) {
                    return new RequiredOption<>(name, description, SlashCommandOptionType.BOOLEAN, (n, e) -> e.getArgumentStringValueByName(n).orElseThrow());
                }

                static RequiredOption<String> longOption(final String name, final String description) {
                    return new RequiredOption<>(name, description, SlashCommandOptionType.LONG, (n, e) -> e.getArgumentStringValueByName(n).orElseThrow());
                }

                static RequiredOption<String> decimalOption(final String name, final String description) {
                    return new RequiredOption<>(name, description, SlashCommandOptionType.DECIMAL, (n, e) -> e.getArgumentStringValueByName(n).orElseThrow());
                }

            }


        }
    }
}
