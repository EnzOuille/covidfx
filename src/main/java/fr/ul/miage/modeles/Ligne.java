package fr.ul.miage.modeles;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Ligne {
	
	private Long count;
	
	private String departement;
	
	private Date date;
	
	public Ligne (Long count, String departement, String date) throws ParseException {
		this.count = count;
		this.departement = departement.replace("DEP-", "");
		this.departement = this.departement.replace("REG-","");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
		this.date = sdf.parse(date);
	}
	
	public String toString() {
		return "Count : " + this.count + " Departement : " + this.departement + " Date : " + this.date;
	}

	public Long getCount() {
		return count;
	}

	public String getDepartement() {
		return departement;
	}

	public Date getDate() {
		return date;
	}
	
	public String getStringDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
		return sdf.format(this.date);
	}
}
