'use strict';

var app = angular.module('lottoApp',
        ['ui.bootstrap', 'ngRoute', 'ngCookies', 'lottoApp.services']);

app.config(['$routeProvider', '$locationProvider', '$httpProvider',
    function($routeProvider, $locationProvider, $httpProvider) {

//			$routeProvider.when('/create', {
//				templateUrl: 'partials/create.html',
//				controller: CreateController
//			});
//			
//			$routeProvider.when('/edit/:id', {
//				templateUrl: 'partials/edit.html',
//				controller: EditController
//			});

        $routeProvider.when('/login', {
            templateUrl: 'partials/login.html',
            controller: 'LoginController'
        });

        $routeProvider.when('/lottoDraw', {
            templateUrl: 'partials/lottoDrawList.html',
            controller: 'LottoDrawController',
            op: 'index'
        });

        $routeProvider.when('/lottoDraw/create', {
            templateUrl: 'partials/lottoDrawCreate.html',
            controller: 'LottoDrawController',
            op: 'create'
        });

        $routeProvider.otherwise({
            templateUrl: 'partials/login.html',
            controller: 'LoginController'
        });


//            $routeProvider.otherwise({
//                templateUrl: 'partials/index.html',
//                controller: IndexController
//            });

        $locationProvider.hashPrefix('!');

        /* Intercept http errors */
        var interceptor = function($rootScope, $q, $location) {

            function success(response) {
                return response;
            }

            function error(response) {

                var status = response.status;
                var config = response.config;
                var method = config.method;
                var url = config.url;

                if (status === 401) {
                    $location.path("/login");
                } else {
                    $rootScope.error = method + " on " + url + " failed with status " + status;
                }

                return $q.reject(response);
            }

            return function(promise) {
                return promise.then(success, error);
            };
        };
        $httpProvider.responseInterceptors.push(interceptor);


    }]);


app.run(['$rootScope', '$http', '$location', '$cookieStore',
    function($rootScope, $http, $location, $cookieStore) {

        /* Reset error when a new view is loaded */
        $rootScope.$on('$viewContentLoaded', function() {
            delete $rootScope.error;
        });

        $rootScope.hasRole = function(role) {

            if ($rootScope.user === undefined) {
                return false;
            }

            if ($rootScope.user.roles[role] === undefined) {
                return false;
            }

            return $rootScope.user.roles[role];
        };

        $rootScope.logout = function() {
            delete $rootScope.user;
            delete $http.defaults.headers.common['X-Auth-Token'];
            $cookieStore.remove('user');
            $location.path("/login");
        };

        /* Try getting valid user from cookie or go to login page */
        var originalPath = $location.path();
        $location.path("/login");
        var user = $cookieStore.get('user');
        if (user !== undefined) {
            $rootScope.user = user;
            $http.defaults.headers.common['X-Auth-Token'] = user.token;

            $location.path(originalPath);
        }

    }]);

// Services
var services = angular.module('lottoApp.services', ['ngResource']);

services.factory('LoginService', function($resource) {

    return $resource('rest/user/:action', {},
            {
                authenticate: {
                    method: 'POST',
                    params: {'action': 'authenticate'},
                    headers: {'Content-Type': 'application/x-www-form-urlencoded'}
                }
            }
    );
});


services.factory('LottoDrawService', function($resource) {
    return $resource('rest/lottoDraw/:action', {},
            {
                list: {
                    method: 'GET',
                    params: {'action': 'list', 'id': '@id'},
                    headers: {'Content-Type': 'application/x-www-form-urlencoded'}
                },
                listElems: {
                    method: 'POST',
                    isArray: true,
                    params: {'action': 'shortList'},
                    headers: {'Content-Type': 'application/x-www-form-urlencoded'}
                },
                initDB: {
                    method: 'GET',
                    isArray: true,
                    params: {'action': 'init'},
                    headers: {'Content-Type': 'application/x-www-form-urlencoded'}
                }
            }
    );
//    return $resource('rest/lottoDraw/:id', {id: '@id'});
});

