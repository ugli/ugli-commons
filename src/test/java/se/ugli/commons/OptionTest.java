package se.ugli.commons;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.NoSuchElementException;

import org.junit.Test;

public class OptionTest {

	@Test
	public void shouldReturnNotNull() {
		assertThat(Option.apply("hej").getOrNull(), equalTo("hej"));
	}

	@Test
	public void shouldReturnNull() {
		assertThat(Option.none().getOrNull(), nullValue());
	}

	@Test
	public void shouldReturnValue() {
		assertThat(Option.apply("hej").get(), equalTo("hej"));
	}

	@Test(expected = NoSuchElementException.class)
	public void shouldThrowException() {
		Option.none().get();
	}

	@Test
	public void shouldReturnToString() {
		assertThat(Option.apply("hej").toString(), equalTo("Option [value=hej]"));
	}

	@Test
	public void shouldHashCodeEqualsToValuePlusPrime() {
		assertThat(Option.apply("hej").hashCode(), equalTo("hej".hashCode() + 31));
	}

	@Test
	public void shouldHashCodeEqualsToPrime() {
		assertThat(Option.none().hashCode(), equalTo(31));
	}

	@Test
	public void shouldHandleEquals() {
		assertThat(Option.none().equals(Option.none()), equalTo(true));
		assertThat(Option.apply("1").equals(Option.apply("1")), equalTo(true));
		{
			final Option<String> option = Option.apply("hej");
			assertThat(option.equals(option), equalTo(true));
		}
		assertThat(Option.apply("1").equals(null), equalTo(false));
		assertThat(Option.apply("1").equals("1"), equalTo(false));
		assertThat(Option.apply("1").equals(Option.none()), equalTo(false));
		assertThat(Option.none().equals(Option.apply("1")), equalTo(false));
		assertThat(Option.apply("1").equals(Option.apply("2")), equalTo(false));
	}

}
