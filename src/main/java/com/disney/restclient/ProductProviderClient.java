package com.disney.restclient;

import com.disney.model.Product;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ProductProviderClient {

  private RestTemplate restTemplate;

  @Value("${product.provider.base.url}")
  public String productServiceBaseUrl;


  public ProductProviderClient() {
    this.restTemplate = new RestTemplate();
  }

  public List<Product> retrieveAllProducts() {
    return Arrays.asList(
        Objects.requireNonNull(restTemplate.getForObject(productServiceBaseUrl+"/products", Product[].class)));
  }

  public Product retrieveProductById(int id) {
    return restTemplate.getForObject(productServiceBaseUrl+"/products/{id}", Product.class, id);
  }
}
