package fr.ul.miage.covid.controller;

import fr.ul.miage.covid.App;
import fr.ul.miage.modeles.Lignes;
import javafx.fxml.FXML;
import javafx.scene.chart.*;

import java.util.ArrayList;
import java.util.Date;

public class NationalController {

    private Lignes hospitalisations;
    private Lignes deces;
    private Lignes reanimations;
    private Lignes vaccinations;

    @FXML
    private NumberAxis y_national;
    @FXML
    private CategoryAxis x_national;
    @FXML
    private BarChart<String, Number> national_chart;

    @FXML
    private void initialize() {
        x_national.setLabel("Date");
        y_national.setLabel("Nombre");
        this.hospitalisations = App.JSONLOADER.getVaccinations_calcul();
        this.deces = App.JSONLOADER.getMorts();
        this.reanimations = App.JSONLOADER.getReanimations();
        this.vaccinations = App.JSONLOADER.getVaccinations();
        this.national_chart.setAnimated(false);
        XYChart.Series<String, Number> dataSeries1 = new XYChart.Series<>();
        dataSeries1.setName("National");
        dataSeries1.getData().add(new XYChart.Data<String, Number>("Morts", this.deces.returnSum()));
        dataSeries1.getData().add(new XYChart.Data<String, Number>("Reanimations", this.reanimations.returnSum()));
        dataSeries1.getData().add(new XYChart.Data<String, Number>("Hospitalisations", this.hospitalisations.returnSum()));
        dataSeries1.getData().add(new XYChart.Data<String, Number>("Vaccinations", this.vaccinations.last()));
        national_chart.getData().add(dataSeries1);
    }
}
