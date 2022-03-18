package market.model.dao;

import javax.persistence.EntityManager;

import market.model.persistence.Product;

public class ProductDAO {
	
	private EntityManager entityManager;
	
	public ProductDAO(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public void create(Product product) {
		this.entityManager.persist(product);
	}
	
	public void delete(Product product) {
		this.entityManager.remove(convertToMerge(product));
	}

	public Product getById(Long id) {
		return this.entityManager.find(Product.class, id);
	}
	
	public Product update(Product product) {
		return convertToMerge(product);
	}
	
	private Product convertToMerge(Product product) {
		return this.entityManager.merge(product);
	}

}
