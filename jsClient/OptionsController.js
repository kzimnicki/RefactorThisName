var module = angular.module('ExplainCC.controllers');

module.controller('OptionsController', function($scope, OptionsService) {
    'use strict';
    console.log('Option controller');

    $scope.options = OptionsService.loadOptions();

});