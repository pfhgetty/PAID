
public class RAPIDMain {
	public static void main(String[] args) {
		Range[] range = {new Range(0,1), new Range(0,1), new Range(0,1)};
		RAPID rapid = new RAPID(new RAPIDSettings(range));
		rapid.run();
	}
}
