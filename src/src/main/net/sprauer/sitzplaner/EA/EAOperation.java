package net.sprauer.sitzplaner.EA;

public abstract class EAOperation {

	public abstract void invoke(Chromosome gene) throws Exception;

	void invoke() throws Exception {
		invoke(null);
	}
}
