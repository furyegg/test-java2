public class CloneObject {

	private Long l;
	private String s;
	private Double d;

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	public CloneObject(Long l, String s, Double d) {
		this.l = l;
		this.s = s;
		this.d = d;
	}

	public Long getL() {
		return l;
	}

	public void setL(Long l) {
		this.l = l;
	}

	public String getS() {
		return s;
	}

	public void setS(String s) {
		this.s = s;
	}

	public Double getD() {
		return d;
	}

	public void setD(Double d) {
		this.d = d;
	}

}
