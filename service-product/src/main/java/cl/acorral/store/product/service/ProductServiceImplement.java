package cl.acorral.store.product.service;

import cl.acorral.store.product.entity.Category;
import cl.acorral.store.product.entity.Product;
import cl.acorral.store.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImplement implements ProductService{

    //@Autowired
    private final ProductRepository productRepository;

    @Override
    public List<Product> listAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProduct(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public Product createProduct(Product product) {
        product.setStatus("Created");
        product.setCreatedAt(new Date());
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Product product) {
        Product pDB = getProduct(product.getId());
        if(null==pDB){
            return null;
        }
        pDB.setName(product.getName());
        pDB.setDescription(product.getDescription());
        pDB.setCategory(product.getCategory());
        pDB.setPrice(product.getPrice());
        return productRepository.save(pDB) ;
    }

    @Override
    public Product deleteProduct(Long id) {
        Product pDB = getProduct(id);
        if(null==pDB){
            return null;
        }
        productRepository.delete(pDB);
        return null;

    }

    @Override
    public List<Product> findByCategory(Category category) {

        return productRepository.findByCategory(category);
    }

    @Override
    public Product updateStock(Long id, Double quantity) {
        Product pDB = getProduct(id);
        if(null==pDB){
            return null;
        }
        pDB.setStock(pDB.getStock()+quantity);

        return productRepository.save(pDB);
    }
}
