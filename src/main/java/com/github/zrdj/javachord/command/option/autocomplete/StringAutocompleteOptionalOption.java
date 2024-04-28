package com.github.zrdj.javachord.command.option.autocomplete;

import com.github.zrdj.javachord.command.ApplicationCommand;
import com.github.zrdj.javachord.command.option.choice.Choice;
import org.javacord.api.interaction.SlashCommandInteractionOption;
import org.javacord.api.interaction.SlashCommandOptionChoice;
import org.javacord.api.interaction.SlashCommandOptionType;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class StringAutocompleteOptionalOption  extends AutocompleteOptionalOption<String> {
    public StringAutocompleteOptionalOption(final String name, final String description, final ApplicationCommand parent) {
        super(name, description, SlashCommandOptionType.STRING, parent, (id, interaction) -> interaction.getArgumentStringValueByName(id));
    }

    @Override
    protected final List<SlashCommandOptionChoice> onAutocomplete(final Optional<SlashCommandInteractionOption> option) {
        return onAutocompleteChoice(option.flatMap(SlashCommandInteractionOption::getStringValue))
                .stream().map(c -> SlashCommandOptionChoice.create(c.name(), c.value())).collect(Collectors.toList());
    }

    protected abstract List<Choice<String>> onAutocompleteChoice(final Optional<String> option);
}
