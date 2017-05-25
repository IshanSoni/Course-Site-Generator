package csg.data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import djf.components.AppDataComponent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import javafx.beans.property.StringProperty;
import properties_manager.PropertiesManager;
import csg.SiteGenApp;
import csg.SiteGenProp;
import csg.workspace.ScheduleWorkspace;
import csg.workspace.SiteGenWorkspace;
import java.io.File;
import java.time.LocalDate;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.paint.Color;

/**
 * This is the data component for TAManagerApp. It has all the data needed to be
 * set by the user via the User Interface and file I/O can set and get all the
 * data from this object
 *
 * @author Richard McKenna
 */
public class SiteGenData implements AppDataComponent {

    // WE'LL NEED ACCESS TO THE APP TO NOTIFY THE GUI WHEN DATA CHANGES
    SiteGenApp app;

    //COURSE DETAILS
    String subject;
    int number;
    String semester;
    int year;
    String title;
    String instructorName;
    String instructorHome;
    String exportDir;
    String templateDir;
    ObservableList<Page> pages;
    String bannerImgDir;
    String leftFooterImgDir;
    String rightFooterImgDir;
    String styleSheet;

    //TA DATA TAB
    ObservableList<TeachingAssistant> teachingAssistants;
    ObservableList<TeachingAssistant> underGradTeachingAssistants;
    ObservableList<TeachingAssistant> gradTeachingAssistants;
    HashMap<String, StringProperty> officeHours;
    ArrayList<String> gridHeaders;
    int startHour;
    int endHour;
    public static final int MIN_START_HOUR = 9;
    public static final int MAX_END_HOUR = 20;

    //RECITATION TAB
    ObservableList<Recitation> recitations;
    ObservableList<String> supTAs;

    //SCHEDULE TAB
    LocalDate startingMonday;
    LocalDate endingFriday;
    ObservableList<Schedule> schedules;
    ObservableList<Schedule> holidaySchedules;
    ObservableList<Schedule> lectureSchedules;
    ObservableList<Schedule> referenceSchedules;
    ObservableList<Schedule> recitationSchedules;
    ObservableList<Schedule> hwSchedules;

    //PROJECT TAB
    ObservableList<Team> teams;
    ObservableList<Student> students;

    /**
     * This constructor will setup the required data structures for use, but
     * will have to wait on the office hours grid, since it receives the
     * StringProperty objects from the Workspace.
     *
     * @param initApp The application this data manager belongs to.
     */
    public SiteGenData(SiteGenApp initApp) {
        // KEEP THIS FOR LATER
        app = initApp;

        // CONSTRUCT THE LIST OF TAs FOR THE TABLE
        teachingAssistants = FXCollections.observableArrayList();
        underGradTeachingAssistants = FXCollections.observableArrayList();
        gradTeachingAssistants = FXCollections.observableArrayList();
        // THESE ARE THE DEFAULT OFFICE HOURS
        startHour = MIN_START_HOUR;
        endHour = MAX_END_HOUR;
        //THIS WILL STORE OUR OFFICE HOURS
        officeHours = new HashMap();
        // THESE ARE THE LANGUAGE-DEPENDENT OFFICE HOURS GRID HEADERS
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        ArrayList<String> timeHeaders = props.getPropertyOptionsList(SiteGenProp.OFFICE_HOURS_TABLE_HEADERS);
        ArrayList<String> dowHeaders = props.getPropertyOptionsList(SiteGenProp.DAYS_OF_WEEK);
        gridHeaders = new ArrayList();
        gridHeaders.addAll(timeHeaders);
        gridHeaders.addAll(dowHeaders);

        recitations = FXCollections.observableArrayList();
        supTAs = FXCollections.observableArrayList();

        schedules = FXCollections.observableArrayList();
        holidaySchedules = FXCollections.observableArrayList();
        lectureSchedules = FXCollections.observableArrayList();
        referenceSchedules = FXCollections.observableArrayList();
        recitationSchedules = FXCollections.observableArrayList();
        hwSchedules = FXCollections.observableArrayList();

        teams = FXCollections.observableArrayList();
        students = FXCollections.observableArrayList();

        pages = FXCollections.observableArrayList();
    }

