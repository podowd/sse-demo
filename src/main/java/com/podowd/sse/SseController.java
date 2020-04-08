package com.podowd.sse;

import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;

@RestController
@RequestMapping("/sse")
public class SseController {

    @GetMapping("/events")
    public Flux<ServerSentEvent<String>> streamEvents() {

        return Flux.interval(Duration.ofSeconds(4))
                .map(sequence -> ServerSentEvent.<String>builder()
                        .id(String.valueOf(sequence))
                        .event("event")
                        .data("Event - " + sequence.longValue())
                        .build());
    }
}