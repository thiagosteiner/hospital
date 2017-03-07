(function() {
    'use strict';

    angular
        .module('hospitalApp')
        .controller('PacienteDialogController', PacienteDialogController);

    PacienteDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Paciente', 'Atendimento', 'Exame', 'Medico', 'Enfermeiro'];

    function PacienteDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Paciente, Atendimento, Exame, Medico, Enfermeiro) {
        var vm = this;

        vm.paciente = entity;
        vm.clear = clear;
        vm.save = save;
        vm.atendimentos = Atendimento.query();
        vm.exames = Exame.query();
        vm.medicos = Medico.query();
        vm.enfermeiros = Enfermeiro.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.paciente.id !== null) {
                Paciente.update(vm.paciente, onSaveSuccess, onSaveError);
            } else {
                Paciente.save(vm.paciente, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('hospitalApp:pacienteUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