    /**
     * Called each time new work is created or loaded, it resets all data and
     * data structures such that they can be used for new values.
     */
    @Override
    public void resetData() {
        startHour = MIN_START_HOUR;
        endHour = MAX_END_HOUR;
        teachingAssistants.clear();
        officeHours.clear();
    }

    // ACCESSOR METHODS
    public int getStartHour() {
        return startHour;
    }

    public int getEndHour() {
        return endHour;
    }

    public void setStartHour(int newStartHour) {
        startHour = newStartHour;
    }

    public void setEndHour(int newEndHour) {
        endHour = newEndHour;
    }

    public ArrayList<String> getGridHeaders() {
        return gridHeaders;
    }

    public ObservableList getUnderGradTeachingAssistants() {
        return underGradTeachingAssistants;
    }

    public ObservableList getGradTeachingAssistants() {
        return gradTeachingAssistants;
    }

    public ObservableList getTeachingAssistants() {
        return teachingAssistants;
    }

    public String getCellKey(int col, int row) {
        return col + "_" + row;
    }

    public StringProperty getCellTextProperty(int col, int row) {
        String cellKey = getCellKey(col, row);
        return officeHours.get(cellKey);
    }

    public HashMap<String, StringProperty> getOfficeHours() {
        return officeHours;
    }

    public int getNumRows() {
        return ((endHour - startHour) * 2) + 1;
    }

    public String getTimeString(int militaryHour, boolean onHour) {
        String minutesText = "00";
        if (!onHour) {
            minutesText = "30";
        }

        // FIRST THE START AND END CELLS
        int hour = militaryHour;
        if (hour > 12) {
            hour -= 12;
        }
        String cellText = "" + hour + ":" + minutesText;
        if (militaryHour < 12) {
            cellText += "am";
        } else {
            cellText += "pm";
        }
        return cellText;
    }

    public String getCellKey(String day, String time) {
        int col = gridHeaders.indexOf(day);
        int row = 1;
        int hour = Integer.parseInt(time.substring(0, time.indexOf("_")));
        int milHour = hour;

        if (hour != 12 && time.contains("pm")) {
            milHour += 12;
        } else if (hour == 12 && time.contains("am")) {
            milHour = 0;
        }

        row += (milHour - startHour) * 2;
        if (time.contains("_30")) {
            row += 1;
        }

        if (row == 0) {
            return null;
        }
        return getCellKey(col, row);
    }

    public TeachingAssistant getTA(String testName) {
        for (TeachingAssistant ta : teachingAssistants) {
            if (ta.getName().equals(testName)) {
                return ta;
            }
        }
        return null;
    }

    public Page getPage(String navbarTitle) {
        for (Page p : pages) {
            if (p.getNavbarTitle().equals(navbarTitle)) {
                return p;
            }
        }
        return null;
    }

    /**
     * This method is for giving this data manager the string property for a
     * given cell.
     */
    public void setCellProperty(int col, int row, StringProperty prop) {
        String cellKey = getCellKey(col, row);
        officeHours.put(cellKey, prop);
    }

    /**
     * This method is for setting the string property for a given cell.
     */
    public void setGridProperty(ArrayList<ArrayList<StringProperty>> grid,
            int column, int row, StringProperty prop) {
        grid.get(row).set(column, prop);
    }

    public void initOfficeHours(int initStartHour, int initEndHour) {
        // NOTE THAT THESE VALUES MUST BE PRE-VERIFIED
        startHour = initStartHour;
        endHour = initEndHour;

        // EMPTY THE CURRENT OFFICE HOURS VALUES
        officeHours.clear();

        // WE'LL BUILD THE USER INTERFACE COMPONENT FOR THE
        // OFFICE HOURS GRID AND FEED THEM TO OUR DATA
        // STRUCTURE AS WE GO
        if (app.getWorkspaceComponent() != null) {
            SiteGenWorkspace workspaceComponent = (SiteGenWorkspace) app.getWorkspaceComponent();
            workspaceComponent.getTaTab().reloadOfficeHoursGrid(this);
        }
    }

    public void initHours(String startHourText, String endHourText) {
        int initStartHour = Integer.parseInt(startHourText);
        int initEndHour = Integer.parseInt(endHourText);
        if ((initStartHour <= initEndHour)) {
            // THESE ARE VALID HOURS SO KEEP THEM
            initOfficeHours(initStartHour, initEndHour);
        }
    }

