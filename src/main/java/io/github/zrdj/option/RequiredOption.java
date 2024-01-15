package io.github.zrdj.option;

import io.github.zrdj.ApplicationCommandOption;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.interaction.SlashCommandOption;
import org.javacord.api.interaction.SlashCommandOptionType;

import java.util.function.BiFunction;

public interface RequiredOption<E> extends ApplicationCommandOption<E> {

    final class Primitive<P> extends Abstract<P> implements RequiredOption<P> {
        public Primitive(final String name, final String description, SlashCommandOptionType type, BiFunction<String, SlashCommandInteraction, P> mapper) {
            super(name, description, type, mapper);
        }

        @Override
        public SlashCommandOption toSlashCommandOption() {
            return SlashCommandOption.create(
                    type(),
                    name(),
                    description(),
                    true
            );
        }
    }
}
