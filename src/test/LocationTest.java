package test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import code.Location;
import code.Person;

public class LocationTest {
	
	private Person person;
	private String codename;
	private boolean isRevealed;
	
	@Test
	public void nullValue() {
		Location x = new Location(this.codename, null);
		assertEquals(null, x.getPerson());
	}

	@Test
	public void returnPerson() {
		Location x = new Location(this.codename, this.person);
		assertEquals(person, x.getPerson());
	}
	
	@Test
	public void notRevealed() {
		Location x = new Location(this.codename, this.person);
		assertEquals(false, x.isRevealed());
	}
}
