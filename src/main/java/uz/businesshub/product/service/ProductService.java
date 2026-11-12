package uz.businesshub.product.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.businesshub.product.api.dto.CreateProductRequest;
import uz.businesshub.product.api.dto.ProductResponse;
import uz.businesshub.product.domain.Product;
import uz.businesshub.product.exception.DuplicateSkuException;
import uz.businesshub.product.exception.ProductNotFoundException;
import uz.businesshub.product.repository.ProductRepository;

import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public ProductResponse create(UUID companyId, CreateProductRequest request) {
        if (productRepository.existsByCompanyIdAndSku(companyId, request.sku())) {
            throw new DuplicateSkuException(request.sku());
        }
        Product product = Product.create(
                companyId,
                request.sku(),
                request.barcode(),
                request.name(),
                request.description(),
                request.price());
        productRepository.save(product);
        return ProductResponse.from(product);
    }

    public ProductResponse getById(UUID companyId, UUID id) {
        return productRepository.findByIdAndCompanyId(id, companyId)
                .map(ProductResponse::from)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }
}