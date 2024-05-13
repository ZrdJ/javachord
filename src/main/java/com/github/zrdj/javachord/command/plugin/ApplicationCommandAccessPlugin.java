package com.github.zrdj.javachord.command.plugin;

import com.github.zrdj.javachord.command.ApplicationCommand;
import org.javacord.api.entity.user.User;

public interface ApplicationCommandAccessPlugin {

    boolean authorized(final User user, final ApplicationCommand command);

    static ApplicationCommandAccessPlugin defaultPlugin() {
        return new BypassAccess();
    }

    final class BypassAccess implements ApplicationCommandAccessPlugin {

        @Override
        public boolean authorized(final User user, final ApplicationCommand command) {
            return true;
        }
    }
}
