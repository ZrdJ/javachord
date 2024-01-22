package com.github.zrdj.javachord.command.option;

import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.interaction.SlashCommandOption;
import org.javacord.api.interaction.SlashCommandOptionBuilder;
import org.javacord.api.interaction.SlashCommandOptionType;

import java.util.Optional;
import java.util.function.BiFunction;

public class OptionalOption<E> extends Option<Optional<E>> {
    public OptionalOption(final String name, final String description, SlashCommandOptionType type, BiFunction<String, SlashCommandInteraction, Optional<E>> mapper) {
        super(name, description, type, mapper);
    }

    protected SlashCommandOptionBuilder buildSlashCommandOption(final SlashCommandOptionBuilder builder) {
        return builder;
    }

    @Override
    public final SlashCommandOption toSlashCommandOption() {
        return buildSlashCommandOption(new SlashCommandOptionBuilder())
                .setType(type())
                .setName(_name)
                .setDescription(_description)
                .setRequired(false)
                .build();
    }
}
