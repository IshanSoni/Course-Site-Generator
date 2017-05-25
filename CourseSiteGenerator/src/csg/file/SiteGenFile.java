package csg.file;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import djf.components.AppDataComponent;
import djf.components.AppFileComponent;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javafx.collections.ObservableList;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonWriter;
import javax.json.JsonWriterFactory;
import javax.json.stream.JsonGenerator;
import jtps.jTPS;
import csg.SiteGenApp;
import csg.data.Page;
import csg.data.Recitation;
import csg.data.Schedule;
import csg.data.SiteGenData;
import csg.data.Student;
import csg.data.TeachingAssistant;
import csg.data.Team;
import csg.workspace.SiteGenWorkspace;
import static djf.settings.AppStartupConstants.PATH_EXPORT;
import java.io.File;
import java.math.BigDecimal;
import java.nio.file.Files;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.time.LocalDate;
import java.time.Month;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import javax.json.JsonObjectBuilder;
import org.apache.commons.io.FileUtils;

/**
 * This class serves as the file component for the TA
 * manager app. It provides all saving and loading 
 * services for the application.
 * 
 * @author Richard McKenna
 */
public class SiteGenFile implements AppFileComponent {
    // THIS IS THE APP ITSELF
    SiteGenApp app;
    
    //COURSE DETAIL TAB
    static final String JSON_COURSE_INFO = "course_info";
    static final String JSON_SUBJECT = "subject";
    static final String JSON_NUMBER = "number";
    static final String JSON_SEMESTER = "semester";
    static final String JSON_YEAR = "year";
    static final String JSON_INSTRUCTOR_NAME = "instructor_name";
    static final String JSON_INSTRUCTOR_HOME = "instructor_home";
    static final String JSON_EXPORT_DIR = "export_dir";
    static final String JSON_SITE_TEMPLATE = "site_template";
    static final String JSON_TEMPLATE_DIR = "template_dir";
    static final String JSON_PAGES = "pages";
    static final String JSON_USE = "use";
    static final String JSON_NAVBAR_TITLE = "navbar_title";
    static final String JSON_PAGE_STYLE = "page_style";
    static final String JSON_BANNER_IMG_DIR = "banner_img_dir";
    static final String JSON_LEFT_FOOTER_IMG_DIR = "left_footer_img_dir";
    static final String JSON_RIGHT_FOOTER_IMG_DIR = "right_footer_img_dir";
    static final String JSON_STYLESHEET = "stylesheet";
    
    //TA DATA TAB
    static final String JSON_START_HOUR = "startHour";
    static final String JSON_END_HOUR = "endHour";
    static final String JSON_OFFICE_HOURS = "officeHours";
    static final String JSON_DAY = "day";
    static final String JSON_TIME = "time";
    static final String JSON_NAME = "name";
    static final String JSON_UNDERGRAD_TAS = "undergrad_tas";
    static final String JSON_GRAD_TAS = "grad_tas";
    static final String JSON_EMAIL = "email";
    
    //RECITATION DATA TAB
    static final String JSON_RECITATIONS_TAB = "recitations_tab";
    static final String JSON_SECTION = "section";
    static final String JSON_INSTRUCTOR = "instructor";
    static final String JSON_DAY_TIME = "day_time";
    static final String JSON_LOCATION = "location";
    static final String JSON_TA_1 = "ta_1";
    static final String JSON_TA_2 = "ta_2";
    
    //SCHEDULE DATA TAB
    static final String JSON_STARTINGMONDAYMONTH = "startingMondayMonth";
    static final String JSON_STARTINGMONDAYDAY = "startingMondayDay";
    static final String JSON_ENDINGFRIDAYMONTH = "endingFridayMonth";
    static final String JSON_ENDINGFRIDAYDAY = "endingFridayDay";
    static final String JSON_HOLIDAYS = "holidays";
    static final String JSON_LECTURES = "lectures";
    static final String JSON_RECITATIONS = "recitations";
    static final String JSON_REFERENCES = "references";
    static final String JSON_HWS = "hws";  //
    static final String JSON_MONTH = "month";
    static final String JSON_TITLE = "title";
    static final String JSON_LINK = "link";
    static final String JSON_TOPIC = "topic";
    static final String JSON_CRITERIA = "criteria";
    
