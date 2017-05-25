package csg.test_bed;

import csg.SiteGenApp;
import csg.data.Page;
import csg.data.Recitation;
import csg.data.Schedule;
import csg.data.SiteGenData;
import csg.data.Student;
import csg.data.Team;
import csg.file.SiteGenFile;
import java.io.File;
import java.time.LocalDate;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;


public class Driver {
    
    // UI DATA FOR COURSE DETAILS TAB
    public static final String SUBJECT = "TEST";
    public static final int NUMBER = 102;
    public static final String SEMESTER = "Autumn";
    public static final int YEAR = 2017;
    public static final String TITLE = "Testology 2";
    public static final String INSTRUCTOR_NAME = "Mr. Tester";
    public static final String INSTRUCTOR_HOME = "www.mrtester.com";
    public static final String EXPORT_DIR = "Users/isoni/Desktop";
    public static final String TEMPLATE_DIR = "Users/isoni1/Desktop1";
    public static final String BANNER_IMG_DIR = "http://xiostorage.com/wp-content/uploads/2015/10/test.png";
    public static final String LEFT_FOOTER_IMG_DIR = "http://xiostorage.com/wp-content/uploads/2015/10/test.png";
    public static final String RIGHT_FOOTER_IMG_DIR = "http://xiostorage.com/wp-content/uploads/2015/10/test.png";
    public static final String STYLESHEET = "test.css";
    
    //UI DATA FOR TA DATA TAB
    public static final int START_HOUR = 10;
    public static final int END_HOUR = 11;
    public static final boolean TA_1_UNDERGRAD = true;
    public static final String TA_1_NAME = "Test TA 1";
    public static final String TA_1_EMAIL = "testta1@test1.com";
    public static final boolean TA_2_UNDERGRAD = false;
    public static final String TA_2_NAME = "Test TA 2";
    public static final String TA_2_EMAIL = "testta2@test2.com";
    public static final String TEST_GRID_TIME = "4:20pm";
    
    //UI DATA FOR RECITATIONS TAB
    public static final String RECITATION_1_SECTION = "T01";
    public static final String RECITATION_1_INSTRUCTOR = "Mr.Recitation1";
    public static final String RECITATION_1_DAYTIME = "Mondays, 1:00pm-2:00pm";
    public static final String RECITATION_1_LOCATION = "New York";
    public static final String RECITATION_1_TA_1 = "Recitation1 TA1";
    public static final String RECITATION_1_TA_2 = "Recitation1 TA2";
    public static final String RECITATION_2_SECTION = "T02";
    public static final String RECITATION_2_INSTRUCTOR = "Mr.Recitation2";
    public static final String RECITATION_2_DAYTIME = "Tuesdays, 2:00pm-2:00pm";
    public static final String RECITATION_2_LOCATION = "California";
    public static final String RECITATION_2_TA_1 = "Recitation2 TA1";
    public static final String RECITATION_2_TA_2 = "Recitation2 TA2";
    public static final String RECITATION_3_SECTION = "T03";
    public static final String RECITATION_3_INSTRUCTOR = "Mr.Recitation3";
    public static final String RECITATION_3_DAYTIME = "Wednesdays, 3:00pm-4:00pm";
    public static final String RECITATION_3_LOCATION = "Texas";
    public static final String RECITATION_3_TA_1 = "Recitation3 TA1";
    public static final String RECITATION_3_TA_2 = "Recitation3 TA2";
    
