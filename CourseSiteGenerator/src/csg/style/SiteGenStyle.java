package csg.style;

import csg.data.Page;
import csg.data.Recitation;
import csg.data.Schedule;
import csg.data.Student;
import djf.AppTemplate;
import djf.components.AppStyleComponent;
import java.util.HashMap;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import csg.data.TeachingAssistant;
import csg.data.Team;
import csg.workspace.SiteGenWorkspace;

/**
 * This class manages all CSS style for this application.
 * 
 * @author Richard McKenna
 * @version 1.0
 */
public class SiteGenStyle extends AppStyleComponent {
    // FIRST WE SHOULD DECLARE ALL OF THE STYLE TYPES WE PLAN TO USE
    
    // WE'LL USE THIS FOR ORGANIZING LEFT AND RIGHT CONTROLS
    public static String CLASS_PLAIN_PANE = "plain_pane";
    public static String CLASS_WORKSPACE_BOX = "workspace_box";
    public static String CLASS_TABPANE = "tabpane";
    public static String CLASS_TAB_CONTENT = "tab_content";
    public static String CLASS_BOTTOM_BORDER_SEPARATION = "bottom_border_separation";
    public static String CLASS_SIDE_BORDER_SEPARATION = "side_border_separation";
    public static String CLASS_BOTTOM_PADDING = "bottom_padding";
    
    // THESE ARE THE HEADERS FOR EACH SIDE
    public static String CLASS_HEADER_PANE = "header_pane";
    public static String CLASS_HEADER_LABEL = "header_label";
    public static String CLASS_SMALLER_HEADER_LABEL = "smaller_header_label";
    public static String GRIDTIME_HEADER_LABEL = "gridtime_header_label";
    public static String GRIDTIME_COMBOBOX = "gridtime_combobox";
    public static String GRIDTIME_HBOX = "gridtime_hbox";

    // ON THE LEFT WE HAVE THE TA ENTRY
    public static String CLASS_TA_TABLE = "ta_table";
    public static String CLASS_TA_TABLE_COLUMN_HEADER = "ta_table_column_header";
    public static String CLASS_ADD_TA_PANE = "add_ta_pane";
    public static String CLASS_ADD_TA_TEXT_FIELD = "add_ta_text_field";
    public static String CLASS_ADD_TA_BUTTON = "add_ta_button";

    // ON THE RIGHT WE HAVE THE OFFICE HOURS GRID
    public static String CLASS_OFFICE_HOURS_GRID = "office_hours_grid";
    public static String CLASS_OFFICE_HOURS_GRID_TIME_COLUMN_HEADER_PANE = "office_hours_grid_time_column_header_pane";
    public static String CLASS_OFFICE_HOURS_GRID_TIME_COLUMN_HEADER_LABEL = "office_hours_grid_time_column_header_label";
    public static String CLASS_OFFICE_HOURS_GRID_DAY_COLUMN_HEADER_PANE = "office_hours_grid_day_column_header_pane";
    public static String CLASS_OFFICE_HOURS_GRID_DAY_COLUMN_HEADER_LABEL = "office_hours_grid_day_column_header_label";
    public static String CLASS_OFFICE_HOURS_GRID_TIME_CELL_PANE = "office_hours_grid_time_cell_pane";
    public static String CLASS_OFFICE_HOURS_GRID_TIME_CELL_LABEL = "office_hours_grid_time_cell_label";
    public static String CLASS_OFFICE_HOURS_GRID_TA_CELL_PANE = "office_hours_grid_ta_cell_pane";
    public static String CLASS_OFFICE_HOURS_GRID_TA_CELL_LABEL = "office_hours_grid_ta_cell_label";

    // FOR HIGHLIGHTING CELLS, COLUMNS, AND ROWS
    public static String CLASS_HIGHLIGHTED_GRID_CELL = "highlighted_grid_cell";
    public static String CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN = "highlighted_grid_row_or_column";
    
    // THIS PROVIDES ACCESS TO OTHER COMPONENTS
    private AppTemplate app;
    
    /**
     * This constructor initializes all style for the application.
     * 
     * @param initApp The application to be stylized.
     */
    public SiteGenStyle(AppTemplate initApp) {
        // KEEP THIS FOR LATER
        app = initApp;

        // LET'S USE THE DEFAULT STYLESHEET SETUP
        super.initStylesheet(app);

        // INIT THE STYLE FOR THE FILE TOOLBAR
        app.getGUI().initFileToolbarStyle();

        // AND NOW OUR WORKSPACE STYLE
        initTAWorkspaceStyle();
    }

