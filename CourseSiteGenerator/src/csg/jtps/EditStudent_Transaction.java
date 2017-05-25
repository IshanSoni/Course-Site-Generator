package csg.jtps;

import djf.ui.AppGUI;
import csg.SiteGenApp;
import csg.data.Recitation;
import csg.workspace.SiteGenWorkspace;
import jtps.jTPS_Transaction;
import csg.data.SiteGenData;
import csg.data.Student;
import csg.data.TeachingAssistant;

/**
 *
 * @author isoni
 */
public class EditStudent_Transaction implements jTPS_Transaction {
    
    SiteGenApp app;
    Student s;
    String oldFirstName;
    String oldLastName;
    String oldTeam;
    String oldRole;
    String newFirstName;
    String newLastName;
    String newTeam;
    String newRole;
    
    public EditStudent_Transaction(SiteGenApp initApp, Student s, String firstName, String lastName, String team, String role) {
        app = initApp;
        this.s = s;
        oldFirstName = firstName;
        oldLastName = lastName;
        oldTeam = team;
        oldRole = role;
        newFirstName = s.getFirstName();
        newLastName = s.getLastName();
        newTeam = s.getTeam();
        newRole = s.getRole();
    }
    
    @Override
    public void doTransaction() {
        SiteGenWorkspace workspace = (SiteGenWorkspace) app.getWorkspaceComponent();
        workspace.getTabPane().getSelectionModel().select(4);
        
        SiteGenData data = (SiteGenData) app.getDataComponent();
        data.editStudent(s, newFirstName, newLastName, newTeam, newRole);
        workspace.getProjectTab().getStudentTable().refresh();
        
        markWorkAsEdited();
    }
    
    @Override
    public void undoTransaction() {
        SiteGenWorkspace workspace = (SiteGenWorkspace) app.getWorkspaceComponent();
        workspace.getTabPane().getSelectionModel().select(4);
        
        SiteGenData data = (SiteGenData) app.getDataComponent();
        data.editStudent(s, oldFirstName, oldLastName, oldTeam, oldRole);
        workspace.getProjectTab().getStudentTable().refresh();
        
        markWorkAsEdited();
    }
    
    private void markWorkAsEdited() {
        // MARK WORK AS EDITED
        AppGUI gui = app.getGUI();
        gui.getFileController().markAsEdited(gui);
    }
}

