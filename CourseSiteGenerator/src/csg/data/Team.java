/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.data;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.paint.Color;

/**
 *
 * @author isoni
 */
public class Team<E extends Comparable<E>> implements Comparable<E> {

    private StringProperty name;
    private StringProperty color;
    private StringProperty textColor;
    private StringProperty link;

    public Team(String name, Color color, Color textColor, String link) {
        this.name = new SimpleStringProperty(name);
        String hexColor = String.format("#%02x%02x%02x", (int) (color.getRed() * 255), (int) (color.getGreen() * 255), (int) (color.getBlue() * 255));
        this.color = new SimpleStringProperty(hexColor);
        String hexColorText = String.format("#%02x%02x%02x", (int) (textColor.getRed() * 255), (int) (textColor.getGreen() * 255), (int) (textColor.getBlue() * 255));
        this.textColor = new SimpleStringProperty(hexColorText);
        this.link = new SimpleStringProperty(link);
    }

    public String getName() {
        return name.get();
    }

    public String getColor() {
        return color.get();
    }

    public Color getColorInstance() {
        return Color.valueOf(color.get());
    }

    public String getTextColor() {
        return textColor.get();
    }

    public Color getTextColorInstance() {
        return Color.valueOf(textColor.get());
    }

    public String getLink() {
        return link.get();
    }

    @Override
    public int compareTo(E o) {
        return name.get().compareTo(((Team) o).getName());
    }

    public String toString() {
        return this.name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public void setColor(Color color) {
        String hexColor = String.format("#%02x%02x%02x", (int) (color.getRed() * 255), (int) (color.getGreen() * 255), (int) (color.getBlue() * 255));
        this.color.set(hexColor);
    }

    public void setTextColor(Color color) {
        String hexColor = String.format("#%02x%02x%02x", (int) (color.getRed() * 255), (int) (color.getGreen() * 255), (int) (color.getBlue() * 255));
        this.textColor.set(hexColor);
    }

    public void setLink(String link) {
        this.link.set(link);
    }

}
