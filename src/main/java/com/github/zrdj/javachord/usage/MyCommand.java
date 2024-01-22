package com.github.zrdj.javachord.usage;

import com.github.zrdj.javachord.command.ApplicationCommand;
import com.github.zrdj.javachord.command.ApplicationCommandGroupBehavior;

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
