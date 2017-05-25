package csg.jtps;

import djf.ui.AppGUI;
import csg.SiteGenApp;
import csg.data.SiteGenData;
import csg.workspace.SiteGenWorkspace;
import jtps.jTPS_Transaction;

/**
 *
 * @author Ishan Soni
 */
public class AddToTable_Transaction implements jTPS_Transaction {
    
    SiteGenApp app;
    private boolean underGrad;
    private String nameToAdd;
    private String emailToAdd;
    
    public AddToTable_Transaction(SiteGenApp initApp, boolean initUndergrad, String initNameToAdd, String initEmailToAdd) {
        app = initApp;
        underGrad = initUndergrad;
        nameToAdd = initNameToAdd;
        emailToAdd = initEmailToAdd;
    }
    
    @Override
    public void doTransaction() {
        SiteGenWorkspace workspace = (SiteGenWorkspace) app.getWorkspaceComponent();
        workspace.getTabPane().getSelectionModel().select(1);
        
        SiteGenData data = (SiteGenData) app.getDataComponent();
        data.addTA(underGrad, nameToAdd, emailToAdd);
        
        markWorkAsEdited();
    }
    
    @Override
    public void undoTransaction() {
        SiteGenWorkspace workspace = (SiteGenWorkspace) app.getWorkspaceComponent();
        workspace.getTabPane().getSelectionModel().select(1);
        
        SiteGenData data = (SiteGenData) app.getDataComponent();
        data.removeTA(nameToAdd);
        
        markWorkAsEdited();
    }
    
    private void markWorkAsEdited() {
        // MARK WORK AS EDITED
        AppGUI gui = app.getGUI();
        gui.getFileController().markAsEdited(gui);
    }
    
}
