
package csg.jtps;

import djf.ui.AppGUI;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Label;
import csg.SiteGenApp;
import csg.data.Recitation;
import csg.workspace.SiteGenWorkspace;
import jtps.jTPS_Transaction;
import csg.data.SiteGenData;
import csg.data.Student;

/**
 *
 * @author isoni
 */
public class DeleteFromStudent_Transaction implements jTPS_Transaction {
    
    SiteGenApp app;
    Student s;
    
    public DeleteFromStudent_Transaction(SiteGenApp initApp, Student s) {
        app = initApp;
        this.s = s;
    }
    
    @Override
    public void doTransaction() {
        SiteGenWorkspace workspace = (SiteGenWorkspace) app.getWorkspaceComponent();
        workspace.getTabPane().getSelectionModel().select(4);
           
        SiteGenData data = (SiteGenData)app.getDataComponent();
        data.getStudents().remove(s);
                        
        markWorkAsEdited();
    }
    
    @Override
    public void undoTransaction() {
        SiteGenWorkspace workspace = (SiteGenWorkspace) app.getWorkspaceComponent();
        workspace.getTabPane().getSelectionModel().select(4);
        
        SiteGenData data = (SiteGenData)app.getDataComponent();
        data.addStudent(s);
        
        markWorkAsEdited();
    }
    
    private void markWorkAsEdited() {
        // MARK WORK AS EDITED
        AppGUI gui = app.getGUI();
        gui.getFileController().markAsEdited(gui);
    }
}
