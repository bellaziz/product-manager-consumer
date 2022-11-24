package com.disney.restclient;

import com.disney.model.Product;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ProductProviderRestClient {

  private RestTemplate restTemplate;

  @Value("${product.provider.fetch.all.products}")
  private String allProductsUrl;

  @Value("${product.provider.fetch.product}")
  private String productByIdUrl;

  public ProductProviderRestClient() {
    this.restTemplate = new RestTemplate();
  }

  public List<Product> retrieveAllProducts() {
    return Arrays.asList(
        Objects.requireNonNull(restTemplate.getForObject(allProductsUrl, Product[].class)));
  }

  public Product retrieveProductById(int id) {
    return restTemplate.getForObject(productByIdUrl, Product.class, id);
  }
}
