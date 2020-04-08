package com.podowd.sse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.springframework.http.MediaType.TEXT_EVENT_STREAM;

@SpringBootTest
class SseApplicationTests {

    WebTestClient client;

    @BeforeEach
    void setUp(ApplicationContext context) {
        client = WebTestClient.bindToController(new SseController()).build();
    }

    @Test
    public void testSseController() throws Exception {

        FluxExchangeResult<String> result = client.get().uri("/sse/events")
                .accept(TEXT_EVENT_STREAM)
                .exchange()
                .expectStatus().isOk()
                .returnResult(String.class);

        Flux<String> eventFlux = result.getResponseBody();

        StepVerifier.create(eventFlux)
                .expectNext("Event - 0")
                .expectNext("Event - 1")
                .expectNext("Event - 2")
                .expectNext("Event - 3")
        .thenCancel()
                .verify();
    }
}
