package com.disney;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import com.disney.model.Ticket;
import com.disney.restclient.TicketProviderClient;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "TicketProvider")
class TicketProviderClientTest {

  @Autowired TicketProviderClient ticketProviderClient;

  @Pact(consumer = "TicketLinking")
  public RequestResponsePact retrieveAllTickets(PactDslWithProvider provider) {
    Map<String, String> headers = new HashMap<>();
    headers.put("Content-Type", "application/json");
    return provider
        .given("Tickets exist")
        .uponReceiving("get all Tickets")
        .path("/tickets")
        .method("GET")
        .willRespondWith()
        .status(200)
        .headers(headers)
        .body(
            "[{"
                + "  \"ticketVisualId\": \"000000001\","
                + "  \"guestName\": \"Micky\","
                + "  \"shared\": false,"
                + "  \"type\": \"annual-pass\","
                + "  \"label\": \"annual pass ticket\","
                + "  \"remainingUse\": 3,"
                + "  \"startDateTime\": \"2022-11-24 15:41:25\","
                + "  \"endDateTime\": \"2023-11-24 15:41:25\""
                + "},"
                + "{"
                + "  \"ticketVisualId\": \"000000002\","
                + "  \"guestName\": \"Micky\","
                + "  \"shared\": false,"
                + "  \"type\": \"pass-en-scene\","
                + "  \"label\": \"pass en scene ticket\","
                + "  \"remainingUse\": 5,"
                + "  \"startDateTime\": \"2022-11-24 15:41:25\","
                + "  \"endDateTime\": \"2023-11-24 15:41:25\""
                + "}]")
        .toPact();
  }

  @Test
  @PactTestFor(pactMethod = "retrieveAllTickets", port = "9999")
  void testRetrieveAllTickets(MockServer mockServer) {
    // given
    ticketProviderClient.ticketProviderServiceBaseUrl = mockServer.getUrl();

    // when
    List<Ticket> response = ticketProviderClient.retrieveAllTickets();

    // then
    Assertions.assertThat(response)
        .hasSize(2)
        .extracting(
            "ticketVisualId",
            "guestName",
            "shared",
            "type",
            "label",
            "remainingUse",
            "startDateTime",
            "endDateTime")
        .containsExactlyInAnyOrder(
            Tuple.tuple(
                "000000001",
                "Micky",
                false,
                "annual-pass",
                "annual pass ticket",
                3,
                LocalDateTime.of(2022, 11, 24, 15, 41, 25),
                LocalDateTime.of(2023, 11, 24, 15, 41, 25)),
            Tuple.tuple(
                "000000002",
                "Micky",
                false,
                "pass-en-scene",
                "pass en scene ticket",
                5,
                LocalDateTime.of(2022, 11, 24, 15, 41, 25),
                LocalDateTime.of(2023, 11, 24, 15, 41, 25)));
  }

  @Pact(consumer = "TicketLinking")
  public RequestResponsePact retrieveTicketById(PactDslWithProvider provider) {
    Map<String, String> headers = new HashMap<>();
    headers.put("Content-Type", "application/json");
    return provider
        .given("Ticket visualId 000000001 exists")
        .uponReceiving("get Ticket by visualId")
        .path("/tickets/000000001")
        .method("GET")
        .willRespondWith()
        .status(200)
        .headers(headers)
        .body(
            "{"
                + "  \"ticketVisualId\": \"000000001\","
                + "  \"guestName\": \"Micky\","
                + "  \"shared\": false,"
                + "  \"type\": \"annual-pass\","
                + "  \"label\": \"annual pass ticket\","
                + "  \"remainingUse\": 3,"
                + "  \"startDateTime\": \"2022-11-24 15:41:25\","
                + "  \"endDateTime\": \"2023-11-24 15:41:25\""
                + "}")
        .toPact();
  }

  @Test
  @PactTestFor(pactMethod = "retrieveTicketById", port = "9999")
  void testTicketById(MockServer mockServer) {
    // given
    ticketProviderClient.ticketProviderServiceBaseUrl = mockServer.getUrl();

    // when
    Ticket response = ticketProviderClient.retrieveTicketByVId("000000001");

    // then
    Assertions.assertThat(response)
        .isEqualTo(
            new Ticket(
                "000000001",
                "Micky",
                false,
                "annual-pass",
                "annual pass ticket",
                3,
                LocalDateTime.of(2022, 11, 24, 15, 41, 25),
                LocalDateTime.of(2023, 11, 24, 15, 41, 25)));
  }
}
