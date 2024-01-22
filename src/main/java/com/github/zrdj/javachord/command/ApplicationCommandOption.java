package com.github.zrdj.javachord.command;

import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.interaction.SlashCommandOption;
import org.javacord.api.interaction.SlashCommandOptionType;

public interface ApplicationCommandOption<E>{
    String name();
    ApplicationCommand command();

    void optionOf(final ApplicationCommand command);

    E toOptionValue(final SlashCommandInteraction interaction);

    SlashCommandOption toSlashCommandOption();

    SlashCommandOptionType type();

}
