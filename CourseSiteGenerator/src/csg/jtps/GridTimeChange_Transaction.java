package csg.jtps;

import djf.ui.AppGUI;
import java.util.ArrayList;
import csg.SiteGenApp;
import csg.workspace.SiteGenWorkspace;
import jtps.jTPS_Transaction;
import csg.data.SiteGenData;
import csg.data.TeachingAssistant;
import csg.file.TimeSlot;

/**
 *
 * @author isoni
 */
public class GridTimeChange_Transaction implements jTPS_Transaction {
    
    SiteGenApp app;
    private ArrayList<TimeSlot> oldTimes;
    private int oldStartTime;
    private int oldEndTime;
    
    public GridTimeChange_Transaction(SiteGenApp initApp, ArrayList<TimeSlot> initOldTimes, int initStartTime, int initEndTime) {
        app = initApp;
        oldTimes = initOldTimes;
        oldStartTime = initStartTime;
        oldEndTime = initEndTime;
    }
    
    @Override
    public void doTransaction() {
        SiteGenWorkspace workspace = (SiteGenWorkspace) app.getWorkspaceComponent();
        SiteGenData data = (SiteGenData) app.getDataComponent();
        
        int currentStartTime = data.getStartHour();
        int currentEndTime = data.getEndHour();
        ArrayList<TimeSlot> currentTimes = new ArrayList(oldTimes);
        
        workspace.resetWorkspace();
        data.initOfficeHours(oldStartTime, oldEndTime);
          
        for(TimeSlot time : oldTimes) {
            data.addOfficeHoursReservation(time.getDay(), time.getTime(), time.getName());
        }   
        
        oldStartTime = currentStartTime;
        oldEndTime = currentEndTime;
        oldTimes = currentTimes;
        
        markWorkAsEdited();
    }
    
    @Override
    public void undoTransaction() {
        SiteGenWorkspace workspace = (SiteGenWorkspace) app.getWorkspaceComponent();
        SiteGenData data = (SiteGenData) app.getDataComponent();
        
        int currentStartTime = data.getStartHour();
        int currentEndTime = data.getEndHour();
        ArrayList<TimeSlot> currentTimes = new ArrayList(oldTimes);
        
        workspace.resetWorkspace();
        data.initOfficeHours(oldStartTime, oldEndTime);
          
        for(TimeSlot time : oldTimes) {
            data.addOfficeHoursReservation(time.getDay(), time.getTime(), time.getName());
        }   
        
        oldStartTime = currentStartTime;
        oldEndTime = currentEndTime;
        oldTimes = currentTimes;
        
        markWorkAsEdited();
    }
    
    private void markWorkAsEdited() {
        // MARK WORK AS EDITED
        AppGUI gui = app.getGUI();
        gui.getFileController().markAsEdited(gui);
    }
}

