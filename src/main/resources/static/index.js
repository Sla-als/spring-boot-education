angular.module('app', []).controller('indexController', function ($scope, $http) {
    const contextPath = 'http://localhost:8189/education/api/v1';

    $scope.lessons = [];

    $scope.form = {
        id: "",
        title: "",
        description: ""
    };

    $scope.fillTableLesson = function () {
        $http.get(contextPath + '/lessons')
            .then(function (response) {
                console.log(response);
                $scope.LessonsList = response.data;
            });
    };

    $scope.downloadFile = function (name) {
        var downloadPath = contextPath + '/files_lessons/files/' + name;
        window.open(downloadPath, '_blank', '');
    }


    $scope.fillTableFiles = function () {
        $http.get(contextPath + '/files_lessons')
            .then(function (response) {
                console.log(response);
                $scope.FilesList = response.data;
            });
    };

    //HTTP POST/PUT methods for add/edit employee
    $scope.editLesson = function () {
        var method = "";
        var url = "";
        //Id is absent so add employee - POST operation
        method = "POST";
        url = 'lessons';
        //If Id is present, it's edit operation - PUT operation
        // method = "PUT";
        // url = 'lessons/' + $scope.form.id;
        $http({
            method: method,
            url: url,
            data: angular.toJson($scope.form),
            headers: {
                'Content-Type': 'application/json'
            }
        }).then(function (response) {
            $scope.fillTableLesson();
            $scope.form.id = null;
            $scope.form.title = null;
            $scope.form.description = null;
        });

    };

    $scope.fillTableLesson();
    $scope.fillTableFiles();
})