package io.github.zrdj.javachord.command.option.choice;

import io.github.zrdj.javachord.command.option.RequiredOption;
import org.javacord.api.interaction.SlashCommandOptionBuilder;
import org.javacord.api.interaction.SlashCommandOptionChoice;
import org.javacord.api.interaction.SlashCommandOptionType;

import java.util.List;

public class ChoiceLongRequiredOption extends RequiredOption<Long> {
    private final List<Choice<Long>> _choices;

    public ChoiceLongRequiredOption(final String name, final String description, final List<Choice<Long>> channelTypes) {
        super(name, description, SlashCommandOptionType.CHANNEL, (n, e) -> e.getArgumentLongValueByName(n).orElseThrow());
        _choices = channelTypes;
    }

    @Override
    protected final SlashCommandOptionBuilder buildSlashCommandOption(final SlashCommandOptionBuilder builder) {
        var choices = _choices.stream().map(c -> SlashCommandOptionChoice.create(c.name(), c.value())).toList();
        return builder.setChoices(choices);
    }
}
