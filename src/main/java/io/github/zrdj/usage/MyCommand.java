package io.github.zrdj.usage;

import io.github.zrdj.command.ApplicationCommandGroup;
import io.github.zrdj.ApplicationCommand;

import java.util.Optional;

public class MyCommand extends ApplicationCommandGroup.Of {
    public MyCommand() {
        super("my command",
                "my command description"
        );
    }

    @Override
    public Optional<ApplicationCommand> subCommand() {
        return Optional.of(new MySubCommand(this));
    }
}