    public void initScheduleConstraints(LocalDate startingMonday, LocalDate endingFriday) {
        this.startingMonday = startingMonday;
        this.endingFriday = endingFriday;

        if (app.getWorkspaceComponent() != null) {
            SiteGenWorkspace workspace = (SiteGenWorkspace) app.getWorkspaceComponent();
            workspace.getController().handleScheduleConstraintChange();
        }
    }

    public boolean containsTA(String testName, String testEmail) {
        for (TeachingAssistant ta : teachingAssistants) {
            if (ta.getName().equals(testName)) {
                return true;
            }
            if (ta.getEmail().equals(testEmail)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean containsStudentName(String firstName, String lastName) {
        for(Student s : students) {
            if(s.getFirstName().equals(firstName) && s.getLastName().equals(lastName)) return true;
        }
        return false;
    }

    public void addPage(String navbar, boolean use) {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        if (navbar.equals(props.getProperty(SiteGenProp.HOME))) {
            Page homePage = new Page(use, props.getProperty(SiteGenProp.HOME), props.getProperty(SiteGenProp.INDEX_HTML), props.getProperty(SiteGenProp.HOMEBUILDER_JS));
            pages.add(homePage);
        } else if (navbar.equals(props.getProperty(SiteGenProp.SYLLABUS))) {
            Page syllabusPage = new Page(use, props.getProperty(SiteGenProp.SYLLABUS), props.getProperty(SiteGenProp.SYLLABUS_HTML), props.getProperty(SiteGenProp.SYLLABUSBUILDER_JS));
            pages.add(syllabusPage);
        } else if (navbar.equals(props.getProperty(SiteGenProp.SCHEDULE_HEADER))) {
            Page schedulePage = new Page(use, props.getProperty(SiteGenProp.SCHEDULE_HEADER), props.getProperty(SiteGenProp.SCHEDULE_HTML), props.getProperty(SiteGenProp.SCHEDULEBUILDER_JS));
            pages.add(schedulePage);
        } else if (navbar.equals(props.getProperty(SiteGenProp.HWS))) {
            Page hwPage = new Page(use, props.getProperty(SiteGenProp.HWS), props.getProperty(SiteGenProp.HWS_HTML), props.getProperty(SiteGenProp.HWSBUILDER_JS));
            pages.add(hwPage);
        } else if (navbar.equals(props.getProperty(SiteGenProp.PROJECTS))) {
            Page projectPage = new Page(use, props.getProperty(SiteGenProp.PROJECTS), props.getProperty(SiteGenProp.PROJECTS_HTML), props.getProperty(SiteGenProp.PROJECTSBUILDER_JS));
            pages.add(projectPage);
        }
    }

    public void addTA(boolean undergrad, String initName, String initEmail) {
        // MAKE THE TA
        TeachingAssistant ta = new TeachingAssistant(undergrad, initName, initEmail);

        // ADD THE TA
        if (!containsTA(initName, initEmail)) {
            teachingAssistants.add(ta);
            if (undergrad) {
                underGradTeachingAssistants.add(ta);
            } else {
                gradTeachingAssistants.add(ta);
            }
            supTAs.add(ta.getName());
        }

        // SORT THE TAS
        Collections.sort(teachingAssistants);
    }

    public Recitation addRecitation(String section, String instructor, String dayTime, String location, String ta1, String ta2) {
        Recitation r = new Recitation(section, instructor, dayTime, location, ta1, ta2);

        //MAYBE CONTAINS FUNCTION NEEDED
        recitations.add(r);
        Collections.sort(recitations);
        
        return r;
    }
    
    public Recitation addRecitation(Recitation r) {
        recitations.add(r);
        Collections.sort(recitations);
        return r;
    }

    public Schedule addSchedule(String type, LocalDate date, String title, String topic, String link, String time, String criteria) {

        Schedule s = new Schedule(type, date, title, topic, link, time, criteria);

        // MAYBE CONTAINS FUNCTION NEEDED
        schedules.add(s);
        if (type.equals("Holiday")) {
            holidaySchedules.add(s);
        } else if (type.equals("Lecture")) {
            lectureSchedules.add(s);
        } else if (type.equals("Reference")) {
            referenceSchedules.add(s);
        } else if (type.equals("Recitation")) {
            recitationSchedules.add(s);
        } else if (type.equals("Hw")) {
            hwSchedules.add(s);
        }

        Collections.sort(schedules);
        
        return s;
    }
    
    public void addSchedule(Schedule s) {
        schedules.add(s);
        Collections.sort(schedules);
    }

    public Team addTeam(String name, Color color, Color textColor, String link) {
        Team t = new Team(name, color, textColor, link);

        // NEEDS CONTAINS FUNCTION
        teams.add(t);

        Collections.sort(teams);
        
        return t;
    }
    
    public void addTeam(Team t) {
        teams.add(t);
        Collections.sort(teams);
    }
    
    public void editTeam(Team t, String name, Color color, Color textColor, String link) {
        t.setName(name);
        t.setColor(color);
        t.setTextColor(textColor);
        t.setLink(link);
        Collections.sort(teams);
    }

    public Student addStudent(String firstName, String lastName, String team, String role) {
        Student s = new Student(firstName, lastName, team, role);

        // NEEDS CONTAINS FUNCTION
        students.add(s);

        Collections.sort(students);
        
        return s;
    }
    
    public void addStudent(Student s) {
        students.add(s);
        Collections.sort(students);
    }
    
    public void editSchedule(Schedule s, String type, LocalDate date, String time, String title, String topic, String link, String criteria) {
        s.setType(type);
        s.setDate(date);
        s.setTime(time);
        s.setTitle(title);
        s.setTopic(topic);
        s.setLink(link);
        s.setCriteria(criteria);
        Collections.sort(schedules);
    }
    
    public void editStudent(Student s, String fName, String lName, String team, String role) {
        s.setFirstName(fName);
        s.setLastName(lName);
        s.setTeam(team);
        s.setRole(role);
        Collections.sort(students);
    }

    public void updateTA(TeachingAssistant ta, String newName, String newEmail) {
        if (!newName.equals(ta.getName()) || !newEmail.equals(ta.getEmail())) {
            String taOriginalName = ta.getName();

            ta.setName(newName);
            ta.setEmail(newEmail);
            Collections.sort(teachingAssistants);

            if (!newName.equals(taOriginalName)) {
                for (StringProperty s : officeHours.values()) {
                    String cellContent = s.getValue();
                    if (cellContent.contains(taOriginalName)) {
                        cellContent = cellContent.replaceAll(taOriginalName, newName);
                        s.setValue(cellContent);
                    }
                }
            }

            supTAs.set(supTAs.indexOf(taOriginalName), newName);
        }
    }

    public void editRecitation(Recitation r, String newSection, String newInstructor, String newDayTime, String newLocation, String newSupTA1, String newSupTA2) {
        r.setSection(newSection);
        r.setInstructor(newInstructor);
        r.setDayTime(newDayTime);
        r.setLocation(newLocation);
        r.setTa1(newSupTA1);
        r.setTa2(newSupTA2);
        Collections.sort(recitations);
    }
    
    public void removeTA(String name) {
        for (TeachingAssistant ta : teachingAssistants) {
            if (name.equals(ta.getName())) {
                teachingAssistants.remove(ta);
                supTAs.remove(name);
                return;
            }
        }
    }
    
    //public void removeRecitation()

    public void addOfficeHoursReservation(String day, String time, String taName) {
        String cellKey = getCellKey(day, time);
        toggleTAOfficeHours(cellKey, taName);
    }

    /**
     * This function toggles the taName in the cell represented by cellKey.
     * Toggle means if it's there it removes it, if it's not there it adds it.
     */
    public void toggleTAOfficeHours(String cellKey, String taName) {
        StringProperty cellProp = officeHours.get(cellKey);
        if (cellProp != null) {
            String cellText = cellProp.getValue();

            // IF IT ALREADY HAS THE TA, REMOVE IT
            if (cellText.contains(taName)) {
                removeTAFromCell(cellProp, taName);
            } // OTHERWISE ADD IT
            else if (cellText.length() == 0) {
                cellProp.setValue(taName);
            } else {
                cellProp.setValue(cellText + "\n" + taName);
            }
        }
    }

    /**
     * This method removes taName from the office grid cell represented by
     * cellProp.
     */
    public void removeTAFromCell(StringProperty cellProp, String taName) {
        // GET THE CELL TEXT
        String cellText = cellProp.getValue();
        // IS IT THE ONLY TA IN THE CELL?
        if (cellText.equals(taName)) {
            cellProp.setValue("");
        } // IS IT THE FIRST TA IN A CELL WITH MULTIPLE TA'S?
        else if (cellText.indexOf(taName) == 0) {
            int startIndex = cellText.indexOf("\n") + 1;
            cellText = cellText.substring(startIndex);
            cellProp.setValue(cellText);
        } // IS IT IN THE MIDDLE OF A LIST OF TAs
        else if (cellText.indexOf(taName) < cellText.indexOf("\n", cellText.indexOf(taName))) {
            int startIndex = cellText.indexOf("\n" + taName);
            int endIndex = startIndex + taName.length() + 1;
            cellText = cellText.substring(0, startIndex) + cellText.substring(endIndex);
            cellProp.setValue(cellText);
        } // IT MUST BE THE LAST TA
        else {
            int startIndex = cellText.indexOf("\n" + taName);
            cellText = cellText.substring(0, startIndex);
            cellProp.setValue(cellText);
        }
    }

    public void addTAToCell(StringProperty cellProp, String taName, Integer index) {
        String cellText = cellProp.getValue();

        if (cellText.length() == 0) {
            cellProp.setValue(taName);
        } else if (index >= cellText.length()) {
            cellText += "\n" + taName;
            cellProp.setValue(cellText);
        } else {
            cellText = cellText.substring(0, index) + taName + "\n" + cellText.substring(index, cellText.length());
            cellProp.setValue(cellText);
        }
    }

    public LocalDate getStartingMonday() {
        return startingMonday;
    }

    public LocalDate getEndingFriday() {
        return endingFriday;
    }

    public ObservableList<Recitation> getRecitations() {
        return recitations;
    }

    public ObservableList<Schedule> getHolidaySchedules() {
        return holidaySchedules;
    }

    public ObservableList<Schedule> getLectureSchedules() {
        return lectureSchedules;
    }

    public ObservableList<Schedule> getReferenceSchedules() {
        return referenceSchedules;
    }

    public ObservableList<Schedule> getRecitationSchedules() {
        return recitationSchedules;
    }

    public ObservableList<Schedule> getHwSchedules() {
        return hwSchedules;
    }

    public void setStartingMonday(LocalDate newDate) {
        startingMonday = newDate;
    }

    public void setEndingFriday(LocalDate newDate) {
        endingFriday = newDate;
    }

    public ObservableList<Team> getTeams() {
        return teams;
    }

    public ObservableList<Student> getStudents() {
        return students;
    }

    public String getSubject() {
        return subject;
    }

    public int getNumber() {
        return number;
    }

    public String getSemester() {
        return semester;
    }

    public int getYear() {
        return year;
    }

    public String getTitle() {
        return title;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public String getInstructorHome() {
        return instructorHome;
    }

    public String getExportDir() {
        return exportDir;
    }

    public String getTemplateDir() {
        return templateDir;
    }

    public ObservableList<Page> getPages() {
        return pages;
    }

    public String getBannerImgDir() {
        return bannerImgDir;
    }

    public String getLeftFooterImgDir() {
        return leftFooterImgDir;
    }

    public String getRightFooterImgDir() {
        return rightFooterImgDir;
    }

    public String getStyleSheet() {
        return styleSheet;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    public void setInstructorHome(String instructorHome) {
        this.instructorHome = instructorHome;
    }

    public void setExportDir(String exportDir) {
        this.exportDir = exportDir;
    }

    public void setTemplateDir(String templateDir) {
        this.templateDir = templateDir;
    }

    public void setBannerImgDir(String bannerImgDir) {
        this.bannerImgDir = bannerImgDir;
    }

    public void setLeftFooterImgDir(String leftFooterImgDir) {
        this.leftFooterImgDir = leftFooterImgDir;
    }

    public void setRightFooterImgDir(String rightFooterImgDir) {
        this.rightFooterImgDir = rightFooterImgDir;
    }

    public void setStyleSheet(String styleSheet) {
        this.styleSheet = styleSheet;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public ObservableList<Schedule> getSchedules() {
        return schedules;
    }

    public ObservableList<String> getTAList() {
        return supTAs;
    }

}
