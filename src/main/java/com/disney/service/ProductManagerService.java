package com.disney.service;

import com.disney.model.Product;
import com.disney.restclient.ProductProviderClient;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductManagerService {

  private ProductProviderClient productProviderRestClient;

  @Autowired
  public ProductManagerService(ProductProviderClient productProviderRestClient) {
    this.productProviderRestClient = productProviderRestClient;
  }

  public List<Product> fetchProducts() {
    return productProviderRestClient.retrieveAllProducts();
  }

  public Product fetchProductById(int id) {
    return productProviderRestClient.retrieveProductById(id);
  }
}
