package fr.ul.miage.modeles;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class Lignes<T> {

	private ArrayList<T> elements = new ArrayList<T>();

	public Lignes() {

	}

	public void add(T ligne) {
		this.elements.add(ligne);
	}

	public ArrayList<T> filterByDep(String dep) {
		return (ArrayList<T>) this.elements.stream().filter(ligne -> (((Ligne) ligne).getDepartement().equals(dep)))
				.collect(Collectors.toList());
	}

	public ArrayList<T> returnAll(){
		return this.elements;
	}
	
	public int size() {
		return this.elements.size();
	}
}
