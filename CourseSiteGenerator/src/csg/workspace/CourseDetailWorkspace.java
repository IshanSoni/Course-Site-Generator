/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.workspace;

import csg.SiteGenApp;
import csg.SiteGenProp;
import csg.data.Page;
import csg.data.Schedule;
import csg.data.SiteGenData;
import csg.data.Team;
import djf.components.AppDataComponent;
import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import properties_manager.PropertiesManager;

/**
 *
 * @author isoni
 */
public class CourseDetailWorkspace {
    SiteGenApp app;
    SiteGenController controller;
    Tab tab;
    
    HBox content;
    VBox leftSide;
    
    VBox courseInfoBox;
    VBox labels;
    HBox fieldsAndLabels;
    Label courseInfoHeader;
    Label subject;
    Label semester;
    Label title;
    Label instructorName;
    Label instructorHome;
    Label exportDir;
    VBox fields;
    HBox comboAndNum;
    TextField subjectF;
    HBox numberLabelField;
    Label number;
    TextField numberF;
    HBox comboAndYear;
    ComboBox semesterF;
    HBox yearLabelField;
    Label year;
    TextField yearF;
    TextField titleF;
    TextField instructorNameF;
    TextField instructorHomeF;
    HBox exportDirBox;
    Label exportF;
    Button exportButton;
    
    VBox pageStyleBox;
    HBox fieldBox;
    Label pageStyleHeader;
    VBox labels2;
    Label bannerSchoolImage;
    Label leftFooter;
    Label rightFooter;
    Label styleSheet;
    VBox fields2;
    HBox bannerSchoolImageBox;
    ImageView bannerSchoolImageF;
    Button bannerSchoolButton;
    HBox leftFooterBox;
    ImageView leftFooterF;
    Button leftFooterButton;
    HBox rightFooterBox;
    ImageView rightFooterF;
    Button rightFooterButton;
    ComboBox styleSheetF;
    Label note;
    
    VBox template;
    VBox smallHeaders;
    Label siteTemplateHeader;
    Label siteTemplateNote;
    Label siteTemplateDir;
    Button siteTemplateDirButton;
    Label sitePages;
    TableView<Page> pageTable;
    TableColumn<Page, String> use;
    TableColumn<Page, String> navTitle;
    TableColumn<Page, String> fileName;
    TableColumn<Page, String> script;
    
    
    
