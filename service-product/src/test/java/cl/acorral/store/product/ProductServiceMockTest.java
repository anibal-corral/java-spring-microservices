package cl.acorral.store.product;

import cl.acorral.store.product.entity.Category;
import cl.acorral.store.product.entity.Product;
import cl.acorral.store.product.repository.ProductRepository;
import cl.acorral.store.product.service.ProductService;
import cl.acorral.store.product.service.ProductServiceImplement;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class ProductServiceMockTest {
    @Mock
    private ProductRepository productRepository;

    private ProductService productService;
    @BeforeEach
    public void setup(){
        MockitoAnnotations.initMocks(this);
        productService = new ProductServiceImplement(productRepository);
        Product laptop = Product.builder()
                .id(1L)
                .name("Laptop XPS")
                .category(Category.builder().id(1L).build())
                .price(Double.parseDouble("123"))
                .stock(Double.parseDouble("10"))
                .build();

        Mockito.when(productRepository.findById(1L))
                .thenReturn(Optional.of(laptop));
        Mockito.when(productRepository.save(laptop)).thenReturn(laptop);
    }
@Test
    public void whenValidGetID_thenReturnProduct(){
        Product found = productService.getProduct(1L);
        Assertions.assertThat(found.getName()).isEqualTo("Laptop XPS");
    }

    @Test
    public void whenValidUpdateStock_ThenReturnNewStock(){
        Product newStock = productService.updateStock(1L,Double.parseDouble("5"));
        Assertions.assertThat(newStock.getStock()).isEqualTo(15);
    }

}
