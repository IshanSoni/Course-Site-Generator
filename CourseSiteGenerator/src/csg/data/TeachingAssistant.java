package csg.data;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * This class represents a Teaching Assistant for the table of TAs.
 * 
 * @author Richard McKenna
 */
public class TeachingAssistant<E extends Comparable<E>> implements Comparable<E>  {
    // THE TABLE WILL STORE TA NAMES AND EMAILS
    private final StringProperty undergrad;
    private final StringProperty name;
    private final StringProperty email;

    /**
     * Constructor initializes both the TA name and email.
     */
    public TeachingAssistant(boolean undergrad, String initName, String initEmail) {
        if(undergrad)
            this.undergrad = new SimpleStringProperty("☑");
        else
            this.undergrad = new SimpleStringProperty("☐");
        name = new SimpleStringProperty(initName);
        email = new SimpleStringProperty(initEmail);
    }

    // ACCESSORS AND MUTATORS FOR THE PROPERTIES

    public String getUndergrad() {
        return undergrad.get();
    }
    
    public void setUnderGrad(boolean isUnderGrad) {
        if(isUnderGrad) undergrad.set("☑");
        else undergrad.set("☐");
    }
    
    public void toggleUnderGrad() {
        if(undergrad.get().equals("☑")) undergrad.set("☐");
        else undergrad.set("☑");
    }
    
    public String getName() {
        return name.get();
    }

    public void setName(String initName) {
        name.set(initName);
    }

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String initEmail) {
        email.set(initEmail);
    }
    
    public boolean isUndergrad() {
        return undergrad.get().equals("☑");
    }

    @Override
    public int compareTo(E otherTA) {
        return getName().compareTo(((TeachingAssistant)otherTA).getName());
    }
    
    @Override
    public String toString() {
        return name.getValue();
    }
}