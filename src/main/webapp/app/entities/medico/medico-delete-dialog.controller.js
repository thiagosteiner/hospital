(function() {
    'use strict';

    angular
        .module('hospitalApp')
        .controller('MedicoDeleteController',MedicoDeleteController);

    MedicoDeleteController.$inject = ['$uibModalInstance', 'entity', 'Medico'];

    function MedicoDeleteController($uibModalInstance, entity, Medico) {
        var vm = this;

        vm.medico = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Medico.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
