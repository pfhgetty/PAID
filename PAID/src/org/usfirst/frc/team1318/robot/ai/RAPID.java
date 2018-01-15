package org.usfirst.frc.team1318.robot.ai;
import java.util.Arrays;

public class RAPID {
	private RAPIDSettings settings;
	
	public RAPID(RAPIDSettings settings) {
		this.settings = settings;
	}
	
	public void run() {
		// Initialize population
		Organism[] sample = new Organism[settings.populationSize];
		if (settings.initialValues != null) {
			this.initialize(sample, settings.initialValues);
		} else {
			this.initialize(sample);
		}
		System.out.println("Population initialized!");

		double lastMaxFit = 0;
		// Run through environment for multiple generations
		for (int i = 0; i < settings.numGenerations; i++) {
			// Generate fitness for each organism
			this.runEnvironment(sample);
			
			// Display average fitness and max fitness
			if (i % (settings.numGenerations / 10) == 0) {
				double aFit = 0;
				for (Organism o : sample) {
					aFit += o.getFitness();
				}
				aFit /= sample.length;
				System.out.println(
						"Generation " + i + "\t Average Fitness: " + aFit + "\t Max Fitness " + sample[0].getFitness());
			}

			// Check for stagnating population
			boolean stagnating = sample[0].getFitness() <= lastMaxFit + settings.stagnationError
					&& sample[0].getFitness() >= lastMaxFit - settings.stagnationError;
			lastMaxFit = sample[0].getFitness();
			// Reproduce organisms with highest fitness
			if (stagnating) {
				// If sample is stagnating
				sample = this.reproduce(sample, settings.acceleratedMutationRate);
			} else {
				// If sample is not stagnating
				sample = this.reproduce(sample, settings.mutationRate);
			}

		}
		this.runEnvironment(sample);

		Arrays.sort(sample);
		System.out.println("\nTop Organisms (Population: " + sample.length + ")");
		System.out.print("1. " + sample[0] + "; ");
		for (int i = 1; i < settings.PRINT_LENGTH; i++) {
			System.out.println();
			System.out.print((i + 1) + ". " + sample[i] + "; ");
		}
	}

	private void initialize(Organism[] organisms, Range[] initialValues) {
		for (int i = 0; i < organisms.length; i++) {
			organisms[i] = new Organism(initialValues, settings.geneBounds);
		}
	}

	private void initialize(Organism[] organisms) {
		// Pick random gene values from the search space to initialize the population.
		this.initialize(organisms, settings.geneBounds);
	}

	// Run through environment and assign fitness
	private void runEnvironment(Organism[] o) {
		for (int i = 0; i < o.length; i++) {
			Genome g = o[i].getGenome();
			double cumulativeError = 0;
			for (int j = 0; j < g.genomeSize(); j++) {
				cumulativeError += g.getGene(j);
			}

			o[i].setFitness(settings.FITNESS(cumulativeError));
		}
		Arrays.sort(o);
	}

	// Pick organisms to reproduce
	private Organism[] reproduce(Organism[] o, double mutationRate) {
		// Cull organisms not fit to reproduce
		Organism[] survivors = new Organism[settings.populationSize - settings.bottleneckSize];
		for (int i = 0; i < survivors.length; i++) {
			survivors[i] = o[i];
		}
		o = survivors;

		// Create new generation of organisms
		Organism[] nextGen = new Organism[settings.populationSize];
		int i = 0;
		while (i < settings.populationSize) {
			// Each organism reproduces with another random organism from the surviving pool
			// until population limit is reached

			// Create an array to randomly choose another random organism that isn't this
			// organism.
			// Organisms can't reproduce with themselves.
			int[] pickList = new int[Math.round((float) o.length - 1)];
			Range range = new Range(0, pickList.length - 1);
			int w = 0;
			for (int j = 0; j < pickList.length; j++) {
				if (j == i % (settings.populationSize - settings.bottleneckSize)) {
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
