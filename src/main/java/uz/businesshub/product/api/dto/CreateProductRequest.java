package uz.businesshub.product.api.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record CreateProductRequest(
        @NotBlank @Size(max = 64)  String sku,
        @Size(max = 64)            String barcode,
        @NotBlank @Size(max = 255) String name,
        @Size(max = 5000)          String description,
        @NotNull @DecimalMin("0.00") @Digits(integer = 17, fraction = 2) BigDecimal price
) {}