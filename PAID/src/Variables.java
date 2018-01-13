
public class Variables {
	// Initial Factors
	public static final boolean USE_INITIAL = false; // Whether to populate around initial values or to populate randomly
	public static final Range[] INITIAL = {new Range(0, 1), new Range(0, 1), 
			new Range(0, 1), new Range(0, 1)}; // Initial values to search around
	
	// Genetic Factors
	public static final double MUTATION_FACTOR = 0.01; // Maximum effect of mutation
	public static final double MUTATION_RATE = 0.015; // Chance that any given gene will mutate when reproducing
	
	// Population Factors
	public static final int NUM_GENERATIONS = 250; // Number of generations
	public static final int POPULATION_SIZE = 200; // Size of sample
	public static final int BOTTLENECK_SIZE = 100; // Number of organisms to kill each generation -- must be
										// less than population size.
	
	// Search Space
	public static final Range[] GENE_BOUNDS = {new Range(0, 1), new Range(0, 1), 
			new Range(0, 1), new Range(0, 1)}; // Bound limits for each gene (array size should equal # of genes)
	
	// User Interface
	public static final int PRINT_LENGTH = 5; // Number of organisms to display at the end.
	
	public static double FITNESS(double cumulativeError) {
		return cumulativeError;
	}
	
}
