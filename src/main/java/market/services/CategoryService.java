package market.services;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import market.model.dao.CategoryDAO;
import market.model.persistence.Category;

public class CategoryService {

	private final Logger LOG = LogManager.getLogger(CategoryService.class);
	
	private EntityManager entityManager;
	
	private CategoryDAO categoryDAO;
	
	public CategoryService(EntityManager entityManager) {
		this.entityManager = entityManager;
		this.categoryDAO = new CategoryDAO(entityManager);
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
	
	public Category findByName(String name) {
		if (name == null || name.isEmpty()) {
			this.LOG.error("O Name não pode ser Nulo!");
			throw new RuntimeException("The name is null!");
		}
		try {
			return this.categoryDAO.findByName(name.toLowerCase());
		} catch (NoResultException r) {
			this.LOG.info("Não foi encontrado Categoria, será criada!");
			return null;
		}
	}
	
	public void delete(Long id) {
		
	}
	
	public Category getById(Long id) {
		if (id == null) {
			this.LOG.error("O ID está nulo!");
			throw new RuntimeException("The parameter is null!");
		}
		
		Category category = this.categoryDAO.getById(id);
		
		if (category == null) {
			this.LOG.error("Não foi encontrado a categoria de id " + id);
			throw new EntityNotFoundException("Category not found!");
		}
		return category;
	}
}
