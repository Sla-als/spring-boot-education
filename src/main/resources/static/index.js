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

    $scope.editFiles = function () {
        var f = document.getElementById('file').files[0],
            r = new FileReader();

        r.onloadend = function (e) {
            var data = e.target.result;
            //send your binary data via $http or $resource or do anything else with it

            $http.post(contextPath + '/files_lessons')
                .then(function (response) {
                    $scope.fillTableFiles();
                });
        }
        r.readAsArrayBuffer(f);
    }


    //
    // $scope.editLesson = function (id,title,description) {
    //     var data = {
    //         id: id,
    //         title: title,
    //         description: description
    //     };
    //
    //     $http.put(contextPath + '/lessons/', JSON.stringify(data))
    //         .then(function (response) {
    //             $scope.msg = "Put Data Method Executed Successfully!";
    //             $scope.fillTableLesson();
    //         });
    //
    //     $http({
    //         method : "PUT",
    //         url : "employees/" + $scope.form.id,
    //         data : angular.toJson($scope.form),
    //         headers : {
    //             'Content-Type' : 'application/json'
    //         }
    //     }).then( _success, _error );
    //
    //
    //     // $http({
    //     //     url: contextPath + '/lessons',$scopenewLesson,
    //     //     method: 'POST',
    //     //     params: {
    //     //         id: $scope.lesson.id
    //     //     }}).then(function (response) {
    //     //     window.alert("OK!");
    //     // });
    //
    //
    // };
    //

    $scope.fillTableLesson();
    $scope.fillTableFiles();

})