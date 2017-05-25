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
import static csg.test_bed.Driver.TEST_GRID_TIME;
import csg.workspace.SiteGenWorkspace;
import djf.components.AppDataComponent;
import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;
import javax.json.JsonObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author isoni
 */
public class TestLoad {
    SiteGenApp app;
    SiteGenData data;
    SiteGenFile fileManager;
    File path;
    
    public TestLoad() {
        app = new SiteGenApp();
        app.loadProperties("app_properties.xml");
        //app.setWorkspaceComponent(new SiteGenWorkspace(app));
        data = new SiteGenData(app);
        fileManager = new SiteGenFile(app);
        path = new File("/Users/isoni/Desktop/SiteSaveTest.json");
        
        try {
            fileManager.loadData(data, path.getPath());
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testCourseDetailsTab() {
        //hard-coded course details
        String expectedSubject = Driver.SUBJECT;
        int expectedNumber = Driver.NUMBER;
        String expectedSemester = Driver.SEMESTER;
        int expectedYear = Driver.YEAR;
        String expectedTitle = Driver.TITLE;
        String expectedInstructorName = Driver.INSTRUCTOR_NAME;
        String expectedInstructorHome = Driver.INSTRUCTOR_HOME;
        String expectedExportDir = Driver.EXPORT_DIR;
        String expectedTemplateDir = Driver.TEMPLATE_DIR;
        String expectedBannerImgDir = Driver.BANNER_IMG_DIR;
        String expectedLeftFooterImgDir = Driver.LEFT_FOOTER_IMG_DIR;
        String expectedRightFooterImgDir = Driver.RIGHT_FOOTER_IMG_DIR;
        String expectedStyleSheet = Driver.STYLESHEET;
        
        //loaded course details
        assertEquals(expectedSubject, data.getSubject());
        assertEquals(expectedNumber, data.getNumber());
        assertEquals(expectedSemester, data.getSemester());
        assertEquals(expectedYear, data.getYear());
        assertEquals(expectedTitle, data.getTitle());
        assertEquals(expectedInstructorName, data.getInstructorName());
        assertEquals(expectedInstructorHome, data.getInstructorHome());
        assertEquals(expectedExportDir, data.getExportDir());
        assertEquals(expectedTemplateDir, data.getTemplateDir());
        assertEquals(expectedBannerImgDir, data.getBannerImgDir());
        assertEquals(expectedLeftFooterImgDir, data.getLeftFooterImgDir());
        assertEquals(expectedRightFooterImgDir, data.getRightFooterImgDir());
        assertEquals(expectedStyleSheet, data.getStyleSheet());
        //loaded pages
        ObservableList<Page> pages = data.getPages();
        int i = 1;
        for(Page p : pages) {
            if(i%2==0) assertEquals(false, p.isUseToggled());
            else assertEquals(true, p.isUseToggled());
            i++;
        }
    }
    
    @Test
    public void testTADataTab() {
        //hard-coded ta data
        int expectedStartHour = Driver.START_HOUR;
        int expectedEndHour = Driver.END_HOUR;
        List<TeachingAssistant> expectedTAs = new ArrayList<>();
        TeachingAssistant expTA1 = new TeachingAssistant(Driver.TA_1_UNDERGRAD, Driver.TA_1_NAME, Driver.TA_1_EMAIL);
        TeachingAssistant expTA2 = new TeachingAssistant(Driver.TA_2_UNDERGRAD, Driver.TA_2_NAME, Driver.TA_2_EMAIL);
        expectedTAs.add(expTA1);
        expectedTAs.add(expTA2);
        
        //loaded ta data
        assertEquals(expectedStartHour, data.getStartHour());
        assertEquals(expectedEndHour, data.getEndHour());
        ObservableList<TeachingAssistant> tas = data.getTeachingAssistants();
        for(int i = 0; i < tas.size(); i++) {
            assertEquals(expectedTAs.get(i).isUndergrad(), tas.get(i).isUndergrad());
            assertEquals(expectedTAs.get(i).getName(), tas.get(i).getName());
            assertEquals(expectedTAs.get(i).getEmail(), tas.get(i).getEmail());            
        }
    }
    
    @Test
    public void testRecitationDataTab() {
        //hard-coded recitations
        List<Recitation> expectedRecitations = new ArrayList<>();
        Recitation expectedRecitation1 = new Recitation(Driver.RECITATION_1_SECTION, Driver.RECITATION_1_INSTRUCTOR, Driver.RECITATION_1_DAYTIME, Driver.RECITATION_1_LOCATION, Driver.RECITATION_1_TA_1, Driver.RECITATION_1_TA_2);
        Recitation expectedRecitation2 = new Recitation(Driver.RECITATION_2_SECTION, Driver.RECITATION_2_INSTRUCTOR, Driver.RECITATION_2_DAYTIME, Driver.RECITATION_2_LOCATION, Driver.RECITATION_2_TA_1, Driver.RECITATION_2_TA_2);
        Recitation expectedRecitation3 = new Recitation(Driver.RECITATION_3_SECTION, Driver.RECITATION_3_INSTRUCTOR, Driver.RECITATION_3_DAYTIME, Driver.RECITATION_3_LOCATION, Driver.RECITATION_3_TA_1, Driver.RECITATION_3_TA_2);
        expectedRecitations.add(expectedRecitation1); 
        expectedRecitations.add(expectedRecitation2);
        expectedRecitations.add(expectedRecitation3);
        
        //loaded recitations
        ObservableList<Recitation> recitations = data.getRecitations();
        for(int i = 0; i < recitations.size(); i++) {
            assertEquals(expectedRecitations.get(i).getSection(), recitations.get(i).getSection());
            assertEquals(expectedRecitations.get(i).getInstructor(), recitations.get(i).getInstructor());
            assertEquals(expectedRecitations.get(i).getDayTime(), recitations.get(i).getDayTime());
            assertEquals(expectedRecitations.get(i).getLocation(), recitations.get(i).getLocation());
            assertEquals(expectedRecitations.get(i).getTa1(), recitations.get(i).getTa1());
            assertEquals(expectedRecitations.get(i).getTa2(), recitations.get(i).getTa2());
        }
    }
    
    @Test
    public void testScheduleDataTab() {
        //hard-coded schedules
        LocalDate expectedStartingMonday = LocalDate.parse(Driver.STARTING_MONDAY);
        LocalDate expectedEndingFriday = LocalDate.parse(Driver.ENDING_FRIDAY);
        List<Schedule> expectedSchedules = new ArrayList<>();
        Schedule schedule1 = new Schedule(Driver.SCHEDULE_1_TYPE, LocalDate.parse(Driver.SCHEDULE_1_DATE), Driver.SCHEDULE_1_TITLE, Driver.SCHEDULE_1_TOPIC, Driver.SCHEDULE_1_LINK, Driver.SCHEDULE_1_TIME, Driver.SCHEDULE_1_CRITERIA);
        Schedule schedule2 = new Schedule(Driver.SCHEDULE_2_TYPE, LocalDate.parse(Driver.SCHEDULE_2_DATE), Driver.SCHEDULE_2_TITLE, Driver.SCHEDULE_2_TOPIC, Driver.SCHEDULE_2_LINK, Driver.SCHEDULE_2_TIME, Driver.SCHEDULE_2_CRITERIA);
        Schedule schedule3 = new Schedule(Driver.SCHEDULE_3_TYPE, LocalDate.parse(Driver.SCHEDULE_3_DATE), Driver.SCHEDULE_3_TITLE, Driver.SCHEDULE_3_TOPIC, Driver.SCHEDULE_3_LINK, Driver.SCHEDULE_3_TIME, Driver.SCHEDULE_3_CRITERIA);
        Schedule schedule4 = new Schedule(Driver.SCHEDULE_4_TYPE, LocalDate.parse(Driver.SCHEDULE_4_DATE), Driver.SCHEDULE_4_TITLE, Driver.SCHEDULE_4_TOPIC, Driver.SCHEDULE_4_LINK, Driver.SCHEDULE_4_TIME, Driver.SCHEDULE_4_CRITERIA);
        Schedule schedule5 = new Schedule(Driver.SCHEDULE_5_TYPE, LocalDate.parse(Driver.SCHEDULE_5_DATE), Driver.SCHEDULE_5_TITLE, Driver.SCHEDULE_5_TOPIC, Driver.SCHEDULE_5_LINK, Driver.SCHEDULE_5_TIME, Driver.SCHEDULE_5_CRITERIA);
        expectedSchedules.add(schedule1);
        expectedSchedules.add(schedule2);
        expectedSchedules.add(schedule3);
        expectedSchedules.add(schedule4);
        expectedSchedules.add(schedule5);
        
        //loaded schedules
        assertEquals(expectedStartingMonday.getMonthValue(), data.getStartingMonday().getMonthValue());
        assertEquals(expectedStartingMonday.getDayOfMonth(), data.getStartingMonday().getDayOfMonth());
        assertEquals(expectedEndingFriday.getMonthValue(), data.getEndingFriday().getMonthValue());
        assertEquals(expectedEndingFriday.getDayOfMonth(), data.getEndingFriday().getDayOfMonth());
        ObservableList<Schedule> schedules = data.getSchedules();
        for(int i = 0; i < schedules.size(); i++) {
            assertEquals(expectedSchedules.get(i).getType(), schedules.get(i).getType());
            assertEquals(expectedSchedules.get(i).getDate().getMonthValue(), schedules.get(i).getDate().getMonthValue());
            assertEquals(expectedSchedules.get(i).getDate().getDayOfMonth(), schedules.get(i).getDate().getDayOfMonth());
            assertEquals(expectedSchedules.get(i).getTitle(), schedules.get(i).getTitle());
            assertEquals(expectedSchedules.get(i).getTopic(), schedules.get(i).getTopic());
            assertEquals(expectedSchedules.get(i).getLink(), schedules.get(i).getLink());
            assertEquals(expectedSchedules.get(i).getTime(), schedules.get(i).getTime());
            assertEquals(expectedSchedules.get(i).getCriteria(), schedules.get(i).getCriteria());
        }

    }
    
    @Test
    public void testProjectDataTab() {
        //hard-coded teams & students
        List<Team> expectedTeams = new ArrayList<>();
        Team team1 = new Team(Driver.TEAM_1_NAME, Color.color((double)Driver.TEAM_1_COLOR_RED/255, (double)Driver.TEAM_1_COLOR_GREEN/255, (double)Driver.TEAM_1_COLOR_BLUE/255), Color.color((double)Driver.TEAM_1_TEXTCOLOR_RED/255, (double)Driver.TEAM_1_TEXTCOLOR_GREEN/255, (double)Driver.TEAM_1_TEXTCOLOR_BLUE/255), Driver.TEAM_1_LINK);
        Team team2 = new Team(Driver.TEAM_2_NAME, Color.color((double)Driver.TEAM_2_COLOR_RED/255, (double)Driver.TEAM_2_COLOR_GREEN/255, (double)Driver.TEAM_2_COLOR_BLUE/255), Color.color((double)Driver.TEAM_2_TEXTCOLOR_RED/255, (double)Driver.TEAM_2_TEXTCOLOR_GREEN/255, (double)Driver.TEAM_2_TEXTCOLOR_BLUE/255), Driver.TEAM_2_LINK);
        expectedTeams.add(team1);
        expectedTeams.add(team2);
        List<Student> expectedStudents = new ArrayList<>();
        Student student1 = new Student(Driver.STUDENT_1_FIRSTNAME, Driver.STUDENT_1_LASTNAME, Driver.STUDENT_1_TEAM, Driver.STUDENT_1_ROLE);
        Student student2 = new Student(Driver.STUDENT_2_FIRSTNAME, Driver.STUDENT_2_LASTNAME, Driver.STUDENT_2_TEAM, Driver.STUDENT_2_ROLE);
        expectedStudents.add(student1);
        expectedStudents.add(student2);
        
        //loaded teams & students
        ObservableList<Team> teams = data.getTeams();
        for(int i = 0; i < teams.size(); i++) {
            assertEquals(expectedTeams.get(i).getName(), teams.get(i).getName());
            assertEquals(expectedTeams.get(i).getColor(), teams.get(i).getColor());
            assertEquals(expectedTeams.get(i).getTextColor(), teams.get(i).getTextColor());
            assertEquals(expectedTeams.get(i).getLink(), teams.get(i).getLink());
        }        
        ObservableList<Student> students = data.getStudents();
        for(int i = 0; i < students.size(); i++) {
            assertEquals(expectedStudents.get(i).getFirstName(), students.get(i).getFirstName());
            assertEquals(expectedStudents.get(i).getLastName(), students.get(i).getLastName());
            assertEquals(expectedStudents.get(i).getTeam(), students.get(i).getTeam());
            assertEquals(expectedStudents.get(i).getRole(), students.get(i).getRole());
        }
    }
    
}
