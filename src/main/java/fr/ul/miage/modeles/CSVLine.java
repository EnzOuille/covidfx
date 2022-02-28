package fr.ul.miage.modeles;

import com.opencsv.bean.CsvBindByName;

public class CSVLine {

	@CsvBindByName(column="date")
	private String date;

	@CsvBindByName(column="maille_code")
	private String departement;

	@CsvBindByName(column="deces")
	private String deces;

	@CsvBindByName(column="reanimation")
	private String reanimations;

	@CsvBindByName(column="hospitalises")
	private String hospitalisations;

	@CsvBindByName(column="vaccines_premier_dose")
	private String vaccinations;

	public String getDate() {
		return date;
	}

	public String getDepartement() {
		return departement;
	}

	public String getDeces() {
		return deces;
	}

	public String getReanimations() {
		return reanimations;
	}

	public String getHospitalisations() {
		return hospitalisations;
	}

	public String getVaccinations() {
		return vaccinations;
	}

}
