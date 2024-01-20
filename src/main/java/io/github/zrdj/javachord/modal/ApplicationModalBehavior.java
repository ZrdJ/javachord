package io.github.zrdj.javachord.modal;

import io.github.zrdj.javachord.Javachord;
import org.javacord.api.entity.message.component.ActionRow;
import org.javacord.api.entity.message.component.HighLevelComponent;
import org.javacord.api.event.interaction.ModalSubmitEvent;
import org.javacord.api.interaction.MessageComponentInteraction;
import org.javacord.api.listener.interaction.ModalSubmitListener;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

abstract class ApplicationModalBehavior implements ApplicationModal, ModalSubmitListener {
    private final String _identifier;
    private final String _name;
    private final List<HighLevelComponent> _discordOptions;

    ApplicationModalBehavior(final String identifier, final String name, final List<ApplicationModalOption<?>> options) {
        Javachord.Constraint.ensureIdentifier(identifier);
        _identifier = identifier;
        _name = name;
        _discordOptions = options.stream().map(o -> ActionRow.of(o.component())).collect(Collectors.toList());
        Javachord.Instance.Get.addListener(this);
    }

    @Override
    public CompletableFuture<Void> respond(MessageComponentInteraction mci) {
        return mci.respondWithModal(_identifier, _name, _discordOptions);
    }

    @Override
    public final void onModalSubmit(ModalSubmitEvent event) {
        if (!event.getModalInteraction().getCustomId().equalsIgnoreCase(_identifier)) {
            return;
        }
        onModalInteractionTriggered(event);
    }

    protected abstract void onModalInteractionTriggered(ModalSubmitEvent event);

}
