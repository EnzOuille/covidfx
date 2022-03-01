package fr.ul.miage.covid.controller;

import fr.ul.miage.covid.App;
import fr.ul.miage.modeles.Lignes;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class TestController {
	
	private final Lignes lignes = App.JSONLOADER.getMorts();
	
    @FXML private CategoryAxis y ;
	@FXML private NumberAxis x ;
	@FXML private LineChart<String, Number> test;
	
	@FXML
	private void initialize() {
		x.setLabel("Date");
		y.setLabel("Nombre");
		XYChart.Series<String, Number> series = new XYChart.Series<>();
		series.setName("Test");
		this.lignes.filterByDep("54").forEach(ligne -> series.getData().add(new XYChart.Data<>(ligne.getStringDate(),ligne.getCount())));
		test.getData().add(series);
	}
}
