package org.ecommerce.repository;

import java.util.List;

import org.ecommerce.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
	public ProductEntity findByProductName(String productName);
	public List<ProductEntity>findByProductNameLike(String pattern);
	public ProductEntity findByProductHsn(String productHsn);
}
