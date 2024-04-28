package com.github.zrdj.javachord;

import org.javacord.api.DiscordApi;
import org.javacord.api.entity.server.Server;

import java.util.Optional;

public interface DiscordInstanceAware {
    default DiscordApi discordInstance() {
        return Javachord.Instance.Get.discordInstance;
    }
    default Optional<Server> serverInstance() {
        return Javachord.Instance.Get.serverInstance;
    }
}
