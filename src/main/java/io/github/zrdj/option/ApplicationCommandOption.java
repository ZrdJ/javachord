package io.github.zrdj.option;

import io.github.zrdj.Identifiable;
import io.github.zrdj.command.ApplicationCommandBase;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.interaction.SlashCommandOption;
import org.javacord.api.interaction.SlashCommandOptionType;

import java.util.function.BiFunction;

public interface ApplicationCommandOption<E> extends Identifiable {
    @Override
    default String fqdn() {
        return name();
    }

    ApplicationCommandBase command();

    void optionOf(final ApplicationCommandBase commandBase);

    E toOptionValue(final SlashCommandInteraction interaction);

    SlashCommandOption toSlashCommandOption();

    SlashCommandOptionType type();

    abstract class Abstract<E> implements ApplicationCommandOption<E> {
        protected final String _name;
        protected final String _description;
        protected final SlashCommandOptionType _type;
        protected final BiFunction<String, SlashCommandInteraction, E> _mapper;
        protected ApplicationCommandBase _command;

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
        public final ApplicationCommandBase command() {
            return _command;
        }

        @Override
        public final void optionOf(final ApplicationCommandBase command) {
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
