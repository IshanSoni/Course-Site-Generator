package csg.workspace;

import djf.controller.AppFileController;
import static djf.settings.AppPropertyType.SAVE_UNSAVED_WORK_MESSAGE;
import static djf.settings.AppPropertyType.SAVE_UNSAVED_WORK_TITLE;
import djf.ui.AppGUI;
import static csg.SiteGenProp.*;
import djf.ui.AppMessageDialogSingleton;
import djf.ui.AppYesNoCancelDialogSingleton;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import properties_manager.PropertiesManager;
import csg.SiteGenApp;
import csg.SiteGenProp;
import csg.data.Page;
import csg.data.Recitation;
import csg.data.Schedule;
import csg.data.SiteGenData;
import csg.data.Student;
import csg.data.TeachingAssistant;
import csg.data.Team;
import csg.style.SiteGenStyle;
import static csg.style.SiteGenStyle.CLASS_HIGHLIGHTED_GRID_CELL;
import static csg.style.SiteGenStyle.CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN;
import static csg.style.SiteGenStyle.CLASS_OFFICE_HOURS_GRID_TA_CELL_PANE;
import csg.workspace.SiteGenWorkspace;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.beans.property.StringProperty;
import javax.json.JsonArray;
import javax.json.JsonObject;
import jtps.jTPS;
import jtps.jTPS_Transaction;
import csg.file.TimeSlot;
import csg.jtps.AddToRecitation_Transaction;
import csg.jtps.AddToSchedule_Transaction;
import csg.jtps.AddToStudent_Transaction;
import csg.jtps.AddToTable_Transaction;
import csg.jtps.AddToTeam_Transaction;
import csg.jtps.DeleteFromRecitation_Transaction;
import csg.jtps.DeleteFromSchedule_Transaction;
import csg.jtps.DeleteFromStudent_Transaction;
import csg.jtps.DeleteFromTable_Transaction;
import csg.jtps.DeleteFromTeam_Transaction;
import csg.jtps.EditRecitation_Transaction;
import csg.jtps.EditSchedule_Transaction;
import csg.jtps.EditStudent_Transaction;
import csg.jtps.EditTable_Transaction;
import csg.jtps.EditTeam_Transaction;
import csg.jtps.GridTimeChange_Transaction;
import csg.jtps.GridToggle_Transaction;
import java.io.File;
import java.time.LocalDate;
import java.util.List;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import org.apache.commons.io.FileUtils;

/**
 * This class provides responses to all workspace interactions, meaning
 * interactions with the application controls not including the file
 * toolbar.
 * 
 * @author Richard McKenna
 * @co-author: Ishan Soni
 * @version 1.0
 */
public class SiteGenController {
    // THE APP PROVIDES ACCESS TO OTHER COMPONENTS AS NEEDED
    SiteGenApp app;
    jTPS jTps;
    private Pattern pattern;
    private Matcher matcher;
    private static final String EMAIL_PATTERN =
		"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
		+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    /**
     * Constructor, note that the app must already be constructed.
     */
    public SiteGenController(SiteGenApp initApp) {
        // KEEP THIS FOR LATER
        app = initApp;
        pattern = Pattern.compile(EMAIL_PATTERN);
        jTps = new jTPS();
    }
    
    public jTPS getJtps() {
        return jTps;
    }
    
    public void setJtps(jTPS newJTPS) {
        jTps = newJTPS;
    }
    
    /**
     * This helper method should be called every time an edit happens.
     */    
    private void markWorkAsEdited() {
        // MARK WORK AS EDITED
        AppGUI gui = app.getGUI();
        gui.getFileController().markAsEdited(gui);
    }
    
    /**
     * Validate hex with regular expression
     *
     * @param hex
     *            hex for validation
     * @return true valid hex, false invalid hex
     */
    public boolean validate(final String hex) {
	matcher = pattern.matcher(hex);
	return matcher.matches();
    }
    
