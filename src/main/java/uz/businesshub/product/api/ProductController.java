package uz.businesshub.product.api;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.businesshub.product.api.dto.CreateProductRequest;
import uz.businesshub.product.api.dto.ProductResponse;
import uz.businesshub.product.service.ProductService;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductResponse> create(
            @RequestHeader("X-Company-Id") UUID companyId,
            @Valid @RequestBody CreateProductRequest request) {
        ProductResponse created = productService.create(companyId, request);
        return ResponseEntity
                .created(URI.create("/api/v1/products/" + created.id()))
                .body(created);
    }

    @GetMapping("/{id}")
    public ProductResponse getById(
            @RequestHeader("X-Company-Id") UUID companyId,
            @PathVariable UUID id) {
        return productService.getById(companyId, id);
    }
}