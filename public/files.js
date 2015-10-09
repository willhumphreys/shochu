var myApp = angular.module('myApp',[]);

myApp.service('dataService', function($http) {
    this.getLocalTickDataFiles = function() {
        return $http({
            method: 'GET',
            url: 'http://localhost:8080/localfiles/tickData'
        });
    };

    this.getRemoteTickDataFiles = function() {
        return $http({
            method: 'GET',
            url: 'http://localhost:8080/remotefiles/tickdata-matcha'
        });
    };

    this.getLocalLiveDataFiles = function() {
        return $http({
            method: 'GET',
            url: 'http://localhost:8080/localfiles/liveData'
        });
    };

    this.getRemoteLiveDataFiles = function() {
        return $http({
            method: 'GET',
            url: 'http://localhost:8080/remotefiles/livedata-matcha'
        });
    };
});

myApp.controller('FileController', function($scope, dataService) {

    //Local Data
    $scope.localTickDataFiles = null;
    dataService.getLocalTickDataFiles().then(function(dataResponse) {
        $scope.localTickDataFiles = dataResponse;
    });

    $scope.remoteTickDataFiles = null;
    dataService.getRemoteTickDataFiles().then(function(dataResponse) {
        $scope.remoteTickDataFiles = dataResponse;
    });

    //Live Data
    $scope.localLiveDataFiles = null;
    dataService.getLocalLiveDataFiles().then(function(dataResponse) {
        $scope.localLiveDataFiles = dataResponse;
    });

    $scope.remoteLiveDataFiles = null;
    dataService.getRemoteLiveDataFiles().then(function(dataResponse) {
        $scope.remoteLiveDataFiles = dataResponse;
    });

});

