package io.github.zrdj.usage;

import io.github.zrdj.ApplicationCommand;
import io.github.zrdj.command.ApplicationCommandGroupBehavior;
import org.javacord.api.DiscordApi;

public class MyCommand extends ApplicationCommandGroupBehavior {
    public MyCommand() {
        super("my command",
                "my command description"
        );
    }

    @Override
    protected void onRegisterInternal(DiscordApi discordApi) {

    }

    @Override
    protected ApplicationCommand subCommandInternal() {
        return new MySubCommand(this);
    }
}
