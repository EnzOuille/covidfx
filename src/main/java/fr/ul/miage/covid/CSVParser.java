package fr.ul.miage.covid;

import com.opencsv.bean.CsvToBeanBuilder;
import fr.ul.miage.modeles.CSVLine;
import fr.ul.miage.modeles.Ligne;
import fr.ul.miage.modeles.Lignes;

import java.io.*;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class CSVParser {

    private final Lignes morts = new Lignes();
    private final Lignes reanimations = new Lignes();
    private final Lignes hospitalisations = new Lignes();
    private final Lignes vaccinations = new Lignes();

    private FileReader file_reader;

    public CSVParser(String filename) {
        try {
            ClassLoader classLoader = CSVParser.class.getClassLoader();
            InputStream initialStream = getClass().getResourceAsStream(filename);
            OutputStream outStream = new FileOutputStream("final.csv");
            byte[] buffer = new byte[8 * 1024];
            int bytesRead;
            while ((bytesRead = initialStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, bytesRead);
            }
            initialStream.close();
            outStream.close();
            this.file_reader = new FileReader("final.csv");
            this.parseCSV();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

}
