// Simple container for a range of numbers with a minimum and maximum.
public class Range {
	private double min;
	private double max;

	public Range(double min, double max) {
		// Idiotproof
		this.min = Math.min(min, max);
		this.max = Math.max(min, max);
	}

	public boolean contains(double value) {
		return value <= max && value >= min;
	}

	// Returns the average of the max and min.
	public double getCenter() {
		return (max + min) / 2;
	}

	// Returns the difference between the max and the min.
	public double getSize() {
		return max - min;
	}

	public double getMin() {
		return min;
	}

	public double getMax() {
		return max;
	}
	
	// Chooses a random number within the range.
	public double getRandom() {
		return min + (Math.random() * this.getSize());
	}

	@Override
	public String toString() {
		return "(" + min + ":" + max + ")";
	}
}
