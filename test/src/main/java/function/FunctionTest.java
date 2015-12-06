package function;

import com.google.common.collect.ImmutableList;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class FunctionTest {
	public static final List<Person> roster = ImmutableList.of(
			new Person("ForestGump", LocalDate.of(1982, 1, 1), Person.Sex.MALE, "ForestGump@163.com"),
			new Person("Simith", LocalDate.of(1981, 1, 1), Person.Sex.MALE, "Simith@163.com"),
			new Person("Amanda", LocalDate.of(1986, 1, 1), Person.Sex.FEMALE, "Amanda@163.com"),
			new Person("Tony", LocalDate.of(1993, 1, 1), Person.Sex.MALE, "Tony@163.com"),
			new Person("Jack", LocalDate.of(1980, 1, 1), Person.Sex.MALE, "Jack@163.com"),
			new Person("Lily", LocalDate.of(1973, 1, 1), Person.Sex.FEMALE, "Jack@163.com"),
			new Person("Jany", LocalDate.of(1990, 1, 1), Person.Sex.FEMALE, "Jack@163.com")
	);

	public static void main(String[] args) {
		List<String> namesOfMaleMembersCollect = roster
				.stream()
				.filter(p -> p.getGender() == Person.Sex.MALE)
//				.map(p -> p.getName())
				.map(Person::getName)
				.collect(Collectors.toList());
		namesOfMaleMembersCollect.forEach(System.out::println);
	}
}