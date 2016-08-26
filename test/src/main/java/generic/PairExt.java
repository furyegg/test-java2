package generic;

public class PairExt<T extends Employee> {
	public T getFirst() {return null;}
	public <K extends Employee> void setFirst(K t) {};
}