    //PROJECT DATA TAB
    static final String JSON_TEAMS = "teams";
    static final String JSON_RED = "red";
    static final String JSON_GREEN = "green";
    static final String JSON_BLUE = "blue";
    static final String JSON_TEXT_RED = "text_red";
    static final String JSON_TEXT_GREEN = "text_green";
    static final String JSON_TEXT_BLUE = "text_blue";
    static final String JSON_STUDENTS = "students";
    static final String JSON_FIRST_NAME = "firstName";
    static final String JSON_LAST_NAME = "lastName";
    static final String JSON_TEAM = "team";
    static final String JSON_ROLE = "role";
    
    
    public SiteGenFile(SiteGenApp initApp) {
        app = initApp;
    }

    @Override
    public void loadData(AppDataComponent data, String filePath) throws IOException {
	// CLEAR THE OLD DATA OUT
	SiteGenData dataManager = (SiteGenData)data;

	// LOAD THE JSON FILE WITH ALL THE DATA
	JsonObject file = loadJSONFile(filePath);
        if(app.getWorkspaceComponent() != null) 
        app.getWorkspaceComponent().reloadWorkspace(app.getDataComponent());
	
        loadCourseDetails(dataManager, file);
        loadTAData(dataManager, file);
        loadScheduleData(dataManager, file);
        loadRecitationData(dataManager, file);
        loadProjectData(dataManager, file);
        
        if(app.getWorkspaceComponent() != null)  {
        SiteGenWorkspace workspace = (SiteGenWorkspace) app.getWorkspaceComponent();
        workspace.getController().setJtps(new jTPS()); }
    }
      
    // HELPER METHOD FOR LOADING DATA FROM A JSON FORMAT
    public JsonObject loadJSONFile(String jsonFilePath) throws IOException {
	InputStream is = new FileInputStream(jsonFilePath);
	JsonReader jsonReader = Json.createReader(is);
	JsonObject json = jsonReader.readObject();
	jsonReader.close();
	is.close();
	return json;
    }
    
    public void loadCourseDetails(SiteGenData data, JsonObject file) {
        //COURSE INFO SECTION
        JsonObject courseData = file.getJsonObject(JSON_COURSE_INFO);
        String subject = courseData.getString(JSON_SUBJECT);
        int number = courseData.getInt(JSON_NUMBER);
        String semester = courseData.getString(JSON_SEMESTER);
        int year = courseData.getInt(JSON_YEAR);
        String title = courseData.getString(JSON_TITLE);
        String instructorName = courseData.getString(JSON_INSTRUCTOR_NAME);
        String instructorHome = courseData.getString(JSON_INSTRUCTOR_HOME);
        String eportDir = courseData.getString(JSON_EXPORT_DIR);
        data.setSubject(subject);
        data.setNumber(number);
        data.setSemester(semester);
        data.setYear(year);
        data.setTitle(title);
        data.setInstructorName(instructorName);
        data.setInstructorHome(instructorHome);
        data.setExportDir(eportDir);
        
        //SITE TEMPLATE SECTION
        JsonObject siteTemplateData = file.getJsonObject(JSON_SITE_TEMPLATE);
        String templateDir = siteTemplateData.getString(JSON_TEMPLATE_DIR);
        data.setTemplateDir(templateDir);
        JsonArray pageArray = siteTemplateData.getJsonArray(JSON_PAGES);
        for(int i = 0; i < pageArray.size(); i++) {
            JsonObject page = pageArray.getJsonObject(i);
            String navbarTitle = page.getString(JSON_NAVBAR_TITLE);
            data.addPage(navbarTitle, page.getBoolean(JSON_USE));
        }
        
        //PAGE STYLE SECTION
        JsonObject styleData = file.getJsonObject(JSON_PAGE_STYLE);
        String bannerImgDir = styleData.getString(JSON_BANNER_IMG_DIR);
        String leftFooterImgDir = styleData.getString(JSON_LEFT_FOOTER_IMG_DIR);
        String rightFooterImgDir = styleData.getString(JSON_RIGHT_FOOTER_IMG_DIR);
        String styleSheet = styleData.getString(JSON_STYLESHEET);
        data.setBannerImgDir(bannerImgDir);
        data.setLeftFooterImgDir(leftFooterImgDir);
        data.setRightFooterImgDir(rightFooterImgDir);
        data.setStyleSheet(styleSheet); 
        
        if(app.getWorkspaceComponent() != null) {
        SiteGenWorkspace workspace = (SiteGenWorkspace) app.getWorkspaceComponent();
        workspace.getCourseDetTab().initDetails(); }
    }
    
