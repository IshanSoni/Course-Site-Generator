package csg.jtps;

import djf.ui.AppGUI;
import csg.SiteGenApp;
import csg.data.Recitation;
import csg.data.SiteGenData;
import csg.workspace.SiteGenWorkspace;
import jtps.jTPS_Transaction;

/**
 *
 * @author Ishan Soni
 */
public class AddToRecitation_Transaction implements jTPS_Transaction {
    
    SiteGenApp app;
    private Recitation r;
    
    public AddToRecitation_Transaction(SiteGenApp initApp, Recitation r) {
        app = initApp;
        this.r = r;
    }
    
    @Override
    public void doTransaction() {
        SiteGenWorkspace workspace = (SiteGenWorkspace) app.getWorkspaceComponent();
        workspace.getTabPane().getSelectionModel().select(2);
        
        SiteGenData data = (SiteGenData) app.getDataComponent();
        data.addRecitation(r);
        
        markWorkAsEdited();
    }
    
    @Override
    public void undoTransaction() {
        SiteGenWorkspace workspace = (SiteGenWorkspace) app.getWorkspaceComponent();
        workspace.getTabPane().getSelectionModel().select(2);
        
        SiteGenData data = (SiteGenData) app.getDataComponent();
        data.getRecitations().remove(r);
        
        markWorkAsEdited();
    }
    
    private void markWorkAsEdited() {
        // MARK WORK AS EDITED
        AppGUI gui = app.getGUI();
        gui.getFileController().markAsEdited(gui);
    }
    
}
