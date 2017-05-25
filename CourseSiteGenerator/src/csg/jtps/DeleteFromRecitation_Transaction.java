
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

/**
 *
 * @author isoni
 */
public class DeleteFromRecitation_Transaction implements jTPS_Transaction {
    
    SiteGenApp app;
    Recitation r;
    
    public DeleteFromRecitation_Transaction(SiteGenApp initApp, Recitation r) {
        app = initApp;
        this.r = r;
    }
    
    @Override
    public void doTransaction() {
        SiteGenWorkspace workspace = (SiteGenWorkspace) app.getWorkspaceComponent();
        workspace.getTabPane().getSelectionModel().select(2);
           
        SiteGenData data = (SiteGenData)app.getDataComponent();
        data.getRecitations().remove(r);
                        
        markWorkAsEdited();
    }
    
    @Override
    public void undoTransaction() {
        SiteGenWorkspace workspace = (SiteGenWorkspace) app.getWorkspaceComponent();
        workspace.getTabPane().getSelectionModel().select(2);
        
        SiteGenData data = (SiteGenData)app.getDataComponent();
        data.addRecitation(r);
        
        markWorkAsEdited();
    }
    
    private void markWorkAsEdited() {
        // MARK WORK AS EDITED
        AppGUI gui = app.getGUI();
        gui.getFileController().markAsEdited(gui);
    }
}