    public void loadTAData(SiteGenData data, JsonObject file) {
        // LOAD THE START AND END HOURS
	String startHour = file.getString(JSON_START_HOUR);
        String endHour = file.getString(JSON_END_HOUR);
        data.initHours(startHour, endHour);

        // NOW LOAD ALL THE UNDERGRAD TAs
        JsonArray jsonUndergradTAArray = file.getJsonArray(JSON_UNDERGRAD_TAS);
        for (int i = 0; i < jsonUndergradTAArray.size(); i++) {
            JsonObject jsonTA = jsonUndergradTAArray.getJsonObject(i);
            String name = jsonTA.getString(JSON_NAME);
            String email = jsonTA.getString(JSON_EMAIL);
            data.addTA(true, name, email);
        }
        
        JsonArray jsonGradTAArray = file.getJsonArray(JSON_GRAD_TAS);
        for (int i = 0; i < jsonGradTAArray.size(); i++) {
            JsonObject jsonTA = jsonGradTAArray.getJsonObject(i);
            String name = jsonTA.getString(JSON_NAME);
            String email = jsonTA.getString(JSON_EMAIL);
            data.addTA(false, name, email);
        }

        // AND THEN ALL THE OFFICE HOURS
        JsonArray jsonOfficeHoursArray = file.getJsonArray(JSON_OFFICE_HOURS);
        for (int i = 0; i < jsonOfficeHoursArray.size(); i++) {
            JsonObject jsonOfficeHours = jsonOfficeHoursArray.getJsonObject(i);
            String day = jsonOfficeHours.getString(JSON_DAY);
            String time = jsonOfficeHours.getString(JSON_TIME);
            String name = jsonOfficeHours.getString(JSON_NAME);
            data.addOfficeHoursReservation(day, time, name);
        }
    }
    
    public void loadRecitationData(SiteGenData data, JsonObject file) {
        JsonArray recitationArray = file.getJsonArray(JSON_RECITATIONS_TAB);
        for(int i = 0; i < recitationArray.size(); i++) {
            JsonObject recitation = recitationArray.getJsonObject(i);
            String section = recitation.getString(JSON_SECTION);
            String instructor = recitation.getString(JSON_INSTRUCTOR);
            String dayTime = recitation.getString(JSON_DAY_TIME);
            String location = recitation.getString(JSON_LOCATION);
            String ta1 = recitation.getString(JSON_TA_1);
            String ta2 = recitation.getString(JSON_TA_2);
            
            data.addRecitation(section, instructor, dayTime, location, ta1, ta2);       
        }
    }
    
