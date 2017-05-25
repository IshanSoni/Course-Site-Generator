/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.workspace;

import csg.SiteGenApp;
import csg.SiteGenProp;
import csg.data.SiteGenData;
import csg.data.TeachingAssistant;
import csg.style.SiteGenStyle;
import djf.components.AppDataComponent;
import djf.components.AppWorkspaceComponent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import properties_manager.PropertiesManager;

/**
 *
 * @author isoni
 */
public class TADataWorkspace {
    SiteGenApp app;
    SiteGenController controller;
    Tab tab;
    
    // FOR THE HEADER ON THE LEFT
    HBox headers;
    HBox tasHeaderBox;
    Label tasHeaderLabel;
    Button removeButton;
    HBox officeHoursHeaderBox;
    Label officeHoursHeaderLabel;
    HBox timeSelectHeaderBox;
    ComboBox gridTimesStart;
    ComboBox gridTimesEnd;
    Label gridTimesStartLabel;
    Label gridTimesEndLabel;
    Button gridTimeChangeButton;
    ArrayList<String> times;
    
    HBox tableAndGridBox;
    // FOR THE TA TABLE
    TableView<TeachingAssistant> taTable;
    TableColumn<TeachingAssistant, String> underGradColumn;
    TableColumn<TeachingAssistant, String> nameColumn;
    TableColumn<TeachingAssistant, String> emailColumn;
    // THE OFFICE HOURS GRID
    GridPane officeHoursGridPane;
    HashMap<String, Pane> officeHoursGridTimeHeaderPanes;
    HashMap<String, Label> officeHoursGridTimeHeaderLabels;
    HashMap<String, Pane> officeHoursGridDayHeaderPanes;
    HashMap<String, Label> officeHoursGridDayHeaderLabels;
    HashMap<String, Pane> officeHoursGridTimeCellPanes;
    HashMap<String, Label> officeHoursGridTimeCellLabels;
    HashMap<String, Pane> officeHoursGridTACellPanes;
    HashMap<String, Label> officeHoursGridTACellLabels;

    // THE TA INPUT
    HBox addBox;
    TextField nameTextField;
    TextField emailTextField;
    Button addButton;
    Button clearButton;
    
    VBox content;
    
