package csg;

/**
 * This enum provides a list of all the user interface
 * text that needs to be loaded from the XML properties
 * file. By simply changing the XML file we could initialize
 * this application such that all UI controls are provided
 * in another language.
 * 
 * @author Richard McKenna
 * @version 1.0
 */
public enum SiteGenProp {
    // FOR SIMPLE OK/CANCEL DIALOG BOXES
    OK_PROMPT,
    CANCEL_PROMPT,
    
    // THESE ARE FOR TEXT PARTICULAR TO THE APP'S WORKSPACE CONTROLS
    TAS_HEADER_TEXT,
    UNDERGRAD_COLUMN_TEXT,
    NAME_COLUMN_TEXT,
    EMAIL_COLUMN_TEXT,
    NAME_PROMPT_TEXT,
    EMAIL_PROMPT_TEXT,
    ADD_BUTTON_TEXT,
    UPDATE_BUTTON_TEXT,
    CLEAR_BUTTON_TEXT,
    OFFICE_HOURS_SUBHEADER,
    OFFICE_HOURS_TABLE_HEADERS,
    DAYS_OF_WEEK,
    GRID_TIME_START,
    GRID_TIME_END,
    GRID_TIME_BUTTON,
    
    RECITATION_HEADER,
    SECTION,
    INSTRUCTOR,
    DAYTIME,
    LOCATION,
    SUPTA1,
    SUPTA2,
    ADD_EDIT,
    SUPERTA1,
    SUPERTA2,
    ADDUPDATE,
    
    SCHEDULE_HEADER,
    CALENDER_BOUNDS,
    START_BOUND,
    END_BOUND,
    SCHED_ITEMS_HEADER,
    TYPE,
    DATE,
    TIME,
    TITLE,
    TOPIC,
    LINK,
    CRITERIA,
    
    TEAM_HEADER,
    NAME,
    COLOR,
    COLOR_HEX,
    TEXT_COLOR,
    TEXT_COLOR_HEX,
    STUDENT_HEADER,
    FIRST_NAME,
    LAST_NAME,
    TEAM,
    ROLE,
    
    COURSE_INFO_HEADER,
    SUBJECT,
    SEMESTER,
    INSTRUCTOR_NAME,
    INSTRUCTOR_HOME,
    EXPORT_DIR,
    NUMBER,
    YEAR,
    CHANGE,
    PAGE_STYLE_HEADER,
    BANNER_SCHOOL_IMAGE,
    LEFT_FOOTER,
    RIGHT_FOOTER,
    STYLESHEET,
    NOTE,
    SITE_TEMPLATE_HEADER,
    SITE_TEMPLATE_NOTE,
    SELECT_DIRECTORY,
    SITE_PAGES,
    USE,
    NAV_TITLE,
    FILE_NAME,
    SCRIPT,
    HOME,
    SYLLABUS,
    HWS,
    PROJECTS,
    INDEX_HTML,
    SYLLABUS_HTML,
    SCHEDULE_HTML,
    HWS_HTML,
    PROJECTS_HTML,
    HOMEBUILDER_JS,
    SYLLABUSBUILDER_JS,
    SCHEDULEBUILDER_JS,
    HWSBUILDER_JS,
    PROJECTSBUILDER_JS,
    
    // THESE ARE FOR ERROR MESSAGES PARTICULAR TO THE APP
    MISSING_TA_NAME_TITLE,
    MISSING_TA_NAME_MESSAGE,
    MISSING_TA_EMAIL_TITLE,
    MISSING_TA_EMAIL_MESSAGE,
    TA_NAME_AND_EMAIL_NOT_UNIQUE_TITLE,
    TA_NAME_AND_EMAIL_NOT_UNIQUE_MESSAGE,
    EMAIL_FORMAT_WRONG_TITLE,
    EMAIL_FORMAT_WRONG_MESSAGE,
    GRID_TIME_TA_ERROR,
    GRID_TIME_TA_ERROR_TITLE,
    GRID_INVALID_TIME_TITLE,
    GRID_INVALID_TIME,
    
    DIRECTORY_UNDEFINED,
    
    //ADD TO XML
    MISSING_SECTION_TITLE,
    MISSING_SECTION_MESSAGE,
    MISSING_DAY_TIME_TITLE,
    MISSING_DAY_TIME_MESSAGE,
    MISSING_LOCATION_TITLE,
    MISSING_LOCATION_MESSAGE,
    INVALID_TA_TITLE,
    INVALID_TA_MESSAGE,
    MISSING_NAME_TITLE,
    MISSING_NAME_MESSAGE,
    MISSING_COLOR_TITLE,
    MISSING_COLOR_MESSAGE,
    MISSING_TEXT_COLOR_TITLE,
    MISSING_TEXT_COLOR_MESSAGE,
    MISSING_LINK_TITLE,
    MISSING_LINK_MESSAGE,
    MISSING_FIRST_NAME_TITLE,
    MISSING_FIRST_NAME_MESSAGE,
    MISSING_LAST_NAME_TITLE,
    MISSING_LAST_NAME_MESSAGE,
    NAME_NOT_UNIQUE_TITLE,
    NAME_NOT_UNIQUE_MESSAGE,
    MISSING_TYPE_TITLE,
    MISSING_TYPE_MESSAGE,
    MISSING_DATE_TITLE,
    MISSING_DATE_MESSAGE,
    MISSING_TITLE_TITLE,
    MISSING_TITLE_MESSAGE,
    BOUND_ERROR_TITLE,
    BOUND_ERROR_MESSAGE
}
