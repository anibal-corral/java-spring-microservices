package cl.acorral.store.product.service;

import cl.acorral.store.product.entity.Category;
import cl.acorral.store.product.entity.Product;

import java.util.List;

/**
Esta interfaz define los metodos que tendra la capa de servicios
 */
public interface ProductService {
    public List<Product> listAllProducts();
    public Product getProduct(Long id);
    public Product createProduct(Product product);
    public Product updateProduct(Product product);
    public Product deleteProduct(Long id);
    public List<Product> findByCategory(Category category);
    public Product updateStock(Long id, Double quantity);

}
