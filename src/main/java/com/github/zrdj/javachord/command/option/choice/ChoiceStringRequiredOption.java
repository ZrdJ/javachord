package com.github.zrdj.javachord.command.option.choice;

import com.github.zrdj.javachord.command.option.RequiredOption;
import org.javacord.api.interaction.SlashCommandOptionBuilder;
import org.javacord.api.interaction.SlashCommandOptionChoice;
import org.javacord.api.interaction.SlashCommandOptionType;

import java.util.List;
import java.util.stream.Collectors;

public class ChoiceStringRequiredOption extends RequiredOption<String> {
    private final List<Choice<String>> _choices;

    public ChoiceStringRequiredOption(final String name, final String description, final List<Choice<String>> channelTypes) {
        super(name, description, SlashCommandOptionType.STRING, (n, e) -> e.getArgumentStringValueByName(n).orElseThrow());
        _choices = channelTypes;
    }

    @Override
    protected final SlashCommandOptionBuilder buildSlashCommandOption(final SlashCommandOptionBuilder builder) {
        var choices = _choices.stream().map(c -> SlashCommandOptionChoice.create(c.name(), c.value())).collect(Collectors.toList());
        return builder.setChoices(choices);
    }
}
