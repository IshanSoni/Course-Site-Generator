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
public class GridToggle_Transaction implements jTPS_Transaction {
    
    SiteGenApp app;
    String cellName;
    String name;
    
    public GridToggle_Transaction(SiteGenApp initApp, String initCellName, String initName) {
        app = initApp;
        cellName = initCellName;
        name = initName;
    }
    
    @Override
    public void doTransaction() {
        SiteGenData data = (SiteGenData) app.getDataComponent();
        data.toggleTAOfficeHours(cellName, name);
        
        markWorkAsEdited();
    }
    
    @Override
    public void undoTransaction() {
        SiteGenData data = (SiteGenData) app.getDataComponent();
        data.toggleTAOfficeHours(cellName, name);
        
        markWorkAsEdited();
    }
    
    private void markWorkAsEdited() {
        // MARK WORK AS EDITED
        AppGUI gui = app.getGUI();
        gui.getFileController().markAsEdited(gui);
    }
}

