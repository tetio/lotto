// LottoDrawController
var LottoDrawController = function($rootScope, $scope, $route, $location, LottoDrawService) {
    $scope.lottoDrawEntries = {};
    var oper = $route.current.oper;
    
    init(oper);

    function init(oper) {
        if (oper === 'index') {
            $scope.lottoDrawEntries = LottoDrawService.query();
        } else if (oper === 'create') {
            $scope.lottoDrawEntry = new LottoDrawService();
        }
    };
    

    $scope.hasRole = function(role) {
        return $rootScope.hasRole(role);
    };

    $scope.listElems = function() {
        $scope.lottoDrawEntries = LottoDrawService.listElems($.param({nElems: $scope.nElems}));
    };

	$scope.save = function() {
		$scope.lottoDrawEntry.$save(function() {
			$location.path('/lottoDraw');
		});
	};
};
LottoDrawController.$inject = ['$rootScope', '$scope', '$route', '$location', 'LottoDrawService'];
angular.module('lottoApp').controller('LottoDrawController', LottoDrawController);
