// LoginController
var LoginController = function($scope, $rootScope, $location, $http, $cookieStore, LoginService) {
    $scope.login = function() {
        LoginService.authenticate($.param({username: $scope.username, password: $scope.password}), function(user) {
            $rootScope.user = user;
            $http.defaults.headers.common['X-Auth-Token'] = user.token;
            $cookieStore.put('user', user);
            $location.path("/lottoDraw");
        });
    };
};
LoginController.$inject = ['$scope', '$rootScope', '$location', '$http', '$cookieStore', 'LoginService'];
angular.module('lottoApp').controller('LoginController', LoginController);
