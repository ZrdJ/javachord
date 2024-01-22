package com.github.zrdj.javachord.command.option.choice;

import com.github.zrdj.javachord.command.option.OptionalOption;
import org.javacord.api.interaction.SlashCommandOptionBuilder;
import org.javacord.api.interaction.SlashCommandOptionChoice;
import org.javacord.api.interaction.SlashCommandOptionType;

import java.util.List;

public class ChoiceLongOptionalOption extends OptionalOption<Long> {
    private final List<Choice<Long>> _choices;

    public ChoiceLongOptionalOption(final String name, final String description, final List<Choice<Long>> channelTypes) {
        super(name, description, SlashCommandOptionType.CHANNEL, (n, e) -> e.getArgumentLongValueByName(n));
        _choices = channelTypes;
    }

    @Override
    protected final SlashCommandOptionBuilder buildSlashCommandOption(final SlashCommandOptionBuilder builder) {
        var choices = _choices.stream().map(c -> SlashCommandOptionChoice.create(c.name(), c.value())).toList();
        return builder.setChoices(choices);
    }
}
