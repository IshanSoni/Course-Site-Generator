/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.workspace;

import csg.SiteGenApp;
import csg.SiteGenProp;
import csg.data.SiteGenData;
import csg.data.Student;
import csg.data.Team;
import djf.components.AppDataComponent;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
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
import javafx.scene.paint.Color;
import properties_manager.PropertiesManager;

/**
 *
 * @author isoni
 */
public class ProjectWorkspace {

    SiteGenApp app;
    SiteGenController controller;
    Tab tab;

    HBox headers;
    Label teamHeader;
    Button removeButton;

    //GRID and ADDBOX
    VBox gridAndAdd;
    TableView<Team> teamTable;
    TableColumn<Team, String> nameCol;
    TableColumn<Team, String> colorCol;
    TableColumn<Team, String> textColorCol;
    TableColumn<Team, String> linkCol;
    VBox addBox;
    Label addLabel;
    HBox fields;
    VBox labels;
    Label name;
    Label color;
    Label textColor;
    Label link;
    VBox textFields;
    TextField nameF;
    ColorPicker colorF;
    ColorPicker textColorF;
    TextField linkF;

    HBox addClearButtons;
    Button addButton;
    Button clearButton;

    HBox headers2;
    Label studentHeader;
    Button removeButton2;

    //GRID and ADDBOX
    VBox gridAndAdd2;
    TableView<Student> studentTable;
    TableColumn<Student, String> firstNameCol;
    TableColumn<Student, String> lastNameCol;
    TableColumn<Student, String> teamCol;
    TableColumn<Student, String> roleCol;
    VBox addBox2;
    Label addLabel2;
    HBox fields2;
    VBox labels2;
    Label firstName;
    Label lastName;
    Label team;
    Label role;
    VBox textFields2;
    TextField firstNameF;
    TextField lastNameF;
    ComboBox teamF;
    TextField roleF;

    HBox addClearButtons2;
    Button addButton2;
    Button clearButton2;

    VBox content1;
    VBox content2;
    VBox content;

