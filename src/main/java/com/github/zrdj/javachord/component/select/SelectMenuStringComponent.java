package com.github.zrdj.javachord.component.select;

import com.github.zrdj.javachord.Javachord;
import com.github.zrdj.javachord.component.MessageComponentBehavior;
import org.javacord.api.entity.message.component.ComponentType;
import org.javacord.api.entity.message.component.LowLevelComponent;
import org.javacord.api.entity.message.component.SelectMenuBuilder;
import org.javacord.api.interaction.MessageComponentInteraction;
import org.javacord.api.interaction.SelectMenuInteraction;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class SelectMenuStringComponent<T> extends MessageComponentBehavior {
    private final List<SelectMenuChoice<T>> _choices;
    private final Map<String, SelectMenuChoice<T>> _choiceByValue;

    public SelectMenuStringComponent(final String identifier, final List<SelectMenuChoice<T>> choices) {
        super(identifier);
        Javachord.Constraint.ensureChoices(choices);
        _choices = choices;
        _choiceByValue = choices.stream().collect(Collectors.toMap(SelectMenuChoice::value, v -> v));
    }

    @Override
    protected final void onInteraction(final MessageComponentInteraction event) {
        var interaction = event.asSelectMenuInteraction().get();
        var choices = interaction.getChosenOptions().stream().map(o -> _choiceByValue.get(o.getValue())).collect(Collectors.toList());
        onChoicesSelected(interaction, choices);
    }

    protected abstract void onChoicesSelected(final SelectMenuInteraction interaction, final List<SelectMenuChoice<T>> choices);

    @Override
    public final LowLevelComponent component() {
        var options = _choices.stream().map(SelectMenuChoice::toSelectMenuOption).collect(Collectors.toList());
        return new SelectMenuBuilder(ComponentType.SELECT_MENU_STRING, _identifier)
                .addOptions(options)
                .setDisabled(_disabled)
                .build();
    }
}
