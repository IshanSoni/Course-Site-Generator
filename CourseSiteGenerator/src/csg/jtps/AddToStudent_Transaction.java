package csg.jtps;

import djf.ui.AppGUI;
import csg.SiteGenApp;
import csg.data.Recitation;
import csg.data.SiteGenData;
import csg.data.Student;
import csg.data.Team;
import csg.workspace.SiteGenWorkspace;
import jtps.jTPS_Transaction;

/**
 *
 * @author Ishan Soni
 */
public class AddToStudent_Transaction implements jTPS_Transaction {
    
    SiteGenApp app;
    private Student s;
    
    public AddToStudent_Transaction(SiteGenApp initApp, Student s) {
        app = initApp;
        this.s = s;
    }
    
    @Override
    public void doTransaction() {
        SiteGenWorkspace workspace = (SiteGenWorkspace) app.getWorkspaceComponent();
        workspace.getTabPane().getSelectionModel().select(4);
        
        SiteGenData data = (SiteGenData) app.getDataComponent();
        data.addStudent(s);
        
        markWorkAsEdited();
    }
    
    @Override
    public void undoTransaction() {
        SiteGenWorkspace workspace = (SiteGenWorkspace) app.getWorkspaceComponent();
        workspace.getTabPane().getSelectionModel().select(4);
        
        SiteGenData data = (SiteGenData) app.getDataComponent();
        data.getStudents().remove(s);
        
        markWorkAsEdited();
    }
    
    private void markWorkAsEdited() {
        // MARK WORK AS EDITED
        AppGUI gui = app.getGUI();
        gui.getFileController().markAsEdited(gui);
    }
    
}
