package mn7.spring.mn7_springboot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import mn7.spring.mn7_springboot.domain.Products;
import mn7.spring.mn7_springboot.repository.ProductRepository;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Products> getAllProducts() {
        return productRepository.findAll();
    }

    public Products saveProducts(Products product) {
        return productRepository.save(product);
    }

    public Optional<Products> getProductById(long id) {
        return this.productRepository.findById(id);
    }

    public void deleteProduct(long id) {
        this.productRepository.deleteById(id);
    }
}
