package com.disney;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import com.disney.model.Product;
import com.disney.model.ProductType;
import com.disney.restclient.ProductProviderClient;
import java.math.BigDecimal;
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
@PactTestFor(providerName = "ProductProvider")
class ProductProviderClientTest {

  @Autowired ProductProviderClient productProviderClient;

  @Pact(consumer = "ProductManager")
  public RequestResponsePact retrieveAllProducts(PactDslWithProvider provider) {
    Map<String, String> headers = new HashMap<>();
    headers.put("Content-Type", "application/json");
    return provider
        .given("products exist")
        .uponReceiving("get all products")
        .path("/products")
        .method("GET")
        .willRespondWith()
        .status(200)
        .headers(headers)
        .body(
            "[{"
                + "  \"id\": 0,"
                + "  \"name\": \"Product 0\","
                + "  \"type\": \"SERVICE\","
                + "  \"description\": \"Product 0 desc\","
                + "  \"price\": 10.0,"
                + "  \"version\": \"v1\""
                + "},"
                + "{"
                + "  \"id\": 1,"
                + "  \"name\": \"Product 1\","
                + "  \"type\": \"SERVICE\","
                + "  \"description\": \"Product 1 desc\","
                + "  \"price\": 20.0,"
                + "  \"version\": \"v2\""
                + "}]")
        .toPact();
  }

  @Test
  @PactTestFor(pactMethod = "retrieveAllProducts", port = "9999")
  void testRetrieveAllProducts(MockServer mockServer) {
    // given
    productProviderClient.productServiceBaseUrl = mockServer.getUrl();

    // when
    List<Product> response = productProviderClient.retrieveAllProducts();

    // then
    Assertions.assertThat(response)
        .hasSize(2)
        .extracting("id", "name", "type", "description", "price", "version")
        .containsExactlyInAnyOrder(
            Tuple.tuple(0, "Product 0", ProductType.SERVICE, "Product 0 desc", BigDecimal.valueOf(10.0), "v1"),
            Tuple.tuple(1, "Product 1", ProductType.SERVICE, "Product 1 desc", BigDecimal.valueOf(20.0), "v2"));
  }
}
