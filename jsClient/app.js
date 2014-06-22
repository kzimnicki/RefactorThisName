var modules = ['ngRoute', 'ui.bootstrap', 'ExplainCC.controllers', 'ExplainCC.services', 'ExplainCC.directives'];

angular.module('ExplainCC', modules)
    .config(function($routeProvider, $locationProvider, $httpProvider) {
        'use strict';

        console.log($routeProvider);

        $routeProvider
            .when('/options', {
                templateUrl: 'template/OptionsDialog.html',
                controller: 'MainController'
            })
            .when('/unknownWords', {
                templateUrl: 'template/ExcludedWordsDialog.html',
                controller: 'WordsController'
            })
            .when('/home', {
                templateUrl: 'template/WatchDialog.html',
                controller: 'MainController'
            })
            .when('/about', {
                templateUrl: 'template/ContactDialog.html',
                controller: 'MainController'
            })
            .when('/translate', {
                templateUrl: 'template/TranslateDialog.html',
                controller: 'TranslateController'
            })
            .otherwise({
                template: 'otherwise'
            });

        $httpProvider.interceptors.push(function($q, $location, $rootScope) {

            function spinnerIncrement() {
                if ($rootScope.spinnerCount === undefined) {
                    $rootScope.spinnerCount = 0;
                }
                $rootScope.spinnerCount++;
            }

            function spinnerDecrement() {
                $rootScope.spinnerCount--;
                $rootScope.spinnerDisabled = false;
            }

            function showAlert(status) {
                $rootScope.alerts = [{
                    type: 'danger',
                    msg: ' Server response status: ' + status
                }];
            }

                return {

                    'request': function(config) {
                        spinnerIncrement();
                        config.headers['Authorization'] = 'Basic a3J6eXN6dG9mQHppbW5pY2tpLmJpejo0YzMxYmE0MzQyYTJlZTg4YjQzOWY5MTc3MGI0Njc1Nw==';
                        return config || $q.when(config);
                    },

                    'response': function(response) {
                        spinnerDecrement();
                        $rootScope.alerts = [];
                        return response || $q.when(response);
                    },

                    'responseError': function(response) {
                        spinnerDecrement();
//                        if (response.status === 401 || response.status === 403) {
//                            $location.path('/login.jsp');
//                            return $q.reject(response);
//                        } else {
//                            showAlert(response.status);
                            return $q.reject(response);
//                        }
                    }
                };
            });

    }).config(function($provide) {
        'use strict';
        $provide.decorator('$exceptionHandler', function($delegate) {
            return function(exception, cause) {
                $delegate(exception, cause);
                //alert(exception.message);
            };
        });
    });