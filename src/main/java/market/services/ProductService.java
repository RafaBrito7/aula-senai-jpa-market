package market.services;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import market.model.dao.ProductDAO;
import market.model.persistence.Product;

public class ProductService {
	
	private final Logger LOG = LogManager.getLogger(ProductService.class);
	
	private EntityManager entityManager;
	
	private ProductDAO productDAO;
	
	public ProductService(EntityManager entityManager) {
		this.entityManager = entityManager;
		this.productDAO = new ProductDAO(entityManager);
	}
	
	private void getBeginTransaction() {
		this.LOG.info("Abrindo Transação com o banco de dados...");
		entityManager.getTransaction().begin();
	}

	private void commitAndCloseTransaction() {
		this.LOG.info("Commitando e Fechando transação com o banco de dados");
		entityManager.getTransaction().commit();
		entityManager.close();
	}
	
	public void create(Product product) {
		this.LOG.info("Preparando para a Criação de um Produto");
		
		if (product == null) {
			this.LOG.error("O Produto informado está nulo!");
			throw new RuntimeException("Product Null!");
		}
		
		try {
			getBeginTransaction();
			this.productDAO.create(product);
			commitAndCloseTransaction();
		} catch (Exception e) {
			this.LOG.error("Erro ao criar um Produto, causado por: " + e.getMessage());
			throw new RuntimeException(e);
		}
		this.LOG.info("Produto foi criado com sucesso!");
	}
	
	public void delete(Long id) {
		this.LOG.info("Preparando para encontrar o Produto");
		if (id == null) {
			this.LOG.error("O ID do Produto informado está nulo!");
			throw new RuntimeException("The ID is Null");
		}
		
		Product product = this.productDAO.getById(id);
		if (product == null) {
			this.LOG.error("O Produto não Existe!");
			throw new EntityNotFoundException("Product not Found!");
		}
		
		this.LOG.info("Produto encontrado com sucesso!");
		
		getBeginTransaction();
		this.productDAO.delete(product);
		commitAndCloseTransaction();
		this.LOG.info("Produto deletado com sucesso!");
	}

}
