package csg.jtps;

import djf.ui.AppGUI;
import csg.SiteGenApp;
import csg.data.Recitation;
import csg.data.Schedule;
import csg.workspace.SiteGenWorkspace;
import jtps.jTPS_Transaction;
import csg.data.SiteGenData;
import csg.data.TeachingAssistant;
import java.time.LocalDate;

/**
 *
 * @author isoni
 */
public class EditSchedule_Transaction implements jTPS_Transaction {
    
    SiteGenApp app;
    Schedule s;
    String oldType;
    LocalDate oldDate;
    String oldTime;
    String oldTitle;
    String oldTopic;
    String oldLink;
    String oldCriteria;
    String newType;
    LocalDate newDate;
    String newTime;
    String newTitle;
    String newTopic;
    String newLink;
    String newCriteria;
    
    public EditSchedule_Transaction(SiteGenApp initApp, Schedule s, String type, LocalDate date, String time, String title, String topic, String link, String criteria) {
        app = initApp;
        this.s = s;
        oldType = type;
        oldDate = date;
        oldTime = time;
        oldTitle = title;
        oldTopic = topic;
        oldLink = link;
        oldCriteria = criteria;
        newType = s.getType();
        newDate = s.getDate();
        newTime = s.getTime();
        newTitle = s.getTitle();
        newTopic = s.getTopic();
        newLink = s.getLink();
        newCriteria = s.getCriteria();
    }
    
    @Override
    public void doTransaction() {
        SiteGenWorkspace workspace = (SiteGenWorkspace) app.getWorkspaceComponent();
        workspace.getTabPane().getSelectionModel().select(3);
        
        SiteGenData data = (SiteGenData) app.getDataComponent();
        data.editSchedule(s, newType, newDate, newTime, newTitle, newTopic, newLink, newCriteria);
        workspace.getScheduleTab().getScheduleTable().refresh();
        
        markWorkAsEdited();
    }
    
    @Override
    public void undoTransaction() {
        SiteGenWorkspace workspace = (SiteGenWorkspace) app.getWorkspaceComponent();
        workspace.getTabPane().getSelectionModel().select(3);
        
        SiteGenData data = (SiteGenData) app.getDataComponent();
        data.editSchedule(s, oldType, oldDate, oldTime, oldTitle, oldTopic, oldLink, oldCriteria);
        workspace.getScheduleTab().getScheduleTable().refresh();
        
        markWorkAsEdited();
    }
    
    private void markWorkAsEdited() {
        // MARK WORK AS EDITED
        AppGUI gui = app.getGUI();
        gui.getFileController().markAsEdited(gui);
    }
}

