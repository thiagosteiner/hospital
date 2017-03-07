(function() {
    'use strict';

    angular
        .module('hospitalApp')
        .controller('EnfermeiroDialogController', EnfermeiroDialogController);

    EnfermeiroDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Enfermeiro', 'Atendimento', 'Paciente'];

    function EnfermeiroDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Enfermeiro, Atendimento, Paciente) {
        var vm = this;

        vm.enfermeiro = entity;
        vm.clear = clear;
        vm.save = save;
        vm.atendimentos = Atendimento.query();
        vm.pacientes = Paciente.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.enfermeiro.id !== null) {
                Enfermeiro.update(vm.enfermeiro, onSaveSuccess, onSaveError);
            } else {
                Enfermeiro.save(vm.enfermeiro, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('hospitalApp:enfermeiroUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
