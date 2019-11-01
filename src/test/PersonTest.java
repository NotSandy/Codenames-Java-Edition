package test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import code.Person;

public class PersonTest {
	
	private String role;
	
	@Test
	public void nullValue() {
		this.role = null;
		Person x = new Person(this.role);
		assertEquals(null, x.getRole());
	}
	
	@Test
	public void returnRole(){
		this.role = "Red";
		Person x = new Person(this.role);
		assertEquals("Red", x.getRole());
	}
}
