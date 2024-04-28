package com.github.zrdj.javachord.command.option.autocomplete;

import com.github.zrdj.javachord.Javachord;
import com.github.zrdj.javachord.command.ApplicationCommand;
import com.github.zrdj.javachord.command.option.OptionalOption;
import org.javacord.api.event.interaction.AutocompleteCreateEvent;
import org.javacord.api.interaction.*;
import org.javacord.api.listener.interaction.AutocompleteCreateListener;
import org.javacord.api.util.logging.ExceptionLogger;

import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;

abstract class AutocompleteOptionalOption<E> extends OptionalOption<E> implements AutocompleteCreateListener {
    private final ApplicationCommand _parent;

    public AutocompleteOptionalOption(final String name, final String description, final SlashCommandOptionType type,
                                      final ApplicationCommand parent, final BiFunction<String, SlashCommandInteraction, Optional<E>> mapper) {
        super(name, description, type, mapper);
        this._parent = parent;
        Javachord.Instance.Get.addListener(this);
    }

    @Override
    protected final SlashCommandOptionBuilder buildSlashCommandOption(final SlashCommandOptionBuilder builder) {
        return builder.setAutocompletable(true);
    }

    @Override
    public final void onAutocompleteCreate(final AutocompleteCreateEvent event) {
        if (!_parent.fqdn().equalsIgnoreCase(event.getAutocompleteInteraction().getFullCommandName())) {
            return;
        }
        var choices = onAutocomplete(event.getAutocompleteInteraction().getArgumentByName(_name));
        event.getAutocompleteInteraction()
                .respondWithChoices(choices)
                .exceptionally(ExceptionLogger.get());
    }

    protected abstract List<SlashCommandOptionChoice> onAutocomplete(final Optional<SlashCommandInteractionOption> option);
}
