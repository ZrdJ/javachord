package io.github.zrdj.command;

import io.github.zrdj.Identifiable;
import io.github.zrdj.option.ApplicationCommandOption;
import org.javacord.api.DiscordApi;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.interaction.SlashCommand;
import org.javacord.api.interaction.SlashCommandBuilder;
import org.javacord.api.interaction.SlashCommandOption;
import org.javacord.api.listener.interaction.SlashCommandCreateListener;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public interface ApplicationCommandBase extends Identifiable {
    @Override
    default String fqdn() {
        return parentCommand()
                .map(p -> p.fqdn() + " " + name())
                .orElse(name());
    }

    SlashCommandBuilder toSlashCommand(final DiscordApi discordApi);

    SlashCommandOption toSlashSubCommand(final DiscordApi discordApi);

    void registerListeners(final DiscordApi discordApi);

    default Optional<ApplicationCommandBase> subCommand() {
        return Optional.empty();
    }

    default Optional<ApplicationCommand> parentCommand() {
        return Optional.empty();
    }

    abstract class Abstract implements ApplicationCommandBase, SlashCommandCreateListener {
        protected final String _name;

        protected final String _description;
        protected final Optional<ApplicationCommand> _parentCommand;
        private final List<ApplicationCommandOption<?>> _options;
        protected DiscordApi _discordApi;

        protected Abstract(final String name, final String description, ApplicationCommand parentCommand, List<ApplicationCommandOption<?>> options) {
            _name = name.contains(" ") ? name.replaceAll(" ", "-") : name;
            _description = description;
            _parentCommand = Optional.ofNullable(parentCommand);
            _options = options;
            options.forEach(o -> o.optionOf(this));
        }

        protected Abstract(final String name, final String description, List<ApplicationCommandOption<?>> options) {
            this(name, description, null, options);
        }

        @Override
        public void registerListeners(final DiscordApi discordApi) {
            discordApi.addListener(this);
            subCommand().ifPresent(sc -> sc.registerListeners(discordApi));
        }

        @Override
        public SlashCommandOption toSlashSubCommand(final DiscordApi discordApi) {
            _discordApi = discordApi;
            var slashCommandOptions = _options.stream().map(ApplicationCommandOption::toSlashCommandOption).collect(Collectors.toList());
            return SlashCommandOption.createSubcommand(name(), description(), slashCommandOptions);
        }

        @Override
        public SlashCommandBuilder toSlashCommand(final DiscordApi discordApi) {
            _discordApi = discordApi;
            var builder = SlashCommand.with(name(), description());
            for (final ApplicationCommandOption<?> option : _options) {
                builder.addOption(option.toSlashCommandOption());
            }
            subCommand().ifPresent(c -> builder.addOption(c.toSlashSubCommand(discordApi)));
            return builder;
        }

        @Override
        public final void onSlashCommandCreate(final SlashCommandCreateEvent event) {
            if (!event.getSlashCommandInteraction().getFullCommandName().equalsIgnoreCase(fqdn())) {
                return;
            }
            onSlashCommandTriggered(event);
        }

        protected abstract void onSlashCommandTriggered(final SlashCommandCreateEvent event);

        @Override
        public Optional<ApplicationCommand> parentCommand() {
            return _parentCommand;
        }

        @Override
        public String name() {
            return _name;
        }

        @Override
        public String description() {
            return _description;
        }
    }
}
