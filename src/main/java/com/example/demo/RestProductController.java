package com.example.demo;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.Dto.ProductDto;
import com.example.demo.Model.Product;
import com.example.demo.Repository.ProductsRepository;

@RestController
@RequestMapping("/api/products")
public class RestProductController {

    @Autowired
    private ProductsRepository repo;

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = repo.findAll();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Product> createProduct(@Valid @RequestBody ProductDto productDto) {
        Product product = new Product();
        product.setName(productDto.getName());
        product.setCompany(productDto.getCompany());
        product.setType(productDto.getType());
        product.setPrice(productDto.getPrice());

        Product savedProduct = repo.save(product);
        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable int id) {
        Product product = repo.findById(id)
                .orElse(null);

        if (product != null) {
            return new ResponseEntity<>(product, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable int id, @Valid @RequestBody ProductDto productDto) {
        Product product = repo.findById(id)
                .orElse(null);

        if (product != null) {
            product.setName(productDto.getName());
            product.setCompany(productDto.getCompany());
            product.setType(productDto.getType());
            product.setPrice(productDto.getPrice());

            Product updatedProduct = repo.save(product);
            return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable int id) {
        Product product = repo.findById(id)
                .orElse(null);

        if (product != null) {
            repo.delete(product);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Search
    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProduct(@RequestParam String keyword) {
        List<Product> products = repo.findByNameContainingIgnoreCase(keyword);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}
