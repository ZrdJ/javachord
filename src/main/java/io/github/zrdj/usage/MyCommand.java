package io.github.zrdj.usage;

import io.github.zrdj.command.ApplicationCommand;
import io.github.zrdj.command.ApplicationCommandBase;

import java.util.Optional;

public class MyCommand extends ApplicationCommand.ParentCommand {
    public MyCommand() {
        super("my command",
                "my command description"
        );
    }

    @Override
    public Optional<ApplicationCommandBase> subCommand() {
        return Optional.of(new MySubCommand(this));
    }
}
