package io.github.zrdj.usage;

import io.github.zrdj.ApplicationCommand;
import io.github.zrdj.command.ApplicationCommandGroupBehavior;

public class MyCommand extends ApplicationCommandGroupBehavior {
    public MyCommand() {
        super("my command",
                "my command description"
        );
    }

    @Override
    protected ApplicationCommand internalSubCommand() {
        return new MySubCommand(this);
    }
}
