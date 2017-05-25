package csg.jtps;

import djf.ui.AppGUI;
import csg.SiteGenApp;
import csg.workspace.SiteGenWorkspace;
import jtps.jTPS_Transaction;
import csg.data.SiteGenData;
import csg.data.TeachingAssistant;

/**
 *
 * @author isoni
 */
public class EditTable_Transaction implements jTPS_Transaction {
    
    SiteGenApp app;
    TeachingAssistant ta;
    String oldName;
    String oldEmail;
    String newName;
    String newEmail;
    
    public EditTable_Transaction(SiteGenApp initApp, TeachingAssistant initTa, String initName, String initEmail) {
        app = initApp;
        ta = initTa;
        oldName = initName;
        oldEmail = initEmail;
        newName = ta.getName();
        newEmail = ta.getEmail();
    }
    
    @Override
    public void doTransaction() {
        SiteGenWorkspace workspace = (SiteGenWorkspace) app.getWorkspaceComponent();
        workspace.getTabPane().getSelectionModel().select(1);
        
        SiteGenData data = (SiteGenData) app.getDataComponent();
        data.updateTA(ta, newName, newEmail);
        workspace.getTaTab().getTATable().refresh();
        
        markWorkAsEdited();
    }
    
    @Override
    public void undoTransaction() {
        SiteGenWorkspace workspace = (SiteGenWorkspace) app.getWorkspaceComponent();
        workspace.getTabPane().getSelectionModel().select(1);
        
        SiteGenData data = (SiteGenData) app.getDataComponent();
        data.updateTA(ta, oldName, oldEmail);
        workspace.getTaTab().getTATable().refresh();
        
        markWorkAsEdited();
    }
    
    private void markWorkAsEdited() {
        // MARK WORK AS EDITED
        AppGUI gui = app.getGUI();
        gui.getFileController().markAsEdited(gui);
    }
}

