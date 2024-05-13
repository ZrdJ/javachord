package com.github.zrdj.javachord.command.plugin;

import org.javacord.api.interaction.SlashCommandInteraction;

public interface ApplicationCommandTriggerPlugin {
    boolean triggered(final SlashCommandInteraction interaction);

    static ApplicationCommandTriggerPlugin defaultPlugin(final String fqdn) {
        return new FQDNTrigger(fqdn);
    }

    final class FQDNTrigger implements ApplicationCommandTriggerPlugin {
        private final String _fqdn;

        public FQDNTrigger(final String fqdn) {
            _fqdn = fqdn;
        }

        @Override
        public boolean triggered(final SlashCommandInteraction interaction) {
            return interaction.getFullCommandName().equalsIgnoreCase(_fqdn);
        }
    }
}
