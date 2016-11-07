package generic;

public class GenericTest {
	public static void main(String[] args) {
		Employee e = new Employee();
		Manager m = new Manager();

		PairExt<Employee> p = new PairExt<>();
		Employee first = p.getFirst();
//		p.setFirst(m);
	}
}