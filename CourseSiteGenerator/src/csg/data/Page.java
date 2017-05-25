/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.data;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author isoni
 */
public class Page {
    StringProperty use;
    StringProperty navbarTitle;
    StringProperty fileName;
    StringProperty script;
    
    public Page(boolean use, String navbarTitle, String fileName, String script) {
        if(use) this.use = new SimpleStringProperty("☑");
        else this.use = new SimpleStringProperty("☐");
        this.navbarTitle = new SimpleStringProperty(navbarTitle);
        this.fileName = new SimpleStringProperty(fileName);
        this.script = new SimpleStringProperty(script);
    }
    
    public String getNavbarTitle() {
        return navbarTitle.get();
    }
    
    public void setUse(boolean isUsed) {
        if(isUsed) use.set("☑");
        else use.set("☐");
    }
    
    public void toggleUse() {
        if(use.get().equals("☑")) use.set("☐");
        else use.set("☑");
    }
    
    public String getUse() {
        return use.get();
    }
    
    public boolean isUseToggled() {
        return use.get().equals("☑");
    }
    
    public String getFileName() {
        return fileName.get();
    }
    
    public String getScript() {
        return script.get();
    }
}
