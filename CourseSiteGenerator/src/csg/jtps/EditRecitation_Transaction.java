package csg.jtps;

import djf.ui.AppGUI;
import csg.SiteGenApp;
import csg.data.Recitation;
import csg.workspace.SiteGenWorkspace;
import jtps.jTPS_Transaction;
import csg.data.SiteGenData;
import csg.data.TeachingAssistant;

/**
 *
 * @author isoni
 */
public class EditRecitation_Transaction implements jTPS_Transaction {
    
    SiteGenApp app;
    Recitation r;
    String oldSection;
    String oldInstructor;
    String oldDayTime;
    String oldLocation;
    String oldTA1;
    String oldTA2;
    String newSection;
    String newInstructor;
    String newDayTime;
    String newLocation;
    String newTA1;
    String newTA2;
    
    public EditRecitation_Transaction(SiteGenApp initApp, Recitation r, String section, String instructor, String dayTime, String location, String ta1, String ta2) {
        app = initApp;
        this.r = r;
        oldSection = section;
        oldInstructor = instructor;
        oldDayTime = dayTime;
        oldLocation = location;
        oldTA1 = ta1;
        oldTA2 = ta2;
        newSection = r.getSection();
        newInstructor = r.getInstructor();
        newDayTime = r.getDayTime();
        newLocation = r.getLocation();
        newTA1 = r.getTa1();
        newTA2 = r.getTa2();
    }
    
    @Override
    public void doTransaction() {
        SiteGenWorkspace workspace = (SiteGenWorkspace) app.getWorkspaceComponent();
        workspace.getTabPane().getSelectionModel().select(2);
        
        SiteGenData data = (SiteGenData) app.getDataComponent();
        data.editRecitation(r, newSection, newInstructor, newDayTime, newLocation, newTA1, newTA2);
        workspace.getRecitationTab().getRecitationTable().refresh();
        
        markWorkAsEdited();
    }
    
    @Override
    public void undoTransaction() {
        SiteGenWorkspace workspace = (SiteGenWorkspace) app.getWorkspaceComponent();
        workspace.getTabPane().getSelectionModel().select(2);
        
        SiteGenData data = (SiteGenData) app.getDataComponent();
        data.editRecitation(r, oldSection, oldInstructor, oldDayTime, oldLocation, oldTA1, oldTA2);
        workspace.getRecitationTab().getRecitationTable().refresh();
        
        markWorkAsEdited();
    }
    
    private void markWorkAsEdited() {
        // MARK WORK AS EDITED
        AppGUI gui = app.getGUI();
        gui.getFileController().markAsEdited(gui);
    }
}

