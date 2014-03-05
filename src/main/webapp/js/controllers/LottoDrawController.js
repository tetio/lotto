// LottoDrawController
var LottoDrawController = function($rootScope, $scope, LottoDrawService) {
    $scope.lottoDrawEntries = {};

    init();

    function init() {
        $scope.lottoDrawEntries = LottoDrawService.query();
    }
    ;

    $scope.hasRole = function(role) {
        return $rootScope.hasRole(role);
    };
    
    $scope.listElems = function() {
        $scope.lottoDrawEntries = LottoDrawService.listElems($.param({nElems: $scope.nElems}));
//        LottoDrawService.listElems($.param({nElems: $scope.nElems}), function(resultList) {
//            $scope.lottoDrawEntries = resultList;
//        });        
    };
};
LottoDrawController.$inject = ['$rootScope', '$scope', 'LottoDrawService'];
angular.module('lottoApp').controller('LottoDrawController', LottoDrawController);