    public void loadScheduleData(SiteGenData data, JsonObject file) {
        //STARTING AND ENDING CONSTANTS
        int startingMondayMonth = file.getInt(JSON_STARTINGMONDAYMONTH);
        int startingMondayDay = file.getInt(JSON_STARTINGMONDAYDAY);
        int endingFridayMonth = file.getInt(JSON_ENDINGFRIDAYMONTH);
        int endingFridayDay = file.getInt(JSON_ENDINGFRIDAYDAY);
        LocalDate startingMonday = LocalDate.of(data.getYear(), startingMondayMonth, startingMondayDay);
        LocalDate endingFriday = LocalDate.of(data.getYear(), endingFridayMonth, endingFridayDay);
        
        data.initScheduleConstraints(startingMonday, endingFriday);
        
        //HOLIDAY ARRAY
        JsonArray holidayArray = file.getJsonArray(JSON_HOLIDAYS);
        for(int i = 0; i < holidayArray.size(); i++) {
            JsonObject holiday = holidayArray.getJsonObject(i);
            int month = holiday.getInt(JSON_MONTH);
            int day = holiday.getInt(JSON_DAY);
            LocalDate date = LocalDate.of(data.getYear(), month, day);
            String title = holiday.getString(JSON_TITLE);
            String topic = holiday.getString(JSON_TOPIC);
            String link = holiday.getString(JSON_LINK);
            String time = holiday.getString(JSON_TIME);
            String criteria = holiday.getString(JSON_CRITERIA);
            
            data.addSchedule("Holiday", date, title, topic, link, time, criteria);
        }
        
        //LECTURE ARRAY
        JsonArray lectureArray = file.getJsonArray(JSON_LECTURES);
        for(int i = 0; i < lectureArray.size(); i++) {
            JsonObject lecture = lectureArray.getJsonObject(i);
            int month = lecture.getInt(JSON_MONTH);
            int day = lecture.getInt(JSON_DAY);
            LocalDate date = LocalDate.of(data.getYear(), month, day);
            String title = lecture.getString(JSON_TITLE);
            String topic = lecture.getString(JSON_TOPIC);
            String link = lecture.getString(JSON_LINK);
            String time = lecture.getString(JSON_TIME);
            String criteria = lecture.getString(JSON_CRITERIA);
            
            data.addSchedule("Lecture", date, title, topic, link, time, criteria);
        }
        
        //REFERENCE ARRAY
        JsonArray referenceArray = file.getJsonArray(JSON_REFERENCES);
        for(int i = 0; i < referenceArray.size(); i++) {
            JsonObject reference = referenceArray.getJsonObject(i);
            int month = reference.getInt(JSON_MONTH);
            int day = reference.getInt(JSON_DAY);
            LocalDate date = LocalDate.of(data.getYear(), month, day);
            String title = reference.getString(JSON_TITLE);
            String topic = reference.getString(JSON_TOPIC);
            String link = reference.getString(JSON_LINK);
            String time = reference.getString(JSON_TIME);
            String criteria = reference.getString(JSON_CRITERIA);
            
            data.addSchedule("Reference", date, title, topic, link, time, criteria);
        }
        
        //RECITATION ARRAY
        JsonArray recitationArray = file.getJsonArray(JSON_RECITATIONS);
        for(int i = 0; i < recitationArray.size(); i++) {
            JsonObject recitation = recitationArray.getJsonObject(i);
            int month = recitation.getInt(JSON_MONTH);
            int day = recitation.getInt(JSON_DAY);
            LocalDate date = LocalDate.of(data.getYear(), month, day);
            String title = recitation.getString(JSON_TITLE);
            String topic = recitation.getString(JSON_TOPIC);
            String link = recitation.getString(JSON_LINK);
            String time = recitation.getString(JSON_TIME);
            String criteria = recitation.getString(JSON_CRITERIA);
            
            data.addSchedule("Recitation", date, title, topic, link, time, criteria);
        }
        
        //HW ARRAY
        JsonArray hwArray = file.getJsonArray(JSON_HWS);
        for(int i = 0; i < hwArray.size(); i++) {
            JsonObject hw = hwArray.getJsonObject(i);
            int month = hw.getInt(JSON_MONTH);
            int day = hw.getInt(JSON_DAY);
            LocalDate date = LocalDate.of(data.getYear(), month, day);
            String title = hw.getString(JSON_TITLE);
            String topic = hw.getString(JSON_TOPIC);
            String link = hw.getString(JSON_LINK);
            String time = hw.getString(JSON_TIME);
            String criteria = hw.getString(JSON_CRITERIA);
            
            data.addSchedule("Hw", date, title, topic, link, time, criteria); 
        }
    }
    
    public void loadProjectData(SiteGenData data, JsonObject file) {
        JsonArray teamArray = file.getJsonArray(JSON_TEAMS);
        for(int i = 0; i < teamArray.size(); i++) {
            JsonObject team = teamArray.getJsonObject(i);
            String name = team.getString(JSON_NAME);
            int red = team.getInt(JSON_RED);
            int green = team.getInt(JSON_GREEN);
            int blue = team.getInt(JSON_BLUE);
            int red_text = team.getInt(JSON_TEXT_RED);
            int green_text = team.getInt(JSON_TEXT_GREEN);
            int blue_text = team.getInt(JSON_TEXT_BLUE);
            String link = team.getString(JSON_LINK);
            
            Color color = Color.color((double)red/255, (double)green/255, (double)blue/255);
            Color textColor = Color.color((double)red_text/255, (double)green_text/255, (double)blue_text/255);
            
            data.addTeam(name, color, textColor, link);
        }
        
        JsonArray studentArray = file.getJsonArray(JSON_STUDENTS);
        for(int i = 0; i < studentArray.size(); i++) {
            JsonObject student = studentArray.getJsonObject(i);
            String firstName = student.getString(JSON_FIRST_NAME);
            String lastName = student.getString(JSON_LAST_NAME);
            String team = student.getString(JSON_TEAM);
            String role = student.getString(JSON_ROLE);
            
            data.addStudent(firstName, lastName, team, role);
        }
    }
    

