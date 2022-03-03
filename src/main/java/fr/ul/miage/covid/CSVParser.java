package fr.ul.miage.covid;

import com.opencsv.bean.CsvToBeanBuilder;
import fr.ul.miage.modeles.CSVLine;
import fr.ul.miage.modeles.Ligne;
import fr.ul.miage.modeles.Lignes;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;
import java.text.ParseException;
import java.util.List;

public class CSVParser {

    private final Lignes morts = new Lignes();
    private final Lignes reanimations = new Lignes();
    private final Lignes hospitalisations = new Lignes();
    private final Lignes vaccinations = new Lignes();
    private final Lignes vaccinations_calcul = new Lignes();
    private FileReader file_reader;

    public CSVParser(String filename) {
        ClassLoader classLoader = CSVParser.class.getClassLoader();
        URL resource = classLoader.getResource(filename);
        try {
            if (resource != null) {
                this.file_reader = (new FileReader(resource.getFile()));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        this.parseCSV();
    }

    public void parseCSV() {
        try {
            List<CSVLine> beans = new CsvToBeanBuilder(this.file_reader).withType(CSVLine.class).build().parse();
            beans.forEach(ligne -> {
                String date = ligne.getDate();
                String code = ligne.getDepartement();
                if (date != null && code != null && code.contains("DEP-")) {
                    try {
                        Long deces = Long.valueOf(ligne.getDeces());
                        this.morts.add(new Ligne(deces, code, date));
                    } catch (ParseException | NumberFormatException ignored) {
                    }
                }
                if (date != null && code != null && code.contains("DEP-")) {
                    try {
                        Long hospitalisations = Long.valueOf(ligne.getHospitalisations());
                        this.hospitalisations.add(new Ligne(hospitalisations, code, date));
                    } catch (ParseException | NumberFormatException ignored) {
                    }
                }
                if (date != null && code != null && code.contains("DEP-")) {
                    try {
                        Long reanimations = Long.valueOf(ligne.getReanimations());
                        this.reanimations.add(new Ligne(reanimations, code, date));
                    } catch (ParseException | NumberFormatException ignored) {
                    }
                }
                if (date != null && code != null && (code.contains("DEP-"))) {
                    try {
                        Long vaccinations = Long.valueOf(ligne.getVaccinations());
                        this.vaccinations.add(new Ligne(vaccinations, code, date));
                    } catch (ParseException | NumberFormatException ignored) {
                    }
                }
                if (date != null && code != null && (code.contains("DEP-") | code.contains("REG-"))) {
                    try {
                        Long vaccinations_calcul = Long.valueOf(ligne.getVaccinations());
                        this.vaccinations_calcul.add(new Ligne(vaccinations_calcul, code, date));
                    } catch (ParseException | NumberFormatException ignored) {
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public Lignes getMorts() {
        return morts;
    }

    public Lignes getReanimations() {
        return reanimations;
    }

    public Lignes getHospitalisations() {
        return hospitalisations;
    }

    public Lignes getVaccinations() {
        return vaccinations;
    }

    public Lignes getVaccinations_calcul() {
        return vaccinations_calcul;
    }
}
