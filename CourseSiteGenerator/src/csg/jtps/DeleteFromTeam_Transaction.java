
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
import csg.data.Team;

/**
 *
 * @author isoni
 */
public class DeleteFromTeam_Transaction implements jTPS_Transaction {
    
    SiteGenApp app;
    Team t;
    
    public DeleteFromTeam_Transaction(SiteGenApp initApp, Team t) {
        app = initApp;
        this.t = t;
    }
    
    @Override
    public void doTransaction() {
        SiteGenWorkspace workspace = (SiteGenWorkspace) app.getWorkspaceComponent();
        workspace.getTabPane().getSelectionModel().select(4);
           
        SiteGenData data = (SiteGenData)app.getDataComponent();
        data.getTeams().remove(t);
                        
        markWorkAsEdited();
    }
    
    @Override
    public void undoTransaction() {
        SiteGenWorkspace workspace = (SiteGenWorkspace) app.getWorkspaceComponent();
        workspace.getTabPane().getSelectionModel().select(4);
        
        SiteGenData data = (SiteGenData)app.getDataComponent();
        data.addTeam(t);
        
        markWorkAsEdited();
    }
    
    private void markWorkAsEdited() {
        // MARK WORK AS EDITED
        AppGUI gui = app.getGUI();
        gui.getFileController().markAsEdited(gui);
    }
}
