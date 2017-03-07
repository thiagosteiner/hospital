(function() {
    'use strict';

    angular
        .module('hospitalApp')
        .controller('AtendimentoDeleteController',AtendimentoDeleteController);

    AtendimentoDeleteController.$inject = ['$uibModalInstance', 'entity', 'Atendimento'];

    function AtendimentoDeleteController($uibModalInstance, entity, Atendimento) {
        var vm = this;

        vm.atendimento = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Atendimento.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
