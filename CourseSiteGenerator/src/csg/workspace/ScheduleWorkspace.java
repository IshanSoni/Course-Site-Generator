/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.workspace;

import csg.SiteGenApp;
import csg.SiteGenProp;
import csg.data.Schedule;
import csg.data.SiteGenData;
import djf.components.AppDataComponent;
import java.time.chrono.ChronoLocalDate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
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
public class ScheduleWorkspace {

    SiteGenApp app;
    SiteGenController controller;
    Tab tab;

    VBox headers;
    Label scheduleHeader;
    VBox smallHeaders;
    Label calenderBoundHeader;
    HBox bounds;
    Label startBound;
    DatePicker start;
    Label endBound;
    DatePicker end;
    Button editDates;
    Label schedItemsHeader;
    Button removeButton;

    VBox gridAndAdd;
    TableView<Schedule> scheduleTable;
    TableColumn<Schedule, String> typeCol;
    TableColumn<Schedule, String> dateCol;
    TableColumn<Schedule, String> titleCol;
    TableColumn<Schedule, String> topicCol;
    VBox addBox;
    Label addLabel;
    HBox fields;
    VBox labels;
    Label type;
    Label date;
    Label time;
    Label title;
    Label topic;
    Label link;
    Label criteria;
    VBox textFields;
    ComboBox typeF;
    DatePicker dateF;
    TextField timeF;
    TextField titleF;
    TextField topicF;
    TextField linkF;
    TextField criteriaF;

    HBox addClearButtons;
    Button addButton;
    Button clearButton;

    VBox content;

