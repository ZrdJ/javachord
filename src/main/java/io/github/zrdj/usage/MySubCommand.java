package io.github.zrdj.usage;

import io.github.zrdj.Javachord;
import io.github.zrdj.command.ApplicationCommand;
import io.github.zrdj.command.ApplicationCommand1;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.util.logging.ExceptionLogger;

public class MySubCommand extends ApplicationCommand1.SubCommand<String> {
    public MySubCommand(final ApplicationCommand parent) {
        super("my sub command",
                "my sub command description",
                parent,
                Javachord.Option.stringRequired("my sub option name", "my sub option description")
        );
    }

    @Override
    public void onInteraction(final SlashCommandInteraction interaction, final String s) {
        interaction.createImmediateResponder()
                .setContent("Done")
                .respond()
                .thenRun(() -> System.out.printf("command %s: option %s: value was %s%n", _name, _option1.name(), s))
                .exceptionally(ExceptionLogger.get());
    }
}
