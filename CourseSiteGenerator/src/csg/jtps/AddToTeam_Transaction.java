package csg.jtps;

import djf.ui.AppGUI;
import csg.SiteGenApp;
import csg.data.Recitation;
import csg.data.SiteGenData;
import csg.data.Team;
import csg.workspace.SiteGenWorkspace;
import jtps.jTPS_Transaction;

/**
 *
 * @author Ishan Soni
 */
public class AddToTeam_Transaction implements jTPS_Transaction {
    
    SiteGenApp app;
    private Team t;
    
    public AddToTeam_Transaction(SiteGenApp initApp, Team t) {
        app = initApp;
        this.t = t;
    }
    
    @Override
    public void doTransaction() {
        SiteGenWorkspace workspace = (SiteGenWorkspace) app.getWorkspaceComponent();
        workspace.getTabPane().getSelectionModel().select(4);
        
        SiteGenData data = (SiteGenData) app.getDataComponent();
        data.addTeam(t);
        
        markWorkAsEdited();
    }
    
    @Override
    public void undoTransaction() {
        SiteGenWorkspace workspace = (SiteGenWorkspace) app.getWorkspaceComponent();
        workspace.getTabPane().getSelectionModel().select(4);
        
        SiteGenData data = (SiteGenData) app.getDataComponent();
        data.getTeams().remove(t);
        
        markWorkAsEdited();
    }
    
    private void markWorkAsEdited() {
        // MARK WORK AS EDITED
        AppGUI gui = app.getGUI();
        gui.getFileController().markAsEdited(gui);
    }
    
}
