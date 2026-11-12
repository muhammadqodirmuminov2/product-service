package uz.businesshub.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.businesshub.product.domain.Product;

import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {

    Optional<Product> findByIdAndCompanyId(UUID id, UUID companyId);

    boolean existsByCompanyIdAndSku(UUID companyId, String sku);
}