    /**
     * This function specifies all the style classes for
     * all user interface controls in the workspace.
     */
    private void initTAWorkspaceStyle() {
        // LEFT SIDE - THE HEADER
        SiteGenWorkspace workspaceComponent = (SiteGenWorkspace)app.getWorkspaceComponent();
        
        workspaceComponent.getWorkspaceBox().getStyleClass().add(CLASS_WORKSPACE_BOX);
        workspaceComponent.getTabPane().getStyleClass().add(CLASS_TABPANE);
        
        workspaceComponent.getTaTab().getHeaders().getStyleClass().add(CLASS_HEADER_PANE);
        workspaceComponent.getTaTab().getTAsHeaderLabel().getStyleClass().add(CLASS_HEADER_LABEL);
        
        workspaceComponent.getRecitationTab().getHeaders().getStyleClass().add(CLASS_HEADER_PANE);
        workspaceComponent.getRecitationTab().getRecitationHeader().getStyleClass().add(CLASS_HEADER_LABEL);
        workspaceComponent.getRecitationTab().getContent().getStyleClass().add(CLASS_TAB_CONTENT);
        workspaceComponent.getRecitationTab().getHeaders().getStyleClass().add(GRIDTIME_HBOX);
        workspaceComponent.getRecitationTab().getAddBox().getStyleClass().add(CLASS_SMALLER_HEADER_LABEL);
        workspaceComponent.getRecitationTab().getAddLabel().getStyleClass().add(CLASS_HEADER_LABEL);
        
        workspaceComponent.getScheduleTab().getHeaders().getStyleClass().add(CLASS_HEADER_PANE);
        workspaceComponent.getScheduleTab().getScheduleHeader().getStyleClass().add(CLASS_HEADER_LABEL);
        workspaceComponent.getScheduleTab().getSmallHeaders().getStyleClass().add(CLASS_SMALLER_HEADER_LABEL);
        workspaceComponent.getScheduleTab().getHeaders().getStyleClass().add(GRIDTIME_HBOX);
        workspaceComponent.getScheduleTab().getAddBox().getStyleClass().add(CLASS_SMALLER_HEADER_LABEL);
        workspaceComponent.getScheduleTab().getAddLabel().getStyleClass().add(CLASS_HEADER_LABEL);
        workspaceComponent.getScheduleTab().getContent().getStyleClass().add(CLASS_TAB_CONTENT);
        
        workspaceComponent.getProjectTab().getHeaders().getStyleClass().add(CLASS_HEADER_PANE);
        workspaceComponent.getProjectTab().getHeaders2().getStyleClass().add(CLASS_HEADER_PANE);
        workspaceComponent.getProjectTab().getTeamHeader().getStyleClass().add(CLASS_HEADER_LABEL);
        workspaceComponent.getProjectTab().getStudentHeader().getStyleClass().add(CLASS_HEADER_LABEL);
        workspaceComponent.getProjectTab().getHeaders().getStyleClass().add(GRIDTIME_HBOX);
        workspaceComponent.getProjectTab().getHeaders2().getStyleClass().add(GRIDTIME_HBOX);
        workspaceComponent.getProjectTab().getAddBox().getStyleClass().add(CLASS_SMALLER_HEADER_LABEL);
        workspaceComponent.getProjectTab().getAddLabel().getStyleClass().add(CLASS_HEADER_LABEL);
        workspaceComponent.getProjectTab().getAddBox2().getStyleClass().add(CLASS_SMALLER_HEADER_LABEL);
        workspaceComponent.getProjectTab().getAddLabel2().getStyleClass().add(CLASS_HEADER_LABEL);
        workspaceComponent.getProjectTab().getContent().getStyleClass().add(CLASS_TAB_CONTENT);
        workspaceComponent.getProjectTab().getContent1().getStyleClass().add(CLASS_BOTTOM_BORDER_SEPARATION);
        
        workspaceComponent.getCourseDetTab().getCourseInfoHeader().getStyleClass().add(CLASS_HEADER_PANE);
        workspaceComponent.getCourseDetTab().getPageStyleHeader().getStyleClass().add(CLASS_HEADER_PANE);
        workspaceComponent.getCourseDetTab().getSiteTemplateHeader().getStyleClass().add(CLASS_HEADER_PANE);
        workspaceComponent.getCourseDetTab().getCourseInfoHeader().getStyleClass().add(CLASS_HEADER_LABEL);
        workspaceComponent.getCourseDetTab().getPageStyleHeader().getStyleClass().add(CLASS_HEADER_LABEL);
        workspaceComponent.getCourseDetTab().getSiteTemplateHeader().getStyleClass().add(CLASS_HEADER_LABEL);
        workspaceComponent.getCourseDetTab().getCourseInfoHeader().getStyleClass().add(GRIDTIME_HBOX);
        workspaceComponent.getCourseDetTab().getPageStyleHeader().getStyleClass().add(GRIDTIME_HBOX);
        workspaceComponent.getCourseDetTab().getSiteTemplateHeader().getStyleClass().add(GRIDTIME_HBOX);
        workspaceComponent.getCourseDetTab().getFieldsAndLabels().getStyleClass().add(CLASS_SMALLER_HEADER_LABEL);
        workspaceComponent.getCourseDetTab().getFieldBox().getStyleClass().add(CLASS_SMALLER_HEADER_LABEL);
        workspaceComponent.getCourseDetTab().getSmallHeaders().getStyleClass().add(CLASS_SMALLER_HEADER_LABEL); 
        workspaceComponent.getCourseDetTab().getNote().getStyleClass().add(GRIDTIME_HBOX);
        workspaceComponent.getCourseDetTab().getCourseInfoBox().getStyleClass().add(CLASS_BOTTOM_BORDER_SEPARATION);
        workspaceComponent.getCourseDetTab().getCourseInfoBox().getStyleClass().add(CLASS_BOTTOM_PADDING);
        workspaceComponent.getCourseDetTab().getTemplate().getStyleClass().add(CLASS_SIDE_BORDER_SEPARATION);
        workspaceComponent.getCourseDetTab().getContent().getStyleClass().add(CLASS_TAB_CONTENT);
        

        // LEFT SIDE - THE TABLE
        TableView<TeachingAssistant> taTable = workspaceComponent.getTaTab().getTATable();
        taTable.getStyleClass().add(CLASS_TA_TABLE);
        for (TableColumn tableColumn : taTable.getColumns()) {
            tableColumn.getStyleClass().add(CLASS_TA_TABLE_COLUMN_HEADER);
        }
        
        TableView<Recitation> recitationTable = workspaceComponent.getRecitationTab().getRecitationTable();
        recitationTable.getStyleClass().add(CLASS_TA_TABLE);
        for (TableColumn tableColumn : recitationTable.getColumns()) {
            tableColumn.getStyleClass().add(CLASS_TA_TABLE_COLUMN_HEADER);
        }
        
        TableView<Schedule> scheduleTable = workspaceComponent.getScheduleTab().getScheduleTable();
        scheduleTable.getStyleClass().add(CLASS_TA_TABLE);
        for (TableColumn tableColumn : scheduleTable.getColumns()) {
            tableColumn.getStyleClass().add(CLASS_TA_TABLE_COLUMN_HEADER);
        }
        
        TableView<Team> teamTable = workspaceComponent.getProjectTab().getTeamTable();
        teamTable.getStyleClass().add(CLASS_TA_TABLE);
        for (TableColumn tableColumn : teamTable.getColumns()) {
            tableColumn.getStyleClass().add(CLASS_TA_TABLE_COLUMN_HEADER);
        }
        
        TableView<Student> studentTable = workspaceComponent.getProjectTab().getStudentTable();
        studentTable.getStyleClass().add(CLASS_TA_TABLE);
        for (TableColumn tableColumn : studentTable.getColumns()) {
            tableColumn.getStyleClass().add(CLASS_TA_TABLE_COLUMN_HEADER);
        }
        
        TableView<Page> pageTable = workspaceComponent.getCourseDetTab().getPageTable();
        pageTable.getStyleClass().add(CLASS_TA_TABLE);
        for (TableColumn tableColumn : pageTable.getColumns()) {
            tableColumn.getStyleClass().add(CLASS_TA_TABLE_COLUMN_HEADER);
        }

        // LEFT SIDE - THE TA DATA ENTRY
        workspaceComponent.getTaTab().getAddBox().getStyleClass().add(CLASS_ADD_TA_PANE);
        workspaceComponent.getTaTab().getNameTextField().getStyleClass().add(CLASS_ADD_TA_TEXT_FIELD);
        workspaceComponent.getTaTab().getEmailTextField().getStyleClass().add(CLASS_ADD_TA_TEXT_FIELD);
        workspaceComponent.getTaTab().getAddButton().getStyleClass().add(CLASS_ADD_TA_BUTTON);
        workspaceComponent.getTaTab().getClearButton().getStyleClass().add(CLASS_ADD_TA_BUTTON);

        // RIGHT SIDE - THE HEADER
        workspaceComponent.getTaTab().getOfficeHoursSubheaderBox().getStyleClass().add(CLASS_HEADER_PANE);
        workspaceComponent.getTaTab().getOfficeHoursSubheaderLabel().getStyleClass().add(CLASS_HEADER_LABEL);
        workspaceComponent.getTaTab().getGridTimesStartLabel().getStyleClass().add(GRIDTIME_HEADER_LABEL);
        workspaceComponent.getTaTab().getGridTimesEndLabel().getStyleClass().add(GRIDTIME_HEADER_LABEL);
        workspaceComponent.getTaTab().getGridTimesStartCombo().getStyleClass().add(GRIDTIME_COMBOBOX);
        workspaceComponent.getTaTab().getGridTimesEndCombo().getStyleClass().add(GRIDTIME_COMBOBOX);
        workspaceComponent.getTaTab().getTimeChangeHBox().getStyleClass().add(GRIDTIME_HBOX);    
        workspaceComponent.getTaTab().getContent().getStyleClass().add(CLASS_TAB_CONTENT);
        workspaceComponent.getTaTab().getTAsHeaderBox().getStyleClass().add(GRIDTIME_HBOX);
    }
    
