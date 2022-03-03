package fr.ul.miage.modeles;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;
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

    public ArrayList<Ligne> returnAll() {
        return this.elements;
    }

    public long returnSum() {
        AtomicLong res = new AtomicLong();
        this.elements.forEach(ligne -> {
            res.addAndGet(ligne.getCount());
        });
        return res.get();
    }

    public long last() {
        ArrayList<String> departements = new ArrayList<>();
        long res = 0;
        for (int i = this.elements.size() - 1; i >= 0; i--) {
            if (departements.size() == 0) {
                res += this.elements.get(i).getCount();
                departements.add(this.elements.get(i).getDepartement());
            } else if (departements.contains(this.elements.get(i).getDepartement().replace("DEP-", "").replace("REG-",""))) {
                res += this.elements.get(i).getCount();
            }
        }
        return res;
    }

    public int size() {
        return this.elements.size();
    }
}
