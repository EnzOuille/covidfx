package fr.ul.miage.covid.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.stream.Collectors;

import fr.ul.miage.covid.App;
import fr.ul.miage.modeles.Ligne;
import fr.ul.miage.modeles.Lignes;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.MenuButton;

public class DeathController {

	private Lignes lignes;
	private ArrayList<String> dep_choosen;
	@FXML
	private NumberAxis y_deaths;
	@FXML
	private CategoryAxis x_deaths;
	@FXML
	private LineChart<String, Number> deaths_chart;

	@FXML
	private void initialize() throws IOException {
		x_deaths.setLabel("Date");
		y_deaths.setLabel("Nombre");
		this.lignes = App.jsonloader.getMorts();
		this.dep_choosen = new ArrayList();
		this.deaths_chart.setAnimated(false);
	}

	void addDeathsDep(String dep) {
		if (this.lignes.filterByDep(dep).size() > 0) {
			XYChart.Series<String, Number> series = new XYChart.Series();
			series.setName(dep);
			this.lignes.filterByDep(dep).forEach(ligne -> {
				series.getData().add(new XYChart.Data<String, Number>(((Ligne) ligne).getStringDate(), ((Ligne) ligne).getCount()));
			});
			this.dep_choosen.add(dep);
			deaths_chart.getData().add(series);
		}
	}

	void removeDeaths() {
		this.dep_choosen = new ArrayList();
		this.deaths_chart.getData().clear();
	}

	void addDeathDate(Date d1, Date d2) {
		this.deaths_chart.getData().clear();
		this.dep_choosen.forEach(dep -> {
			if (this.lignes.filterByDep(dep).size() > 0) {
				XYChart.Series<String, Number> series = new XYChart.Series();
				series.setName(dep);
				this.lignes.filterByDep(dep).stream()
				.filter(ligne -> ((Ligne) ligne).getDate().after(d1) && ((Ligne) ligne).getDate().before(d2))
				.forEach(ligne -> {
					series.getData().add(new XYChart.Data<String, Number>(((Ligne) ligne).getStringDate(), ((Ligne) ligne).getCount()));
				});
				deaths_chart.getData().add(series);
			} else {
				System.out.println("Aucune données pour ce filtre");
			}
		});
	}
}
