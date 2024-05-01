package com.vaatu.bots.dixtro.command;

import org.springframework.stereotype.Component;

import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import discord4j.core.spec.InteractionReplyEditSpec;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class PingCommand implements SlashCommand {

    @Override
    public String getName() {
        return "ping";
    }

    @Override
    public String getDescription() {
        return "ping pong!";
    }

    @Override
    public Mono<Void> execute(ChatInputInteractionEvent event) {
        event.reply("Pong!").withEphemeral(true).block();

        return event.editReply(InteractionReplyEditSpec.builder()
                .build()
                .withContentOrNull("Pong Pong!")).then();
    }

}
