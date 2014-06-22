var module = angular.module('ExplainCC.controllers');

module.controller('OptionsController', function($scope, OptionsService) {
    'use strict';
    console.log('Option controller');

    $scope.options = OptionsService.loadOptions();

    $scope.restoreDefaultOption = function(){
         $scope.options.max = 89;
         $scope.options.min = 5;
         $scope.options.phrasalVerbAdded = false;
         $scope.options.subtitleProcessor = 'IN_TEXT'
         $scope.options.subtitleTemplate = '<font color="yellow">(@@TRANSLATED_TEXT@@)</font>';
         $scope.options.phrasalVerbTemplate = '<font color="red">@@TRANSLATED_TEXT@@</font>';
    };

    $scope.changeSubtitleProcessor = function(name){
        $scope.options.subtitleProcessor = name;
    };

    $scope.save = function(){
        OptionsService.save($scope.options);
    };
});