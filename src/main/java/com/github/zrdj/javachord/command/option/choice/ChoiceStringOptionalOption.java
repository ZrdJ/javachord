package com.github.zrdj.javachord.command.option.choice;

import com.github.zrdj.javachord.command.option.OptionalOption;
import org.javacord.api.interaction.SlashCommandOptionBuilder;
import org.javacord.api.interaction.SlashCommandOptionChoice;
import org.javacord.api.interaction.SlashCommandOptionType;

import java.util.List;

public class ChoiceStringOptionalOption extends OptionalOption<String> {
    private final List<Choice<String>> _choices;

    public ChoiceStringOptionalOption(final String name, final String description, final List<Choice<String>> choices) {
        super(name, description, SlashCommandOptionType.CHANNEL, (n, e) -> e.getArgumentStringValueByName(n));
        _choices = choices;
    }

    @Override
    protected final SlashCommandOptionBuilder buildSlashCommandOption(final SlashCommandOptionBuilder builder) {
        var choices = _choices.stream().map(c -> SlashCommandOptionChoice.create(c.name(), c.value())).toList();
        return builder.setChoices(choices);
    }
}
