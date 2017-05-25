function buildRecitations() {   
    var dataFile = "./js/Data.json";
    loadData(dataFile, addRecitations);
}

function addRecitations(data) {
    var recTable = $("#rec_table");
    var rowParity = 0;
    for (var i = 0; i < data.recitations_tab.length; i+=2) {
        var text = "<tr>";
        var rec = data.recitations_tab[i];
        var cellParity = rowParity;
        text += buildRecCell(cellParity, rec);
        cellParity++;
        cellParity %= 2;
        if ((i+1) < data.recitations_tab.length) {
            rec = data.recitations_tab[i+1];
            text += buildRecCell(cellParity, rec);
        }
        else
            text += "<td></td>";
        text += "</tr>";
        recTable.append(text);
        rowParity++;
        rowParity %= 2;
    }
}

function buildRecCell(recClassNum, recData) {
    var text = "<td class='rec_" + recClassNum + "'>"
                + "<table><tr><td valign='top' class='rec_cell'>" 
                +"<strong>"+recData.section+"</strong> ("+recData.instructor+")" + "<br />"
                + recData.day_time + "<br />"
                + recData.location + "<br /></td></tr>"
                + "<tr>";
    
    // RECITATION TA #1
    text += "<td class='ta_cell'><strong>Supervising TA:</strong><br />";
    if (recData.ta_1 != "none")
        text += "<br clear='both' />"
             + recData.ta_1 + "<br />";
    else
        text += "TBA";
    text += "</td>";
    
    // RECITATION TA #2
    text += "<td class='ta_cell'><strong>Supervising TA:</strong><br />";
    if (recData.ta_2 != "none")
        text += "<br clear='both' />"
            + recData.ta_2 + "<br />";            
    else
        text += "TBA";
    text += "</table></td>";
    return text;
}

