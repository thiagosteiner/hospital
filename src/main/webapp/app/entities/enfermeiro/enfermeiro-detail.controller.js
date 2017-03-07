(function() {
    'use strict';

    angular
        .module('hospitalApp')
        .controller('EnfermeiroDetailController', EnfermeiroDetailController);

    EnfermeiroDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Enfermeiro', 'Atendimento', 'Paciente'];

    function EnfermeiroDetailController($scope, $rootScope, $stateParams, previousState, entity, Enfermeiro, Atendimento, Paciente) {
        var vm = this;

        vm.enfermeiro = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('hospitalApp:enfermeiroUpdate', function(event, result) {
            vm.enfermeiro = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
