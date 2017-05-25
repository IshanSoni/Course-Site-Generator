package csg.jtps;

import djf.ui.AppGUI;
import csg.SiteGenApp;
import csg.data.Recitation;
import csg.data.Schedule;
import csg.data.SiteGenData;
import csg.workspace.SiteGenWorkspace;
import jtps.jTPS_Transaction;

/**
 *
 * @author Ishan Soni
 */
public class AddToSchedule_Transaction implements jTPS_Transaction {
    
    SiteGenApp app;
    private Schedule s;
    
    public AddToSchedule_Transaction(SiteGenApp initApp, Schedule s) {
        app = initApp;
        this.s = s;
    }
    
    @Override
    public void doTransaction() {
        SiteGenWorkspace workspace = (SiteGenWorkspace) app.getWorkspaceComponent();
        workspace.getTabPane().getSelectionModel().select(3);
        
        SiteGenData data = (SiteGenData) app.getDataComponent();
        data.addSchedule(s);
        
        markWorkAsEdited();
    }
    
    @Override
    public void undoTransaction() {
        SiteGenWorkspace workspace = (SiteGenWorkspace) app.getWorkspaceComponent();
        workspace.getTabPane().getSelectionModel().select(3);
        
        SiteGenData data = (SiteGenData) app.getDataComponent();
        data.getSchedules().remove(s);
        
        markWorkAsEdited();
    }
    
    private void markWorkAsEdited() {
        // MARK WORK AS EDITED
        AppGUI gui = app.getGUI();
        gui.getFileController().markAsEdited(gui);
    }
    
}
