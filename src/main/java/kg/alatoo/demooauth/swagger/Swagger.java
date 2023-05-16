package kg.alatoo.demooauth.swagger;


import kg.alatoo.demooauth.model.Products;
import kg.alatoo.demooauth.repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/swagger")
public class Swagger {

    @Autowired
    ProductRepository productRepository;

    @GetMapping("/products")
    public List<Products> getAllPtoducts(){
        return productRepository.findByOrderByIdAsc();
    }
    @GetMapping("/product/{id}")
    public Optional<Products> getProductById(@PathVariable("id") Long id){
        return productRepository.findById(id);
    }
    @PutMapping("/products/{id}")
    public Products updateById(@PathVariable("id") Long id, @ModelAttribute Products products){
        productRepository.deleteById(id);
        products.setId(id);
        return productRepository.save(products);
    }
    @PostMapping("/product")
    public Products saveProduct(@ModelAttribute Products products){
        return productRepository.save(products);
    }



}
