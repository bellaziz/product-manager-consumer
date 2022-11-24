package com.disney.model;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class Product {
  private int id;
  private String name;
  private ProductType type;
  private String description;
  private BigDecimal price;
  private String version;
}
