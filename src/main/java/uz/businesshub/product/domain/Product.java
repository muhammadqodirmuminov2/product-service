package uz.businesshub.product.domain;

import jakarta.persistence.*;
import uz.businesshub.product.exception.VersionConflictException;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "products")
public class Product {

    @Id
    private UUID id;

    @Column(name = "company_id", nullable = false, updatable = false)
    private UUID companyId;

    @Column(nullable = false, length = 64)
    private String sku;

    @Column(length = 64)
    private String barcode;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ProductStatus status;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @Version
    private Long version;

    protected Product() {
        // JPA uchun
    }

    public static Product create(UUID companyId, String sku, String barcode,
                                 String name, String description, BigDecimal price) {
        Product p = new Product();
        p.id = UUID.randomUUID();
        p.companyId = Objects.requireNonNull(companyId);
        p.sku = sku;
        p.barcode = barcode;
        p.name = name;
        p.description = description;
        p.changePrice(price);
        p.status = ProductStatus.ACTIVE;
        return p;
    }

    public void changePrice(BigDecimal newPrice) {
        if (newPrice == null || newPrice.signum() < 0) {
            throw new IllegalArgumentException("Price must be >= 0");
        }
        this.price = newPrice;
    }

    public void rename(String newName) {
        this.name = newName;
    }

    public void deactivate() {
        this.status = ProductStatus.INACTIVE;
    }

    public void checkVersion(long expectedVersion) {
        if (this.version == null || this.version != expectedVersion) {
            throw new VersionConflictException(expectedVersion, this.version == null ? -1 : this.version);
        }
    }

    public void update(String barcode, String name, String description, BigDecimal price) {
        this.barcode = barcode;
        this.name = name;
        this.description = description;
        changePrice(price);
    }

    @PrePersist
    void onCreate() {
        Instant now = Instant.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    void onUpdate() {
        this.updatedAt = Instant.now();
    }

    public UUID getId() { return id; }
    public UUID getCompanyId() { return companyId; }
    public String getSku() { return sku; }
    public String getBarcode() { return barcode; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public BigDecimal getPrice() { return price; }
    public ProductStatus getStatus() { return status; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
    public Long getVersion() { return version; }
}