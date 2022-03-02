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

public class ReanimationController {

    private Lignes lignes;
    private ArrayList<String> dep_choosen;
    @FXML
    private NumberAxis y_reanimation;
    @FXML
    private CategoryAxis x_reanimation;
    @FXML
    private LineChart<String, Number> reanimation_chart;

    @FXML
    private void initialize() {
        x_reanimation.setLabel("Date");
        y_reanimation.setLabel("Nombre");
        this.lignes = App.JSONLOADER.getHospitalisations();
        this.dep_choosen = new ArrayList<>();
        this.reanimation_chart.setAnimated(false);
    }

    void addReanimationDep(String dep) {
        if (this.lignes.filterByDep(dep).size() > 0) {
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName(dep);
            this.lignes.filterByDep(dep).forEach(ligne -> series.getData().add(new XYChart.Data<>(ligne.getStringDate(), ligne.getCount())));
            this.dep_choosen.add(dep);
            reanimation_chart.getData().add(series);
        }
    }

    void removeReanimation() {
        this.dep_choosen = new ArrayList<>();
        this.reanimation_chart.getData().clear();
    }

    void addReanimationDate(Date d1, Date d2) {
        this.reanimation_chart.getData().clear();
        this.dep_choosen.forEach(dep -> {
            if (this.lignes.filterByDep(dep).size() > 0) {
                XYChart.Series<String, Number> series = new XYChart.Series<>();
                series.setName(dep);
                this.lignes.filterByDep(dep).stream()
                        .filter(ligne -> ligne.getDate().after(d1) && ligne.getDate().before(d2))
                        .forEach(ligne -> series.getData().add(new XYChart.Data<>(ligne.getStringDate(), ligne.getCount())));
                reanimation_chart.getData().add(series);
            } else {
                System.out.println("Aucune données pour ce filtre");
            }
        });
    }
}
