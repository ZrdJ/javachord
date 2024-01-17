package io.github.zrdj.javachord.command.option.autocomplete;

import io.github.zrdj.javachord.command.option.choice.Choice;
import org.javacord.api.interaction.SlashCommandInteractionOption;
import org.javacord.api.interaction.SlashCommandOptionChoice;
import org.javacord.api.interaction.SlashCommandOptionType;

import java.util.List;
import java.util.Optional;

public abstract class LongAutocompleteRequiredOption extends AutocompleteRequiredOption<Long> {
    public LongAutocompleteRequiredOption(final String name, final String description) {
        super(name, description, SlashCommandOptionType.LONG, (id, interaction) -> interaction.getArgumentLongValueByName(id).orElseThrow());
    }

    @Override
    protected final List<SlashCommandOptionChoice> onAutocomplete(final Optional<SlashCommandInteractionOption> option) {
        return onAutocompleteChoice(option.flatMap(SlashCommandInteractionOption::getLongValue))
                .stream().map(c -> SlashCommandOptionChoice.create(c.name(), c.value())).toList();
    }

    protected abstract List<Choice<Long>> onAutocompleteChoice(final Optional<Long> option);
}