    /**
     * This method responds to when the user requests to add
     * a new TA via the UI. Note that it must first do some
     * validation to make sure a unique name and email address
     * has been provided.
     */
    public void handleAddTA() {
        // WE'LL NEED THE WORKSPACE TO RETRIEVE THE USER INPUT VALUES
        SiteGenWorkspace workspace = (SiteGenWorkspace)app.getWorkspaceComponent();
        TextField nameTextField = workspace.getTaTab().getNameTextField();
        TextField emailTextField = workspace.getTaTab().getEmailTextField();
        String name = nameTextField.getText();
        String email = emailTextField.getText();
        
        // WE'LL NEED TO ASK THE DATA SOME QUESTIONS TOO
        SiteGenData data = (SiteGenData)app.getDataComponent();
        
        // WE'LL NEED THIS IN CASE WE NEED TO DISPLAY ANY ERROR MESSAGES
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        // DID THE USER NEGLECT TO PROVIDE A TA NAME?
        if (name.isEmpty()) {
	    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(MISSING_TA_NAME_TITLE), props.getProperty(MISSING_TA_NAME_MESSAGE));            
        }
        // DID THE USER NEGLECT TO PROVIDE A TA EMAIL?
        else if (email.isEmpty()) {
	    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(MISSING_TA_EMAIL_TITLE), props.getProperty(MISSING_TA_EMAIL_MESSAGE));                        
        }
        // DOES A TA ALREADY HAVE THE SAME NAME OR EMAIL?
        else if (data.containsTA(name, email)) {
	    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(TA_NAME_AND_EMAIL_NOT_UNIQUE_TITLE), props.getProperty(TA_NAME_AND_EMAIL_NOT_UNIQUE_MESSAGE));                                    
        }
        // DOES EMAIL HAS CORRECT FORMAT
        else if(!validate(email)) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(EMAIL_FORMAT_WRONG_TITLE), props.getProperty(EMAIL_FORMAT_WRONG_MESSAGE));                                    
        }
        // EVERYTHING IS FINE, ADD A NEW TA
        else {
            // ADD THE NEW TA TO THE DATA
            data.addTA(true, name, email);
            jTPS_Transaction transaction = new AddToTable_Transaction(app, true, name, email);
            jTps.addTransaction(transaction);
            
            // CLEAR THE TEXT FIELDS
            nameTextField.setText("");
            emailTextField.setText("");
            
            // AND SEND THE CARET BACK TO THE NAME TEXT FIELD FOR EASY DATA ENTRY
            nameTextField.requestFocus();
            
            // WE'VE CHANGED STUFF
            markWorkAsEdited();
        }
    }
    
    // EDITS A TA WHEN UPDATE TA BUTTON IS CLICKED
    public void handleEditTA(TeachingAssistant ta) {
        
        SiteGenWorkspace workspace = (SiteGenWorkspace)app.getWorkspaceComponent();
        TextField nameTextField = workspace.getTaTab().getNameTextField();
        TextField emailTextField = workspace.getTaTab().getEmailTextField();
        String name = nameTextField.getText();
        String email = emailTextField.getText();
        
        SiteGenData data = (SiteGenData)app.getDataComponent();
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        if (name.isEmpty()) {
	    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(MISSING_TA_NAME_TITLE), props.getProperty(MISSING_TA_NAME_MESSAGE));            
        }
        else if (email.isEmpty()) {
	    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(MISSING_TA_EMAIL_TITLE), props.getProperty(MISSING_TA_EMAIL_MESSAGE));                        
        }
        else if ((!name.equals(ta.getName()) && !email.equals(ta.getEmail())) && data.containsTA(name, email)) {
	    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(TA_NAME_AND_EMAIL_NOT_UNIQUE_TITLE), props.getProperty(TA_NAME_AND_EMAIL_NOT_UNIQUE_MESSAGE));                                    
        }
        else if(!validate(email)) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(EMAIL_FORMAT_WRONG_TITLE), props.getProperty(EMAIL_FORMAT_WRONG_MESSAGE));                                    
        }
        else {
            String origName = ta.getName();
            String origEmail = ta.getEmail();
            
            data.updateTA(ta, name, email);
            workspace.getTaTab().taTable.refresh();
            
            jTPS_Transaction transaction = new EditTable_Transaction(app, ta, origName, origEmail);
            jTps.addTransaction(transaction);
            
            nameTextField.setText("");
            emailTextField.setText("");
            nameTextField.requestFocus();
           
            markWorkAsEdited();
        }
    }
    
    public void handleAddRecitation() {
        // WE'LL NEED THE WORKSPACE TO RETRIEVE THE USER INPUT VALUES
        SiteGenWorkspace workspace = (SiteGenWorkspace)app.getWorkspaceComponent();
        TextField sectionTextField = workspace.getRecitationTab().getSection();
        TextField instructorTextField = workspace.getRecitationTab().getInstructor();
        TextField dayTimeTextField = workspace.getRecitationTab().getDayTime();
        TextField locationTextField = workspace.getRecitationTab().getLocation();
        ComboBox ta1CB = workspace.getRecitationTab().getTA1();
        ComboBox ta2CB = workspace.getRecitationTab().getTA2();
        
        String section = sectionTextField.getText();
        String instructor = instructorTextField.getText();
        String dayTime = dayTimeTextField.getText();
        String location = locationTextField.getText();
        String ta1 = (String) ta1CB.getValue();
        String ta2 = (String) ta2CB.getValue();
        
        // WE'LL NEED TO ASK THE DATA SOME QUESTIONS TOO
        SiteGenData data = (SiteGenData)app.getDataComponent();
        
        // WE'LL NEED THIS IN CASE WE NEED TO DISPLAY ANY ERROR MESSAGES
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        if (section.isEmpty()) {
	    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(MISSING_SECTION_TITLE), props.getProperty(MISSING_SECTION_MESSAGE));            
        }
        else if (dayTime.isEmpty()) {
	    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(MISSING_DAY_TIME_TITLE), props.getProperty(MISSING_DAY_TIME_MESSAGE));                        
        }
        else if (location.isEmpty()) {
	    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(MISSING_LOCATION_TITLE), props.getProperty(MISSING_LOCATION_MESSAGE));                        
        }
        else if(ta1CB.getValue() != null && !((String)ta1CB.getValue()).equals("") && !data.getTAList().contains(ta1)) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(INVALID_TA_TITLE), props.getProperty(INVALID_TA_MESSAGE));  
        }
        else if(ta2CB.getValue() != null && !((String)ta2CB.getValue()).equals("") && !data.getTAList().contains(ta2)) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(INVALID_TA_TITLE), props.getProperty(INVALID_TA_MESSAGE));  
        }
        else {
            // ADD THE NEW TA TO THE DATA
            Recitation r = data.addRecitation(section,instructor,dayTime,location,ta1,ta2);
            jTPS_Transaction transaction = new AddToRecitation_Transaction(app, r);
            jTps.addTransaction(transaction);
            
            // CLEAR THE TEXT FIELDS
            sectionTextField.setText("");
            instructorTextField.setText("");
            dayTimeTextField.setText("");
            locationTextField.setText("");
            ta1CB.setValue("");
            ta2CB.setValue("");
            
            // AND SEND THE CARET BACK TO THE NAME TEXT FIELD FOR EASY DATA ENTRY
            sectionTextField.requestFocus();
            
            // WE'VE CHANGED STUFF
            markWorkAsEdited();
        }
    }
    
    public void handleEditRecitation(Recitation r) {     
        // WE'LL NEED THE WORKSPACE TO RETRIEVE THE USER INPUT VALUES
        SiteGenWorkspace workspace = (SiteGenWorkspace)app.getWorkspaceComponent();
        TextField sectionTextField = workspace.getRecitationTab().getSection();
        TextField instructorTextField = workspace.getRecitationTab().getInstructor();
        TextField dayTimeTextField = workspace.getRecitationTab().getDayTime();
        TextField locationTextField = workspace.getRecitationTab().getLocation();
        ComboBox ta1CB = workspace.getRecitationTab().getTA1();
        ComboBox ta2CB = workspace.getRecitationTab().getTA2();
        
        String section = sectionTextField.getText();
        String instructor = instructorTextField.getText();
        String dayTime = dayTimeTextField.getText();
        String location = locationTextField.getText();
        String ta1 = (String) ta1CB.getValue();
        String ta2 = (String) ta2CB.getValue();
        
        // WE'LL NEED TO ASK THE DATA SOME QUESTIONS TOO
        SiteGenData data = (SiteGenData)app.getDataComponent();
        
        // WE'LL NEED THIS IN CASE WE NEED TO DISPLAY ANY ERROR MESSAGES
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        if (section.isEmpty()) {
	    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(MISSING_SECTION_TITLE), props.getProperty(MISSING_SECTION_MESSAGE));            
        }
        else if (dayTime.isEmpty()) {
	    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(MISSING_DAY_TIME_TITLE), props.getProperty(MISSING_DAY_TIME_MESSAGE));                        
        }
        else if (location.isEmpty()) {
	    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(MISSING_LOCATION_TITLE), props.getProperty(MISSING_LOCATION_MESSAGE));                        
        }
        else if(ta1CB.getValue() != null && !((String)ta1CB.getValue()).equals("") && !data.getTAList().contains(ta1)) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(INVALID_TA_TITLE), props.getProperty(INVALID_TA_MESSAGE)); 
        }
        else if(ta2CB.getValue() != null && !((String)ta2CB.getValue()).equals("") && !data.getTAList().contains(ta2)) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(INVALID_TA_TITLE), props.getProperty(INVALID_TA_MESSAGE)); 
        }
        else {
            String oSection = r.getSection();
            String oInstructor = r.getInstructor();
            String oDayTime = r.getDayTime();
            String oLocation = r.getLocation();
            String oTA1 = r.getTa1();
            String oTA2 = r.getTa2();
            
            data.editRecitation(r, section,instructor,dayTime,location,ta1,ta2);
            
            workspace.getRecitationTab().recitationTable.refresh();           
            jTPS_Transaction transaction = new EditRecitation_Transaction(app, r, oSection, oInstructor, oDayTime, oLocation, oTA1, oTA2);
            jTps.addTransaction(transaction);
            // CLEAR THE TEXT FIELDS
            sectionTextField.setText("");
            instructorTextField.setText("");
            dayTimeTextField.setText("");
            locationTextField.setText("");
            ta1CB.setValue("");
            ta2CB.setValue("");
            
            // AND SEND THE CARET BACK TO THE NAME TEXT FIELD FOR EASY DATA ENTRY
            sectionTextField.requestFocus();
            
            // WE'VE CHANGED STUFF
            markWorkAsEdited();
        }
    }
    
    public void handleAddSchedule() {
        SiteGenWorkspace workspace = (SiteGenWorkspace)app.getWorkspaceComponent();
        ComboBox typeF = workspace.getScheduleTab().typeF;
        DatePicker dateF = workspace.getScheduleTab().dateF;
        TextField timeF = workspace.getScheduleTab().timeF;
        TextField titleF = workspace.getScheduleTab().titleF;
        TextField topicF = workspace.getScheduleTab().topicF;
        TextField linkF = workspace.getScheduleTab().linkF;
        TextField criteriaF = workspace.getScheduleTab().criteriaF;
        String type = (String) typeF.getValue();
        LocalDate date = dateF.getValue();
        String time = timeF.getText();
        String title = titleF.getText();
        String topic = topicF.getText();
        String link = linkF.getText();
        String criteria = criteriaF.getText();
        
        SiteGenData data = (SiteGenData)app.getDataComponent();
        PropertiesManager props = PropertiesManager.getPropertiesManager();        
        
        if(type.isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(MISSING_TYPE_TITLE), props.getProperty(MISSING_TYPE_MESSAGE)); 
        } else if(date == null) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(MISSING_DATE_TITLE), props.getProperty(MISSING_DATE_MESSAGE)); 
        } else if(title.isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(MISSING_TITLE_TITLE), props.getProperty(MISSING_DATE_MESSAGE)); 
        } else {
            Schedule s = data.addSchedule(type, date, time, title, topic, link, criteria);
            jTPS_Transaction transaction = new AddToSchedule_Transaction(app, s);
            jTps.addTransaction(transaction);
            
            typeF.setValue("");
            dateF.setValue(null);
            timeF.setText("");
            titleF.setText("");
            topicF.setText("");
            linkF.setText("");
            criteriaF.setText("");
            
            titleF.requestFocus();
            markWorkAsEdited();
        }
    }
    
    public void handleEditSchedule(Schedule s) {
        SiteGenWorkspace workspace = (SiteGenWorkspace)app.getWorkspaceComponent();
        ComboBox typeF = workspace.getScheduleTab().typeF;
        DatePicker dateF = workspace.getScheduleTab().dateF;
        TextField timeF = workspace.getScheduleTab().timeF;
        TextField titleF = workspace.getScheduleTab().titleF;
        TextField topicF = workspace.getScheduleTab().topicF;
        TextField linkF = workspace.getScheduleTab().linkF;
        TextField criteriaF = workspace.getScheduleTab().criteriaF;
        String type = (String) typeF.getValue();
        LocalDate date = dateF.getValue();
        String time = timeF.getText();
        String title = titleF.getText();
        String topic = topicF.getText();
        String link = linkF.getText();
        String criteria = criteriaF.getText();
        
        SiteGenData data = (SiteGenData)app.getDataComponent();
        PropertiesManager props = PropertiesManager.getPropertiesManager();   
        
        if(type.isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(MISSING_TYPE_TITLE), props.getProperty(MISSING_TYPE_MESSAGE)); 
        } else if(date == null) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(MISSING_DATE_TITLE), props.getProperty(MISSING_DATE_MESSAGE)); 
        } else if(title.isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(MISSING_TITLE_TITLE), props.getProperty(MISSING_DATE_MESSAGE)); 
        } else {
            String oType = s.getType();
            LocalDate oDate = s.getDate();
            String oTime = s.getTime();
            String oTitle = s.getTitle();
            String oTopic = s.getTopic();
            String oLink = s.getLink();
            String oCriteria = s.getCriteria();
            
            data.editSchedule(s, type, date, time, title, topic, link, criteria);
            
            workspace.getScheduleTab().scheduleTable.refresh();     
            
            jTPS_Transaction transaction = new EditSchedule_Transaction(app, s, oType, oDate, oTime, oTitle, oTopic, oLink, oCriteria);
            jTps.addTransaction(transaction);
            
            typeF.setValue("");
            dateF.setValue(null);
            timeF.setText("");
            titleF.setText("");
            topicF.setText("");
            linkF.setText("");
            criteriaF.setText("");
            
            titleF.requestFocus();
            markWorkAsEdited();
        }
    }
    
    public void handleAddTeam() {
        SiteGenWorkspace workspace = (SiteGenWorkspace)app.getWorkspaceComponent();
        TextField nameF = workspace.getProjectTab().getNameF();
        ColorPicker colorF = workspace.getProjectTab().getColorF();
        ColorPicker textColorF = workspace.getProjectTab().getTextColorF();
        TextField linkF = workspace.getProjectTab().getLinkF();
        String name = nameF.getText();
        Color color = colorF.getValue();
        Color textColor = textColorF.getValue();
        String link = linkF.getText();
        
        SiteGenData data = (SiteGenData)app.getDataComponent();
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        if(name.isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(MISSING_NAME_TITLE), props.getProperty(MISSING_NAME_MESSAGE));  
        } else if(color == null) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(MISSING_COLOR_TITLE), props.getProperty(MISSING_COLOR_MESSAGE));  
        } else if(textColor == null) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(MISSING_TEXT_COLOR_TITLE), props.getProperty(MISSING_TEXT_COLOR_MESSAGE));  
        } else if(link.isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(MISSING_LINK_TITLE), props.getProperty(MISSING_LINK_MESSAGE));  
        } else {
            Team t = data.addTeam(name, color, textColor, link);
            jTPS_Transaction transaction = new AddToTeam_Transaction(app, t);
            jTps.addTransaction(transaction);
            
            nameF.setText("");
            colorF.setValue(Color.WHITE);
            textColorF.setValue(Color.WHITE);
            linkF.setText("");
            
            nameF.requestFocus();
            markWorkAsEdited();
        }
        
    }
    
    public void handleEditTeam(Team t) {
        SiteGenWorkspace workspace = (SiteGenWorkspace)app.getWorkspaceComponent();
        TextField nameF = workspace.getProjectTab().getNameF();
        ColorPicker colorF = workspace.getProjectTab().getColorF();
        ColorPicker textColorF = workspace.getProjectTab().getTextColorF();
        TextField linkF = workspace.getProjectTab().getLinkF();
        String name = nameF.getText();
        Color color = colorF.getValue();
        Color textColor = textColorF.getValue();
        String link = linkF.getText();
        
        SiteGenData data = (SiteGenData)app.getDataComponent();
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        if(name.isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(MISSING_NAME_TITLE), props.getProperty(MISSING_NAME_MESSAGE));  
        } else if(color == null) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(MISSING_COLOR_TITLE), props.getProperty(MISSING_COLOR_MESSAGE));  
        } else if(textColor == null) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(MISSING_TEXT_COLOR_TITLE), props.getProperty(MISSING_TEXT_COLOR_MESSAGE));  
        } else if(link.isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(MISSING_LINK_TITLE), props.getProperty(MISSING_LINK_MESSAGE));  
        } else {
            String oName = t.getName();
            Color oColor = t.getColorInstance();
            Color oTextColor = t.getTextColorInstance();
            String oLink = t.getLink();
            
            data.editTeam(t, name, color, textColor, link);
            
            workspace.getProjectTab().teamTable.refresh();     
            
            jTPS_Transaction transaction = new EditTeam_Transaction(app, t, oName, oColor, oTextColor, oLink);
            jTps.addTransaction(transaction);
            
            nameF.setText("");
            colorF.setValue(Color.WHITE);
            textColorF.setValue(Color.WHITE);
            linkF.setText("");
            
            nameF.requestFocus();
            markWorkAsEdited();
        }
        
    }
    
    public void handleAddStudent() {
         SiteGenWorkspace workspace = (SiteGenWorkspace)app.getWorkspaceComponent();
         TextField firstNameF = workspace.getProjectTab().getFirstNameF();
         TextField lastNameF = workspace.getProjectTab().getLastNameF();
         Team teamF = (Team) workspace.getProjectTab().getTeamF().getValue();
         TextField roleF = workspace.getProjectTab().getRoleF();
         String firstName = firstNameF.getText();
         String lastName = lastNameF.getText();
         String team = teamF.getName();
         String role = roleF.getText();
         
         SiteGenData data = (SiteGenData)app.getDataComponent();
         PropertiesManager props = PropertiesManager.getPropertiesManager();
         
         if(firstName.isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(MISSING_FIRST_NAME_TITLE), props.getProperty(MISSING_FIRST_NAME_MESSAGE)); 
         } else if(lastName.isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(MISSING_LAST_NAME_TITLE), props.getProperty(MISSING_LAST_NAME_MESSAGE)); 
         } else if(data.containsStudentName(firstName, lastName)) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(NAME_NOT_UNIQUE_TITLE), props.getProperty(NAME_NOT_UNIQUE_MESSAGE)); 
         } else {
            Student s = data.addStudent(firstName, lastName, team, role);
            jTPS_Transaction transaction = new AddToStudent_Transaction(app, s);
            jTps.addTransaction(transaction);
             
            firstNameF.setText("");
            lastNameF.setText("");
            workspace.getProjectTab().getTeamF().setValue("");
            roleF.setText("");
             
            firstNameF.requestFocus();
            markWorkAsEdited();
         }
    }
    
    public void handleEditStudent(Student s) {
         SiteGenWorkspace workspace = (SiteGenWorkspace)app.getWorkspaceComponent();
         TextField firstNameF = workspace.getProjectTab().getFirstNameF();
         TextField lastNameF = workspace.getProjectTab().getLastNameF();       
         TextField roleF = workspace.getProjectTab().getRoleF();
         String firstName = firstNameF.getText();
         String lastName = lastNameF.getText();
         String team = (String) workspace.getProjectTab().getTeamF().getValue();
         String role = roleF.getText();
         
         SiteGenData data = (SiteGenData)app.getDataComponent();
         PropertiesManager props = PropertiesManager.getPropertiesManager();
         
         if(firstName.isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(MISSING_FIRST_NAME_TITLE), props.getProperty(MISSING_FIRST_NAME_MESSAGE)); 
         } else if(lastName.isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(MISSING_LAST_NAME_TITLE), props.getProperty(MISSING_LAST_NAME_MESSAGE)); 
         } else if(data.containsStudentName(firstName, lastName)) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(NAME_NOT_UNIQUE_TITLE), props.getProperty(NAME_NOT_UNIQUE_MESSAGE)); 
         } else {
            String oFirstName = s.getFirstName();
            String oLastName = s.getLastName();
            String oTeam = s.getTeam();
            String oRole = s.getRole();
             
            data.editStudent(s, firstName, lastName, team, role);
             
            workspace.getProjectTab().studentTable.refresh(); 
            
            jTPS_Transaction transaction = new EditStudent_Transaction(app, s, oFirstName, oLastName, oTeam, oRole);
            jTps.addTransaction(transaction);
            
            firstNameF.setText("");
            lastNameF.setText("");
            workspace.getProjectTab().getTeamF().setValue("");
            roleF.setText("");
             
            firstNameF.requestFocus();
            markWorkAsEdited();
         }
    }
    
    
    public void handleUnderGradToggle(TeachingAssistant ta) {
        ta.toggleUnderGrad();
        SiteGenWorkspace workspace = (SiteGenWorkspace) app.getWorkspaceComponent();     
        workspace.getTaTab().getTATable().refresh();
        
        markWorkAsEdited();
    }

    /**
     * This function provides a response for when the user presses a
     * keyboard key. Note that we're only responding to Delete, to remove
     * a TA.
     * 
     * @param code The keyboard code pressed.
     */
    public void handleKeyPress(KeyCode code) {
        // DID THE USER PRESS THE DELETE KEY?
        if (code == KeyCode.DELETE || code == KeyCode.BACK_SPACE) {
            // GET THE TABLE
            SiteGenWorkspace workspace = (SiteGenWorkspace)app.getWorkspaceComponent();
            TableView taTable = workspace.getTaTab().getTATable();
            
            // IS A TA SELECTED IN THE TABLE?
            Object selectedItem = taTable.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                // GET THE TA AND REMOVE IT
                TeachingAssistant ta = (TeachingAssistant)selectedItem;
                String taName = ta.getName();
                SiteGenData data = (SiteGenData)app.getDataComponent();
                data.removeTA(taName);
                
                // FOR UNDO/REDO TRANSACTION
                String taEmail = ta.getEmail(); 
                boolean undergrad = ta.isUndergrad();
                ArrayList<StringProperty> propList = new ArrayList();
                ArrayList<Integer> indexes = new ArrayList(); 
                
                // AND BE SURE TO REMOVE ALL THE TA'S OFFICE HOURS
                HashMap<String, Label> labels = workspace.getTaTab().getOfficeHoursGridTACellLabels();
                for (Label label : labels.values()) {
                    if (label.getText().equals(taName)
                    || (label.getText().contains(taName + "\n"))
                    || (label.getText().contains("\n" + taName))) {
                        StringProperty stringProp = label.textProperty();
                        propList.add(stringProp);
                        indexes.add(stringProp.getValue().indexOf(taName));
                        data.removeTAFromCell(stringProp, taName);
                    }
                }
                
                jTPS_Transaction transaction = new DeleteFromTable_Transaction(app, undergrad, taName, taEmail, propList, indexes);
                jTps.addTransaction(transaction);
                // WE'VE CHANGED STUFF
                markWorkAsEdited();
            }
        }
    }
    
    public void handleRecitationKeyPress(KeyCode code) {
        if (code == KeyCode.DELETE || code == KeyCode.BACK_SPACE) {
            // GET THE TABLE
            SiteGenWorkspace workspace = (SiteGenWorkspace)app.getWorkspaceComponent();
            TableView recitationTable = workspace.getRecitationTab().getRecitationTable();
            
            // IS A TA SELECTED IN THE TABLE?
            Object selectedItem = recitationTable.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                // GET THE TA AND REMOVE IT
                Recitation r = (Recitation)selectedItem;
                // FOR UNDO/REDO TRANSACTION
                jTPS_Transaction transaction = new DeleteFromRecitation_Transaction(app, r);
                jTps.addTransaction(transaction);
                
                SiteGenData data = (SiteGenData)app.getDataComponent();
                //data.removeRecitation(section);
                data.getRecitations().remove(r);
                
                // WE'VE CHANGED STUFF
                markWorkAsEdited();
            }
        }
    }
    
    public void handleScheduleRemove(KeyCode code) {
        if (code == KeyCode.DELETE || code == KeyCode.BACK_SPACE) {
            // GET THE TABLE
            SiteGenWorkspace workspace = (SiteGenWorkspace)app.getWorkspaceComponent();
            TableView scheduleTable = workspace.getScheduleTab().getScheduleTable();
            
            // IS A TA SELECTED IN THE TABLE?
            Object selectedItem = scheduleTable.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                // GET THE TA AND REMOVE IT
                Schedule s = (Schedule)selectedItem;
                // FOR UNDO/REDO TRANSACTION
                jTPS_Transaction transaction = new DeleteFromSchedule_Transaction(app, s);
                jTps.addTransaction(transaction);
                
                SiteGenData data = (SiteGenData)app.getDataComponent();
                //data.removeRecitation(section);
                data.getSchedules().remove(s);
                
                // WE'VE CHANGED STUFF
                markWorkAsEdited();
            }
        }
    }
    
    public void handleRemoveTeam(KeyCode code) {
        if (code == KeyCode.DELETE || code == KeyCode.BACK_SPACE) {
            // GET THE TABLE
            SiteGenWorkspace workspace = (SiteGenWorkspace)app.getWorkspaceComponent();
            TableView teamTable = workspace.getProjectTab().getTeamTable();
            
            // IS A TA SELECTED IN THE TABLE?
            Object selectedItem = teamTable.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                // GET THE TA AND REMOVE IT
                Team t = (Team)selectedItem;
                // FOR UNDO/REDO TRANSACTION
                jTPS_Transaction transaction = new DeleteFromTeam_Transaction(app, t);
                jTps.addTransaction(transaction);
                
                SiteGenData data = (SiteGenData)app.getDataComponent();
                //data.removeRecitation(section);
                data.getTeams().remove(t);
                
                // WE'VE CHANGED STUFF
                markWorkAsEdited();
            }
        }
    }
    
    public void handleRemoveStudent(KeyCode code) {
        if (code == KeyCode.DELETE || code == KeyCode.BACK_SPACE) {
            // GET THE TABLE
            SiteGenWorkspace workspace = (SiteGenWorkspace)app.getWorkspaceComponent();
            TableView studentTable = workspace.getProjectTab().getStudentTable();
            
            // IS A TA SELECTED IN THE TABLE?
            Object selectedItem = studentTable.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                // GET THE TA AND REMOVE IT
                Student s = (Student)selectedItem;
                // FOR UNDO/REDO TRANSACTION
                jTPS_Transaction transaction = new DeleteFromStudent_Transaction(app, s);
                jTps.addTransaction(transaction);
                
                SiteGenData data = (SiteGenData)app.getDataComponent();
                //data.removeRecitation(section);
                data.getStudents().remove(s);
                
                // WE'VE CHANGED STUFF
                markWorkAsEdited();
            }
        }
    }

    /**
     * This function provides a response for when the user clicks
     * on the office hours grid to add or remove a TA to a time slot.
     * 
     * @param pane The pane that was toggled.
     */
    public void handleCellToggle(Pane pane) {
        // GET THE TABLE
        SiteGenWorkspace workspace = (SiteGenWorkspace)app.getWorkspaceComponent();
        TableView taTable = workspace.getTaTab().getTATable();
        
        // IS A TA SELECTED IN THE TABLE?
        Object selectedItem = taTable.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            // GET THE TA
            TeachingAssistant ta = (TeachingAssistant)selectedItem;
            String taName = ta.getName();
            SiteGenData data = (SiteGenData)app.getDataComponent();
            String cellKey = pane.getId();
            
            // AND TOGGLE THE OFFICE HOURS IN THE CLICKED CELL
            data.toggleTAOfficeHours(cellKey, taName);
            
            jTPS_Transaction transaction = new GridToggle_Transaction(app, cellKey, taName);
            jTps.addTransaction(transaction);
            // WE'VE CHANGED STUFF
            markWorkAsEdited();
        }
    }
    
    void handleGridCellMouseExited(Pane pane) {
        String cellKey = pane.getId();
        SiteGenData data = (SiteGenData)app.getDataComponent();
        int column = Integer.parseInt(cellKey.substring(0, cellKey.indexOf("_")));
        int row = Integer.parseInt(cellKey.substring(cellKey.indexOf("_") + 1));
        SiteGenWorkspace workspace = (SiteGenWorkspace)app.getWorkspaceComponent();

        Pane mousedOverPane = workspace.getTaTab().getTACellPane(data.getCellKey(column, row));
        mousedOverPane.getStyleClass().clear();
        mousedOverPane.getStyleClass().add(CLASS_OFFICE_HOURS_GRID_TA_CELL_PANE);

        // THE MOUSED OVER COLUMN HEADER
        Pane headerPane = workspace.getTaTab().getOfficeHoursGridDayHeaderPanes().get(data.getCellKey(column, 0));
        headerPane.getStyleClass().remove(CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN);

        // THE MOUSED OVER ROW HEADERS
        headerPane = workspace.getTaTab().getOfficeHoursGridTimeCellPanes().get(data.getCellKey(0, row));
        headerPane.getStyleClass().remove(CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN);
        headerPane = workspace.getTaTab().getOfficeHoursGridTimeCellPanes().get(data.getCellKey(1, row));
        headerPane.getStyleClass().remove(CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN);
        
        // AND NOW UPDATE ALL THE CELLS IN THE SAME ROW TO THE LEFT
        for (int i = 2; i < column; i++) {
            cellKey = data.getCellKey(i, row);
            Pane cell = workspace.getTaTab().getTACellPane(cellKey);
            cell.getStyleClass().remove(CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN);
            cell.getStyleClass().add(CLASS_OFFICE_HOURS_GRID_TA_CELL_PANE);
        }

        // AND THE CELLS IN THE SAME COLUMN ABOVE
        for (int i = 1; i < row; i++) {
            cellKey = data.getCellKey(column, i);
            Pane cell = workspace.getTaTab().getTACellPane(cellKey);
            cell.getStyleClass().remove(CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN);
            cell.getStyleClass().add(CLASS_OFFICE_HOURS_GRID_TA_CELL_PANE);
        }
    }

    void handleGridCellMouseEntered(Pane pane) {
        String cellKey = pane.getId();
        SiteGenData data = (SiteGenData)app.getDataComponent();
        int column = Integer.parseInt(cellKey.substring(0, cellKey.indexOf("_")));
        int row = Integer.parseInt(cellKey.substring(cellKey.indexOf("_") + 1));
        SiteGenWorkspace workspace = (SiteGenWorkspace)app.getWorkspaceComponent();
        
        // THE MOUSED OVER PANE
        Pane mousedOverPane = workspace.getTaTab().getTACellPane(data.getCellKey(column, row));
        mousedOverPane.getStyleClass().clear();
        mousedOverPane.getStyleClass().add(CLASS_HIGHLIGHTED_GRID_CELL);
        
        // THE MOUSED OVER COLUMN HEADER
        Pane headerPane = workspace.getTaTab().getOfficeHoursGridDayHeaderPanes().get(data.getCellKey(column, 0));
        headerPane.getStyleClass().add(CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN);
        
        // THE MOUSED OVER ROW HEADERS
        headerPane = workspace.getTaTab().getOfficeHoursGridTimeCellPanes().get(data.getCellKey(0, row));
        headerPane.getStyleClass().add(CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN);
        headerPane = workspace.getTaTab().getOfficeHoursGridTimeCellPanes().get(data.getCellKey(1, row));
        headerPane.getStyleClass().add(CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN);
        
        // AND NOW UPDATE ALL THE CELLS IN THE SAME ROW TO THE LEFT
        for (int i = 2; i < column; i++) {
            cellKey = data.getCellKey(i, row);
            Pane cell = workspace.getTaTab().getTACellPane(cellKey);
            cell.getStyleClass().add(CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN);
        }

        // AND THE CELLS IN THE SAME COLUMN ABOVE
        for (int i = 1; i < row; i++) {
            cellKey = data.getCellKey(column, i);
            Pane cell = workspace.getTaTab().getTACellPane(cellKey);
            cell.getStyleClass().add(CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN);
        }
    }
    
    
    private int convertToMiltHour(int initHour, String time) {
        if(initHour != 12 && time.contains("pm")) 
            return initHour+=12;
        else if(initHour == 12 && time.contains("am"))
            return initHour = 0;
        else return initHour;
    }
    
    
    // HANDLES WHEN GRID TIMES ARE CHANGED
    public void handleGridTimeChange() {
        SiteGenData data = (SiteGenData) app.getDataComponent();
        SiteGenWorkspace workspace = (SiteGenWorkspace) app.getWorkspaceComponent();
        AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        int oldStartTime = data.getStartHour();
        int oldEndTime = data.getEndHour();
        
        String startTime = (String) workspace.getTaTab().getGridTimesStartCombo().getSelectionModel().getSelectedItem();       
        String endTime = (String) workspace.getTaTab().getGridTimesEndCombo().getSelectionModel().getSelectedItem();
        int initStartHour = Integer.parseInt(startTime.substring(0, startTime.indexOf(":")));
        initStartHour = convertToMiltHour(initStartHour, startTime);
        int initEndHour = Integer.parseInt(endTime.substring(0, endTime.indexOf(":")));
        initEndHour = convertToMiltHour(initEndHour, endTime);
        
        if ((initStartHour < initEndHour)) {            
            ArrayList<TimeSlot> times = TimeSlot.buildOfficeHoursList(data);
            
            for(TimeSlot time : times) {
                String currTime = time.getTime();
                int currH = Integer.parseInt(currTime.substring(0, currTime.indexOf("_")));
                int currHour = convertToMiltHour(currH, currTime);
                if(currHour < initStartHour || currHour > initEndHour) {
                    AppYesNoCancelDialogSingleton yesNoDialog = AppYesNoCancelDialogSingleton.getSingleton();
                    yesNoDialog.show(props.getProperty(GRID_TIME_TA_ERROR_TITLE), props.getProperty(GRID_TIME_TA_ERROR));
                    String selection = yesNoDialog.getSelection();
                    if (selection.equals(AppYesNoCancelDialogSingleton.YES)) break;
                    else return;
                }
            }
            
            ArrayList<TimeSlot> oldTimes = new ArrayList(times);
            jTPS_Transaction transaction = new GridTimeChange_Transaction(app, oldTimes, oldStartTime, oldEndTime);
            jTps.addTransaction(transaction);
            
            workspace.resetWorkspace();
            data.initOfficeHours(initStartHour, initEndHour);
          
            for(TimeSlot time : times) {
                data.addOfficeHoursReservation(time.getDay(), time.getTime(), time.getName());
            }   
            
            markWorkAsEdited();
        } else {
            dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(GRID_INVALID_TIME_TITLE), props.getProperty(GRID_INVALID_TIME));  
        }  
                    
    }
    
    public void handleScheduleConstraintChange() {
        SiteGenData data = (SiteGenData) app.getDataComponent();
        SiteGenWorkspace workspace = (SiteGenWorkspace) app.getWorkspaceComponent();
        ScheduleWorkspace scheduleWorkspace = workspace.getScheduleTab();
        scheduleWorkspace.getStartDate().setValue(data.getStartingMonday());
        scheduleWorkspace.getEndDate().setValue(data.getEndingFriday());
    }
    
    public void handleExportDirChange() {
        SiteGenData data = (SiteGenData) app.getDataComponent();
        SiteGenWorkspace workspace = (SiteGenWorkspace) app.getWorkspaceComponent();
        DirectoryChooser save = new DirectoryChooser();
        File toDir = save.showDialog(app.getGUI().getWindow());
        String exportDir = toDir.getPath();
        data.setExportDir(exportDir);
        workspace.getCourseDetTab().getExportDir().setText(exportDir);
    }
    
    public void handleImageChange(String side) {
        SiteGenData data = (SiteGenData) app.getDataComponent();
        SiteGenWorkspace workspace = (SiteGenWorkspace) app.getWorkspaceComponent();
        
        FileChooser save = new FileChooser();
        File imgDir = save.showOpenDialog(app.getGUI().getWindow());
        
        if(imgDir != null) {
            if(side.equals("banner")) {
    
                data.setBannerImgDir(imgDir.toString());
                workspace.getCourseDetTab().getBannerImage().setImage(new Image(imgDir.toURI().toString()));
            }
            else if(side.equals("left")) {
                data.setLeftFooterImgDir(imgDir.toString());
                workspace.getCourseDetTab().getLeftFooterImage().setImage(new Image(imgDir.toURI().toString()));            
            }
            else if(side.equals("right")) {
                data.setRightFooterImgDir(imgDir.toString());
                workspace.getCourseDetTab().getRightFooterImage().setImage(new Image(imgDir.toURI().toString()));
            }
        }
        
//        try {
//            FileUtils.copyFileToDirectory(imgDir, new File("./work/images/"));
//        } catch(Exception e) {
//            
//        }
        markWorkAsEdited();
    }
    
    public List<String> handleStylesheetLoad() {        
        File stylesheetDir = new File("./work/css");
        ArrayList<String> stylesheets = new ArrayList<>();
        
        for(File f : stylesheetDir.listFiles()) {
            if(f.isFile()) {
                String extension = "";
                int i = f.getPath().lastIndexOf('.');
                if (i > 0) extension = f.getPath().substring(i+1);
                
                if(extension.equals("css")) stylesheets.add(f.getName());
            }
        }      
        return stylesheets;            
    }
    
    public void handleTemplateDirChange() {
        SiteGenData data = (SiteGenData) app.getDataComponent();
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        SiteGenWorkspace workspace = (SiteGenWorkspace) app.getWorkspaceComponent();
        DirectoryChooser load = new DirectoryChooser();
        File templateDir = load.showDialog(app.getGUI().getWindow());
        String exportDir = templateDir.getPath();
        
        data.setTemplateDir(exportDir);
        workspace.getCourseDetTab().getTemplateDir().setText(exportDir);
        data.getPages().clear();
        for(File f : templateDir.listFiles()) {
            if(f.isFile()) {
                if(f.getName().equals(props.getProperty(SiteGenProp.INDEX_HTML))) {
                    Page homePage = new Page(true, props.getProperty(SiteGenProp.HOME), props.getProperty(SiteGenProp.INDEX_HTML), props.getProperty(SiteGenProp.HOMEBUILDER_JS));
                    data.getPages().add(homePage);
                } else if(f.getName().equals(props.getProperty(SiteGenProp.SYLLABUS_HTML))) {
                    Page syllabusPage = new Page(true, props.getProperty(SiteGenProp.SYLLABUS), props.getProperty(SiteGenProp.SYLLABUS_HTML), props.getProperty(SiteGenProp.SYLLABUSBUILDER_JS));
                    data.getPages().add(syllabusPage);
                } else if(f.getName().equals(props.getProperty(SiteGenProp.SCHEDULE_HTML))) {
                    Page schedulePage = new Page(true, props.getProperty(SiteGenProp.SCHEDULE_HEADER), props.getProperty(SiteGenProp.SCHEDULE_HTML), props.getProperty(SiteGenProp.SCHEDULEBUILDER_JS));
                    data.getPages().add(schedulePage);
                } else if(f.getName().equals(props.getProperty(SiteGenProp.HWS_HTML))) {
                    Page hwPage = new Page(true, props.getProperty(SiteGenProp.HWS), props.getProperty(SiteGenProp.HWS_HTML), props.getProperty(SiteGenProp.HWSBUILDER_JS));
                    data.getPages().add(hwPage);
                } else if(f.getName().equals(props.getProperty(SiteGenProp.PROJECTS_HTML))) {
                    Page projectPage = new Page(true, props.getProperty(SiteGenProp.PROJECTS), props.getProperty(SiteGenProp.PROJECTS_HTML), props.getProperty(SiteGenProp.PROJECTSBUILDER_JS));    
                    data.getPages().add(projectPage);
                }               
            }    
        }
        workspace.getCourseDetTab().getPageTable().setItems(data.getPages());    
        workspace.getCourseDetTab().getPageTable().refresh();
        
        markWorkAsEdited();
    }
    
    public void handlePageToggle(Page p) {
        p.toggleUse();
        SiteGenWorkspace workspace = (SiteGenWorkspace) app.getWorkspaceComponent();     
        workspace.getCourseDetTab().getPageTable().refresh();
        
        markWorkAsEdited();
    }
    
    public void handleBoundError() {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	dialog.show(props.getProperty(BOUND_ERROR_TITLE), props.getProperty(BOUND_ERROR_MESSAGE));
    }
    
}