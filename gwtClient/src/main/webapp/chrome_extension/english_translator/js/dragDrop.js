var dragDrop = dragDrop || {};

dragDrop.handleFileSelect = function(evt){
    evt.stopPropagation();
    evt.preventDefault();

    var files = evt.dataTransfer.files; // FileList object.
    EnglishTranslator.loadFile(files[0]);
}

dragDrop.handleDragOver = function(evt){
    evt.stopPropagation();
    evt.preventDefault();
    evt.dataTransfer.dropEffect = 'copy'; // Explicitly show this is a copy.
}