    public CourseDetailWorkspace(SiteGenApp initApp, Tab initTab, Pane workspace, SiteGenController initController) {
        app = initApp;
        controller = initController;
        tab = initTab;
        
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        courseInfoBox = new VBox();
        
        labels = new VBox(11);
        courseInfoHeader = new Label(props.getProperty(SiteGenProp.COURSE_INFO_HEADER));
        subject = new Label(props.getProperty(SiteGenProp.SUBJECT));
        semester = new Label(props.getProperty(SiteGenProp.SEMESTER));
        title = new Label(props.getProperty(SiteGenProp.TITLE)+":");
        instructorName = new Label(props.getProperty(SiteGenProp.INSTRUCTOR_NAME));
        instructorHome = new Label(props.getProperty(SiteGenProp.INSTRUCTOR_HOME));
        exportDir = new Label(props.getProperty(SiteGenProp.EXPORT_DIR));
        labels.getChildren().addAll(subject, semester, title, instructorName, instructorHome, exportDir);
        
        fields = new VBox(1);
        comboAndNum = new HBox();
        subjectF = new TextField();
        numberLabelField = new HBox();
        number = new Label(props.getProperty(SiteGenProp.NUMBER));
        numberF = new TextField();
        numberLabelField.getChildren().addAll(number, numberF);
        comboAndNum.getChildren().addAll(subjectF, numberLabelField);
        comboAndYear = new HBox();
        semesterF = new ComboBox();
        ArrayList<String> sems = new ArrayList<>();
        sems.addAll(Arrays.asList("Summer", "Fall", "Winter", "Spring"));
        semesterF.getItems().addAll(sems);
        semesterF.setValue("Fall");
        yearLabelField = new HBox(15);
        year = new Label(props.getProperty(SiteGenProp.YEAR));
        yearF = new TextField(String.valueOf(LocalDate.now().getYear()));
        yearLabelField.getChildren().addAll(year, yearF);
        comboAndYear.getChildren().addAll(semesterF, yearLabelField);
        titleF = new TextField();
        instructorNameF = new TextField();
        instructorHomeF = new TextField();
        exportDirBox = new HBox(3);
        exportF = new Label(props.getProperty(SiteGenProp.DIRECTORY_UNDEFINED));
        exportButton = new Button(props.getProperty(SiteGenProp.CHANGE));
        exportDirBox.getChildren().addAll(exportF, exportButton);
        fields.getChildren().addAll(comboAndNum, comboAndYear, titleF, instructorNameF, instructorHomeF, exportDirBox);
        
        fieldsAndLabels = new HBox();
        fieldsAndLabels.getChildren().addAll(labels, fields);
        courseInfoBox.getChildren().addAll(courseInfoHeader, fieldsAndLabels);
        
        //--------------------------------------------------
        
        pageStyleBox = new VBox();
        pageStyleHeader = new Label(props.getProperty(SiteGenProp.PAGE_STYLE_HEADER));
        labels2 = new VBox(11);
        bannerSchoolImage = new Label(props.getProperty(SiteGenProp.BANNER_SCHOOL_IMAGE));
        leftFooter = new Label(props.getProperty(SiteGenProp.LEFT_FOOTER));
        rightFooter = new Label(props.getProperty(SiteGenProp.RIGHT_FOOTER));
        styleSheet = new Label(props.getProperty(SiteGenProp.STYLESHEET));
        labels2.getChildren().addAll(bannerSchoolImage, leftFooter, rightFooter, styleSheet);
        fields2 = new VBox();
        bannerSchoolImageBox = new HBox(40);
        bannerSchoolImageF = new ImageView();
        bannerSchoolImageF.setFitHeight(20);
        bannerSchoolImageF.setFitWidth(20);
        bannerSchoolButton = new Button(props.getProperty(SiteGenProp.CHANGE));
        bannerSchoolImageBox.getChildren().addAll(bannerSchoolImageF, bannerSchoolButton);
        leftFooterBox = new HBox(40);
        leftFooterF = new ImageView();
        leftFooterF.setFitHeight(20);
        leftFooterF.setFitWidth(20);
        leftFooterButton = new Button(props.getProperty(SiteGenProp.CHANGE));
        leftFooterBox.getChildren().addAll(leftFooterF, leftFooterButton);
        rightFooterBox = new HBox(40);
        rightFooterF = new ImageView();
        rightFooterF.setFitHeight(20);
        rightFooterF.setFitWidth(20);
        rightFooterButton = new Button(props.getProperty(SiteGenProp.CHANGE));
        rightFooterBox.getChildren().addAll(rightFooterF, rightFooterButton);
        styleSheetF = new ComboBox();
        styleSheetF.getItems().addAll(controller.handleStylesheetLoad());
        fields2.getChildren().addAll(bannerSchoolImageBox, leftFooterBox, rightFooterBox, styleSheetF);
        note = new Label(props.getProperty(SiteGenProp.NOTE));       
        fieldBox = new HBox(45);
        fieldBox.getChildren().addAll(labels2, fields2);
        pageStyleBox.getChildren().addAll(pageStyleHeader, fieldBox, note);
        
        //---------------------------------------------------------------------------------
        template = new VBox();
        siteTemplateHeader = new Label(props.getProperty(SiteGenProp.SITE_TEMPLATE_HEADER));
        siteTemplateNote = new Label(props.getProperty(SiteGenProp.SITE_TEMPLATE_NOTE));
        siteTemplateDir = new Label(props.getProperty(SiteGenProp.DIRECTORY_UNDEFINED));
        siteTemplateDirButton = new Button(props.getProperty(SiteGenProp.SELECT_DIRECTORY));
        sitePages = new Label(props.getProperty(SiteGenProp.SITE_PAGES));
        pageTable = new TableView();
        pageTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        SiteGenData data = (SiteGenData) app.getDataComponent();
        ObservableList<Page> pages = data.getPages();
        pageTable.setItems(pages);
        use = new TableColumn(props.getProperty(SiteGenProp.USE));
        navTitle = new TableColumn(props.getProperty(SiteGenProp.NAV_TITLE));
        fileName = new TableColumn(props.getProperty(SiteGenProp.FILE_NAME));
        script = new TableColumn(props.getProperty(SiteGenProp.SCRIPT));
        use.setCellValueFactory(
                new PropertyValueFactory<Page, String>("use")
        );
        navTitle.setCellValueFactory(
                new PropertyValueFactory<Page, String>("navbarTitle")
        );
        fileName.setCellValueFactory(
                new PropertyValueFactory<Page, String>("fileName")
        );
        script.setCellValueFactory(
                new PropertyValueFactory<Page, String>("script")
        );
        use.prefWidthProperty().bind(pageTable.widthProperty().multiply(0.10));
        navTitle.prefWidthProperty().bind(pageTable.widthProperty().multiply(0.30));
        fileName.prefWidthProperty().bind(pageTable.widthProperty().multiply(0.30));
        script.prefWidthProperty().bind(pageTable.widthProperty().multiply(0.30));
        pageTable.getColumns().addAll(use, navTitle, fileName, script);  
        smallHeaders = new VBox(10);
        smallHeaders.getChildren().addAll(siteTemplateNote, siteTemplateDir, siteTemplateDirButton, sitePages, pageTable);
        template.getChildren().addAll(siteTemplateHeader, smallHeaders);
        
        leftSide = new VBox(50);
        leftSide.getChildren().addAll(courseInfoBox, pageStyleBox);
        content = new HBox();
        content.getChildren().addAll(leftSide, template);
        tab.setContent(content);
        
        exportButton.setOnAction(e -> {
            controller.handleExportDirChange();
        });
        
        bannerSchoolButton.setOnAction(e -> {
            controller.handleImageChange("banner");
        });
        leftFooterButton.setOnAction(e -> {
            controller.handleImageChange("left");
        });
        rightFooterButton.setOnAction(e -> {
            controller.handleImageChange("right");
        });
        
        siteTemplateDirButton.setOnAction(e -> {
            controller.handleTemplateDirChange();
        });
        
        pageTable.setOnMouseClicked(e -> {
            Object selectedItem = pageTable.getSelectionModel().getSelectedItem();
            if(selectedItem != null) {
                Page p = (Page)selectedItem;
                pageTable.getSelectionModel().clearSelection();
                controller.handlePageToggle(p);               
            }
        });
        
    }
    
