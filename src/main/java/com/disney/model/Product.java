package com.disney.model;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
  private int id;
  private String name;
  private ProductType type;
  private String description;
  private BigDecimal price;
  private String version;
}