    /**
     * This method initializes the style for all UI components in
     * the office hours grid. Note that this should be called every
     * time a new TA Office Hours Grid is created or loaded.
     */
    public void initOfficeHoursGridStyle() {
        // RIGHT SIDE - THE OFFICE HOURS GRID TIME HEADERS
        SiteGenWorkspace workspaceComponent = (SiteGenWorkspace)app.getWorkspaceComponent();
        workspaceComponent.getTaTab().getOfficeHoursGridPane().getStyleClass().add(CLASS_OFFICE_HOURS_GRID);
        setStyleClassOnAll(workspaceComponent.getTaTab().getOfficeHoursGridTimeHeaderPanes(), CLASS_OFFICE_HOURS_GRID_TIME_COLUMN_HEADER_PANE);
        setStyleClassOnAll(workspaceComponent.getTaTab().getOfficeHoursGridTimeHeaderLabels(), CLASS_OFFICE_HOURS_GRID_TIME_COLUMN_HEADER_LABEL);
        setStyleClassOnAll(workspaceComponent.getTaTab().getOfficeHoursGridDayHeaderPanes(), CLASS_OFFICE_HOURS_GRID_DAY_COLUMN_HEADER_PANE);
        setStyleClassOnAll(workspaceComponent.getTaTab().getOfficeHoursGridDayHeaderLabels(), CLASS_OFFICE_HOURS_GRID_DAY_COLUMN_HEADER_LABEL);
        setStyleClassOnAll(workspaceComponent.getTaTab().getOfficeHoursGridTimeCellPanes(), CLASS_OFFICE_HOURS_GRID_TIME_CELL_PANE);
        setStyleClassOnAll(workspaceComponent.getTaTab().getOfficeHoursGridTimeCellLabels(), CLASS_OFFICE_HOURS_GRID_TIME_CELL_LABEL);
        setStyleClassOnAll(workspaceComponent.getTaTab().getOfficeHoursGridTACellPanes(), CLASS_OFFICE_HOURS_GRID_TA_CELL_PANE);
        setStyleClassOnAll(workspaceComponent.getTaTab().getOfficeHoursGridTACellLabels(), CLASS_OFFICE_HOURS_GRID_TA_CELL_LABEL);
    }
    
    /**
     * This helper method initializes the style of all the nodes in the nodes
     * map to a common style, styleClass.
     */
    private void setStyleClassOnAll(HashMap nodes, String styleClass) {
        for (Object nodeObject : nodes.values()) {
            Node n = (Node)nodeObject;
            n.getStyleClass().add(styleClass);
        }
    }
}