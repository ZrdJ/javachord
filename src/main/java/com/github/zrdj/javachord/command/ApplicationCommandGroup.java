package com.github.zrdj.javachord.command;

public interface ApplicationCommandGroup extends ApplicationCommand {
    void registerSubCommand(final ApplicationCommand command);
}
