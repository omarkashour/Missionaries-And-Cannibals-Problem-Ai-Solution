import java.util.Objects;

public class State {
	private int missonaries;
	private int cannibals;
	private int boat;

	private State parent; // Add a reference to the parent state

	public State getParent() {
		return parent;
	}

	public void setParent(State parent) {
		this.parent = parent;
	}

	public State(int missionaries, int cannibals, int boat) {
		this.missonaries = missionaries;
		this.cannibals = cannibals;
		this.boat = boat;
	}

	public int getMissionaries() {
		return missonaries;
	}

	public void setMissionaries(int missionaries) {
		this.missonaries = missionaries;
	}

	public int getCannibals() {
		return cannibals;
	}

	public void setCannibals(int cannibals) {
		this.cannibals = cannibals;
	}

	public int getBoat() {
		return boat;
	}

	public void setBoat(int boat) {
		this.boat = boat;
	}

	public String toString() {
		return "(" + missonaries + "," + cannibals + "," + boat + ")";
	}

	public State add(State state) {
		if (boat == 1)
			return null;
		if (missonaries + state.missonaries > 3 || cannibals + state.cannibals > 3 || boat + state.boat > 1)
			return null;
		return new State(missonaries + state.missonaries, cannibals + state.cannibals, boat + state.boat);
	}

	public State subtract(State state) {
		if (boat == 0)
			return null;
		if (missonaries - state.missonaries < 0 || cannibals - state.cannibals < 0 || boat - state.boat < 0)
			return null;
		return new State(missonaries - state.missonaries, cannibals - state.cannibals, boat - state.boat);
	}

}
