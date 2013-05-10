package net.sprauer.sitzplaner.EA;

public interface MutationOperation<I, O> {

	public O call(I input);

}