    public ProjectWorkspace(SiteGenApp initApp, Tab initTab, Pane workspace, SiteGenController initController) {
        app = initApp;
        controller = initController;
        tab = initTab;

        PropertiesManager props = PropertiesManager.getPropertiesManager();

        headers = new HBox(20);
        teamHeader = new Label(props.getProperty(SiteGenProp.TEAM_HEADER.toString()));
        removeButton = new Button("-");
        headers.getChildren().add(teamHeader);
        headers.getChildren().add(removeButton);

        gridAndAdd = new VBox();
        teamTable = new TableView();
        teamTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        SiteGenData data = (SiteGenData) app.getDataComponent();
        ObservableList<Team> teamData = data.getTeams();
        teamTable.setItems(teamData);
        String nameText = props.getProperty(SiteGenProp.NAME.toString());
        String colorText = props.getProperty(SiteGenProp.COLOR.toString());
        String colorHexText = props.getProperty(SiteGenProp.COLOR_HEX.toString());
        String textColorText = props.getProperty(SiteGenProp.TEXT_COLOR.toString());
        String textColorHexText = props.getProperty(SiteGenProp.TEXT_COLOR_HEX.toString());
        String linkText = props.getProperty(SiteGenProp.LINK.toString());
        nameCol = new TableColumn(nameText);
        colorCol = new TableColumn(colorHexText);
        textColorCol = new TableColumn(textColorHexText);
        linkCol = new TableColumn(linkText);
        nameCol.setCellValueFactory(
                new PropertyValueFactory<Team, String>("name")
        );
        colorCol.setCellValueFactory(
                new PropertyValueFactory<Team, String>("color")
        );
        textColorCol.setCellValueFactory(
                new PropertyValueFactory<Team, String>("textColor")
        );
        linkCol.setCellValueFactory(
                new PropertyValueFactory<Team, String>("link")
        );
        nameCol.prefWidthProperty().bind(teamTable.widthProperty().multiply(0.25));
        colorCol.prefWidthProperty().bind(teamTable.widthProperty().multiply(0.25));
        textColorCol.prefWidthProperty().bind(teamTable.widthProperty().multiply(0.25));
        linkCol.prefWidthProperty().bind(teamTable.widthProperty().multiply(0.25));

        teamTable.getColumns().add(nameCol);
        teamTable.getColumns().add(colorCol);
        teamTable.getColumns().add(textColorCol);
        teamTable.getColumns().add(linkCol);
        gridAndAdd.getChildren().add(teamTable);

        addBox = new VBox();
        String addLabelText = props.getProperty(SiteGenProp.ADD_EDIT.toString());
        addLabel = new Label(addLabelText);
        addBox.getChildren().add(addLabel);

        fields = new HBox(35);

        labels = new VBox(11);
        name = new Label(nameText);
        color = new Label(colorText);
        textColor = new Label(textColorText);
        link = new Label(linkText);
        addButton = new Button(props.getProperty(SiteGenProp.ADDUPDATE.toString()));
        labels.getChildren().add(name);
        labels.getChildren().add(color);
        labels.getChildren().add(textColor);
        labels.getChildren().add(link);

        textFields = new VBox();
        nameF = new TextField();
        colorF = new ColorPicker();
        textColorF = new ColorPicker();
        linkF = new TextField();
        clearButton = new Button(props.getProperty(SiteGenProp.CLEAR_BUTTON_TEXT.toString()));
        textFields.getChildren().add(nameF);
        textFields.getChildren().add(colorF);
        textFields.getChildren().add(textColorF);
        textFields.getChildren().add(linkF);

        fields.getChildren().add(labels);
        fields.getChildren().add(textFields);

        addBox.getChildren().add(fields);

        addClearButtons = new HBox(22);
        addClearButtons.getChildren().add(addButton);
        addClearButtons.getChildren().add(clearButton);
        addBox.getChildren().add(addClearButtons);

        gridAndAdd.getChildren().add(addBox);

        //------------------------------------------------
        headers2 = new HBox(20);
        studentHeader = new Label(props.getProperty(SiteGenProp.STUDENT_HEADER.toString()));
        removeButton2 = new Button("-");
        headers2.getChildren().add(studentHeader);
        headers2.getChildren().add(removeButton2);

        gridAndAdd2 = new VBox();
        studentTable = new TableView();
        studentTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        ObservableList<Student> studentData = data.getStudents();
        studentTable.setItems(studentData);
        String firstNameText = props.getProperty(SiteGenProp.FIRST_NAME.toString());
        String lastNameText = props.getProperty(SiteGenProp.LAST_NAME.toString());
        String teamText = props.getProperty(SiteGenProp.TEAM.toString());
        String roleText = props.getProperty(SiteGenProp.ROLE.toString());
        firstNameCol = new TableColumn(firstNameText);
        lastNameCol = new TableColumn(lastNameText);
        teamCol = new TableColumn(teamText);
        roleCol = new TableColumn(roleText);
        firstNameCol.setCellValueFactory(
                new PropertyValueFactory<Student, String>("firstName")
        );
        lastNameCol.setCellValueFactory(
                new PropertyValueFactory<Student, String>("lastName")
        );
        teamCol.setCellValueFactory(
                new PropertyValueFactory<Student, String>("team")
        );
        roleCol.setCellValueFactory(
                new PropertyValueFactory<Student, String>("role")
        );
        firstNameCol.prefWidthProperty().bind(studentTable.widthProperty().multiply(0.25));
        lastNameCol.prefWidthProperty().bind(studentTable.widthProperty().multiply(0.25));
        teamCol.prefWidthProperty().bind(studentTable.widthProperty().multiply(0.25));
        roleCol.prefWidthProperty().bind(studentTable.widthProperty().multiply(0.25));

        studentTable.getColumns().add(firstNameCol);
        studentTable.getColumns().add(lastNameCol);
        studentTable.getColumns().add(teamCol);
        studentTable.getColumns().add(roleCol);
        gridAndAdd2.getChildren().add(studentTable);

        addBox2 = new VBox();
        addLabel2 = new Label(addLabelText);
        addBox2.getChildren().add(addLabel2);

        fields2 = new HBox(35);

        labels2 = new VBox(11);
        firstName = new Label(firstNameText);
        lastName = new Label(lastNameText);
        team = new Label(teamText);
        role = new Label(roleText);
        addButton2 = new Button(props.getProperty(SiteGenProp.ADDUPDATE.toString()));
        labels2.getChildren().add(firstName);
        labels2.getChildren().add(lastName);
        labels2.getChildren().add(team);
        labels2.getChildren().add(role);

        textFields2 = new VBox();
        firstNameF = new TextField();
        lastNameF = new TextField();
        teamF = new ComboBox();
        teamF.setItems(data.getTeams());
        roleF = new TextField();
        clearButton2 = new Button(props.getProperty(SiteGenProp.CLEAR_BUTTON_TEXT.toString()));
        textFields2.getChildren().add(firstNameF);
        textFields2.getChildren().add(lastNameF);
        textFields2.getChildren().add(teamF);
        textFields2.getChildren().add(roleF);

        fields2.getChildren().add(labels2);
        fields2.getChildren().add(textFields2);

        addBox2.getChildren().add(fields2);

        addClearButtons2 = new HBox(22);
        addClearButtons2.getChildren().add(addButton2);
        addClearButtons2.getChildren().add(clearButton2);
        addBox2.getChildren().add(addClearButtons2);

        gridAndAdd2.getChildren().add(addBox2);

        content1 = new VBox();
        content1.getChildren().add(headers);
        content1.getChildren().add(gridAndAdd);
        content2 = new VBox();
        content2.getChildren().add(headers2);
        content2.getChildren().add(gridAndAdd2);

        content = new VBox();
        content.getChildren().add(content1);
        content.getChildren().add(content2);
        tab.setContent(content);

        addButton.setOnAction(e -> {
            controller.handleAddTeam();
        });
        addButton2.setOnAction(e -> {
            controller.handleAddStudent();
        });

        teamTable.setOnMouseClicked(e -> {
            Object selectedItem = teamTable.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                Team t = (Team) selectedItem;
                nameF.setText(t.getName());
                colorF.setValue(t.getColorInstance());
                textColorF.setValue(t.getTextColorInstance());
                linkF.setText(t.getLink());
                addButton.setOnAction(e2 -> {
                    controller.handleEditTeam(t);
                });
            }
        });

