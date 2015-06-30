function Hello($scope, $http) {
    $http.get('http://localhost:8080/localfiles').
        success(function(data) {
            $scope.localfiles = data;
        });
}