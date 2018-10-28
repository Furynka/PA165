package cz.fi.muni.pa165.tasks;

import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.validation.ConstraintViolationException;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import cz.fi.muni.pa165.PersistenceSampleApplicationContext;
import cz.fi.muni.pa165.entity.Category;
import cz.fi.muni.pa165.entity.Product;

 
@ContextConfiguration(classes = PersistenceSampleApplicationContext.class)
public class Task02 extends AbstractTestNGSpringContextTests {

	private Category electro;
	private Category kitchen;
	private Product flashlight;
	private Product kitchenRobot;
	private Product plate;


	@PersistenceUnit
	private EntityManagerFactory emf;

	@BeforeClass
	private void beforeClass() {
		electro = new Category();
		electro.setName("Electro");
		kitchen = new Category();
		kitchen.setName("Kitchen");

		flashlight = new Product();
		flashlight.setName("Flashlight");
		kitchenRobot = new Product();
		kitchenRobot.setName("Kitchen robot");
		plate = new Product();
		plate.setName("Plate");

		flashlight.addCategory(electro);
		kitchenRobot.addCategory(electro);
		kitchenRobot.addCategory(kitchen);
		plate.addCategory(kitchen);

		EntityManager entityManager = emf.createEntityManager();
		entityManager.getTransaction().begin();
		entityManager.persist(electro);
		entityManager.persist(kitchen);
		entityManager.persist(flashlight);
		entityManager.persist(kitchenRobot);
		entityManager.persist(plate);
		entityManager.getTransaction().commit();
		entityManager.close();
	}

	@Test
	public void testCategoryElectro() {
		EntityManager entityManager = emf.createEntityManager();
		Category foundCategory = entityManager.find(Category.class, electro.getId());

		assertContainsProductWithName(foundCategory.getProducts(), flashlight.getName());
		assertContainsProductWithName(foundCategory.getProducts(), kitchenRobot.getName());
	}

	@Test
	public void testCategoryKitchen() {
		EntityManager entityManager = emf.createEntityManager();
		Category foundCategory = entityManager.find(Category.class, kitchen.getId());

		assertContainsProductWithName(foundCategory.getProducts(), plate.getName());
		assertContainsProductWithName(foundCategory.getProducts(), kitchenRobot.getName());
	}

	@Test
	public void testProductFlashlight() {
		EntityManager entityManager = emf.createEntityManager();
		Product foundProduct = entityManager.find(Product.class, flashlight.getId());

		assertContainsCategoryWithName(foundProduct.getCategories(), electro.getName());
	}

	@Test
	public void testProductKitchenRobot() {
		EntityManager entityManager = emf.createEntityManager();
		Product foundProduct = entityManager.find(Product.class, kitchenRobot.getId());

		assertContainsCategoryWithName(foundProduct.getCategories(), kitchen.getName());
		assertContainsCategoryWithName(foundProduct.getCategories(), electro.getName());
	}

	@Test
	public void testProductPlate() {
		EntityManager entityManager = emf.createEntityManager();
		Product foundProduct = entityManager.find(Product.class, plate.getId());

		assertContainsCategoryWithName(foundProduct.getCategories(), kitchen.getName());
	}

	@Test(expectedExceptions=ConstraintViolationException.class)
	public void testDoesntSaveNullName(){
		Product noNamedProduct = new Product();
		noNamedProduct.setName(null);

		EntityManager entityManager = emf.createEntityManager();
		entityManager.getTransaction().begin();
		entityManager.persist(noNamedProduct);
		entityManager.getTransaction().commit();
		entityManager.close();
	}
	
	private void assertContainsCategoryWithName(Set<Category> categories,
			String expectedCategoryName) {
		for(Category cat: categories){
			if (cat.getName().equals(expectedCategoryName))
				return;
		}
			
		Assert.fail("Couldn't find category "+ expectedCategoryName+ " in collection "+categories);
	}
	private void assertContainsProductWithName(Set<Product> products,
			String expectedProductName) {
		
		for(Product prod: products){
			if (prod.getName().equals(expectedProductName))
				return;
		}
			
		Assert.fail("Couldn't find product "+ expectedProductName+ " in collection "+products);
	}

	
}
