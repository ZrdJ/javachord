package io.github.zrdj.usage;

import io.github.zrdj.Javachord;
import io.github.zrdj.command.ApplicationCommandGroupBehavior;
import io.github.zrdj.command.ApplicationCommandBehavior1;
import org.javacord.api.entity.message.component.ActionRow;
import org.javacord.api.entity.message.component.Button;
import org.javacord.api.entity.message.component.TextInput;
import org.javacord.api.entity.message.component.TextInputStyle;
import org.javacord.api.event.interaction.MessageComponentCreateEvent;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.listener.interaction.MessageComponentCreateListener;
import org.javacord.api.util.logging.ExceptionLogger;

public class MySubCommand extends ApplicationCommandBehavior1<String> implements MessageComponentCreateListener {
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
                .ifPresent(mci ->
                        mci.respondWithModal("modal", "test modal", ActionRow.of(TextInput.create(TextInputStyle.SHORT, "field", "label")))
                );
    }

    @Override
    protected void onInteraction(SlashCommandInteraction interaction, String s) {
        interaction.createImmediateResponder()
                .addComponents(ActionRow.of(Button.primary("button", "click me")))
                .setContent("Done")
                .respond()
                .thenRun(() -> System.out.printf("command %s: option %s: value was %s%n", _name, _option1.getName(), s))
                .exceptionally(ExceptionLogger.get());
    }
}
