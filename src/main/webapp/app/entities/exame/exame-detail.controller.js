(function() {
    'use strict';

    angular
        .module('hospitalApp')
        .controller('ExameDetailController', ExameDetailController);

    ExameDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Exame', 'Atendimento', 'Paciente', 'Medico'];

    function ExameDetailController($scope, $rootScope, $stateParams, previousState, entity, Exame, Atendimento, Paciente, Medico) {
        var vm = this;

        vm.exame = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('hospitalApp:exameUpdate', function(event, result) {
            vm.exame = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
