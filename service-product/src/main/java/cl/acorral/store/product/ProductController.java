package cl.acorral.store.product;

import cl.acorral.store.product.entity.Category;
import cl.acorral.store.product.entity.Product;
import cl.acorral.store.product.service.ProductService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/products")
public class ProductController {
    @Autowired
    private ProductService productService;
/*
    @GetMapping
    public ResponseEntity<List<Product>>listProduct(){
        List<Product> products = productService.listAllProducts();
        if(products.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(products);

    }
*/
    @GetMapping
    public ResponseEntity<List<Product>>listProduct(@RequestParam(name = "categoryId",required=false) Long categoryId){
        List<Product> products=new ArrayList<>();
        if(categoryId==null){
         products = productService.listAllProducts();
            if(products.isEmpty()){
                return ResponseEntity.noContent().build();
            }
    }else{
            products = productService.findByCategory(Category.builder().id(categoryId).build());
            if(products.isEmpty()){
                return ResponseEntity.notFound().build();
            }
        }


        return ResponseEntity.ok(products);

    }
@GetMapping(value="/{id}")
public ResponseEntity<Product> getProduct(@PathVariable("id") Long id){
        Product p = productService.getProduct(id);
        if(null == p){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(p);
}
@PostMapping
public ResponseEntity<Product> createProduct(@RequestBody Product p){
        Product pCreated = productService.createProduct(p);
        return ResponseEntity.status(HttpStatus.CREATED).body(pCreated);

}
@PutMapping(value = "/{id}")
public ResponseEntity<Product> updateProduct(@PathVariable("id") Long id, @RequestBody Product p){
        p.setId(id);
        Product pDB= productService.updateProduct(p);
        if(pDB == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pDB);
}

@DeleteMapping(value = "/{id}")
public ResponseEntity<Product> deleteProduct(@PathVariable("id") Long id){
        Product pDeleted = productService.deleteProduct(id);
    if(pDeleted == null){
        return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(pDeleted);
}
@PutMapping(value = "/{id}/stock")
public ResponseEntity<Product> updateStockProducts(@PathVariable("id") Long id,@RequestParam(name = "quantity",required = true) Double quantity){
    Product product = productService.updateStock(id,quantity);
    if(null == product){
        return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(product);
}
}
