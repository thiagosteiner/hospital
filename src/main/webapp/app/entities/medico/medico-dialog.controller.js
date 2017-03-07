(function() {
    'use strict';

    angular
        .module('hospitalApp')
        .controller('MedicoDialogController', MedicoDialogController);

    MedicoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Medico', 'Atendimento', 'Exame', 'Paciente'];

    function MedicoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Medico, Atendimento, Exame, Paciente) {
        var vm = this;

        vm.medico = entity;
        vm.clear = clear;
        vm.save = save;
        vm.atendimentos = Atendimento.query();
        vm.exames = Exame.query();
        vm.pacientes = Paciente.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.medico.id !== null) {
                Medico.update(vm.medico, onSaveSuccess, onSaveError);
            } else {
                Medico.save(vm.medico, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('hospitalApp:medicoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
