package cl.acorral.store.product.repository;

import cl.acorral.store.product.entity.Category;
import cl.acorral.store.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {
    public List<Product> findByCategory(Category category);
}
