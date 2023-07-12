package com.example.springdataredis.repository;

import com.example.springdataredis.entity.Product;
import java.util.List;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ProductDao {
  public static final String HASH_KEY = "Product";

  private final RedisTemplate redisTemplate;

  public ProductDao(RedisTemplate redisTemplate) {
    this.redisTemplate = redisTemplate;
  }

  public Product save(Product product) {
    redisTemplate.opsForHash().put(HASH_KEY, product.getId(), product);
    return product;
  }

  public List<Product> findAll() {
    return redisTemplate.opsForHash().values(HASH_KEY);
  }

  public Product findProductById(int id) {
    System.out.println("called findProductById from DB");
    return (Product) redisTemplate.opsForHash().get(HASH_KEY, id);
  }

  public String deleteProduct(int id) {
    redisTemplate.opsForHash().delete(HASH_KEY, id);
    return "product removed";
  }
}
