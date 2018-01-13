import java.util.Arrays;

public class Genome {
	private double[] genes;

	public Genome(double[] genes) {
		this.genes = new double[genes.length];
		
		for (int i = 0; i < genes.length; i++) {
			if (Variables.GENE_BOUNDS[i].contains(genes[i])) {
				this.genes[i] = genes[i];
			}
		}
	}

	public double getGene(int index) {
		if (index < genes.length) {
			return genes[index];
		} else {
			System.out.println("Index out of range!");
			return 0;
		}
	}

	public int genomeSize() {
		return genes.length;
	}

	public Genome combine(Genome other) {
		return this.crossover(other).mutate();
	}

	private Genome crossover(Genome other) {
		if (other.genomeSize() != this.genomeSize()) {
			System.out.println("Two organisms crossing over are not the same species (" + this + " and " + other + ")");
			return null;
		}

		double[] childGenes = new double[this.genomeSize()];

		for (int i = 0; i < this.genomeSize(); i++) {
			if (Math.random() > 0.5) {
				childGenes[i] = this.getGene(i);
			} else {
				childGenes[i] = other.getGene(i);
			}
		}
		return new Genome(childGenes);
	}

	private Genome mutate() {
		double[] mutatedGenes = new double[this.genomeSize()];
		for (int i = 0; i < this.genomeSize(); i++) {
			mutatedGenes[i] = this.getGene(i);

			// Randomly selects genes to mutate
			if (Math.random() < Variables.MUTATION_RATE) {
				// Adds or subtracts up to MUTATION_FACTOR from the mutated gene
				mutatedGenes[i] += ((Math.random() * 2) - 1) * Variables.MUTATION_FACTOR;
			}
		}

		return new Genome(mutatedGenes);
	}
	
	public String toString() {
		return Arrays.toString(genes);
	}
}