        studentTable.setOnMouseClicked(e -> {
            Object selectedItem = studentTable.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                Student s = (Student) selectedItem;
                firstNameF.setText(s.getFirstName());
                lastNameF.setText(s.getLastName());
                teamF.setValue(s.getTeam());
                roleF.setText(s.getRole());
                addButton2.setOnAction(e2 -> {
                    controller.handleEditStudent(s);
                });
            }
        });
        
        clearButton.setOnAction(e -> {
            
            nameF.setText("");
            colorF.setValue(Color.WHITE);
            textColorF.setValue(Color.WHITE);
            linkF.setText("");
            
            teamTable.getSelectionModel().clearSelection();
            nameF.requestFocus();
            addButton.setOnAction(e2 -> {
                controller.handleAddTeam();
            });
        });
        
        clearButton2.setOnAction(e -> {
            
            firstNameF.setText("");
            lastNameF.setText("");
            teamF.setValue("");
            roleF.setText("");
            
            studentTable.getSelectionModel().clearSelection();
            firstNameF.requestFocus();
            addButton.setOnAction(e2 -> {
                controller.handleAddStudent();
            });
        });
        
        teamTable.setFocusTraversable(true);
        teamTable.setOnKeyPressed(e -> {
            controller.handleRemoveTeam(e.getCode());
        }); 
        removeButton.setOnAction(e -> {
           controller.handleRemoveTeam(KeyCode.DELETE);
        });
        
        studentTable.setFocusTraversable(true);
        studentTable.setOnKeyPressed(e -> {
            controller.handleRemoveStudent(e.getCode());
        });
        
        removeButton2.setOnAction(e -> {
           controller.handleRemoveStudent(KeyCode.DELETE);
        });
    }
    
    public void resetWorkspace() {
        
    }
    
    public void reloadWorkspace(AppDataComponent dataComponent) {
        SiteGenData data = (SiteGenData) dataComponent;
        data.getTeams().clear();
        data.getStudents().clear();
        teamTable.refresh();
        studentTable.refresh();
    }

    public HBox getHeaders() {
        return headers;
    }

    public HBox getHeaders2() {
        return headers2;
    }

    public Label getTeamHeader() {
        return teamHeader;
    }

    public Label getStudentHeader() {
        return studentHeader;
    }

    public VBox getAddBox() {
        return addBox;
    }

    public VBox getAddBox2() {
        return addBox2;
    }

    public Label getAddLabel() {
        return addLabel;
    }

    public Label getAddLabel2() {
        return addLabel2;
    }

    public VBox getContent() {
        return content;
    }

    public VBox getContent1() {
        return content1;
    }

    public TableView getTeamTable() {
        return teamTable;
    }

    public TableView getStudentTable() {
        return studentTable;
    }

    public TextField getNameF() {
        return nameF;
    }

    public ColorPicker getColorF() {
        return colorF;
    }

    public ColorPicker getTextColorF() {
        return textColorF;
    }

    public TextField getLinkF() {
        return linkF;
    }

    public TextField getFirstNameF() {
        return firstNameF;
    }

    public TextField getLastNameF() {
        return lastNameF;
    }

    public ComboBox getTeamF() {
        return teamF;
    }

    public TextField getRoleF() {
        return roleF;
    }

}