    @Override
    public void saveData(AppDataComponent data, String filePath) throws IOException {
	// GET THE DATA
	SiteGenData dataManager = (SiteGenData)data;
        
        JsonObject courseDetails = saveCourseDetails(dataManager);
        JsonObject taManagerJSO = saveTAData(dataManager);
	JsonObject recitationManagerJSO = saveRecitationData(dataManager);
        JsonObject scheduleManagerJSO = saveScheduleData(dataManager);
        JsonObject projectManagerJSO = saveProjectData(dataManager);
        
        JsonObjectBuilder merger = Json.createObjectBuilder();
        courseDetails.entrySet().forEach(s -> merger.add(s.getKey(), s.getValue()));
        taManagerJSO.entrySet().forEach(s -> merger.add(s.getKey(), s.getValue()));
        recitationManagerJSO.entrySet().forEach(s -> merger.add(s.getKey(), s.getValue()));
	scheduleManagerJSO.entrySet().forEach(s -> merger.add(s.getKey(), s.getValue()));
        projectManagerJSO.entrySet().forEach(s -> merger.add(s.getKey(), s.getValue()));
        
        JsonObject dataManagerJSO = merger.build();
	
	// AND NOW OUTPUT IT TO A JSON FILE WITH PRETTY PRINTING
	Map<String, Object> properties = new HashMap<>(1);
	properties.put(JsonGenerator.PRETTY_PRINTING, true);
	JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
	StringWriter sw = new StringWriter();
	JsonWriter jsonWriter = writerFactory.createWriter(sw);
	jsonWriter.writeObject(dataManagerJSO);
	jsonWriter.close();

	// INIT THE WRITER
	OutputStream os = new FileOutputStream(filePath);
	JsonWriter jsonFileWriter = Json.createWriter(os);
	jsonFileWriter.writeObject(dataManagerJSO);
	String prettyPrinted = sw.toString();
	PrintWriter pw = new PrintWriter(filePath);
	pw.write(prettyPrinted);
	pw.close();
    }
    
    private JsonObject saveCourseDetails(SiteGenData data) {
        SiteGenWorkspace workspace = (SiteGenWorkspace) app.getWorkspaceComponent();
        workspace.getCourseDetTab().saveDetails(); 
        
        JsonObject courseInfos = Json.createObjectBuilder()
                .add(JSON_SUBJECT, data.getSubject())
                .add(JSON_NUMBER, data.getNumber())
                .add(JSON_SEMESTER, data.getSemester())
                .add(JSON_YEAR, data.getYear())
                .add(JSON_TITLE, data.getTitle())
                .add(JSON_INSTRUCTOR_NAME, data.getInstructorName())
                .add(JSON_INSTRUCTOR_HOME, data.getInstructorHome())
                .add(JSON_EXPORT_DIR, data.getExportDir())
                .build();
        
        ObservableList<Page> pages = data.getPages();
        JsonArrayBuilder pagesBuilder = Json.createArrayBuilder();
        for(Page p : pages) {
            JsonObject page = Json.createObjectBuilder()
                    .add(JSON_USE, p.isUseToggled())
                    .add(JSON_NAVBAR_TITLE, p.getNavbarTitle())
                    .build();
            pagesBuilder.add(page);
        }
        JsonArray pageArray = pagesBuilder.build();
        
        JsonObject siteTemplates = Json.createObjectBuilder()
                .add(JSON_TEMPLATE_DIR, data.getTemplateDir())
                .add(JSON_PAGES, pageArray)
                .build();
        
        JsonObject pageStyles = Json.createObjectBuilder()
                .add(JSON_BANNER_IMG_DIR, data.getBannerImgDir())
                .add(JSON_LEFT_FOOTER_IMG_DIR, data.getLeftFooterImgDir())
                .add(JSON_RIGHT_FOOTER_IMG_DIR, data.getRightFooterImgDir())
                .add(JSON_STYLESHEET, data.getStyleSheet())
                .build();
        
        JsonObject courseDetailData = Json.createObjectBuilder()
                .add(JSON_COURSE_INFO, courseInfos)
                .add(JSON_SITE_TEMPLATE, siteTemplates)
                .add(JSON_PAGE_STYLE, pageStyles)
                .build();
        
        return courseDetailData;
    }
    
