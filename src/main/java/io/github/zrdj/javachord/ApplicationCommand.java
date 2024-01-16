package io.github.zrdj.javachord;

import org.javacord.api.interaction.SlashCommandBuilder;
import org.javacord.api.interaction.SlashCommandOption;

import java.util.List;
import java.util.Optional;

public interface ApplicationCommand {
    String fqdn(); // fully qualified discord name
    SlashCommandOption toSlashSubCommand();
    SlashCommandBuilder toSlashCommand();
    Optional<ApplicationCommandGroup> parentCommand();
    List<ApplicationCommand> subCommands();
}