    public void resetWorkspace() {
        SiteGenData data = (SiteGenData) app.getDataComponent();
        subjectF.setText("");
        numberF.setText("");
        semesterF.setValue("");
        yearF.setText("");
        titleF.setText("");
        instructorHomeF.setText("");
        instructorNameF.setText("");
        exportF.setText("Undefined Directory");
        siteTemplateDir.setText("Undefined Directory");
        bannerSchoolImageF.setImage(null);
        leftFooterF.setImage(null);
        rightFooterF.setImage(null);
        styleSheetF.setValue("");
        data.getPages().clear();
        
        SiteGenWorkspace workspace = (SiteGenWorkspace) app.getWorkspaceComponent();
        workspace.getCourseDetTab().getPageTable().setItems(data.getPages());    
        workspace.getCourseDetTab().getPageTable().refresh();
    }
    
    public void reloadWorkspace(AppDataComponent dataComponent) {
        resetWorkspace();
    }
    
    public void initDetails() {
        SiteGenData data = (SiteGenData) app.getDataComponent();
        subjectF.setText(data.getSubject());
        numberF.setText(String.valueOf(data.getNumber()));
        semesterF.setValue(data.getSemester());
        yearF.setText(String.valueOf(data.getYear()));
        titleF.setText(data.getTitle());
        instructorHomeF.setText(data.getInstructorHome());
        instructorNameF.setText(data.getInstructorName());
        exportF.setText(data.getExportDir());
        siteTemplateDir.setText(data.getTemplateDir());
        bannerSchoolImageF.setImage(new Image(new File(data.getBannerImgDir()).toURI().toString()));
        leftFooterF.setImage(new Image(new File(data.getLeftFooterImgDir()).toURI().toString()));
        rightFooterF.setImage(new Image(new File(data.getRightFooterImgDir()).toURI().toString()));
        styleSheetF.setValue(data.getStyleSheet());
        
        SiteGenWorkspace workspace = (SiteGenWorkspace) app.getWorkspaceComponent();
        workspace.getCourseDetTab().getPageTable().setItems(data.getPages());    
        workspace.getCourseDetTab().getPageTable().refresh();
    }
    
    public void saveDetails() {
        SiteGenData data = (SiteGenData) app.getDataComponent();
        data.setSubject(subjectF.getText());
        data.setNumber(Integer.parseInt(numberF.getText()));
        data.setSemester((String)semesterF.getValue());
        data.setYear(Integer.parseInt(yearF.getText()));
        data.setTitle(titleF.getText());
        data.setInstructorHome(instructorHomeF.getText());
        data.setInstructorName(instructorNameF.getText());
        data.setExportDir(exportF.getText());
        data.setTemplateDir(siteTemplateDir.getText());
        data.setStyleSheet((String)styleSheetF.getValue());
    }
    
    public Label getCourseInfoHeader() {
        return courseInfoHeader;
    }
    
    public Label getPageStyleHeader() {
        return pageStyleHeader;
    }
    
    public Label getSiteTemplateHeader() {
        return siteTemplateHeader;
    }
    
    public HBox getFieldsAndLabels() {
        return fieldsAndLabels;
    }
    
    public HBox getFieldBox() {
        return fieldBox;
    }
    
    public VBox getSmallHeaders() {
        return smallHeaders;
    }
    
    public Label getNote() {
        return note;
    }
    
    public VBox getCourseInfoBox() {
        return courseInfoBox;
    }
    
    public VBox getTemplate() {
        return template;
    }
    
    public TableView getPageTable() {
        return pageTable;
    }
    
    public HBox getContent() {
        return content;
    }
    
    public Label getExportDir() {
        return exportF;
    }
    
    public ImageView getBannerImage() {
        return bannerSchoolImageF;
    }
    
    public ImageView getLeftFooterImage() {
        return leftFooterF;
    }
    
    public ImageView getRightFooterImage() {
        return rightFooterF;
    }
    
    public ComboBox getStyleSheet() {
        return styleSheetF;
    }
    
    public Label getTemplateDir() {
        return siteTemplateDir;
    }
}