    private JsonObject saveTAData(SiteGenData dataManager) {
        JsonArrayBuilder undergradTaArrayBuilder = Json.createArrayBuilder();
	ObservableList<TeachingAssistant> underGradTas = dataManager.getUnderGradTeachingAssistants();
	for (TeachingAssistant ta : underGradTas) {	    
	    JsonObject undergradTaJson = Json.createObjectBuilder()
		    .add(JSON_NAME, ta.getName())
		    .add(JSON_EMAIL, ta.getEmail()).build();
	    undergradTaArrayBuilder.add(undergradTaJson);
	}
	JsonArray undergradTAsArray = undergradTaArrayBuilder.build();
        
        JsonArrayBuilder gradTaArrayBuilder = Json.createArrayBuilder();
	ObservableList<TeachingAssistant> gradTas = dataManager.getGradTeachingAssistants();
	for (TeachingAssistant ta : gradTas) {	    
	    JsonObject gradTaJson = Json.createObjectBuilder()
		    .add(JSON_NAME, ta.getName())
		    .add(JSON_EMAIL, ta.getEmail()).build();
	    gradTaArrayBuilder.add(gradTaJson);
	}
	JsonArray gradTAsArray = gradTaArrayBuilder.build();

	// NOW BUILD THE TIME SLOT JSON OBJCTS TO SAVE
	JsonArrayBuilder timeSlotArrayBuilder = Json.createArrayBuilder();
	ArrayList<TimeSlot> officeHours = TimeSlot.buildOfficeHoursList(dataManager);
	for (TimeSlot ts : officeHours) {	    
	    JsonObject tsJson = Json.createObjectBuilder()
		    .add(JSON_DAY, ts.getDay())
		    .add(JSON_TIME, ts.getTime())
		    .add(JSON_NAME, ts.getName()).build();
	    timeSlotArrayBuilder.add(tsJson);
	}
	JsonArray timeSlotsArray = timeSlotArrayBuilder.build();
        
	// THEN PUT IT ALL TOGETHER IN A JsonObject
	JsonObject dataManagerJSO = Json.createObjectBuilder()
		.add(JSON_START_HOUR, "" + dataManager.getStartHour())
		.add(JSON_END_HOUR, "" + dataManager.getEndHour())
                .add(JSON_UNDERGRAD_TAS, undergradTAsArray)
                .add(JSON_GRAD_TAS, gradTAsArray)
                .add(JSON_OFFICE_HOURS, timeSlotsArray)
		.build();
        
        return dataManagerJSO;
    }
    
    private JsonObject saveRecitationData(SiteGenData dataManager) {
        
        JsonArrayBuilder recitationArrayBuilder = Json.createArrayBuilder();
        ObservableList<Recitation> recitations = dataManager.getRecitations();
        for (Recitation r : recitations) {	    
	    JsonObject recitationJson = Json.createObjectBuilder()
		    .add(JSON_SECTION, r.getSection())
                    .add(JSON_INSTRUCTOR, r.getInstructor())
		    .add(JSON_DAY_TIME, r.getDayTime())
                    .add(JSON_LOCATION, r.getLocation())
                    .add(JSON_TA_1, r.getTa1())
                    .add(JSON_TA_2, r.getTa2()).build();
	    recitationArrayBuilder.add(recitationJson);
	}
        JsonArray recitationArray = recitationArrayBuilder.build();
               
        JsonObject recitationManagerJSO = Json.createObjectBuilder()
                .add(JSON_RECITATIONS_TAB, recitationArray)
                .build();
        
        return recitationManagerJSO;
    } 
    
