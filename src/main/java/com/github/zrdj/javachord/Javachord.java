package com.github.zrdj.javachord;

import com.github.zrdj.javachord.command.ApplicationCommand;
import com.github.zrdj.javachord.command.option.OptionalOption;
import com.github.zrdj.javachord.command.option.RequiredOption;
import com.github.zrdj.javachord.command.option.autocomplete.LongAutocompleteOptionalOption;
import com.github.zrdj.javachord.command.option.autocomplete.LongAutocompleteRequiredOption;
import com.github.zrdj.javachord.command.option.autocomplete.StringAutocompleteOptionalOption;
import com.github.zrdj.javachord.command.option.autocomplete.StringAutocompleteRequiredOption;
import com.github.zrdj.javachord.command.option.channel.ChannelOptionalOption;
import com.github.zrdj.javachord.command.option.channel.ChannelRequiredOption;
import com.github.zrdj.javachord.command.option.choice.Choice;
import com.github.zrdj.javachord.component.button.ButtonComponent;
import com.github.zrdj.javachord.component.select.SelectMenuChoice;
import com.github.zrdj.javachord.error.JavachordConstraintError;
import com.github.zrdj.javachord.modal.option.TextInputOptionalOption;
import com.github.zrdj.javachord.modal.option.TextInputRequiredOption;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.Mentionable;
import org.javacord.api.entity.channel.ChannelType;
import org.javacord.api.entity.channel.ServerChannel;
import org.javacord.api.entity.message.component.ButtonBuilder;
import org.javacord.api.entity.message.component.ButtonStyle;
import org.javacord.api.entity.message.component.TextInputBuilder;
import org.javacord.api.entity.message.component.TextInputStyle;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.interaction.MessageComponentInteraction;
import org.javacord.api.interaction.SlashCommandOptionType;
import org.javacord.api.listener.GloballyAttachableListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public interface Javachord {
    enum Instance implements Javachord {
        Get;
        private final List<ApplicationCommand> _commands = new ArrayList<>();
        private final List<GloballyAttachableListener> _listeners = new ArrayList<>();
        public DiscordApi discordInstance;
        public Optional<Server> serverInstance = Optional.empty();

        public void addCommand(final ApplicationCommand command) {
            _commands.add(command);
        }
        public void addListener(final GloballyAttachableListener listener) {
            _listeners.add(listener);
        }
        public CompletableFuture<Set<org.javacord.api.interaction.ApplicationCommand>> register(final DiscordApi discordApi) {
            discordInstance = discordApi;
            var commandSet = _commands.stream().map(ApplicationCommand::toSlashCommand).collect(Collectors.toSet());
            _listeners.forEach(discordApi::addListener);
            return discordApi.bulkOverwriteGlobalApplicationCommands(commandSet);
        }
        public CompletableFuture<Set<org.javacord.api.interaction.ApplicationCommand>> register(final DiscordApi discordApi, final Server server) {
            discordInstance = discordApi;
            serverInstance = Optional.of(server);
            var commandSet = _commands.stream().map(ApplicationCommand::toSlashCommand).collect(Collectors.toSet());
            _listeners.forEach(discordApi::addListener);
            return discordApi.bulkOverwriteServerApplicationCommands(server, commandSet);
        }
    }

    interface Constraint {
        static void ensureIdentifier(final String identifier) {
            if (identifier == null || identifier.trim().isEmpty()) {
                throw new JavachordConstraintError("Identifier missing. Did you forget to define a proper identifier?");
            }
        }

        static <T> void ensureChoices(final List<SelectMenuChoice<T>> choices) {
            if (choices.size() != choices.stream().map(SelectMenuChoice::value).distinct().count()) {
                throw new JavachordConstraintError("Choices must be unique. Did you provide the same value for multiple choices?");
            }
        }

    }
    interface Modal {
        interface Option {
            interface Required {
                static TextInputRequiredOption textOption(final String name, final String identifier, final boolean paragraph) {
                    return new TextInputRequiredOption(new TextInputBuilder(paragraph ? TextInputStyle.PARAGRAPH : TextInputStyle.SHORT, identifier, name));
                }
            }

            interface Optional {
                static TextInputOptionalOption textOption(final String name, final String identifier, final boolean paragraph) {
                    return new TextInputOptionalOption(new TextInputBuilder(paragraph ? TextInputStyle.PARAGRAPH : TextInputStyle.SHORT, identifier, name));
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

                static RequiredOption<Boolean> booleanOption(final String name, final String description) {
                    return new RequiredOption<>(name, description, SlashCommandOptionType.BOOLEAN, (n, e) -> e.getArgumentBooleanValueByName(n).orElseThrow());
                }

                static RequiredOption<Long> longOption(final String name, final String description) {
                    return new RequiredOption<>(name, description, SlashCommandOptionType.LONG, (n, e) -> e.getArgumentLongValueByName(n).orElseThrow());
                }

                static RequiredOption<Double> decimalOption(final String name, final String description) {
                    return new RequiredOption<>(name, description, SlashCommandOptionType.DECIMAL, (n, e) -> e.getArgumentDecimalValueByName(n).orElseThrow());
                }

            }


        }
    }

    interface Message {

        interface Button {

            static ButtonComponent primary(final String identifier, final String label, final Consumer<MessageComponentInteraction> onClick) {
                return new ButtonComponent(identifier, label) {

                    @Override
                    protected ButtonBuilder configureComponent(final ButtonBuilder builder) {
                        return builder.setStyle(ButtonStyle.PRIMARY);
                    }

                    @Override
                    protected void onButtonClicked(final MessageComponentInteraction event) {
                        onClick.accept(event);
                    }
                };
            }


            static ButtonComponent secondary(final String identifier, final String label, final Consumer<MessageComponentInteraction> onClick) {
                return new ButtonComponent(identifier, label) {

                    @Override
                    protected ButtonBuilder configureComponent(final ButtonBuilder builder) {
                        return builder.setStyle(ButtonStyle.SECONDARY);
                    }

                    @Override
                    protected void onButtonClicked(final MessageComponentInteraction event) {
                        onClick.accept(event);
                    }
                };
            }

            static ButtonComponent danger(final String identifier, final String label, final Consumer<MessageComponentInteraction> onClick) {
                return new ButtonComponent(identifier, label) {

                    @Override
                    protected ButtonBuilder configureComponent(final ButtonBuilder builder) {
                        return builder.setStyle(ButtonStyle.DANGER);
                    }

                    @Override
                    protected void onButtonClicked(final MessageComponentInteraction event) {
                        onClick.accept(event);
                    }
                };
            }

            static ButtonComponent success(final String identifier, final String label, final Consumer<MessageComponentInteraction> onClick) {
                return new ButtonComponent(identifier, label) {

                    @Override
                    protected ButtonBuilder configureComponent(final ButtonBuilder builder) {
                        return builder.setStyle(ButtonStyle.SUCCESS);
                    }

                    @Override
                    protected void onButtonClicked(final MessageComponentInteraction event) {
                        onClick.accept(event);
                    }
                };
            }

            static ButtonComponent link(final String identifier, final String label, final String url, final Consumer<MessageComponentInteraction> onClick) {
                return new ButtonComponent(identifier, label) {

                    @Override
                    protected ButtonBuilder configureComponent(final ButtonBuilder builder) {
                        return builder
                                .setUrl(url)
                                .setStyle(ButtonStyle.LINK);
                    }

                    @Override
                    protected void onButtonClicked(final MessageComponentInteraction event) {
                        onClick.accept(event);
                    }
                };
            }
        }
    }
}
