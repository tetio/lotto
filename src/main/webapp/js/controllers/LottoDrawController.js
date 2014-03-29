// LottoDrawController
var LottoDrawController = function($rootScope, $scope, $route, $location, LottoDrawService) {
    $scope.lottoDrawEntries = {};
    var op = $route.current.op;

    init(op);

    function init(op) {
        if (op === 'index') {
            $scope.lottoDrawEntries = LottoDrawService.query();
        } else if (op === 'create') {
            $scope.lottoDrawEntry = new LottoDrawService();
            $scope.lottoDrawEntry.date = new Date();
        }
    };


//    $scope.hasRole = function(role) {
//        return $rootScope.hasRole(role);
//    };

    $scope.listElems = function() {
        $scope.lottoDrawEntries = LottoDrawService.listElems($.param({nElems: $scope.nElems}));
    };

    $scope.initDB = function() {
        $scope.lottoDrawEntries = LottoDrawService.initDB();
    };


    $scope.save = function() {
        $scope.lottoDrawEntry.$save(function() {
            $location.path('/lottoDraw');
        });
    };


// Date zone
    $scope.today = function() {
        $scope.lottoDrawEntry.date = new Date();
    };
    

    $scope.showWeeks = true;
    $scope.toggleWeeks = function() {
        $scope.showWeeks = !$scope.showWeeks;
    };

    $scope.clear = function() {
        $scope.lottoDrawEntry.date = null;
    };

    // Disable weekend selection
    $scope.disabled = function(date, mode) {
        return (mode === 'day' && (date.getDay() === 0 || date.getDay() === 6));
    };

    $scope.toggleMin = function() {
        $scope.minDate = ($scope.minDate) ? null : new Date();
    };
    $scope.toggleMin();

    $scope.open = function($event) {
        $event.preventDefault();
        $event.stopPropagation();

        $scope.opened = true;
    };

    $scope.dateOptions = {
        'year-format': "'yy'",
        'starting-day': 1
    };

    $scope.formats = ['dd-MMMM-yyyy', 'yyyy/MM/dd', 'shortDate'];
    $scope.format = $scope.formats[0];
// End date zone

};

LottoDrawController.$inject = ['$rootScope', '$scope', '$route', '$location', 'LottoDrawService'];
angular.module('lottoApp').controller('LottoDrawController', LottoDrawController);
