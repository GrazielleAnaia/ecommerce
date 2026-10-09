package com.productionready.ecommerce.service;


import com.productionready.ecommerce.dto.ProductRequest;
import com.productionready.ecommerce.dto.ProductResponse;
import com.productionready.ecommerce.exception.ProductNotFoundException;
import com.productionready.ecommerce.model.Product;
import com.productionready.ecommerce.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public ProductResponse create(ProductRequest request) {
        Product product = new Product(request.name(), request.price(), request.stockQuantity());
        return ProductResponse.from(repository.save(product));
    }

    public Page<ProductResponse> findAllProducts(Pageable pageable) {
        return repository.findAll(pageable).map(ProductResponse::from);
    }

    private Product getProductOrThrow(Long id) {
        return repository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
    }

    public ProductResponse findById(Long id) {
        return ProductResponse.from(getProductOrThrow(id));
    }

    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ProductNotFoundException(id);
        }
        repository.deleteById(id);
    }

}
