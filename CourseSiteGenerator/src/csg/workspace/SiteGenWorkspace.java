package csg.workspace;

import djf.controller.AppFileController;
import djf.components.AppDataComponent;
import djf.components.AppWorkspaceComponent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import csg.SiteGenApp;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import properties_manager.PropertiesManager;
import csg.SiteGenProp;
import csg.style.SiteGenStyle;
import csg.data.SiteGenData;
import csg.data.TeachingAssistant;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import jtps.jTPS_Transaction;

/**
 * This class serves as the workspace component for the TA Manager application.
 * It provides all the user interface controls in the workspace area.
 *
 * @author Richard McKenna
 * @co-author Ishan Soni
 */
public class SiteGenWorkspace extends AppWorkspaceComponent {

    // THIS PROVIDES US WITH ACCESS TO THE APP COMPONENTS
    SiteGenApp app;

    // THIS PROVIDES RESPONSES TO INTERACTIONS WITH THIS WORKSPACE
    SiteGenController controller;

    HBox workspaceBox;
    // NOTE THAT EVERY CONTROL IS PUT IN A BOX TO HELP WITH ALIGNMENT

    Button undoButton;
    Button redoButton;
    Button aboutButton;
    TabPane tabs;

    //TABS
    CourseDetailWorkspace cDetailTab;
    Tab cDetailTabUI;
    TADataWorkspace taTab;
    Tab taTabUI;
    RecitationWorkspace recitationTab;
    Tab recitationTabUI;
    ScheduleWorkspace scheduleTab;
    Tab scheduleTabUI;
    ProjectWorkspace projectTab;
    Tab projectTabUI;

    /**
     * The contstructor initializes the user interface, except for the full
     * office hours grid, since it doesn't yet know what the hours will be until
     * a file is loaded or a new one is created.
     */
    public SiteGenWorkspace(SiteGenApp initApp) {
        app = initApp;
        controller = new SiteGenController(app);
        workspaceBox = new HBox();
        workspace = new BorderPane();

        tabs = new TabPane();
        tabs.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        //Initialize Tab UI
        cDetailTabUI = new Tab("Course Detail");
        cDetailTab = new CourseDetailWorkspace(app, cDetailTabUI, workspace, controller);
        taTabUI = new Tab("TA Data");
        taTab = new TADataWorkspace(app, taTabUI, workspace, controller);
        recitationTabUI = new Tab("Recitation Data");
        recitationTab = new RecitationWorkspace(app, recitationTabUI, workspace, controller);
        scheduleTabUI = new Tab("Schedule Data");
        scheduleTab = new ScheduleWorkspace(app, scheduleTabUI, workspace, controller);
        projectTabUI = new Tab("Project Data");
        projectTab = new ProjectWorkspace(app, projectTabUI, workspace, controller);

        //Add Tabs to UI
        tabs.getTabs().add(cDetailTabUI);
        tabs.getTabs().add(taTabUI);
        tabs.getTabs().add(recitationTabUI);
        tabs.getTabs().add(scheduleTabUI);
        tabs.getTabs().add(projectTabUI);

        workspaceBox.getChildren().add(tabs);
        ((BorderPane) workspace).setCenter(workspaceBox);

        // UNDO AND REDO
        workspace.setOnKeyPressed(e -> {
            if (e.isControlDown() && e.getCode() == KeyCode.Z) {
                controller.getJtps().undoTransaction();
            } else if (e.isControlDown() && e.getCode() == KeyCode.Y) {
                controller.getJtps().doTransaction();
            }
        });
        
        app.getGUI().getUndoButton().setOnAction(e -> {
            controller.getJtps().undoTransaction();
        });
        
        app.getGUI().getRedoButton().setOnAction(e -> {
            controller.getJtps().doTransaction();
        });

    }

    public TabPane getTabPane() {
        return tabs;
    }

    public CourseDetailWorkspace getCourseDetTab() {
        return cDetailTab;
    }

    public TADataWorkspace getTaTab() {
        return taTab;
    }

    public RecitationWorkspace getRecitationTab() {
        return recitationTab;
    }

    public ScheduleWorkspace getScheduleTab() {
        return scheduleTab;
    }

    public ProjectWorkspace getProjectTab() {
        return projectTab;
    }

    public SiteGenController getController() {
        return controller;
    }

    public HBox getWorkspaceBox() {
        return workspaceBox;
    }

    @Override
    public void resetWorkspace() {
        cDetailTab.resetWorkspace();
        recitationTab.resetWorkspace();
        scheduleTab.resetWorkspace();
        projectTab.resetWorkspace();
        taTab.resetWorkspace();
    }

    @Override
    public void reloadWorkspace(AppDataComponent dataComponent) {
        SiteGenData data = (SiteGenData) dataComponent;
        cDetailTab.reloadWorkspace(dataComponent);
        recitationTab.reloadWorkspace(dataComponent);
        scheduleTab.reloadWorkspace(dataComponent);
        projectTab.reloadWorkspace(dataComponent);
        taTab.reloadWorkspace(dataComponent);

    }

}
