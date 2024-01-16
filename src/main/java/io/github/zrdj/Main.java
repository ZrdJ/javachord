package io.github.zrdj;

import io.github.zrdj.usage.MyCommand;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.intent.Intent;
import org.javacord.api.util.logging.ExceptionLogger;

import java.util.Set;

public class Main {
    private static DiscordApi _discord;
    private static String _token = System.getProperty("token");
    private static long _serverId = Long.parseLong(System.getProperty("serverId"));
    private static MyCommand _command = new MyCommand();

    public static void main(String[] args) {
        _discord = new DiscordApiBuilder()
                .setToken(_token)
                .setIntents(
                        Intent.GUILDS
                        , Intent.GUILD_MEMBERS
                        , Intent.GUILD_MESSAGES
                        , Intent.GUILD_BANS
                        , Intent.GUILD_INVITES
                        , Intent.GUILD_PRESENCES
                        , Intent.MESSAGE_CONTENT
                )
                .login()
                .join()
        ;
        var server = _discord.getServerById(_serverId).get();
        _discord.bulkOverwriteServerApplicationCommands(server, Set.of(_command.toSlashCommand(_discord)))
                .thenAccept(commands -> {
                    commands.forEach(c -> System.out.printf("command registered: %s (%s)%n", c.getName(), c.getId()));
                })
                .exceptionally(ExceptionLogger.get());
        _command.register(_discord);
        System.out.printf("Discord bot successfully initialized with ID %s%n", _discord.getYourself().getIdAsString());

        while (true) {
        }
    }

}