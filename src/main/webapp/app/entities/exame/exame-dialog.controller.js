(function() {
    'use strict';

    angular
        .module('hospitalApp')
        .controller('ExameDialogController', ExameDialogController);

    ExameDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Exame', 'Atendimento', 'Paciente', 'Medico'];

    function ExameDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Exame, Atendimento, Paciente, Medico) {
        var vm = this;

        vm.exame = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.atendimentos = Atendimento.query();
        vm.pacientes = Paciente.query();
        vm.medicos = Medico.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.exame.id !== null) {
                Exame.update(vm.exame, onSaveSuccess, onSaveError);
            } else {
                Exame.save(vm.exame, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('hospitalApp:exameUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.dataSolicitacao = false;
        vm.datePickerOpenStatus.dataLaudo = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
