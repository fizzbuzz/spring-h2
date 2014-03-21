package fizzbuzz.springh2;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Employee {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	Integer id;
	String name;
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name="DEPT_ID")
	Department department;
	
	@OneToMany/*(cascade = CascadeType.PERSIST)*/
	@JoinTable(name="EMP_PHONE",
	joinColumns=@JoinColumn(name="EMP_ID"),
	inverseJoinColumns=@JoinColumn(name="PHONE_ID"))
	private List<Phone> phones;
	
	@OneToOne(fetch=FetchType.LAZY, cascade = CascadeType.PERSIST)
	private ParkingSpace parkingSpace;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	public List<Phone> getPhones() {
		return phones;
	}
	public void setPhones(List<Phone> phones) {
		this.phones = phones;
	}
	public ParkingSpace getParkingSpace() {
		return parkingSpace;
	}
	public void setParkingSpace(ParkingSpace parkingSpace) {
		this.parkingSpace = parkingSpace;
	}
	
	
}
