package io.github.zrdj.javachord.command.option;

import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.interaction.SlashCommandOption;
import org.javacord.api.interaction.SlashCommandOptionType;

import java.util.Optional;
import java.util.function.BiFunction;

public class ApplicationCommandOptionOptionalBehavior<E> extends ApplicationCommandOptionBehavior<Optional<E>> {
    public ApplicationCommandOptionOptionalBehavior(final String name, final String description, SlashCommandOptionType type, BiFunction<String, SlashCommandInteraction, Optional<E>> mapper) {
        super(name, description, type, mapper);
    }

    public SlashCommandOption toSlashCommandOption() {
        return SlashCommandOption.create(
                type(),
                _name,
                _description,
                false
        );
    }
}