    //UI DATA FOR SCHEDULE TAB
    public static final String STARTING_MONDAY = "2017-01-02";
    public static final String ENDING_FRIDAY = "2017-01-20";
    public static final String SCHEDULE_1_TYPE = "Holiday";
    public static final String SCHEDULE_1_DATE = "2017-01-05";
    public static final String SCHEDULE_1_TITLE = "Schedule1 Title";
    public static final String SCHEDULE_1_TOPIC = "Schedule1 Topic";
    public static final String SCHEDULE_1_LINK = "www.schedule1-link.com";
    public static final String SCHEDULE_1_TIME = "1:00pm";
    public static final String SCHEDULE_1_CRITERIA = "www.schedule1-criteria.com";
    public static final String SCHEDULE_2_TYPE = "Lecture";
    public static final String SCHEDULE_2_DATE = "2017-01-06";
    public static final String SCHEDULE_2_TITLE = "Schedule2 Title";
    public static final String SCHEDULE_2_TOPIC = "Schedule2 Topic";
    public static final String SCHEDULE_2_LINK = "www.schedule2-link.com";
    public static final String SCHEDULE_2_TIME = "2:00pm";
    public static final String SCHEDULE_2_CRITERIA = "www.schedule2-criteria.com";
    public static final String SCHEDULE_3_TYPE = "Reference";
    public static final String SCHEDULE_3_DATE = "2017-01-09";
    public static final String SCHEDULE_3_TITLE = "Schedule3 Title";
    public static final String SCHEDULE_3_TOPIC = "Schedule3 Topic";
    public static final String SCHEDULE_3_LINK = "www.schedule3-link.com";
    public static final String SCHEDULE_3_TIME = "3:00pm";
    public static final String SCHEDULE_3_CRITERIA = "www.schedule3-criteria.com";
    public static final String SCHEDULE_4_TYPE = "Recitation";
    public static final String SCHEDULE_4_DATE = "2017-01-10";
    public static final String SCHEDULE_4_TITLE = "Schedule4 Title";
    public static final String SCHEDULE_4_TOPIC = "Schedule4 Topic";
    public static final String SCHEDULE_4_LINK = "www.schedule4-link.com";
    public static final String SCHEDULE_4_TIME = "4:00pm";
    public static final String SCHEDULE_4_CRITERIA = "www.schedule4-criteria.com";
    public static final String SCHEDULE_5_TYPE = "Hw";
    public static final String SCHEDULE_5_DATE = "2017-01-11";
    public static final String SCHEDULE_5_TITLE = "Schedule5 Title";
    public static final String SCHEDULE_5_TOPIC = "Schedule5 Topic";
    public static final String SCHEDULE_5_LINK = "www.schedule5-link.com";
    public static final String SCHEDULE_5_TIME = "5:00pm";
    public static final String SCHEDULE_5_CRITERIA = "www.schedule5-criteria.com";
    
