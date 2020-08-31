package cl.acorral.store.product.controller;

import cl.acorral.store.product.entity.Category;
import cl.acorral.store.product.entity.Product;
import cl.acorral.store.product.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
public ResponseEntity<Product> createProduct(@Valid @RequestBody Product p, BindingResult result){
        if(result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
        }
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

private String formatMessage(BindingResult result){
        List<Map<String,String>> errors = result.getFieldErrors().stream()
                .map(err -> {
                    Map<String,String> error = new HashMap<>();
                    error.put(err.getField(),err.getDefaultMessage());
                    return error;
                }).collect(Collectors.toList());

        ErrorMessage errorMessage = ErrorMessage.builder()
                .code("01")
                .messages(errors).build();
        //Este fragmento de codigo es para devolver el objeto en un json
    ObjectMapper mapper = new ObjectMapper();
    String jsonString ="";
    try {
        jsonString = mapper.writeValueAsString(errorMessage);
    }catch (JsonProcessingException e){
        e.printStackTrace();
    }
    return jsonString;

}
}
