package net.sprauer.sitzplaner.exceptions;

public class ClassNotLoadedException extends Exception {

	private static final long serialVersionUID = -6181681662153769328L;

	public ClassNotLoadedException(String string) {
		super(string);
	}
}