    public TADataWorkspace(SiteGenApp initApp, Tab initTab, Pane workspace, SiteGenController initController) {
        app = initApp;
        controller = initController;
        tab = initTab;
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        headers = new HBox(150);
        
        tasHeaderBox = new HBox();
        String tasHeaderText = props.getProperty(SiteGenProp.TAS_HEADER_TEXT.toString());
        tasHeaderLabel = new Label(tasHeaderText);
        removeButton = new Button("-");
        tasHeaderBox.getChildren().add(tasHeaderLabel);
        tasHeaderBox.getChildren().add(removeButton);
        
        officeHoursHeaderBox = new HBox(50);
        String officeHoursGridText = props.getProperty(SiteGenProp.OFFICE_HOURS_SUBHEADER.toString());
        officeHoursHeaderLabel = new Label(officeHoursGridText);
        timeSelectHeaderBox = new HBox();
        gridTimesStart = new ComboBox();
        gridTimesStartLabel = new Label(props.getProperty(SiteGenProp.GRID_TIME_START.toString()));
        gridTimesEnd = new ComboBox();
        gridTimesEndLabel = new Label(props.getProperty(SiteGenProp.GRID_TIME_END.toString()));
        times = new ArrayList();
        times.addAll(Arrays.asList("12:00am", "1:00am", "2:00am", "3:00am", "4:00am",
                "5:00am", "6:00am", "7:00am", "8:00am", "9:00am", "10:00am", "11:00am", "12:00pm",
                "1:00pm", "2:00pm", "3:00pm", "4:00pm", "5:00pm", "6:00pm", "7:00pm", "8:00pm", "9:00pm"
                , "10:00pm", "11:00pm"));
        gridTimesStart.getItems().addAll(times);
        gridTimesEnd.getItems().addAll(times);
        gridTimeChangeButton = new Button(props.getProperty(SiteGenProp.GRID_TIME_BUTTON.toString()));
        officeHoursHeaderBox.getChildren().add(officeHoursHeaderLabel);
        timeSelectHeaderBox.getChildren().add(gridTimesStartLabel);
        timeSelectHeaderBox.getChildren().add(gridTimesStart);
        timeSelectHeaderBox.getChildren().add(gridTimesEndLabel);
        timeSelectHeaderBox.getChildren().add(gridTimesEnd);
        timeSelectHeaderBox.getChildren().add(gridTimeChangeButton);
        officeHoursHeaderBox.getChildren().add(timeSelectHeaderBox);
        
        headers.getChildren().add(tasHeaderBox);
        headers.getChildren().add(officeHoursHeaderBox);

        //TABLE AND GRID
        
        tableAndGridBox = new HBox();
        // MAKE THE TABLE AND SETUP THE DATA MODEL
        taTable = new TableView();
        taTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        SiteGenData data = (SiteGenData) app.getDataComponent();
        ObservableList<TeachingAssistant> tableData = data.getTeachingAssistants();
        taTable.setItems(tableData);
        String underGradColumnText = props.getProperty(SiteGenProp.UNDERGRAD_COLUMN_TEXT.toString());
        String nameColumnText = props.getProperty(SiteGenProp.NAME_COLUMN_TEXT.toString());
        String emailColumnText = props.getProperty(SiteGenProp.EMAIL_COLUMN_TEXT.toString());
        underGradColumn = new TableColumn(underGradColumnText);
        nameColumn = new TableColumn(nameColumnText);
        emailColumn = new TableColumn(emailColumnText);
        underGradColumn.setCellValueFactory(
                new PropertyValueFactory<TeachingAssistant, String>("undergrad")
        );
        nameColumn.setCellValueFactory(
                new PropertyValueFactory<TeachingAssistant, String>("name")
        );
        emailColumn.setCellValueFactory(
                new PropertyValueFactory<TeachingAssistant, String>("email")
        );
        underGradColumn.setStyle("-fx-alignment: CENTER;");
        taTable.getSelectionModel().setCellSelectionEnabled(true);
        taTable.getColumns().add(underGradColumn);       
        taTable.getColumns().add(nameColumn);
        taTable.getColumns().add(emailColumn);

        ScrollPane scrollTable = new ScrollPane();
        scrollTable.setPrefSize(395, 600);
        taTable.prefWidthProperty().bind(scrollTable.widthProperty().multiply(0.95));
        taTable.prefHeightProperty().bind(scrollTable.heightProperty());
        underGradColumn.prefWidthProperty().bind(taTable.widthProperty().multiply(0.24));
        nameColumn.prefWidthProperty().bind(taTable.widthProperty().multiply(0.3));
        emailColumn.prefWidthProperty().bind(taTable.widthProperty().multiply(0.45));
        nameColumn.setResizable(false);
        emailColumn.setResizable(false);
        scrollTable.setContent(taTable);
        
        // THESE WILL STORE PANES AND LABELS FOR OUR OFFICE HOURS GRID
        officeHoursGridPane = new GridPane();
        officeHoursGridTimeHeaderPanes = new HashMap();
        officeHoursGridTimeHeaderLabels = new HashMap();
        officeHoursGridDayHeaderPanes = new HashMap();
        officeHoursGridDayHeaderLabels = new HashMap();
        officeHoursGridTimeCellPanes = new HashMap();
        officeHoursGridTimeCellLabels = new HashMap();
        officeHoursGridTACellPanes = new HashMap();
        officeHoursGridTACellLabels = new HashMap();
        ScrollPane scrollGrid = new ScrollPane();
        scrollGrid.setPrefSize(600, 600);
        scrollGrid.setContent(officeHoursGridPane);
        
        tableAndGridBox.getChildren().add(scrollTable);
        tableAndGridBox.getChildren().add(scrollGrid);

        // ADD BOX FOR ADDING A TA
        String namePromptText = props.getProperty(SiteGenProp.NAME_PROMPT_TEXT.toString());
        String emailPromptText = props.getProperty(SiteGenProp.EMAIL_PROMPT_TEXT.toString());
        String addButtonText = props.getProperty(SiteGenProp.ADD_BUTTON_TEXT.toString());
        String clearButtonText = props.getProperty(SiteGenProp.CLEAR_BUTTON_TEXT.toString());
        nameTextField = new TextField();
        emailTextField = new TextField();
        nameTextField.setPromptText(namePromptText);
        emailTextField.setPromptText(emailPromptText);
        addButton = new Button(addButtonText);
        addBox = new HBox();
        clearButton = new Button(clearButtonText);
        clearButton.setDisable(true);
        nameTextField.prefWidthProperty().bind(addBox.widthProperty().multiply(.11));
        emailTextField.prefWidthProperty().bind(addBox.widthProperty().multiply(.14));
        addButton.prefWidthProperty().bind(addBox.widthProperty().multiply(.08));
        clearButton.prefWidthProperty().bind(addBox.widthProperty().multiply(.06));
        addBox.getChildren().add(nameTextField);
        addBox.getChildren().add(emailTextField);
        addBox.getChildren().add(addButton);
        addBox.getChildren().add(clearButton);

        // ORGANIZE THE LEFT AND RIGHT PANES
        content = new VBox();
        content.getChildren().add(headers);        
        content.getChildren().add(tableAndGridBox);        
        content.getChildren().add(addBox);
        
        // AND PUT EVERYTHING IN THE WORKSPACE
        tab.setContent(content);

        // MAKE SURE THE TABLE EXTENDS DOWN FAR ENOUGH
        taTable.prefHeightProperty().bind(workspace.heightProperty().multiply(1.9));

        // CONTROLS FOR ADDING TAs
        nameTextField.setOnAction(e -> {
            controller.handleAddTA();
        });
        emailTextField.setOnAction(e -> {
            controller.handleAddTA();
        });
        addButton.setOnAction(e -> {
            controller.handleAddTA();
        });
        
        
        
        // Toggles Update Button
        taTable.setOnMouseClicked(e1 -> {
            clearButton.setDisable(false);  
            
            Object selectedItem = taTable.getSelectionModel().getSelectedItem();
            if(selectedItem != null) {
                TeachingAssistant ta = (TeachingAssistant)selectedItem;
                //If undergrad column is clicked
                if(taTable.getSelectionModel().isSelected(taTable.getSelectionModel().getSelectedIndex(), underGradColumn)) {
                    taTable.getSelectionModel().clearSelection();
                    controller.handleUnderGradToggle(ta);
                } else {
                    nameTextField.setText(ta.getName());
                    emailTextField.setText(ta.getEmail());
                    String updateButtonText = props.getProperty(SiteGenProp.UPDATE_BUTTON_TEXT.toString());
                    addButton.setText(updateButtonText);
                    addButton.setOnAction(e2 -> {
                        controller.handleEditTA(ta);
                    });
                }
            }
        });
        
        clearButton.setOnAction(e1 -> {
            nameTextField.setText("");
            emailTextField.setText("");
            taTable.getSelectionModel().clearSelection();
            nameTextField.requestFocus();
            
            addButton.setText(addButtonText);
            addButton.setOnAction(e2 -> {
                controller.handleAddTA();
            });
            
            clearButton.setDisable(true);       
        });

        taTable.setFocusTraversable(true);
        taTable.setOnKeyPressed(e -> {
            controller.handleKeyPress(e.getCode());
        });
        
        removeButton.setOnAction(e -> {
           controller.handleKeyPress(KeyCode.DELETE);
        });
        
        // CHANGE GRID HOURS TIME BUTTON
        gridTimeChangeButton.setOnAction(e -> {
            controller.handleGridTimeChange();
        });
        
    }
    
