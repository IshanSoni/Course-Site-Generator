var data = "./js/Data.json";
loadData(data);

function convertPageName(title) {
  if(title === "Home") return "index.html";
  if(title === "Syllabus") return "syllabus.html";
  if(title === "Schedule") return "schedule.html";
  if(title === "Hws") return "hws.html";
  if(title === "Projects") return "projects.html";
}

function loadData(jsonFile) {
    $.getJSON(jsonFile, function (json) {
        var navDiv = $("#navbar");
        //var pageArray = json.site_template.pages;
        for(var i = 0; i < json.site_template.pages.length; i++) {
          page = json.site_template.pages[i];
          if(page.use) {
            navDiv.append("<a class=\"nav\" href=\""+convertPageName(page.navbar_title)+"\" id=\""+page.navbar_title+"_link\">"+page.navbar_title+"</a>");
          }
        }

        var subject = json.course_info.subject;
        var number = json.course_info.number;
        var semester = json.course_info.semester;
        var year = json.course_info.year;
        var title = json.course_info.title;

        document.title = subject+" "+number;
        var bannerTitle = subject+" "+number+" - "+semester+" "+year+"<br>"+title;
        var div = $("#banner");
        div.append(bannerTitle);
    });
}
