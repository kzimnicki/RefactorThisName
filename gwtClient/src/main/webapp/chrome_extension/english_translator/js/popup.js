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
    var word = deleteRow(object);
    ajaxExecutor.removeExcludedWord(word, function() {
        console.log("Wyslane");
    });
}

function deleteRow(object) {
    var row = $(object).closest("tr").get(0);
    oSiteTable.fnDeleteRow(oSiteTable.fnGetPosition(row));
    var word = $(row).children(":first").text();
    return word;
}
popup.removeIncludedWord = function(object) {
    var word = deleteRow(object);
    ajaxExecutor.removeIncludedWord(word, function() {
        console.log("Wyslane");
    });
}

popup.createExcludedSiteRows = function(wordFamilies) {
    var rows = [];
    for (var key in  wordFamilies) {
        var wordFamilyArray = (wordFamilies[key]);
        var row = [key, createWordFamilyString(wordFamilyArray),'<a class="btn btn-danger" href="#" onclick="popup.removeExcludedWord(this)">Delete</a>'];
        rows.push(row);
    }
    return rows;
}

popup.createIncludedSiteRows = function(wordFamilies) {
    var rows = [];
    for (var key in  wordFamilies) {
        var wordFamilyArray = (wordFamilies[key]);
        var row = [key, createWordFamilyString(wordFamilyArray),'<a class="btn btn-danger" href="#" onclick="popup.removeIncludedWord(this)">Delete</a>'];
        rows.push(row);
    }
    return rows;
}

popup.addAllToExclude = function() {
    var words = popup.listWordsFromTable();
    ajaxExecutor.sendExcludedWords(words, function(){
        console.log('sended');
    });
}

function createWordFamilyString (wordFamilyArray){
    var wordFamilyString = [];
    for (var i=0; i<wordFamilyArray.length; i++) {
        wordFamilyString.push(wordFamilyArray[i]);
    }
    return wordFamilyString.join(' ');
}


popup.createSiteTable = function(rows, title, id) {
    oSiteTable = $(id).dataTable({
               //<'row'<'span6'l><'span6'f>r>t<'row'<'span6'i><'span6'p>>
        "sDom": "<'row'<'span8'><'span8'f>r>t<'row'<'spa8'><'span8'pi>>",
        "sPaginationType": "bootstrap",
        "iDisplayLength": 10,
        "bFilter": true,
        "bInfo": true,
        "aaData": rows,
        "bDestroy":true,
        "aoColumns":[
            { "sTitle": title},
            { "sTitle": "Word family"},
            { "sTitle": "action"}
        ]
    });
}

popup.createTable = function(wordsMap) {
     var words = [];
    for (var key in wordsMap) {
        var excludeButton = '<a class="btn btn-warning" href="#" onclick="popup.removeThisRow(this)">Exclude</a>';
        var row = [key,wordsMap[key]["frequency"],excludeButton];
        words.push(row);
    }
    oTable = $('#words').dataTable({
        "sDom": "<'row'<'span8'><'span8'f>r>t<'row'<'span8'><'span8'p>>",
        "sPaginationType": "bootstrap",
        "iDisplayLength": 10,
        "iDisplayStart":0,
        "bFilter": true,
        "aaData": words,
        "bDestroy":true,
        "oLanguage": {
			"sSearch": "Search all columns:"
		},
        "aoColumns":[
            { "sTitle": "words", "sWidth": "30px" },
            { "sTitle": "freq", "sWidth": "40px"},
            { "sTitle": "action", "sWidth": "30px"}


        ]
    });
    $('#words').after("<a class='btn btn-danger' href='#' onclick='popup.addAllToExclude();'>Add all words to exclude</a>");
}