    // WE'LL PROVIDE AN ACCESSOR METHOD FOR EACH VISIBLE COMPONENT
    // IN CASE A CONTROLLER OR STYLE CLASS NEEDS TO CHANGE IT
    
    public VBox getContent() {
        return content;
    }
    
    public Label getGridTimesStartLabel() {
        return gridTimesStartLabel;
    }
    
    public Label getGridTimesEndLabel() {
        return gridTimesEndLabel;
    }
    
    public ComboBox getGridTimesStartCombo() {
        return gridTimesStart;
    }
    
    public ComboBox getGridTimesEndCombo() {
        return gridTimesEnd;
    }
    
    public HBox getTimeChangeHBox() {
        return timeSelectHeaderBox;
    }
    
    public HBox getHeaders() {
        return headers;
    }
    
    public HBox getTAsHeaderBox() {
        return tasHeaderBox;
    }

    public Label getTAsHeaderLabel() {
        return tasHeaderLabel;
    }

    public TableView getTATable() {
        return taTable;
    }

    public HBox getAddBox() {
        return addBox;
    }

    public TextField getNameTextField() {
        return nameTextField;
    }

    public TextField getEmailTextField() {
        return emailTextField;
    }

    public Button getAddButton() {
        return addButton;
    }
    
    public Button getClearButton() {
        return clearButton;
    }

