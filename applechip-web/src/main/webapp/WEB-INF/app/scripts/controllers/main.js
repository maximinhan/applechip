'use strict';

/**
 * @ngdoc function
 * @name webInfApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the webInfApp
 */
angular.module('webInfApp')
  .controller('MainCtrl', function ($scope) {
    $scope.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];
  });
