(function() {
    'use strict';

    angular
        .module('hospitalApp')
        .controller('ExameDeleteController',ExameDeleteController);

    ExameDeleteController.$inject = ['$uibModalInstance', 'entity', 'Exame'];

    function ExameDeleteController($uibModalInstance, entity, Exame) {
        var vm = this;

        vm.exame = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Exame.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
