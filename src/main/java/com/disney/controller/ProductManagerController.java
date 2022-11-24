package com.disney.controller;

import com.disney.model.Product;
import com.disney.service.ProductManagerService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductManagerController {

  @Autowired ProductManagerService productManagerService;

  @ApiOperation(value = "Get all products")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
  @GetMapping(value = "/products", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<Product> retrieveAllProducts() {
    return productManagerService.fetchProducts();
  }

  @ApiOperation(value = "Get product by id")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
  @GetMapping(value = "/products/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Product retrieveAllProducts(@PathVariable int id) {
    return productManagerService.fetchProductById(id);
  }
}
