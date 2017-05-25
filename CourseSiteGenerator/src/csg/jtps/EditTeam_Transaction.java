package csg.jtps;

import djf.ui.AppGUI;
import csg.SiteGenApp;
import csg.data.Recitation;
import csg.workspace.SiteGenWorkspace;
import jtps.jTPS_Transaction;
import csg.data.SiteGenData;
import csg.data.TeachingAssistant;
import csg.data.Team;
import javafx.scene.paint.Color;

/**
 *
 * @author isoni
 */
public class EditTeam_Transaction implements jTPS_Transaction {
    
    SiteGenApp app;
    Team t;
    String oldName;
    Color oldColor;
    Color oldTextColor;
    String oldLink;
    String newName;
    Color newColor;
    Color newTextColor;
    String newLink;
    
    public EditTeam_Transaction(SiteGenApp initApp, Team t, String name, Color color, Color textColor, String link) {
        app = initApp;
        this.t = t;
        oldName = name;
        oldColor = color;
        oldTextColor = textColor;
        oldLink = link;
        newName = t.getName();
        newColor = t.getColorInstance();
        newTextColor = t.getTextColorInstance();
        newLink = t.getLink();
    }
    
    @Override
    public void doTransaction() {
        SiteGenWorkspace workspace = (SiteGenWorkspace) app.getWorkspaceComponent();
        workspace.getTabPane().getSelectionModel().select(4);
        
        SiteGenData data = (SiteGenData) app.getDataComponent();
        data.editTeam(t, newName, newColor, newTextColor, newLink);
        workspace.getProjectTab().getTeamTable().refresh();
        
        markWorkAsEdited();
    }
    
    @Override
    public void undoTransaction() {
        SiteGenWorkspace workspace = (SiteGenWorkspace) app.getWorkspaceComponent();
        workspace.getTabPane().getSelectionModel().select(4);
        
        SiteGenData data = (SiteGenData) app.getDataComponent();
        data.editTeam(t, oldName, oldColor, oldTextColor, oldLink);
        workspace.getProjectTab().getTeamTable().refresh();
        
        markWorkAsEdited();
    }
    
    private void markWorkAsEdited() {
        // MARK WORK AS EDITED
        AppGUI gui = app.getGUI();
        gui.getFileController().markAsEdited(gui);
    }
}

