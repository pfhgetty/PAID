import java.util.Arrays;

public class PAIDMain {
	public static void main(String[] args) {
		// Initialize population
		Organism[] sample = new Organism[Variables.POPULATION_SIZE];
		if (Variables.USE_INITIAL) {
			PAIDMain.initialize(sample, Variables.INITIAL);
		} else {
			PAIDMain.initialize(sample);
		}
		System.out.println("Population initialized!");

		double lastMaxFit = 0;
		// Run through environment for multiple generations
		for (int i = 0; i < Variables.NUM_GENERATIONS; i++) {
			// Generate fitness for each organism
			PAIDMain.runEnvironment(sample);

			// Display average fitness and max fitness
			if (i % (Variables.NUM_GENERATIONS / 10) == 0) {
				double aFit = 0;
				for (Organism o : sample) {
					aFit += o.getFitness();
				}
				aFit /= sample.length;
				System.out.println(
						"Generation " + i + "\t Average Fitness: " + aFit + "\t Max Fitness " + sample[0].getFitness());
			}

			// Check for stagnating population
			boolean stagnating = sample[0].getFitness() <= lastMaxFit + Variables.STAGNATION_ERROR
					&& sample[0].getFitness() >= lastMaxFit - Variables.STAGNATION_ERROR;
			lastMaxFit = sample[0].getFitness();
			// Reproduce organisms with highest fitness
			if (stagnating) {
				// If sample is stagnating
				sample = PAIDMain.reproduce(sample, Variables.ACCELERATED_MUTATION_RATE);
			} else {
				// If sample is not stagnating
				sample = PAIDMain.reproduce(sample, Variables.MUTATION_RATE);
			}

		}
		PAIDMain.runEnvironment(sample);

		Arrays.sort(sample);
		System.out.println("\nTop Organisms (Population: " + sample.length + ")");
		System.out.print("1. " + sample[0] + "; ");
		for (int i = 1; i < Variables.PRINT_LENGTH; i++) {
			System.out.println();
			System.out.print((i + 1) + ". " + sample[i] + "; ");
		}
	}

	private static void initialize(Organism[] organisms, Range[] initialValues) {
		for (int i = 0; i < organisms.length; i++) {
			organisms[i] = new Organism(initialValues);
		}
	}

	private static void initialize(Organism[] organisms) {
		// Pick random gene values from the search space to initialize the population.
		PAIDMain.initialize(organisms, Variables.GENE_BOUNDS);
	}

	// Run through environment and assign fitness
	private static void runEnvironment(Organism[] o) {
		for (int i = 0; i < o.length; i++) {
			Genome g = o[i].getGenome();
			double cumulativeError = 0;
			for (int j = 0; j < g.genomeSize(); j++) {
				cumulativeError += 2 * Math.pow(g.getGene(j), 3) - 3 * Math.pow(g.getGene(j), 2) + g.getGene(j);
			}

			o[i].setFitness(Variables.FITNESS(cumulativeError));
		}
		Arrays.sort(o);
	}

	// Pick organisms to reproduce
	private static Organism[] reproduce(Organism[] o, double mutationRate) {
		// Cull organisms not fit to reproduce
		Organism[] survivors = new Organism[Variables.POPULATION_SIZE - Variables.BOTTLENECK_SIZE];
		for (int i = 0; i < survivors.length; i++) {
			survivors[i] = o[i];
		}
		o = survivors;

		// Create new generation of organisms
		Organism[] nextGen = new Organism[Variables.POPULATION_SIZE];
		int i = 0;
		while (i < Variables.POPULATION_SIZE) {
			// Each organism reproduces with another random organism from the surviving pool
			// until population limit is reached

			// Create an array to randomly choose another random organism that isn't this
			// organism.
			// Organisms can't reproduce with themselves.
			int[] pickList = new int[Math.round((float) o.length - 1)];
			Range range = new Range(0, pickList.length - 1);
			int w = 0;
			for (int j = 0; j < pickList.length; j++) {
				if (j == i % (Variables.POPULATION_SIZE - Variables.BOTTLENECK_SIZE)) {
					w++;
				}
				pickList[j] = w;
				w++;
			}
			nextGen[i] = o[i % o.length].reproduce(o[pickList[Math.round((float) range.getRandom())]], mutationRate);
			i++;
		}

		return nextGen;
	}
}
