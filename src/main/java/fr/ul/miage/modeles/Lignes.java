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

	public ArrayList<T> filterByDate(Date date1, Date date2) {
		return (ArrayList<T>) this.elements.stream()
				.filter(ligne -> ((Ligne) ligne).getDate().after(date1) && ((Ligne) ligne).getDate().before(date2))
				.collect(Collectors.toList());
	}
	
	public ArrayList<T> filterByDateAndDep(Date date1, Date date2, String dep){
		return (ArrayList<T>) this.elements.stream()
				.filter(ligne -> ((Ligne) ligne).getDate().after(date1) && ((Ligne) ligne).getDate().before(date2))
				.collect(Collectors.toList());
	}
	
	public int size() {
		return this.elements.size();
	}
}
