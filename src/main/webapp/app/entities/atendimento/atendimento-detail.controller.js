(function() {
    'use strict';

    angular
        .module('hospitalApp')
        .controller('AtendimentoDetailController', AtendimentoDetailController);

    AtendimentoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Atendimento', 'Exame', 'Medico', 'Enfermeiro', 'Paciente'];

    function AtendimentoDetailController($scope, $rootScope, $stateParams, previousState, entity, Atendimento, Exame, Medico, Enfermeiro, Paciente) {
        var vm = this;

        vm.atendimento = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('hospitalApp:atendimentoUpdate', function(event, result) {
            vm.atendimento = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
