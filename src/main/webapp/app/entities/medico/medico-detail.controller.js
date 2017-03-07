(function() {
    'use strict';

    angular
        .module('hospitalApp')
        .controller('MedicoDetailController', MedicoDetailController);

    MedicoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Medico', 'Atendimento', 'Exame', 'Paciente'];

    function MedicoDetailController($scope, $rootScope, $stateParams, previousState, entity, Medico, Atendimento, Exame, Paciente) {
        var vm = this;

        vm.medico = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('hospitalApp:medicoUpdate', function(event, result) {
            vm.medico = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
