package io.github.zrdj.javachord.command.option;

import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.interaction.SlashCommandOption;
import org.javacord.api.interaction.SlashCommandOptionType;

import java.util.function.BiFunction;

public class ApplicationCommandOptionRequiredBehavior<E> extends ApplicationCommandOptionBehavior<E> {

    public ApplicationCommandOptionRequiredBehavior(final String name, final String description, SlashCommandOptionType type, BiFunction<String, SlashCommandInteraction, E> mapper) {
        super(name, description, type, mapper);
    }

    @Override
    public SlashCommandOption toSlashCommandOption() {
        return SlashCommandOption.create(
                type(),
                _name,
                _description,
                true
        );
    }
}
