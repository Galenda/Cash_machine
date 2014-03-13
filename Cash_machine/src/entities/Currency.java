package entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Currency {
	private String name;
	private Map<Integer, Integer> notes = new HashMap<Integer, Integer>();
	private List<Integer> validNotes = new ArrayList<Integer>();

	private boolean checkInputNotes(int value, int amount) {
		validNotes.clear();
		validNotes.add(5000);
		validNotes.add(1000);
		validNotes.add(500);
		validNotes.add(100);
		validNotes.add(50);
		validNotes.add(10);
		validNotes.add(5);
		validNotes.add(1);
		if (notes.containsKey(value)) {
			int currentAmount = notes.get(value);
			if ((currentAmount + amount > Integer.MAX_VALUE) || (currentAmount - amount > Integer.MIN_VALUE)|| (amount < 0))
				return false;
		}
		if (!validNotes.contains(value))
			return false;
		return true;
	}

	public boolean addNotes(Integer value, Integer amount) {
		if (!checkInputNotes(value, amount))
			return false;
		if (notes.containsKey(value)) {
			amount += notes.get(value);
		}
		notes.put(value, amount);
		return true;
	}

	public ArrayList<String> getNotes(Integer amount) {
		ArrayList<String> returnNotes = new ArrayList<String>();
		Map<Integer, Integer> currentNotes = new HashMap<Integer, Integer>(
				notes);
		int currentAmount = amount;
		for (int i = 0; i < validNotes.size(); i++) {
			int currentNote = validNotes.get(i);
			if (currentNotes.containsKey(currentNote)) {
				if (currentAmount / currentNote > 0) {
					int need = currentAmount / currentNote;
					if (need >= currentNotes.get(currentNote)) {
						currentAmount -= currentNote
								* currentNotes.get(currentNote);
						returnNotes.add(Integer.toString(currentNote)
								+ " "
								+ Integer.toString(currentNotes
										.get(currentNote)));
						currentNotes.put(currentNote, 0);
					} else {
						currentAmount -= currentNote * need;
						currentNotes.put(currentNote,
								currentNotes.get(currentNote) - need);
						returnNotes.add(Integer.toString(currentNote) + " "
								+ Integer.toString(need));
					}
				}
			}
			if (currentAmount == 0) {
				notes = new HashMap<Integer, Integer>(currentNotes);
				return returnNotes;
			}
		}
		returnNotes.clear();
		return returnNotes;
	}

	public ArrayList<String> showMoney() {
		ArrayList<String> returnNotes = new ArrayList<String>();
		for (int i = 0; i < validNotes.size(); i++) {
			int currentNote = validNotes.get(i);
			if ((notes.containsKey(currentNote)) && notes.get(currentNote) > 0) {
				returnNotes.add(Integer.toString(currentNote) + " "
						+ notes.get(currentNote));
			}
		}
		return returnNotes;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
