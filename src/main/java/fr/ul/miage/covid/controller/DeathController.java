package fr.ul.miage.covid.controller;

import fr.ul.miage.covid.App;
import fr.ul.miage.modeles.Lignes;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.util.ArrayList;
import java.util.Date;

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
    private void initialize() {
        x_deaths.setLabel("Date");
        y_deaths.setLabel("Nombre");
        this.lignes = App.JSONLOADER.getMorts();
        this.dep_choosen = new ArrayList<>();
        this.deaths_chart.setAnimated(false);
    }

    void addDeathsDep(String dep) {
        if (this.lignes.filterByDep(dep).size() > 0) {
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName(dep);
            this.lignes.filterByDep(dep).forEach(ligne -> series.getData().add(new XYChart.Data<>(ligne.getStringDate(), ligne.getCount())));
            this.dep_choosen.add(dep);
            deaths_chart.getData().add(series);
        }
    }

    void removeDeaths() {
        this.dep_choosen = new ArrayList<>();
        this.deaths_chart.getData().clear();
    }

    void addDeathDate(Date d1, Date d2) {
        this.deaths_chart.getData().clear();
        this.dep_choosen.forEach(dep -> {
            if (this.lignes.filterByDep(dep).size() > 0) {
                XYChart.Series<String, Number> series = new XYChart.Series<>();
                series.setName(dep);
                this.lignes.filterByDep(dep).stream()
                        .filter(ligne -> ligne.getDate().after(d1) && ligne.getDate().before(d2))
                        .forEach(ligne -> series.getData().add(new XYChart.Data<>(ligne.getStringDate(), ligne.getCount())));
                deaths_chart.getData().add(series);
            } else {
                System.out.println("Aucune donn??es pour ce filtre");
            }
        });
    }
}
