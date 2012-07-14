var popup = popup || {};

var oTable;
var oSiteTable;

popup.write = function(numberOfWordsToTranslate) {
    $(document.body).append("Words to Translate: " + numberOfWordsToTranslate + "<br/>");
}

popup.writeError = function(error) {
    $(document.body).append("Errror: " + error + "<br/>");
}

popup.remove = function(wordArray) {
    $("#" + wordArray[0]).parent().remove();
    ajaxExecutor.sendExcludedWords(wordArray, function() {
        console.log("Wyslane");
    });
}

popup.listWordsFromTable = function() {
    var wordsArray = [];
    $(oTable.fnGetNodes()).each(function() {
        wordsArray.push($(this).children(":first").text());
    });
    return wordsArray;
}

popup.removeThisRow = function(object) {
    var row = $(object).closest("tr").get(0);
    oTable.fnDeleteRow(oTable.fnGetPosition(row));
    var wordsArray = [];
    wordsArray.push($(row).children(":first").text())
    popup.remove(wordsArray);
}

popup.removeExcludedWord = function(object) {
    var row = $(object).closest("tr").get(0);
    oTable.fnDeleteRow(oTable.fnGetPosition(row));
    var word = $(row).children(":first").text();
    ajaxExecutor.removeExcludedWord(word, function() {
        console.log("Wyslane");
    });
}

popup.removeWholeFamily = function(object) {
    var row = $(object).closest("tr").get(0);
    oTable.fnDeleteRow(oTable.fnGetPosition(row));
    var exludedWordArray = $(row).children(":nth-child(4)").text().split(",");
    popup.remove(exludedWordArray);
}

popup.createSiteRows = function(wordFamilies) {
    var rows = [];
    for (var i = 0; i < wordFamilies.length; i++) {
        console.log("test");
        var wordFamilyArray = (wordFamilies[i]["family"]);
        var row = [wordFamilies[i]["root"]["value"], createWordFamilyString(wordFamilyArray),'<a class="btn btn-danger" href="#" onclick="popup.removeExcludedWord(this)">Delete</a>'];
        rows.push(row);
    }
    return rows;
}

function createWordFamilyString (wordFamilyArray){
    var wordFamilyString = [];
    for (var i=0; i<wordFamilyArray.length; i++) {
        wordFamilyString.push(wordFamilyArray[i]['value']);
    }
    return wordFamilyString.join(' ');
}


popup.createSiteTable = function(rows) {
    oSiteTable = $('#excludedWords').dataTable({
               //<'row'<'span6'l><'span6'f>r>t<'row'<'span6'i><'span6'p>>
        "sDom": "<'row'<'span8'><'span8'f>r>t<'row'<'spa8'><'span8'p>>",
        "sPaginationType": "bootstrap",
        "iDisplayLength": 13,
        "bFilter": false,
        "aaData": rows,
        "bDestroy":true,
        "aoColumns":[
            { "sTitle": "Excluded word"},
            { "sTitle": "Word family"},
            { "sTitle": "action"}
        ]
    });
}

popup.createTable = function(wordsMap) {
     var words = [];
    for (var key in wordsMap) {
        var excludeAllButton = "";
        if (wordsMap[key]["wordFamily"] != undefined) {
            excludeButton = '<a class="btn btn-warning" href="#" onclick="popup.removeThisRow(this)">Exclude</a>';
        }
        var row = [key,wordsMap[key]["frequency"],excludeButton];
        words.push(row);
    }
    oTable = $('#words').dataTable({
        "sDom": "<'row'<'span8'><'span8'f>r>t<'row'<'span8'><'span8'p>>",
        "sPaginationType": "bootstrap",
        "iDisplayLength": 10,
        "iDisplayStart":0,
        "bFilter": false,
        "aaData": words,
        "bDestroy":true,
        "aoColumns":[
            { "sTitle": "words", "sWidth": "30px" },
            { "sTitle": "freq", "sWidth": "40px"},
            { "sTitle": "action", "sWidth": "30px"}


        ]
    });
    $('#words').after("<a class='btn btn-danger' href='#' onclick='EnglishTranslator.translateWords(popup.listWordsFromTable());'>Translate</a>");
}