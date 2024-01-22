package com.github.zrdj.javachord.usage;

import com.github.zrdj.javachord.Javachord;
import com.github.zrdj.javachord.command.ApplicationCommandBehavior1;
import com.github.zrdj.javachord.command.ApplicationCommandGroupBehavior;
import org.javacord.api.entity.message.component.ActionRow;
import org.javacord.api.entity.message.component.Button;
import org.javacord.api.event.interaction.MessageComponentCreateEvent;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.listener.interaction.MessageComponentCreateListener;
import org.javacord.api.util.logging.ExceptionLogger;

public class MySubCommand extends ApplicationCommandBehavior1<String> implements MessageComponentCreateListener {
    private final MyModal _modal = new MyModal();
    public MySubCommand(final ApplicationCommandGroupBehavior parent) {
        super("my sub command",
                "my sub command description",
                parent,
                Javachord.Command.Option.Required.stringOption("my sub option name", "my sub option description")
        );
    }
    @Override
    public void onComponentCreate(MessageComponentCreateEvent event) {
        event.getMessageComponentInteractionWithCustomId("button")
                .ifPresent(_modal::respond);
    }

    @Override
    protected void onInteraction(SlashCommandInteraction interaction, String s) {
        interaction.createImmediateResponder()
                .addComponents(ActionRow.of(Button.primary("button", "click me")))
                .setContent("Done")
                .respond()
                .thenRun(() -> System.out.printf("command %s: option %s: value was %s%n", _name, _option1.name(), s))
                .exceptionally(ExceptionLogger.get());
    }
}
