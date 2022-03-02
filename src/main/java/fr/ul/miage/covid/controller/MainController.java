package fr.ul.miage.covid.controller;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TitledPane;

public class MainController {
	
	@FXML private MenuButton menu_button_dep;
	@FXML private TitledPane accordion_d;
	@FXML private TitledPane accordion_h;
	@FXML private TitledPane accordion_r;
	@FXML private TitledPane accordion_v;
	@FXML private Accordion accordion;
	@FXML private Button reinit;
	@FXML private DatePicker dp1;
	@FXML private DatePicker dp2;
	@FXML private Button apply_date;
	
	private DeathController deathController;
	private HospitalController hospitalController;
	private ReanimationController reanimationController;
	private VaccinationController vaccinationController;
	
	@FXML
	private void initialize() throws IOException {
		this.addListenersAndContainers();
		this.loadFXML();
	}
	
	private void addListenersAndContainers() {
		ArrayList<CustomMenuItem> menu_list = new ArrayList<>();
		for (int i=1; i <91; i++) {
			CheckBox cb = new CheckBox(String.valueOf(i));
			CustomMenuItem item = new CustomMenuItem(cb);
			item.setHideOnClick(false);
			cb.setOnAction(e -> {
				CheckBox check = (CheckBox) e.getSource();
				if (check.isSelected()) {
					this.deathController.addDeathsDep(check.getText());
					this.hospitalController.addHospitalDep(check.getText());
					this.reanimationController.addReanimationDep(check.getText());
					this.vaccinationController.addVaccinationDep(check.getText());
					check.setDisable(true);
				}
			});
			menu_list.add(item);
		}
		this.accordion.setExpandedPane(this.accordion_d);
		this.menu_button_dep.getItems().setAll(menu_list);
		this.reinit.setOnAction(e -> {
			this.deathController.removeDeaths();
			this.hospitalController.removeHospital();
			this.reanimationController.removeReanimation();
			this.vaccinationController.removeVaccination();
			this.reinit();
		});
		this.apply_date.setOnAction(e -> {
			if(dp1.getValue() == null) {
				dp1.setValue(dp1.getConverter().fromString(dp1.getEditor().getText()));
			}
			if(dp2.getValue() == null) {
				dp2.setValue(dp2.getConverter().fromString(dp2.getEditor().getText()));
			}
			if (dp1.getValue() != null && dp2.getValue() != null) {
				LocalDate localDate = dp1.getValue();
				Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
				Date date = Date.from(instant);
				LocalDate localDate2 = dp2.getValue();
				Instant instant2 = Instant.from(localDate2.atStartOfDay(ZoneId.systemDefault()));
				Date date2 = Date.from(instant2);
				this.deathController.addDeathDate(date,date2);
				this.hospitalController.addHospitalDate(date,date2);
				this.reanimationController.addReanimationDate(date, date2);
				this.vaccinationController.addVaccinationDate(date, date2);
			}
		});
	}
	
	private void loadFXML() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/deaths_panel.fxml"));
		this.accordion_d.setContent(loader.load());
		this.deathController = loader.getController();
		loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/hospitals_panel.fxml"));
		this.accordion_h.setContent(loader.load());
		this.hospitalController = loader.getController();
		loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/reanimations_panel.fxml"));
		this.accordion_r.setContent(loader.load());
		this.reanimationController = loader.getController();
		loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/vaccinations_panel.fxml"));
		this.accordion_v.setContent(loader.load());
		this.vaccinationController = loader.getController();
	}
	
	private void reinit() {
		this.menu_button_dep.getItems().forEach(x -> {
			CustomMenuItem custom_menu = (CustomMenuItem) x;
			CheckBox check = (CheckBox) custom_menu.getContent();
			check.setSelected(false);
			check.setDisable(false);
		});
		this.dp1.setValue(null);
		this.dp2.setValue(null);
	}
}
