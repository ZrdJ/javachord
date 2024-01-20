package io.github.zrdj.javachord.usage;

import io.github.zrdj.javachord.command.ApplicationCommand;
import io.github.zrdj.javachord.command.ApplicationCommandGroupBehavior;

import java.util.List;

public class MyCommand extends ApplicationCommandGroupBehavior {
    public MyCommand() {
        super("my command",
                "my command description"
        );
    }

    @Override
    public List<ApplicationCommand> subCommands() {
        return List.of(
                new MySubCommand(this)
        );
    }
}
