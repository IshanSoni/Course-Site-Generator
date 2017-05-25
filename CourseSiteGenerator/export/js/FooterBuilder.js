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
        var banner = "<img height=\"45\" width=\"263\" src= '"+bannerSrc+"' style=\"float:right\">";
        bannerDiv.append(banner);
        var footerDiv = $("#footers");
        var leftFooter = "<img height=\"50\" width=\"296\" src= '"+leftFooterSrc+"' style=\"float:left\">";
        var rightFooter = "<img height=\"50\" width=\"296\" src= '"+rightFooterSrc+"' style=\"float:right\">";
        footerDiv.append(leftFooter);
        footerDiv.append(rightFooter);
    });
}
