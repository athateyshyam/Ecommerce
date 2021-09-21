package org.ecommerce.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "category_tbl")
public class CategoryEntity implements Serializable {

	private static final long serialVersionUID = -9120840313740444379L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String categoryName;
	@OneToMany(mappedBy = "category",cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH })
	private List<ProductEntity> productList;

	public CategoryEntity() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public List<ProductEntity> getProductList() {
		return productList;
	}

	public void setProductList(List<ProductEntity> productList) {
		this.productList = productList;
	}

}
