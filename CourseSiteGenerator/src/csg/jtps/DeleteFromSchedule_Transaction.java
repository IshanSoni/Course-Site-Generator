
package csg.jtps;

import djf.ui.AppGUI;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Label;
import csg.SiteGenApp;
import csg.data.Recitation;
import csg.data.Schedule;
import csg.workspace.SiteGenWorkspace;
import jtps.jTPS_Transaction;
import csg.data.SiteGenData;

/**
 *
 * @author isoni
 */
public class DeleteFromSchedule_Transaction implements jTPS_Transaction {
    
    SiteGenApp app;
    Schedule s;
    
    public DeleteFromSchedule_Transaction(SiteGenApp initApp, Schedule s) {
        app = initApp;
        this.s = s;
    }
    
    @Override
    public void doTransaction() {
        SiteGenWorkspace workspace = (SiteGenWorkspace) app.getWorkspaceComponent();
        workspace.getTabPane().getSelectionModel().select(3);
           
        SiteGenData data = (SiteGenData)app.getDataComponent();
        data.getSchedules().remove(s);
                        
        markWorkAsEdited();
    }
    
    @Override
    public void undoTransaction() {
        SiteGenWorkspace workspace = (SiteGenWorkspace) app.getWorkspaceComponent();
        workspace.getTabPane().getSelectionModel().select(3);
        
        SiteGenData data = (SiteGenData)app.getDataComponent();
        data.addSchedule(s);
        
        markWorkAsEdited();
    }
    
    private void markWorkAsEdited() {
        // MARK WORK AS EDITED
        AppGUI gui = app.getGUI();
        gui.getFileController().markAsEdited(gui);
    }
}
