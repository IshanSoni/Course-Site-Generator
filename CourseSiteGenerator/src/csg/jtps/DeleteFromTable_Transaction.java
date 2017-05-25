
package csg.jtps;

import djf.ui.AppGUI;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Label;
import csg.SiteGenApp;
import csg.workspace.SiteGenWorkspace;
import jtps.jTPS_Transaction;
import csg.data.SiteGenData;

/**
 *
 * @author isoni
 */
public class DeleteFromTable_Transaction implements jTPS_Transaction {
    
    SiteGenApp app;
    boolean undergrad;
    String taName;
    String taEmail;
    ArrayList<StringProperty> propList;
    ArrayList<Integer> indexes;
    
    public DeleteFromTable_Transaction(SiteGenApp initApp, boolean initUndergrad, String initTaName, String initTaEmail, ArrayList<StringProperty> initPropList, ArrayList<Integer> initIndexes) {
        app = initApp;
        undergrad = initUndergrad;
        taName = initTaName;
        taEmail = initTaEmail;
        propList = initPropList;
        indexes = initIndexes;
    }
    
    @Override
    public void doTransaction() {
        SiteGenWorkspace workspace = (SiteGenWorkspace) app.getWorkspaceComponent();
        workspace.getTabPane().getSelectionModel().select(1);
                   
        SiteGenData data = (SiteGenData)app.getDataComponent();
        data.removeTA(taName);
                
        HashMap<String, Label> labels = workspace.getTaTab().getOfficeHoursGridTACellLabels();
        for (Label label : labels.values()) {
            if (label.getText().equals(taName)
            || (label.getText().contains(taName + "\n"))
            || (label.getText().contains("\n" + taName))) {             
                data.removeTAFromCell(label.textProperty(), taName);
            }
        }
                
        markWorkAsEdited();
    }
    
    @Override
    public void undoTransaction() {
        SiteGenWorkspace workspace = (SiteGenWorkspace) app.getWorkspaceComponent();
        workspace.getTabPane().getSelectionModel().select(1);
        
        SiteGenData data = (SiteGenData) app.getDataComponent();
        data.addTA(undergrad, taName, taEmail);
        
        for(int i = 0; i < propList.size(); i++) {
            data.addTAToCell(propList.get(i), taName, indexes.get(i));
        }
        
        markWorkAsEdited();
    }
    
    private void markWorkAsEdited() {
        // MARK WORK AS EDITED
        AppGUI gui = app.getGUI();
        gui.getFileController().markAsEdited(gui);
    }
}
