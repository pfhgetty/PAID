
public class Variables {
	// Initial Factors
	public static final boolean USE_INITIAL = false; // Whether to populate around initial values
	public static final Range[] INITIAL = {new Range(0, 0), new Range(0, 0), 
			new Range(0, 0.01), new Range(0, 0.01)}; // Initial values to search around
	
	// Genetic Factors
	public static final double MUTATION_FACTOR = 0.4; // Maximum effect of mutation
	public static final double MUTATION_RATE = 0.05; // Chance that any given gene will mutate when reproducing
	public static final double ACCELERATED_MUTATION_RATE = 0.15; // Chance to mutate when stagnation occurs
	public static final double STAGNATION_ERROR = 0.01; // Maximum deviation to classify stagnation
	
	// Population Factors
	public static final int NUM_GENERATIONS = 10; // Number of generations
	public static final int POPULATION_SIZE = 100; // Size of sample
	public static final int BOTTLENECK_SIZE = 90; // Number of organisms to kill each generation -- must be
										// less than population size.
	
	// Search Space
	public static final Range[] GENE_BOUNDS = {new Range(0, 1), new Range(0, 1), 
			new Range(0, 1), new Range(0, 1)}; // Bound limits for each gene (array size should equal # of genes)
	
	// User Interface
	public static final int PRINT_LENGTH = 5; // Number of organisms to display at the end.
	
	// Fitness Algorithm
	public static double FITNESS(double cumulativeError) {
		if(Double.isNaN(cumulativeError) || Double.isInfinite(cumulativeError) || !Double.isFinite(cumulativeError)) {
			return 0;
		}
		return cumulativeError;
	}
	
}