    private JsonObject saveScheduleData(SiteGenData dataManager) {
        
        JsonArrayBuilder holidayArrayBuilder = Json.createArrayBuilder();
        ObservableList<Schedule> holidaySchedule = dataManager.getHolidaySchedules();
        JsonArrayBuilder lectureArrayBuilder = Json.createArrayBuilder();
        ObservableList<Schedule> lectureSchedule = dataManager.getLectureSchedules();
        JsonArrayBuilder referenceArrayBuilder = Json.createArrayBuilder();
        ObservableList<Schedule> referenceSchedule = dataManager.getReferenceSchedules();
        JsonArrayBuilder recitationArrayBuilder = Json.createArrayBuilder();
        ObservableList<Schedule> recitationSchedule = dataManager.getRecitationSchedules();
        JsonArrayBuilder hwArrayBuilder = Json.createArrayBuilder();
        ObservableList<Schedule> hwSchedule = dataManager.getHwSchedules();
        
        for(Schedule s : holidaySchedule) {
            JsonObject scheduleJson = Json.createObjectBuilder()
                    .add(JSON_MONTH, s.getDate().getMonthValue())
                    .add(JSON_DAY, s.getDate().getDayOfMonth())
                    .add(JSON_TITLE, s.getTitle())
                    .add(JSON_TOPIC, s.getTopic())
                    .add(JSON_LINK, s.getLink())
                    .add(JSON_TIME, s.getTime())
                    .add(JSON_CRITERIA, s.getCriteria())
                    .build();
            holidayArrayBuilder.add(scheduleJson);
        }
        JsonArray holidayArray = holidayArrayBuilder.build();
        
        for(Schedule s : lectureSchedule) {
            JsonObject scheduleJson = Json.createObjectBuilder()
                    .add(JSON_MONTH, s.getDate().getMonthValue())
                    .add(JSON_DAY, s.getDate().getDayOfMonth())
                    .add(JSON_TITLE, s.getTitle())
                    .add(JSON_TOPIC, s.getTopic())
                    .add(JSON_LINK, s.getLink())
                    .add(JSON_TIME, s.getTime())
                    .add(JSON_CRITERIA, s.getCriteria())
                    .build();
            lectureArrayBuilder.add(scheduleJson);
        }
        JsonArray lectureArray = lectureArrayBuilder.build();
        
        for(Schedule s : referenceSchedule) {
            JsonObject scheduleJson = Json.createObjectBuilder()
                    .add(JSON_MONTH, s.getDate().getMonthValue())
                    .add(JSON_DAY, s.getDate().getDayOfMonth())
                    .add(JSON_TITLE, s.getTitle())
                    .add(JSON_TOPIC, s.getTopic())
                    .add(JSON_LINK, s.getLink())
                    .add(JSON_TIME, s.getTime())
                    .add(JSON_CRITERIA, s.getCriteria())
                    .build();
            referenceArrayBuilder.add(scheduleJson);
        }
        JsonArray referenceArray = referenceArrayBuilder.build();
        
        for(Schedule s : recitationSchedule) {
            JsonObject scheduleJson = Json.createObjectBuilder()
                    .add(JSON_MONTH, s.getDate().getMonthValue())
                    .add(JSON_DAY, s.getDate().getDayOfMonth())
                    .add(JSON_TITLE, s.getTitle())
                    .add(JSON_TOPIC, s.getTopic())
                    .add(JSON_LINK, s.getLink())
                    .add(JSON_TIME, s.getTime())
                    .add(JSON_CRITERIA, s.getCriteria())
                    .build();
            recitationArrayBuilder.add(scheduleJson);
        }
        JsonArray recitationArray = recitationArrayBuilder.build();
        
        for(Schedule s : hwSchedule) {
            JsonObject scheduleJson = Json.createObjectBuilder()
                    .add(JSON_MONTH, s.getDate().getMonthValue())
                    .add(JSON_DAY, s.getDate().getDayOfMonth())
                    .add(JSON_TITLE, s.getTitle())
                    .add(JSON_TOPIC, s.getTopic())
                    .add(JSON_LINK, s.getLink())
                    .add(JSON_TIME, s.getTime())
                    .add(JSON_CRITERIA, s.getCriteria())
                    .build();
            hwArrayBuilder.add(scheduleJson);
        }
        JsonArray hwArray = hwArrayBuilder.build();
        
        JsonObject scheduleManagerJSO = Json.createObjectBuilder()
                .add(JSON_STARTINGMONDAYMONTH, dataManager.getStartingMonday().getMonthValue())
                .add(JSON_STARTINGMONDAYDAY, dataManager.getStartingMonday().getDayOfMonth())
                .add(JSON_ENDINGFRIDAYMONTH, dataManager.getEndingFriday().getMonthValue())
                .add(JSON_ENDINGFRIDAYDAY, dataManager.getEndingFriday().getDayOfMonth())
                .add(JSON_HOLIDAYS, holidayArray)
                .add(JSON_LECTURES, lectureArray)
                .add(JSON_REFERENCES, referenceArray)
                .add(JSON_RECITATIONS, recitationArray)
                .add(JSON_HWS, hwArray)
                .build();
        
        return scheduleManagerJSO;
    }
    
