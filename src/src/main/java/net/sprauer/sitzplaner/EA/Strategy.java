package net.sprauer.sitzplaner.EA;

public enum Strategy {
	Normal, Rank, Tournament;

	@Override
	public String toString() {
		switch (this) {
		default:
		case Normal:
			return " ";
		case Rank:
			return "R";
		case Tournament:
			return "T";
		}
	}

}
