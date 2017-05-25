var data = "./js/Data.json";
loadData(data);

var footerDiv = $("#footers");
var leftFooter = "<img src="+ +"style=\"float:left\">";
var rightFooter = "<img src="+ +"style=\"float:right\">";

function loadData(jsonFile) {
    $.getJSON(jsonFile, function (json) {
        var bannerSrc = json.page_style.banner_img_dir;
        var leftFooterSrc = json.page_style.left_footer_img_dir;
        var rightFooterSrc = json.page_style.right_footer_img_dir;
        
        var bannerDiv = $("#navbar");
        var banner = "<img height=\"45\" width=\"263\" src= 'images/"+bannerSrc.substring(bannerSrc.lastIndexOf("/"),bannerSrc.length)+"' style=\"float:right\">";
        bannerDiv.append(banner);
        var footerDiv = $("#footers");
        var leftFooter = "<img height=\"50\" width=\"296\" src= 'images/"+leftFooterSrc.substring(leftFooterSrc.lastIndexOf("/"),leftFooterSrc.length)+"' style=\"float:left\">";
        var rightFooter = "<img height=\"50\" width=\"296\" src= 'images/"+rightFooterSrc.substring(rightFooterSrc.lastIndexOf("/"),rightFooterSrc.length)+"' style=\"float:right\">";
        footerDiv.append(leftFooter);
        footerDiv.append(rightFooter);
    });
}