    public ScheduleWorkspace(SiteGenApp initApp, Tab initTab, Pane workspace, SiteGenController initController) {
        app = initApp;
        controller = initController;
        tab = initTab;
        PropertiesManager props = PropertiesManager.getPropertiesManager();

        headers = new VBox(10);
        scheduleHeader = new Label(props.getProperty(SiteGenProp.SCHEDULE_HEADER));

        smallHeaders = new VBox(10);

        VBox boundBox = new VBox(5);
        calenderBoundHeader = new Label(props.getProperty(SiteGenProp.CALENDER_BOUNDS));
        bounds = new HBox(50);

        HBox startBox = new HBox();
        startBound = new Label(props.getProperty(SiteGenProp.START_BOUND));
        start = new DatePicker();
        startBox.getChildren().add(startBound);
        startBox.getChildren().add(start);

        HBox endBox = new HBox();
        endBound = new Label(props.getProperty(SiteGenProp.END_BOUND));
        end = new DatePicker();
        endBox.getChildren().add(endBound);
        endBox.getChildren().add(end);

        bounds.getChildren().add(startBox);
        bounds.getChildren().add(endBox);

        boundBox.getChildren().add(calenderBoundHeader);
        boundBox.getChildren().add(bounds);

        HBox schedAndButton = new HBox(20);
        schedItemsHeader = new Label(props.getProperty(SiteGenProp.SCHED_ITEMS_HEADER));
        removeButton = new Button("-");
        schedAndButton.getChildren().add(schedItemsHeader);
        schedAndButton.getChildren().add(removeButton);

        smallHeaders.getChildren().add(boundBox);
        smallHeaders.getChildren().add(schedAndButton);
        headers.getChildren().add(scheduleHeader);
        headers.getChildren().add(smallHeaders);

        gridAndAdd = new VBox();
        scheduleTable = new TableView();
        scheduleTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        SiteGenData data = (SiteGenData) app.getDataComponent();
        ObservableList<Schedule> scheduleData = data.getSchedules();
        scheduleTable.setItems(scheduleData);
        String typeText = props.getProperty(SiteGenProp.TYPE.toString());
        String dateText = props.getProperty(SiteGenProp.DATE.toString());
        String timeText = props.getProperty(SiteGenProp.TIME.toString());
        String titleText = props.getProperty(SiteGenProp.TITLE.toString());
        String topicText = props.getProperty(SiteGenProp.TOPIC.toString());
        String linkText = props.getProperty(SiteGenProp.LINK.toString());
        String criteriaText = props.getProperty(SiteGenProp.CRITERIA.toString());
        typeCol = new TableColumn(typeText);
        dateCol = new TableColumn(dateText);
        titleCol = new TableColumn(titleText);
        topicCol = new TableColumn(topicText);
        typeCol.setCellValueFactory(
                new PropertyValueFactory<Schedule, String>("type")
        );
        dateCol.setCellValueFactory(
                new PropertyValueFactory<Schedule, String>("date")
        );
        titleCol.setCellValueFactory(
                new PropertyValueFactory<Schedule, String>("title")
        );
        topicCol.setCellValueFactory(
                new PropertyValueFactory<Schedule, String>("topic")
        );
        scheduleTable.setPrefHeight(220);
        typeCol.prefWidthProperty().bind(scheduleTable.widthProperty().multiply(0.15));
        dateCol.prefWidthProperty().bind(scheduleTable.widthProperty().multiply(0.20));
        titleCol.prefWidthProperty().bind(scheduleTable.widthProperty().multiply(0.30));
        topicCol.prefWidthProperty().bind(scheduleTable.widthProperty().multiply(0.35));

        scheduleTable.getColumns().add(typeCol);
        scheduleTable.getColumns().add(dateCol);
        scheduleTable.getColumns().add(titleCol);
        scheduleTable.getColumns().add(topicCol);
        gridAndAdd.getChildren().add(scheduleTable);

        addBox = new VBox();
        String addLabelText = props.getProperty(SiteGenProp.ADD_EDIT.toString());
        addLabel = new Label(addLabelText);
        addBox.getChildren().add(addLabel);

        fields = new HBox(10);

        labels = new VBox(11);
        type = new Label(typeText);
        date = new Label(dateText);
        time = new Label(timeText);
        title = new Label(titleText);
        topic = new Label(topicText);
        link = new Label(linkText);
        criteria = new Label(criteriaText);
        addButton = new Button(props.getProperty(SiteGenProp.ADDUPDATE.toString()));
        labels.getChildren().add(type);
        labels.getChildren().add(date);
        labels.getChildren().add(time);
        labels.getChildren().add(title);
        labels.getChildren().add(topic);
        labels.getChildren().add(link);
        labels.getChildren().add(criteria);

        textFields = new VBox();
        typeF = new ComboBox();
        ObservableList<String> scheduleTypes = FXCollections.observableArrayList();
        scheduleTypes.addAll("Holiday", "Lecture", "Reference", "Recitation", "Hw");
        typeF.setItems(scheduleTypes);
        dateF = new DatePicker();
        timeF = new TextField();
        titleF = new TextField();
        topicF = new TextField();
        linkF = new TextField();
        criteriaF = new TextField();
        clearButton = new Button(props.getProperty(SiteGenProp.CLEAR_BUTTON_TEXT.toString()));
        textFields.getChildren().add(typeF);
        textFields.getChildren().add(dateF);
        textFields.getChildren().add(timeF);
        textFields.getChildren().add(titleF);
        textFields.getChildren().add(topicF);
        textFields.getChildren().add(linkF);
        textFields.getChildren().add(criteriaF);

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
            controller.handleAddSchedule();
        });

        scheduleTable.setOnMouseClicked(e1 -> {
            Object selectedItem = scheduleTable.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                Schedule s = (Schedule) selectedItem;
                typeF.setValue(s.getType());
                dateF.setValue(s.getDate());
                timeF.setText(s.getTime());
                titleF.setText(s.getTitle());
                topicF.setText(s.getTopic());
                linkF.setText(s.getLink());
                criteriaF.setText(s.getCriteria());
                addButton.setOnAction(e2 -> {
                    controller.handleEditSchedule(s);
                });
            }
        });

        clearButton.setOnAction(e -> {

            typeF.setValue("");
            dateF.setValue(null);
            timeF.setText("");
            titleF.setText("");
            topicF.setText("");
            linkF.setText("");
            criteriaF.setText("");

            scheduleTable.getSelectionModel().clearSelection();
            titleF.requestFocus();
            addButton.setOnAction(e2 -> {
                controller.handleAddSchedule();
            });
        });

        scheduleTable.setFocusTraversable(true);
        scheduleTable.setOnKeyPressed(e -> {
            controller.handleScheduleRemove(e.getCode());
        });

        removeButton.setOnAction(e -> {
            controller.handleScheduleRemove(KeyCode.DELETE);
        });

        start.valueProperty().addListener((e, o, n) -> {
            if (n != null && end.getValue() != null) {
                if (n.compareTo((ChronoLocalDate) end.getValue()) > 0) {
                    controller.handleBoundError();
                    start.setValue(o);
                } else {
                    data.setStartingMonday(n);
                    data.setEndingFriday(end.getValue());
                }
            } 
        });

        end.valueProperty().addListener((e, o, n) -> {
            if (n != null && start.getValue() != null) {
                if (n.compareTo((ChronoLocalDate) start.getValue()) < 0) {
                    controller.handleBoundError();
                    end.setValue(o);
                } else {
                    data.setEndingFriday(n);
                    data.setStartingMonday(start.getValue());
                }
            }
        });
    }

    public void resetWorkspace() {
        
    }
    
    public void reloadWorkspace(AppDataComponent dataComponent) {
        SiteGenData data = (SiteGenData) dataComponent;
        data.getSchedules().clear();
        start.setValue(null);
        end.setValue(null);
        scheduleTable.refresh();
    }

    public VBox getSmallHeaders() {
        return smallHeaders;
    }

    public VBox getAddBox() {
        return addBox;
    }

    public Label getAddLabel() {
        return addLabel;
    }

    public VBox getHeaders() {
        return headers;
    }

    public Label getScheduleHeader() {
        return scheduleHeader;
    }

    public VBox getContent() {
        return content;
    }

    public TableView getScheduleTable() {
        return scheduleTable;
    }

    public DatePicker getStartDate() {
        return start;
    }

    public DatePicker getEndDate() {
        return end;
    }
}
