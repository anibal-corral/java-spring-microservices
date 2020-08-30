package cl.acorral.store.product;


import cl.acorral.store.product.entity.Category;
import cl.acorral.store.product.entity.Product;
import cl.acorral.store.product.repository.ProductRepository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Date;
import java.util.List;

@DataJpaTest
public class ProductRepositoryMockTest {
    @Autowired
    private ProductRepository productRepository;
    @Test
public void whenFindByCategory_thenReturnListProducts(){
    Product product = Product.builder()
            .name("Laptop XPS")
            .category(Category.builder().id(1L).build())
            .description("")
            .stock(Double.parseDouble("10"))
            .price(Double.parseDouble("1240.99"))
            .status("Created")
            .createdAt(new Date())
            .build();
    productRepository.save(product);
    List<Product> found= productRepository.findByCategory(product.getCategory());
    Assertions.assertThat(found.size()).isEqualTo(3);
}


}
