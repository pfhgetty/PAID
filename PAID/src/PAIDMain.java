import java.util.Arrays;

public class PAIDMain {
	public static void main(String[] args) {
		// Initialize population
		Organism[] sample = new Organism[Variables.POPULATION_SIZE];
		if(Variables.USE_INITIAL) {
			PAIDMain.initialize(sample, Variables.INITIAL);
		} else {
			PAIDMain.initialize(sample);
		}		
		System.out.println("Population initialized!");
		// Run through environment for multiple generations
		for (int i = 0; i < Variables.NUM_GENERATIONS; i++) {
			// Generate fitness for each organism
			PAIDMain.runEnvironment(sample);
			
			// Display average fitness
			if(i % (Variables.NUM_GENERATIONS / 10) == 0) {
				double aFit = 0;
				for(Organism o : sample) {
					aFit += o.getFitness();
				}
				aFit /= sample.length;
				System.out.println("Generation " + i + "\t Average Fitness:\t" + aFit);
			}
			
			// Reproduce organisms with highest fitness
			sample = PAIDMain.reproduce(sample);
		}
		PAIDMain.runEnvironment(sample);
		
		Arrays.sort(sample);
		System.out.println("\nTop Organisms (Population: " + sample.length + ")");
		System.out.print("1. " + sample[0] + "; ");
		for(int i = 1; i < Variables.PRINT_LENGTH; i++) {
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
		// Pick random gene values from the range to initialize the population.
		PAIDMain.initialize(organisms, Variables.GENE_BOUNDS);
	}
	
	// Run through environment and assign fitness
	private static void runEnvironment(Organism[] o) {		
		for(int i = 0; i < o.length; i++) {
			double cumulativeError = 0;
			for(int j = 0; j < o[i].getGenome().genomeSize(); j++) {
				cumulativeError += o[i].getGenome().getGene(j);
			}
			o[i].setFitness(Variables.FITNESS(cumulativeError));
		}
		Arrays.sort(o);
	}
	
	// Pick organisms to reproduce
	private static Organism[] reproduce(Organism[] o) {
		Organism[] survivors = new Organism[Variables.POPULATION_SIZE - Variables.BOTTLENECK_SIZE];
		for(int i = 0; i < survivors.length; i++) {
			survivors[i] = o[i];
		}		
		o = survivors;
		
		Organism[] nextGen = new Organism[Variables.POPULATION_SIZE];
		Range range = new Range(0, o.length - 1);
		int i = 0;
		while(i < Variables.POPULATION_SIZE) {
			// Each organism reproduces with another random organism from the surviving pool until 
			// population limit is reached
			nextGen[i] = o[i % o.length].reproduce(o[Math.round((float) range.getRandom())]);
			i++;
		}
		
		return nextGen;
	}
}
