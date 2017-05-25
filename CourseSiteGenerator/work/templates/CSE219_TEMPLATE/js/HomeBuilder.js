
function initHome() {
    var dataFile = "./js/Data.json";
    loadData(dataFile);
}
function loadData(jsonFile) {
    $.getJSON(jsonFile, function (json) {
	loadJSONData(json);
    });
}
function loadJSONData(data) {
    var instructorName = data.course_info.instructor_name;
    var instructorHome = "<a href= https://"+data.course_info.instructor_home+">"+data.course_info.instructor_home;
    var instName = $("#instructor_name");
    var instHome = $("#instructor_home");
    instName.append(instructorName);
    instHome.append(instructorHome);
    
    var courseSubject = data.course_info.subject;
    var courseNumber = data.course_info.number;
    var course = $("#inlined_course");
    course.append(courseSubject+" "+courseNumber);
}
