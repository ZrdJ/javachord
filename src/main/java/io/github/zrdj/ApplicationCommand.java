package io.github.zrdj;

import org.javacord.api.DiscordApi;
import org.javacord.api.entity.Nameable;
import org.javacord.api.interaction.SlashCommandBuilder;
import org.javacord.api.interaction.SlashCommandOption;

import java.util.Optional;


public interface ApplicationCommand extends Nameable, Descriptable, Qualifiable {
    void registerListeners(final DiscordApi discordApi);
    SlashCommandOption toSlashSubCommand(final DiscordApi discordApi);

    SlashCommandBuilder toSlashCommand(final DiscordApi discordApi);

    Optional<ApplicationCommandGroup> parentCommand();

    Optional<ApplicationCommand> subCommand();
}
