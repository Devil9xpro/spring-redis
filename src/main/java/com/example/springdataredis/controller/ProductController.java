package com.example.springdataredis.controller;

import com.example.springdataredis.entity.Product;
import com.example.springdataredis.repository.ProductDao;
import java.util.List;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {

  private final ProductDao productDao;

  public ProductController(ProductDao productDao) {
    this.productDao = productDao;
  }

  @PostMapping
  public Product save(@RequestBody Product product) {
    return productDao.save(product);
  }

  @GetMapping
  public List<Product> save() {
    return productDao.findAll();
  }

  @GetMapping("/{id}")
  @Cacheable(key = "#id", value = "Product", unless = "#result.price>1000") //enable caching: just fetching from db for the first time
  public Product findProduct(@PathVariable int id) {
    return productDao.findProductById(id);
  }

  @DeleteMapping("/{id}")
  public String remove(@PathVariable int id) {
    return productDao.deleteProduct(id);
  }
}
