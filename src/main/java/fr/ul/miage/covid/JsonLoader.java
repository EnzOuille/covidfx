package fr.ul.miage.covid;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvValidationException;

import fr.ul.miage.modeles.CSVLine;
import fr.ul.miage.modeles.Ligne;
import fr.ul.miage.modeles.Lignes;

import java.text.ParseException;

public class JsonLoader {

	private FileReader file_reader;

	private Lignes morts = new Lignes();

	private Lignes reanimations = new Lignes();

	private Lignes hospitalisations = new Lignes();

	private Lignes vaccinations = new Lignes();

	public JsonLoader(String filename) {
		ClassLoader classLoader = JsonLoader.class.getClassLoader();
		URL resource = classLoader.getResource(filename);
		try {
			this.file_reader = (new FileReader(resource.getFile()));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		this.parseCSV();
		// this.parseJson();
	}

	public void parseJson() {
		try {
			JSONArray json_array = (JSONArray) new JSONParser().parse(this.file_reader);
			Iterator<JSONObject> iterator = json_array.iterator();
			while (iterator.hasNext()) {
				JSONObject temp = iterator.next();
				Long deces = (Long) temp.get("deces");
				Long hospitalisations = (Long) temp.get("hospitalises");
				Long reanimations = (Long) temp.get("reanimation");
				String date = (String) temp.get("date");
				String code = (String) temp.get("code");
				if (deces != null && date != null && code != null && code.contains("DEP-")) {
					this.morts.add(new Ligne(deces, code, date));
				}
				if (hospitalisations != null && date != null && code != null && code.contains("DEP-")) {
					this.hospitalisations.add(new Ligne(hospitalisations, code, date));
				}
				if (reanimations != null && date != null && code != null && code.contains("DEP-")) {
					this.reanimations.add(new Ligne(reanimations, code, date));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void parseCSV() {
		try {
			List<CSVLine> beans = new CsvToBeanBuilder(this.file_reader).withType(CSVLine.class).build().parse();
			beans.forEach(ligne -> {
				Long deces = Long.parseLong(ligne.getDeces());
				Long hospitalisations = Long.parseLong(ligne.getHospitalisations());
				Long reanimations = Long.parseLong(ligne.getReanimations());
				String date = (String) ligne.getDate();
				String code = (String) ligne.getDepartement();
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
