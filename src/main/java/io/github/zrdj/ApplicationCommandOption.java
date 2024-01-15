package io.github.zrdj;

import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.interaction.SlashCommandOption;
import org.javacord.api.interaction.SlashCommandOptionType;

import java.util.function.BiFunction;

public interface ApplicationCommandOption<E> extends Identifiable {
    @Override
    default String fqdn() {
        return name();
    }

    ApplicationCommand command();

    void optionOf(final ApplicationCommand commandBase);

    E toOptionValue(final SlashCommandInteraction interaction);

    SlashCommandOption toSlashCommandOption();

    SlashCommandOptionType type();

    abstract class Abstract<E> implements ApplicationCommandOption<E> {
        protected final String _name;
        protected final String _description;
        protected final SlashCommandOptionType _type;
        protected final BiFunction<String, SlashCommandInteraction, E> _mapper;
        protected ApplicationCommand _command;

        public Abstract(final String name, final String description, SlashCommandOptionType type, BiFunction<String, SlashCommandInteraction, E> mapper) {
            _name = name.contains(" ") ? name.replaceAll(" ", "-") : name;
            _description = description;
            _type = type;
            _mapper = mapper;
        }

        @Override
        public abstract SlashCommandOption toSlashCommandOption();

        @Override
        public final String name() {
            return _name;
        }

        @Override
        public final String description() {
            return _description;
        }

        @Override
        public final ApplicationCommand command() {
            return _command;
        }

        @Override
        public final void optionOf(final ApplicationCommand command) {
            _command = command;
        }

        @Override
        public final E toOptionValue(final SlashCommandInteraction interaction) {
            return _mapper.apply(_name, interaction);
        }

        @Override
        public final SlashCommandOptionType type() {
            return _type;
        }
    }
}
