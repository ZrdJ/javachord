package io.github.zrdj;

import org.javacord.api.entity.Nameable;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.interaction.SlashCommandOption;
import org.javacord.api.interaction.SlashCommandOptionType;

import java.util.function.BiFunction;

public interface ApplicationCommandOption<E> extends Nameable, Descriptable, Qualifiable {
    @Override
    default String fqdn() {
        return getName();
    }

    ApplicationCommand command();

    void optionOf(final ApplicationCommand command);

    E toOptionValue(final SlashCommandInteraction interaction);

    SlashCommandOption toSlashCommandOption();

    SlashCommandOptionType type();

}
