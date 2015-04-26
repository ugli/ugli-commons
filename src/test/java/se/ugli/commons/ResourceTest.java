package se.ugli.commons;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class ResourceTest {

	@Test
	public void shouldFindClass() {
		assertThat(true, equalTo(Resource.classExists("se.ugli.commons.Id")));
	}

	@Test
	public void shouldNotFindClass() {
		assertThat(false, equalTo(Resource.classExists("se.ugli.commons.IdId")));
	}
}
