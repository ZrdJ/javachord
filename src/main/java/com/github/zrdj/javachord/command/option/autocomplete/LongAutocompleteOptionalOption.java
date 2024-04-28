package com.github.zrdj.javachord.command.option.autocomplete;

import com.github.zrdj.javachord.command.ApplicationCommand;
import com.github.zrdj.javachord.command.option.choice.Choice;
import org.javacord.api.interaction.SlashCommandInteractionOption;
import org.javacord.api.interaction.SlashCommandOptionChoice;
import org.javacord.api.interaction.SlashCommandOptionType;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class LongAutocompleteOptionalOption  extends AutocompleteOptionalOption<Long>{
    public LongAutocompleteOptionalOption(final String name, final String description, final ApplicationCommand parent) {
        super(name, description, SlashCommandOptionType.LONG, parent,(id, interaction) -> interaction.getArgumentLongValueByName(id));
    }

    @Override
    protected final List<SlashCommandOptionChoice> onAutocomplete(final Optional<SlashCommandInteractionOption> option) {
        return onAutocompleteChoice(option.flatMap(SlashCommandInteractionOption::getLongValue))
                .stream().map(c -> SlashCommandOptionChoice.create(c.name(), c.value())).collect(Collectors.toList());
    }

    protected abstract List<Choice<Long>> onAutocompleteChoice(final Optional<Long> option);
}
