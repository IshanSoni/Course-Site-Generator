/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.workspace;

import csg.SiteGenApp;
import csg.SiteGenProp;
import csg.data.Recitation;
import csg.data.SiteGenData;
import csg.data.TeachingAssistant;
import djf.components.AppDataComponent;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import properties_manager.PropertiesManager;

/**
 *
 * @author isoni
 */
public class RecitationWorkspace {
    SiteGenApp app;
    SiteGenController controller;
    Tab tab;
    
    //HEADERS
    HBox headers;
    Label recitationHeader;
    Button removeButton;
    
    //GRID and ADDBOX
    VBox gridAndAdd;
    TableView<Recitation> recitationTable;
    TableColumn<Recitation, String> sectionCol;
    TableColumn<Recitation, String> instructorCol;
    TableColumn<Recitation, String> dayTimeCol;
    TableColumn<Recitation, String> locationCol;
    TableColumn<Recitation, String> supTA1Col;
    TableColumn<Recitation, String> supTA2Col;
    VBox addBox;
    Label addLabel;
    HBox fields;
    VBox labels;
    Label section;
    Label instructor;
    Label dayTime;
    Label location;
    Label supTA1;
    Label supTA2;
    VBox textFields;
    TextField sectionF;
    TextField instructorF;
    TextField dayTimeF;
    TextField locationF;
    ComboBox supTA1F;
    ComboBox supTA2F;   
    
    HBox addClearButtons;
    Button addButton;
    Button clearButton;
    
    VBox content;
    
