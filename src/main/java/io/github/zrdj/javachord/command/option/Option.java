package io.github.zrdj.javachord.command.option;

import io.github.zrdj.javachord.command.ApplicationCommand;
import io.github.zrdj.javachord.command.ApplicationCommandOption;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.interaction.SlashCommandOption;
import org.javacord.api.interaction.SlashCommandOptionType;

import java.util.function.BiFunction;

abstract class Option<E> implements ApplicationCommandOption<E> {
    protected final String _name;
    protected final String _description;
    protected final SlashCommandOptionType _type;
    protected final BiFunction<String, SlashCommandInteraction, E> _mapper;
    protected ApplicationCommand _command;

    public Option(final String name, final String description, SlashCommandOptionType type, BiFunction<String, SlashCommandInteraction, E> mapper) {
        _name = name.contains(" ") ? name.replaceAll(" ", "-") : name;
        _description = description;
        _type = type;
        _mapper = mapper;
    }

    @Override
    public final String name() {
        return _name;
    }

    @Override
    public abstract SlashCommandOption toSlashCommandOption();

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
