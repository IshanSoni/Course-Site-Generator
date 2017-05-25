/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.test_bed;

import csg.SiteGenApp;
import csg.data.Page;
import csg.data.Recitation;
import csg.data.Schedule;
import csg.data.SiteGenData;
import csg.data.Student;
import csg.data.TeachingAssistant;
import csg.data.Team;
import csg.file.SiteGenFile;
import djf.AppTemplate;
import java.io.File;
import java.time.LocalDate;
import java.util.HashMap;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;
import properties_manager.PropertiesManager;

/**
 *
 * @author isoni
 */
public class TestSave {
    
    public static void TestSave() {
        
        SiteGenApp app = new SiteGenApp();
        app.loadProperties("app_properties.xml");
        SiteGenData testData = Driver.initValues(app);
        SiteGenFile fileManager = new SiteGenFile(app);
        File path = new File("/Users/isoni/Desktop/SiteSaveTest.json");
        
        try {
            fileManager.saveData(testData, path.getPath());
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        System.out.println("Saved successfuly at "+path.getPath());
    }
    
    public static void main(String[] args) {
        TestSave();
    }
    
}
