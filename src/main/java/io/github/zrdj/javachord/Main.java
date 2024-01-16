package io.github.zrdj.javachord;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.intent.Intent;

public class Main {
    private static DiscordApi _discord;
    private static String _token = System.getProperty("token");
    private static long _serverId = Long.parseLong(System.getProperty("serverId"));
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
        Javachord.Instance.Get.register(_discord, server);
        System.out.printf("Discord bot successfully initialized with ID %s%n", _discord.getYourself().getIdAsString());

        while (true) {
        }
    }

}