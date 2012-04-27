var popup = popup || {};

var oTable;
var words = [];

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

popup.createSiteRows = function(words) {
    var rows = [];
    for (var i = 0; i < words.length; i++) {
        var row = [words[i],'<a class="btn btn-danger" href="#" onclick="popup.removeExcludedWord(this)">Usun</a>'];
        rows.push(row);
    }
    return rows;
}

popup.createSiteTable = function(rows) {
    oTable = $('#words').dataTable({
               //<'row'<'span6'l><'span6'f>r>t<'row'<'span6'i><'span6'p>>
        "sDom": "<'row'<'span8'><'span8'f>r>t<'row'<'spa8'><'span8'p>>",
        "sPaginationType": "bootstrap",
        "iDisplayLength": 15,
        "bFilter": false,
        "aaData": rows,
        "aoColumns":[
            { "sTitle": "words"},
            { "sTitle": "action"}
        ]
    });
}

popup.createTable = function(wordsMap) {
    $(document.body).append('<table cellpadding="0" cellspacing="0" border="0" class="table table-striped table-bordered dataTable" id="words"></table>');

    for (var key in wordsMap) {
        var excludeAllButton = "";
        if (wordsMap[key]["wordFamily"] != undefined) {
            excludeAllButton = '<a class="btn btn-info" href="#" onclick="popup.removeWholeFamily(this)">Wyklucz wszystkie</a>';
        }
        var row = [key,'<a class="btn btn-warning" href="#" onclick="popup.removeThisRow(this)">Wylucz</a>',wordsMap[key]["frequency"], wordsMap[key]["wordFamily"], excludeAllButton];
        words.push(row);
    }
    oTable = $('#words').dataTable({
        "sDom": "<'row'<'span8'><'span8'f>r>t<'row'<'span8'><'span8'p>>",
        "sPaginationType": "bootstrap",
        "iDisplayLength": 10,
        "bFilter": false,
        "aaData": words,
        "aoColumns":[
            { "sTitle": "words", "sWidth": "30px" },
            { "sTitle": "action", "sWidth": "30px"},
            { "sTitle": "freq", "sWidth": "40px"},
            { "sTitle": "word family", "sWidth": "100px"},
            { "sTitle": "action", "sWidth": "30px"}


        ]
    });
    $(document.body).append("<a class='btn btn-danger' href='#' onclick='EnglishTranslator.translateWords(popup.listWordsFromTable());'>Translate</a>")
}