    private JsonObject saveProjectData(SiteGenData dataManager) {
        JsonArrayBuilder teamArrayBuilder = Json.createArrayBuilder();
        ObservableList<Team> teams = dataManager.getTeams();
        JsonArrayBuilder studentArrayBuilder = Json.createArrayBuilder();
        ObservableList<Student> students = dataManager.getStudents();
        
        for(Team t : teams) {
            JsonObject team = Json.createObjectBuilder()
                    .add(JSON_NAME, t.getName())
                    .add(JSON_RED, (int)(t.getColorInstance().getRed()*255))
                    .add(JSON_GREEN, (int)(t.getColorInstance().getGreen()*255))
                    .add(JSON_BLUE, (int)(t.getColorInstance().getBlue()*255))
                    .add(JSON_TEXT_RED, (int)(t.getTextColorInstance().getRed()*255))
                    .add(JSON_TEXT_GREEN, (int)(t.getTextColorInstance().getGreen()*255))
                    .add(JSON_TEXT_BLUE, (int)(t.getTextColorInstance().getBlue()*255))
                    .add(JSON_LINK, t.getLink())
                    .build();
            teamArrayBuilder.add(team);
        }
        JsonArray teamArray = teamArrayBuilder.build();
        
        for(Student s : students) {
            JsonObject student = Json.createObjectBuilder()
                    .add(JSON_FIRST_NAME, s.getFirstName())
                    .add(JSON_LAST_NAME, s.getLastName())
                    .add(JSON_TEAM, s.getTeam())
                    .add(JSON_ROLE, s.getRole())
                    .build();
            studentArrayBuilder.add(student);
        }
        JsonArray studentArray = studentArrayBuilder.build();
        
        JsonObject projectDataJSO = Json.createObjectBuilder()
                .add(JSON_TEAMS, teamArray)
                .add(JSON_STUDENTS, studentArray)
                .build();
        
        return projectDataJSO;
    }
    
    // IMPORTING/EXPORTING DATA IS USED WHEN WE READ/WRITE DATA IN AN
    // ADDITIONAL FORMAT USEFUL FOR ANOTHER PURPOSE, LIKE ANOTHER APPLICATION

    @Override
    public void importData(AppDataComponent data, String filePath) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void exportData(AppDataComponent d) throws IOException {
        SiteGenData data = (SiteGenData) d;
           
        //DirectoryChooser load = new DirectoryChooser();
        File fromDir = new File(data.getTemplateDir());
        //load.setInitialDirectory(fromDir);
        
        //DirectoryChooser save = new DirectoryChooser();
        File toDir = new File(data.getExportDir());
        //save.s
        FileUtils.copyDirectory(fromDir, toDir);
        
        // SAVE THE DATA FILE TO EXPORT DIRECTORY
        File selectedFile = new File(data.getExportDir()+"/js/Data.json");
        saveData(app.getDataComponent(), selectedFile.getPath());
        
        // SAVE CSS FILES TO EXPORT DIRECTORY IN CSS FOLDER
        File cssDir = new File("./work/css/");
        File[] cssFiles = cssDir.listFiles();
        for(File f : cssFiles) {
            if(f.isFile()) {
                FileUtils.copyFileToDirectory(f, new File(data.getExportDir()+"/css/"));
            }
        }
           
        FileUtils.copyFileToDirectory(new File(data.getBannerImgDir()), new File(data.getExportDir()+"/images/"));
        FileUtils.copyFileToDirectory(new File(data.getLeftFooterImgDir()), new File(data.getExportDir()+"/images/"));
        FileUtils.copyFileToDirectory(new File(data.getRightFooterImgDir()), new File(data.getExportDir()+"/images/"));
        
    
//        if(toDir != null)
//            save.setInitialDirectory(toDir);
//        else return;
//        
//        FileUtils.copyDirectory(fromDir, toDir);
    }
    
}