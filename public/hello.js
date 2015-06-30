var myApp = angular.module('myApp',[]);

myApp.service('dataService', function($http) {
    this.getData = function() {
        return $http({
            method: 'GET',
            url: 'http://localhost:8080/localfiles'
        });
    }
});

myApp.controller('Hello', function($scope, dataService) {
    $scope.data = null;
    dataService.getData().then(function(dataResponse) {
        $scope.data = dataResponse;
    });
});

