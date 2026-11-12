package uz.businesshub.product.api.dto;

import uz.businesshub.product.domain.Product;
import uz.businesshub.product.domain.ProductStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record ProductResponse(
        UUID id,
        String sku,
        String barcode,
        String name,
        String description,
        BigDecimal price,
        ProductStatus status,
        Instant createdAt,
        Instant updatedAt
) {
    public static ProductResponse from(Product p) {
        return new ProductResponse(
                p.getId(), p.getSku(), p.getBarcode(), p.getName(),
                p.getDescription(), p.getPrice(), p.getStatus(),
                p.getCreatedAt(), p.getUpdatedAt());
    }
}