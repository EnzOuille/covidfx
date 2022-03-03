package fr.ul.miage.modeles;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Lignes {

    private final ArrayList<Ligne> elements;

    public Lignes() {
        this.elements = new ArrayList<>();
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
