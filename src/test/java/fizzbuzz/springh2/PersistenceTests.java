package fizzbuzz.springh2;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import fizzbuzz.springh2.manytoone.Department;
import fizzbuzz.springh2.manytoone.Employee;

@ContextConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class PersistenceTests {

	@PersistenceContext
	private EntityManager entityManager;

	@Ignore
	@Test
	@Transactional
	public void testSaveOrderWithItems() throws Exception {
		Order order = new Order();
		order.getItems().add(new Item());
		entityManager.persist(order);
		entityManager.flush();
		assertNotNull(order.getId());
	}

	@Ignore
	@Test
	@Transactional
	public void testSaveAndGet() throws Exception {
		Order order = new Order();
		order.getItems().add(new Item());
		entityManager.persist(order);
		entityManager.flush();
		// Otherwise the query returns the existing order (and we didn't set the
		// parent in the item)...
		entityManager.clear();
		Order other = (Order) entityManager.find(Order.class, order.getId());
		assertEquals(1, other.getItems().size());
		assertEquals(other, other.getItems().iterator().next().getOrder());
	}

	@Ignore
	@Test
	@Transactional
	public void testSaveAndFind() throws Exception {
		Order order = new Order();
		Item item = new Item();
		item.setProduct("foo");
		order.getItems().add(item);
		entityManager.persist(order);
		entityManager.flush();
		// Otherwise the query returns the existing order (and we didn't set the
		// parent in the item)...
		entityManager.clear();
		Order other = (Order) entityManager
				.createQuery(
						"select o from Order o join o.items i where i.product=:product")
				.setParameter("product", "foo").getSingleResult();
		assertEquals(1, other.getItems().size());
		assertEquals(other, other.getItems().iterator().next().getOrder());
	}
	
	@Test
	@Transactional
	public void testManyToOneEmployeeDepartment() throws Exception {
		
		Department department1 = new Department();
		Employee employee1 = new Employee();
		employee1.setDepartment(department1);
		
		Department department2 = new Department();
		Employee employee2 = new Employee();
		employee2.setDepartment(department2);
		
		
		entityManager.persist(employee1);
		entityManager.persist(employee2);
		entityManager.flush();
		Employee loadEmployee1 = entityManager.find(Employee.class, employee1.getId());
		assertNotNull(loadEmployee1.getDepartment().getId());
		Employee loadEmployee2 = entityManager.find(Employee.class, employee2.getId());
		Assert.assertThat(loadEmployee1.getDepartment().getId(), not(equalTo(loadEmployee2.getDepartment().getId())));
	}

}
