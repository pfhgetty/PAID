package org.usfirst.frc.team1318.robot.ai;

public class Organism implements Comparable<Organism> {
	private Genome genome;
	private double fitness;

	// Generate random organism within the search space
	public Organism(Range[] bounds, Range[] geneBounds) {
		double[] genes = new double[bounds.length];

		for (int i = 0; i < genes.length; i++) {
			genes[i] = bounds[i].getRandom();
		}

		this.genome = new Genome(genes, geneBounds);
	}

	public Organism(Genome genome) {
		this.genome = genome;
	}

	public Organism(double[] genes, Range[] geneBounds) {
		this.genome = new Genome(genes, geneBounds);
	}
	
	public Organism reproduce(Organism o, double mutationRate) {
		Genome c = this.genome.combine(o.getGenome(), mutationRate);
		return new Organism(c);		
	}

	public void setFitness(double f) {
		this.fitness = f;
	}
	
	public double getFitness() {
		return this.fitness;
	}
	
	public Genome getGenome() {
		return this.genome;
	}
	
	@Override
	public String toString() {
		return genome.toString() + ": (" + this.getFitness() + ")";
	}

	@Override
	public int compareTo(Organism o) {
		if (this.fitness < o.fitness) {
			return 1;
		} else if (o.fitness < this.fitness) {
			return -1;
		} else {
			return 0;
		}
	}
}