    public HBox getOfficeHoursSubheaderBox() {
        return officeHoursHeaderBox;
    }

    public Label getOfficeHoursSubheaderLabel() {
        return officeHoursHeaderLabel;
    }

    public GridPane getOfficeHoursGridPane() {
        return officeHoursGridPane;
    }

    public HashMap<String, Pane> getOfficeHoursGridTimeHeaderPanes() {
        return officeHoursGridTimeHeaderPanes;
    }

    public HashMap<String, Label> getOfficeHoursGridTimeHeaderLabels() {
        return officeHoursGridTimeHeaderLabels;
    }

    public HashMap<String, Pane> getOfficeHoursGridDayHeaderPanes() {
        return officeHoursGridDayHeaderPanes;
    }

    public HashMap<String, Label> getOfficeHoursGridDayHeaderLabels() {
        return officeHoursGridDayHeaderLabels;
    }

    public HashMap<String, Pane> getOfficeHoursGridTimeCellPanes() {
        return officeHoursGridTimeCellPanes;
    }

    public HashMap<String, Label> getOfficeHoursGridTimeCellLabels() {
        return officeHoursGridTimeCellLabels;
    }

    public HashMap<String, Pane> getOfficeHoursGridTACellPanes() {
        return officeHoursGridTACellPanes;
    }

    public HashMap<String, Label> getOfficeHoursGridTACellLabels() {
        return officeHoursGridTACellLabels;
    }
    
    public String getCellKey(Pane testPane) {
        for (String key : officeHoursGridTACellLabels.keySet()) {
            if (officeHoursGridTACellPanes.get(key) == testPane) {
                return key;
            }
        }
        return null;
    }

    public Label getTACellLabel(String cellKey) {
        return officeHoursGridTACellLabels.get(cellKey);
    }

    public Pane getTACellPane(String cellPane) {
        return officeHoursGridTACellPanes.get(cellPane);
    }

    public String buildCellKey(int col, int row) {
        return "" + col + "_" + row;
    }

    public String buildCellText(int militaryHour, String minutes) {
        if( militaryHour == 0) {
            return "12:"+minutes+"am";
        }
        // FIRST THE START AND END CELLS
        int hour = militaryHour;
        if (hour > 12) {
            hour -= 12;
        }
        String cellText = "" + hour + ":" + minutes;
        if (militaryHour < 12) {
            cellText += "am";
        } else {
            cellText += "pm";
        }
        return cellText;
    }

    public void resetWorkspace() {
        // CLEAR OUT THE GRID PANE
        officeHoursGridPane.getChildren().clear();
        
        // AND THEN ALL THE GRID PANES AND LABELS
        officeHoursGridTimeHeaderPanes.clear();
        officeHoursGridTimeHeaderLabels.clear();
        officeHoursGridDayHeaderPanes.clear();
        officeHoursGridDayHeaderLabels.clear();
        officeHoursGridTimeCellPanes.clear();
        officeHoursGridTimeCellLabels.clear();
        officeHoursGridTACellPanes.clear();
        officeHoursGridTACellLabels.clear();
    }
    
    public void reloadWorkspace(AppDataComponent dataComponent) {
        SiteGenData taData = (SiteGenData)dataComponent;
        reloadOfficeHoursGrid(taData);
    }

