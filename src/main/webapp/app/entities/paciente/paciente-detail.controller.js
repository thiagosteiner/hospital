(function() {
    'use strict';

    angular
        .module('hospitalApp')
        .controller('PacienteDetailController', PacienteDetailController);

    PacienteDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Paciente', 'Atendimento', 'Exame', 'Medico', 'Enfermeiro'];

    function PacienteDetailController($scope, $rootScope, $stateParams, previousState, entity, Paciente, Atendimento, Exame, Medico, Enfermeiro) {
        var vm = this;

        vm.paciente = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('hospitalApp:pacienteUpdate', function(event, result) {
            vm.paciente = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
