package io.github.zrdj.javachord.command.option.autocomplete;

import io.github.zrdj.javachord.command.option.choice.Choice;
import org.javacord.api.interaction.SlashCommandInteractionOption;
import org.javacord.api.interaction.SlashCommandOptionChoice;
import org.javacord.api.interaction.SlashCommandOptionType;

import java.util.List;
import java.util.Optional;

public abstract class StringAutocompleteOptionalOption  extends AutocompleteOptionalOption<String> {
    public StringAutocompleteOptionalOption(final String name, final String description) {
        super(name, description, SlashCommandOptionType.STRING, (id, interaction) -> interaction.getArgumentStringValueByName(id));
    }

    @Override
    protected final List<SlashCommandOptionChoice> onAutocomplete(final Optional<SlashCommandInteractionOption> option) {
        return onAutocompleteChoice(option.flatMap(SlashCommandInteractionOption::getStringValue))
                .stream().map(c -> SlashCommandOptionChoice.create(c.name(), c.value())).toList();
    }

    protected abstract List<Choice<String>> onAutocompleteChoice(final Optional<String> option);
}
