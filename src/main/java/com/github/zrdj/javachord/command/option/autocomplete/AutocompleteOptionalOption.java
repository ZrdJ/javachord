package com.github.zrdj.javachord.command.option.autocomplete;

import com.github.zrdj.javachord.command.option.OptionalOption;
import org.javacord.api.event.interaction.AutocompleteCreateEvent;
import org.javacord.api.interaction.*;
import org.javacord.api.listener.interaction.AutocompleteCreateListener;
import org.javacord.api.util.logging.ExceptionLogger;

import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;

abstract class AutocompleteOptionalOption<E> extends OptionalOption<E> implements AutocompleteCreateListener {
    public AutocompleteOptionalOption(final String name, final String description, final SlashCommandOptionType type, final BiFunction<String, SlashCommandInteraction, Optional<E>> mapper) {
        super(name, description, type, mapper);
    }

    @Override
    protected final SlashCommandOptionBuilder buildSlashCommandOption(final SlashCommandOptionBuilder builder) {
        return builder.setAutocompletable(true);
    }

    @Override
    public final void onAutocompleteCreate(final AutocompleteCreateEvent event) {
        var choices = onAutocomplete(event.getAutocompleteInteraction().getArgumentByName(_name));
        event.getAutocompleteInteraction()
                .respondWithChoices(choices)
                .exceptionally(ExceptionLogger.get());
    }

    protected abstract List<SlashCommandOptionChoice> onAutocomplete(final Optional<SlashCommandInteractionOption> option);
}
