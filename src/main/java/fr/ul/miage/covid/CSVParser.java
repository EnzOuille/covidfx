package fr.ul.miage.covid;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;
import java.util.List;

import com.opencsv.bean.CsvToBeanBuilder;

import fr.ul.miage.modeles.CSVLine;
import fr.ul.miage.modeles.Ligne;
import fr.ul.miage.modeles.Lignes;

import java.text.ParseException;

public class CSVParser {

	private FileReader file_reader;

	private final Lignes morts = new Lignes();

	private final Lignes reanimations = new Lignes();

	private final Lignes hospitalisations = new Lignes();

	private final Lignes vaccinations = new Lignes();

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
				Long deces = Long.valueOf(ligne.getDeces());
				Long hospitalisations = Long.valueOf(ligne.getHospitalisations());
				Long reanimations = Long.valueOf(ligne.getReanimations());
				String date = ligne.getDate();
				String code = ligne.getDepartement();
				if (deces != null && date != null && code != null && code.contains("DEP-")) {
					try {
						this.morts.add(new Ligne(deces, code, date));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (hospitalisations != null && date != null && code != null && code.contains("DEP-")) {
					try {
						this.hospitalisations.add(new Ligne(hospitalisations, code, date));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (reanimations != null && date != null && code != null && code.contains("DEP-")) {
					try {
						this.reanimations.add(new Ligne(reanimations, code, date));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public FileReader getFile_reader() {
		return file_reader;
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
