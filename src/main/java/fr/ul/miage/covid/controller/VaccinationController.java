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

public class VaccinationController {

    private Lignes lignes;
    private ArrayList<String> dep_choosen;
    @FXML
    private NumberAxis y_vaccination;

    @FXML
    private CategoryAxis x_vaccination;
    @FXML
    private LineChart<String, Number> vaccination_chart;

    @FXML
    private void initialize() {
        x_vaccination.setLabel("Date");
        y_vaccination.setLabel("Nombre");
        this.lignes = App.JSONLOADER.getVaccinations();
        this.dep_choosen = new ArrayList<>();
        this.vaccination_chart.setAnimated(false);
    }

    void addVaccinationDep(String dep) {
        if (this.lignes.filterByDep(dep).size() > 0) {
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName(dep);
            this.lignes.filterByDep(dep).forEach(ligne -> series.getData().add(new XYChart.Data<>(ligne.getStringDate(), ligne.getCount())));
            this.dep_choosen.add(dep);
            vaccination_chart.getData().add(series);
        }
    }

    void removeVaccination() {
        this.dep_choosen = new ArrayList<>();
        this.vaccination_chart.getData().clear();
    }

    void addVaccinationDate(Date d1, Date d2) {
        this.vaccination_chart.getData().clear();
        this.dep_choosen.forEach(dep -> {
            if (this.lignes.filterByDep(dep).size() > 0) {
                XYChart.Series<String, Number> series = new XYChart.Series<>();
                series.setName(dep);
                this.lignes.filterByDep(dep).stream()
                        .filter(ligne -> ligne.getDate().after(d1) && ligne.getDate().before(d2))
                        .forEach(ligne -> series.getData().add(new XYChart.Data<>(ligne.getStringDate(), ligne.getCount())));
                vaccination_chart.getData().add(series);
            } else {
                System.out.println("Aucune données pour ce filtre");
            }
        });
    }
}
