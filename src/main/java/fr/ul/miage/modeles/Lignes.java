package fr.ul.miage.modeles;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class Lignes {

	private final ArrayList<Ligne> elements;

	public Lignes() {
		this.elements = new ArrayList<Ligne>();
	}

	public void add(Ligne ligne) {
		this.elements.add(ligne);
	}

	public ArrayList<Ligne> filterByDep(String dep) {
		return (ArrayList<Ligne>) this.elements.stream().filter(ligne -> ligne.getDepartement().equals(dep))
				.collect(Collectors.toList());
	}

	public int size() {
		return this.elements.size();
	}
}
