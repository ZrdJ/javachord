package com.github.zrdj.javachord.modal;

import org.javacord.api.interaction.MessageComponentInteraction;

import java.util.concurrent.CompletableFuture;

public interface ApplicationModal {
    CompletableFuture<Void> respond(final MessageComponentInteraction mci);
}