    //UI DATA FOR PROJECTS TAB
    public static final String TEAM_1_NAME = "Team Name 1";
    public static final int TEAM_1_COLOR_RED = 127;
    public static final int TEAM_1_COLOR_GREEN = 240;
    public static final int TEAM_1_COLOR_BLUE = 0;
    public static final int TEAM_1_TEXTCOLOR_RED = 0;
    public static final int TEAM_1_TEXTCOLOR_GREEN = 0;
    public static final int TEAM_1_TEXTCOLOR_BLUE = 0;
    public static final String TEAM_1_LINK = "www.team1-link.com";
    public static final String TEAM_2_NAME = "Team Name 2";
    public static final int TEAM_2_COLOR_RED = 127;
    public static final int TEAM_2_COLOR_GREEN = 255;
    public static final int TEAM_2_COLOR_BLUE = 212;
    public static final int TEAM_2_TEXTCOLOR_RED = 255;
    public static final int TEAM_2_TEXTCOLOR_GREEN = 255;
    public static final int TEAM_2_TEXTCOLOR_BLUE = 255;
    public static final String TEAM_2_LINK = "www.team2-link.com";
    public static final String STUDENT_1_FIRSTNAME = "first name 1";
    public static final String STUDENT_1_LASTNAME = "last name 1";
    public static final String STUDENT_1_TEAM = "Team Name 1";
    public static final String STUDENT_1_ROLE = "student role 1";
    public static final String STUDENT_2_FIRSTNAME = "first name 2";
    public static final String STUDENT_2_LASTNAME = "last name 2";
    public static final String STUDENT_2_TEAM = "Team Name 2";
    public static final String STUDENT_2_ROLE = "student role 2";
    
    
    public static SiteGenData initValues(SiteGenApp app) {
        SiteGenData dataManager = new SiteGenData(app);
        
        //COURSE DETAILS TAB UI INITIALIZATION
        dataManager.setSubject(SUBJECT);
        dataManager.setNumber(NUMBER);
        dataManager.setSemester(SEMESTER);
        dataManager.setYear(YEAR);
        dataManager.setTitle(TITLE);
        dataManager.setInstructorName(INSTRUCTOR_NAME);
        dataManager.setInstructorHome(INSTRUCTOR_HOME);
        dataManager.setExportDir(EXPORT_DIR);
        dataManager.setTemplateDir(TEMPLATE_DIR);
        dataManager.setBannerImgDir(BANNER_IMG_DIR);
        dataManager.setLeftFooterImgDir(LEFT_FOOTER_IMG_DIR);
        dataManager.setRightFooterImgDir(RIGHT_FOOTER_IMG_DIR);
        dataManager.setStyleSheet(STYLESHEET);        
        //setting page use 
        ObservableList<Page> pages = dataManager.getPages();
        int i = 1;
        for(Page p : pages) {
            if(i%2==0) p.setUse(false);
            i++;
        }
        
        //TA TAB TEST UI INITIALIZATION
        dataManager.setStartHour(10);
        dataManager.setEndHour(11);
        dataManager.addTA(TA_1_UNDERGRAD, TA_1_NAME, TA_1_EMAIL);
        dataManager.addTA(TA_2_UNDERGRAD, TA_2_NAME, TA_2_EMAIL);
        //setting test grid data 
        for (int row = 1; row < dataManager.getNumRows(); row++) {
            for (int col = 0; col < 7; col++) {
                dataManager.getOfficeHours().put(col+"_"+row, new SimpleStringProperty(TEST_GRID_TIME));
            }
        }
        
        //RECITATION TAB TEST UI DATA INITIALIZATION
        dataManager.getRecitations().add(new Recitation(RECITATION_1_SECTION,RECITATION_1_INSTRUCTOR,RECITATION_1_DAYTIME,RECITATION_1_LOCATION,RECITATION_1_TA_1,RECITATION_1_TA_2));
        dataManager.getRecitations().add(new Recitation(RECITATION_2_SECTION,RECITATION_2_INSTRUCTOR,RECITATION_2_DAYTIME,RECITATION_2_LOCATION,RECITATION_2_TA_1,RECITATION_2_TA_2));
        dataManager.getRecitations().add(new Recitation(RECITATION_3_SECTION,RECITATION_3_INSTRUCTOR,RECITATION_3_DAYTIME,RECITATION_3_LOCATION,RECITATION_3_TA_1,RECITATION_3_TA_2));
        
        //SCHEDULE TAB TEST UI DATA INITIALIZATION
        dataManager.setStartingMonday(LocalDate.parse(STARTING_MONDAY));
        dataManager.setEndingFriday(LocalDate.parse(ENDING_FRIDAY));
        dataManager.getHolidaySchedules().add(new Schedule(SCHEDULE_1_TYPE, LocalDate.parse(SCHEDULE_1_DATE), SCHEDULE_1_TITLE, SCHEDULE_1_TOPIC, SCHEDULE_1_LINK, SCHEDULE_1_TIME, SCHEDULE_1_CRITERIA));
        dataManager.getLectureSchedules().add(new Schedule(SCHEDULE_2_TYPE, LocalDate.parse(SCHEDULE_2_DATE), SCHEDULE_2_TITLE, SCHEDULE_2_TOPIC, SCHEDULE_2_LINK, SCHEDULE_2_TIME, SCHEDULE_2_CRITERIA));
        dataManager.getReferenceSchedules().add(new Schedule(SCHEDULE_3_TYPE, LocalDate.parse(SCHEDULE_3_DATE), SCHEDULE_3_TITLE, SCHEDULE_3_TOPIC, SCHEDULE_3_LINK, SCHEDULE_3_TIME, SCHEDULE_3_CRITERIA));
        dataManager.getRecitationSchedules().add(new Schedule(SCHEDULE_4_TYPE, LocalDate.parse(SCHEDULE_4_DATE), SCHEDULE_4_TITLE, SCHEDULE_4_TOPIC, SCHEDULE_4_LINK, SCHEDULE_4_TIME, SCHEDULE_4_CRITERIA));
        dataManager.getHwSchedules().add(new Schedule(SCHEDULE_5_TYPE, LocalDate.parse(SCHEDULE_5_DATE), SCHEDULE_5_TITLE, SCHEDULE_5_TOPIC, SCHEDULE_5_LINK, SCHEDULE_5_TIME, SCHEDULE_5_CRITERIA));
    
        
        //PROJECT TAB TEST UI DATA
        dataManager.getTeams().add(new Team(TEAM_1_NAME, Color.color((double)TEAM_1_COLOR_RED/255, (double)TEAM_1_COLOR_GREEN/255, (double)TEAM_1_COLOR_BLUE/255), Color.color((double)TEAM_1_TEXTCOLOR_RED/255, (double)TEAM_1_TEXTCOLOR_GREEN/255, (double)TEAM_1_TEXTCOLOR_BLUE/255), TEAM_1_LINK));
        dataManager.getTeams().add(new Team(TEAM_2_NAME, Color.color((double)TEAM_2_COLOR_RED/255, (double)TEAM_2_COLOR_GREEN/255, (double)TEAM_2_COLOR_BLUE/255), Color.color((double)TEAM_2_TEXTCOLOR_RED/255, (double)TEAM_2_TEXTCOLOR_GREEN/255, (double)TEAM_2_TEXTCOLOR_BLUE/255), TEAM_2_LINK));
        dataManager.getStudents().add(new Student(STUDENT_1_FIRSTNAME, STUDENT_1_LASTNAME, STUDENT_1_TEAM, STUDENT_1_ROLE));
        dataManager.getStudents().add(new Student(STUDENT_2_FIRSTNAME, STUDENT_2_LASTNAME, STUDENT_2_TEAM, STUDENT_2_ROLE));
                
        return dataManager;
    }
}
