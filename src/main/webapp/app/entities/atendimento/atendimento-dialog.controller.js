(function() {
    'use strict';

    angular
        .module('hospitalApp')
        .controller('AtendimentoDialogController', AtendimentoDialogController);

    AtendimentoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Atendimento', 'Exame', 'Medico', 'Enfermeiro', 'Paciente'];

    function AtendimentoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Atendimento, Exame, Medico, Enfermeiro, Paciente) {
        var vm = this;

        vm.atendimento = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.exames = Exame.query();
        vm.medicos = Medico.query();
        vm.enfermeiros = Enfermeiro.query();
        vm.pacientes = Paciente.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.atendimento.id !== null) {
                Atendimento.update(vm.atendimento, onSaveSuccess, onSaveError);
            } else {
                Atendimento.save(vm.atendimento, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('hospitalApp:atendimentoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.data = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
