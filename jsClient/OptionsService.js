var module = angular.module('ExplainCC.services');

module.factory('OptionsService', function($resource) {
        'use strict';
        var optionsResource = $resource('http://explain.cc/app/options');
        return {
            loadOptions: function() {
                return optionsResource.get();
            },
            save: function(options){
                options.$save();
            }
        };
    }
);