    public RecitationWorkspace(SiteGenApp initApp, Tab initTab, Pane workspace, SiteGenController initController) {
        app = initApp;
        controller = initController;
        tab = initTab;
        
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        headers = new HBox(20);
        recitationHeader = new Label(props.getProperty(SiteGenProp.RECITATION_HEADER.toString()));
        removeButton = new Button("-");
        headers.getChildren().add(recitationHeader);
        headers.getChildren().add(removeButton);
        
        gridAndAdd = new VBox();
        recitationTable = new TableView();
        recitationTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        SiteGenData data = (SiteGenData) app.getDataComponent();
        ObservableList<Recitation> recitationData = data.getRecitations();
        recitationTable.setItems(recitationData);
        String sectionText = props.getProperty(SiteGenProp.SECTION.toString());
        String instructorText = props.getProperty(SiteGenProp.INSTRUCTOR.toString());
        String dayTimeText = props.getProperty(SiteGenProp.DAYTIME.toString());
        String locationText = props.getProperty(SiteGenProp.LOCATION.toString());
        String supTA1Text = props.getProperty(SiteGenProp.SUPTA1.toString());
        String supTA2Text = props.getProperty(SiteGenProp.SUPTA2.toString());
        sectionCol = new TableColumn(sectionText);
        instructorCol = new TableColumn(instructorText);
        dayTimeCol = new TableColumn(dayTimeText);
        locationCol = new TableColumn(locationText);
        supTA1Col = new TableColumn(supTA1Text);
        supTA2Col = new TableColumn(supTA2Text);
        sectionCol.setCellValueFactory(
                new PropertyValueFactory<Recitation, String>("section")
        );
        instructorCol.setCellValueFactory(
                new PropertyValueFactory<Recitation, String>("instructor")
        );
        dayTimeCol.setCellValueFactory(
                new PropertyValueFactory<Recitation, String>("dayTime")
        );
        locationCol.setCellValueFactory(
                new PropertyValueFactory<Recitation, String>("location")
        );
        supTA1Col.setCellValueFactory(
                new PropertyValueFactory<Recitation, String>("ta1")
        );
        supTA2Col.setCellValueFactory(
                new PropertyValueFactory<Recitation, String>("ta2")
        );
        sectionCol.prefWidthProperty().bind(recitationTable.widthProperty().multiply(0.10));
        instructorCol.prefWidthProperty().bind(recitationTable.widthProperty().multiply(0.15));
        dayTimeCol.prefWidthProperty().bind(recitationTable.widthProperty().multiply(0.35));
        locationCol.prefWidthProperty().bind(recitationTable.widthProperty().multiply(0.20));
        supTA1Col.prefWidthProperty().bind(recitationTable.widthProperty().multiply(0.10));
        supTA2Col.prefWidthProperty().bind(recitationTable.widthProperty().multiply(0.10));
        
        recitationTable.getColumns().add(sectionCol);
        recitationTable.getColumns().add(instructorCol);
        recitationTable.getColumns().add(dayTimeCol);
        recitationTable.getColumns().add(locationCol);
        recitationTable.getColumns().add(supTA1Col);
        recitationTable.getColumns().add(supTA2Col);
        gridAndAdd.getChildren().add(recitationTable);
        
        addBox = new VBox();
        String addLabelText = props.getProperty(SiteGenProp.ADD_EDIT.toString());
        addLabel = new Label(addLabelText);
        addBox.getChildren().add(addLabel);
        
        fields = new HBox(10);
        
        labels = new VBox(11);
        section = new Label(sectionText);
        instructor = new Label(instructorText);
        dayTime = new Label(dayTimeText);
        location = new Label(locationText);
        supTA1 = new Label(props.getProperty(SiteGenProp.SUPERTA1.toString()));
        supTA2 = new Label(props.getProperty(SiteGenProp.SUPERTA2.toString()));
        addButton = new Button(props.getProperty(SiteGenProp.ADDUPDATE.toString()));
        labels.getChildren().add(section);
        labels.getChildren().add(instructor);
        labels.getChildren().add(dayTime);
        labels.getChildren().add(location);
        labels.getChildren().add(supTA1);
        labels.getChildren().add(supTA2);
        
        textFields = new VBox();
        sectionF = new TextField();
        instructorF = new TextField();
        dayTimeF = new TextField();
        locationF = new TextField();
        supTA1F = new ComboBox();
        supTA2F = new ComboBox();
        supTA1F.setItems(data.getTAList());
        supTA2F.setItems(data.getTAList());
        clearButton = new Button(props.getProperty(SiteGenProp.CLEAR_BUTTON_TEXT.toString()));
        textFields.getChildren().add(sectionF);
        textFields.getChildren().add(instructorF);
        textFields.getChildren().add(dayTimeF);
        textFields.getChildren().add(locationF);
        textFields.getChildren().add(supTA1F);
        textFields.getChildren().add(supTA2F);
        
        fields.getChildren().add(labels);
        fields.getChildren().add(textFields);
        
        addBox.getChildren().add(fields);
        
        addClearButtons = new HBox(22);
        addClearButtons.getChildren().add(addButton);
        addClearButtons.getChildren().add(clearButton);
        addBox.getChildren().add(addClearButtons);
        
        gridAndAdd.getChildren().add(addBox);
        
        content = new VBox();
        content.getChildren().add(headers);
        content.getChildren().add(gridAndAdd);
        tab.setContent(content);
        
        addButton.setOnAction(e -> {
            controller.handleAddRecitation();
        });
        
        recitationTable.setOnMouseClicked(e1 -> {   
            Object selectedItem = recitationTable.getSelectionModel().getSelectedItem();
            if(selectedItem != null) {
                Recitation r = (Recitation)selectedItem;
                sectionF.setText(r.getSection());
                instructorF.setText(r.getInstructor());
                dayTimeF.setText(r.getDayTime());
                locationF.setText(r.getLocation());
                supTA1F.setValue(r.getTa1());
                supTA2F.setValue(r.getTa2());
                addButton.setOnAction(e2 -> {
                    controller.handleEditRecitation(r);
                });
            }
        });
        
        clearButton.setOnAction(e -> {
            
            sectionF.setText("");
            instructorF.setText("");
            dayTimeF.setText("");
            locationF.setText("");
            supTA1F.setValue("");
            supTA2F.setValue("");
            
            recitationTable.getSelectionModel().clearSelection();
            sectionF.requestFocus();
            addButton.setOnAction(e2 -> {
                controller.handleAddRecitation();
            });
        });
        
        recitationTable.setFocusTraversable(true);
        recitationTable.setOnKeyPressed(e -> {
            controller.handleRecitationKeyPress(e.getCode());
        });
        
        removeButton.setOnAction(e -> {
           controller.handleRecitationKeyPress(KeyCode.DELETE);
        });
    }
    
    public void resetWorkspace() {
        
    }
    
    public void reloadWorkspace(AppDataComponent dataComponent) {
        SiteGenData data = (SiteGenData) dataComponent;
        data.getRecitations().clear();
        recitationTable.refresh();
    }
    
    public VBox getAddBox() {
        return addBox;
    }
    
    public Label getAddLabel() {
        return addLabel;
    }
    
    public HBox getHeaders() {
        return headers;
    }
    
    public Label getRecitationHeader() {
        return recitationHeader;
    }
    
    public VBox getContent() {
        return content;
    }
    
    public TableView getRecitationTable() {
        return recitationTable;
    }
    
    public TextField getSection() {
        return sectionF;
    }

    TextField getInstructor() {
        return instructorF;
    }

    TextField getDayTime() {
        return dayTimeF;
    }

    TextField getLocation() {
        return locationF;
    }

    ComboBox getTA1() {
        return supTA1F;
    }

    ComboBox getTA2() {
        return supTA2F;
    }
}
