package io.github.zrdj.javachord.command.option.autocomplete;

import io.github.zrdj.javachord.command.option.RequiredOption;
import io.github.zrdj.javachord.command.option.choice.Choice;
import org.javacord.api.interaction.*;

import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;

public abstract class StringAutocompleteRequiredOption extends AutocompleteRequiredOption<String> {
    public StringAutocompleteRequiredOption(final String name, final String description) {
        super(name, description, SlashCommandOptionType.STRING, (id, interaction) -> interaction.getArgumentStringValueByName(id).orElseThrow());
    }

    @Override
    protected final List<SlashCommandOptionChoice> onAutocomplete(final Optional<SlashCommandInteractionOption> option) {
        return onAutocompleteChoice(option.flatMap(SlashCommandInteractionOption::getStringValue))
                .stream().map(c -> SlashCommandOptionChoice.create(c.name(), c.value())).toList();
    }

    protected abstract List<Choice<String>> onAutocompleteChoice(final Optional<String> option);
}