    public void reloadOfficeHoursGrid(SiteGenData dataComponent) {        
        ArrayList<String> gridHeaders = dataComponent.getGridHeaders();

        // ADD THE TIME HEADERS
        for (int i = 0; i < 2; i++) {
            addCellToGrid(dataComponent, officeHoursGridTimeHeaderPanes, officeHoursGridTimeHeaderLabels, i, 0);
            dataComponent.getCellTextProperty(i, 0).set(gridHeaders.get(i));
        }
        
        // THEN THE DAY OF WEEK HEADERS
        for (int i = 2; i < 7; i++) {
            addCellToGrid(dataComponent, officeHoursGridDayHeaderPanes, officeHoursGridDayHeaderLabels, i, 0);
            dataComponent.getCellTextProperty(i, 0).set(gridHeaders.get(i));            
        }
        
        // THEN THE TIME AND TA CELLS
        int row = 1;
        for (int i = dataComponent.getStartHour(); i < dataComponent.getEndHour(); i++) {
            // START TIME COLUMN
            int col = 0;
            addCellToGrid(dataComponent, officeHoursGridTimeCellPanes, officeHoursGridTimeCellLabels, col, row);
            dataComponent.getCellTextProperty(col, row).set(buildCellText(i, "00"));
            addCellToGrid(dataComponent, officeHoursGridTimeCellPanes, officeHoursGridTimeCellLabels, col, row+1);
            dataComponent.getCellTextProperty(col, row+1).set(buildCellText(i, "30"));

            // END TIME COLUMN
            col++;
            int endHour = i;
            addCellToGrid(dataComponent, officeHoursGridTimeCellPanes, officeHoursGridTimeCellLabels, col, row);
            dataComponent.getCellTextProperty(col, row).set(buildCellText(endHour, "30"));
            addCellToGrid(dataComponent, officeHoursGridTimeCellPanes, officeHoursGridTimeCellLabels, col, row+1);
            dataComponent.getCellTextProperty(col, row+1).set(buildCellText(endHour+1, "00"));
            col++;

            // AND NOW ALL THE TA TOGGLE CELLS
            while (col < 7) {
                addCellToGrid(dataComponent, officeHoursGridTACellPanes, officeHoursGridTACellLabels, col, row);
                addCellToGrid(dataComponent, officeHoursGridTACellPanes, officeHoursGridTACellLabels, col, row+1);
                col++;
            }
            row += 2;
        }

        // CONTROLS FOR TOGGLING TA OFFICE HOURS
        for (Pane p : officeHoursGridTACellPanes.values()) {
            p.setFocusTraversable(true);
            p.setOnKeyPressed(e -> {
                controller.handleKeyPress(e.getCode());
            });
            p.setOnMouseClicked(e -> {
                controller.handleCellToggle((Pane) e.getSource());
            });
            p.setOnMouseExited(e -> {
                controller.handleGridCellMouseExited((Pane) e.getSource());
            });
            p.setOnMouseEntered(e -> {
                controller.handleGridCellMouseEntered((Pane) e.getSource());
            });
        }
        
        // AND MAKE SURE ALL THE COMPONENTS HAVE THE PROPER STYLE
        SiteGenStyle taStyle = (SiteGenStyle)app.getStyleComponent();
        taStyle.initOfficeHoursGridStyle();
    }
    
    public void addCellToGrid(SiteGenData dataComponent, HashMap<String, Pane> panes, HashMap<String, Label> labels, int col, int row) {       
        // MAKE THE LABEL IN A PANE
        Label cellLabel = new Label("");
        HBox cellPane = new HBox();
        cellPane.setAlignment(Pos.CENTER);
        cellPane.getChildren().add(cellLabel);

        // BUILD A KEY TO EASILY UNIQUELY IDENTIFY THE CELL
        String cellKey = dataComponent.getCellKey(col, row);
        cellPane.setId(cellKey);
        cellLabel.setId(cellKey);
        
        // NOW PUT THE CELL IN THE WORKSPACE GRID
        officeHoursGridPane.add(cellPane, col, row);
        
        // AND ALSO KEEP IN IN CASE WE NEED TO STYLIZE IT
        panes.put(cellKey, cellPane);
        labels.put(cellKey, cellLabel);
        
        // AND FINALLY, GIVE THE TEXT PROPERTY TO THE DATA MANAGER
        // SO IT CAN MANAGE ALL CHANGES
        dataComponent.setCellProperty(col, row, cellLabel.textProperty());        
    }

}
