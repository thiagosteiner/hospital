(function() {
    'use strict';

    angular
        .module('hospitalApp')
        .controller('EnfermeiroDeleteController',EnfermeiroDeleteController);

    EnfermeiroDeleteController.$inject = ['$uibModalInstance', 'entity', 'Enfermeiro'];

    function EnfermeiroDeleteController($uibModalInstance, entity, Enfermeiro) {
        var vm = this;

        vm.enfermeiro = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Enfermeiro.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
