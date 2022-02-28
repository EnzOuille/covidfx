package fr.ul.miage.covid.controller;

import fr.ul.miage.covid.App;
import fr.ul.miage.modeles.Ligne;
import fr.ul.miage.modeles.Lignes;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class TestController {
	
	private Lignes lignes;
	
    @FXML private CategoryAxis y ;
	@FXML private NumberAxis x ;
	@FXML private LineChart<String, Number> test;
	
	@FXML
	private void initialize() {
		x.setLabel("Date");
		y.setLabel("Nombre");
		XYChart.Series series = new XYChart.Series();
		series.setName("Test");
		this.lignes = App.jsonloader.getMorts();
		this.lignes.filterByDep("54").forEach(ligne -> series.getData().add(new XYChart.Data<>(((Ligne) ligne).getStringDate(),((Ligne) ligne).getCount())));
		test.getData().add(series);
	}
}
