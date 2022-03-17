package market.application;

import java.math.BigDecimal;

import javax.persistence.EntityManager;

import market.connection.JpaConnectionFactory;
import market.model.persistence.Category;
import market.model.persistence.Product;
import market.services.ProductService;

public class Program {
	
	public static void main(String[] args) {
		EntityManager entityManager = new JpaConnectionFactory().getEntityManager();
		ProductService productService = new ProductService(entityManager);
		
		Product product = new Product("Notebook", "Acer Aspire 5", new BigDecimal(3550) , 
				new Category("Informática"));
		
		productService.create(product);
		
	}

}
