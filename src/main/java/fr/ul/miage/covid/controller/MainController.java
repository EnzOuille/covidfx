package fr.ul.miage.covid.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import javafx.scene.layout.BorderPane;

public class MainController {
	
	@FXML private BorderPane main_pane;
	@FXML private MenuButton menu_button_dep;
	@FXML private TitledPane accordion_d;
	@FXML private TitledPane accordion_h;
	@FXML private TitledPane accordion_r;
	@FXML private Accordion accordion;
	@FXML private Button reinit;
	@FXML private DatePicker dp1;
	@FXML private DatePicker dp2;
	@FXML private Button apply_date;
	
	private DeathController deathController;
	
	@FXML
	private void initialize() throws IOException {
		this.addListenersAndContainers();
		this.loadFXML();
	}
	
	private void addListenersAndContainers() {
		ArrayList<CustomMenuItem> menu_list = new ArrayList();
		for (int i=1; i <91; i++) {
			CheckBox cb = new CheckBox(String.valueOf(i));
			CustomMenuItem item = new CustomMenuItem(cb);
			item.setHideOnClick(false);
			cb.setOnAction(e -> {
				CheckBox check = (CheckBox) e.getSource();
				if (check.isSelected() == true) {
					this.deathController.addDeathsDep(check.getText());
					check.setDisable(true);
				}
			});
			menu_list.add(item);
		}
		this.accordion.setExpandedPane(this.accordion_d);
		this.menu_button_dep.getItems().setAll(menu_list);
		this.reinit.setOnAction(e -> {
			this.deathController.removeDeaths();
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
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
				System.out.println(date);
				System.out.println(date2);
				this.deathController.addDeathDate(date,date2);
			}else {
				System.out.println("Date nulle");
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
		loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/reanimations_panel.fxml"));
		this.accordion_r.setContent(loader.load());
	}
	
	private void reinit() {
		this.menu_button_dep.getItems().forEach(x -> {
			CustomMenuItem custom_menu = (CustomMenuItem) x;
			CheckBox check = (CheckBox) custom_menu.getContent();
			check.setSelected(false);
			check.setDisable(false);
		});
	}
}
