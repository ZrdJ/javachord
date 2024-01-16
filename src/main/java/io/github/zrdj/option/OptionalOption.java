package io.github.zrdj.option;

import io.github.zrdj.ApplicationCommandOption;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.interaction.SlashCommandOption;
import org.javacord.api.interaction.SlashCommandOptionType;

import java.util.Optional;
import java.util.function.BiFunction;

public interface OptionalOption<E> extends ApplicationCommandOption<Optional<E>> {
    final class Primitive<P> extends Abstract<Optional<P>> implements OptionalOption<P> {
        public Primitive(final String name, final String description, SlashCommandOptionType type, BiFunction<String, SlashCommandInteraction, Optional<P>> mapper) {
            super(name, description, type, mapper);
        }

        @Override
        public SlashCommandOption toSlashCommandOption() {
            return SlashCommandOption.create(
                    type(),
                    getName(),
                    description(),
                    false
            );
        }
    }
}
