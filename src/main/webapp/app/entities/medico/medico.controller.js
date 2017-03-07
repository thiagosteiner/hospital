(function() {
    'use strict';

    angular
        .module('hospitalApp')
        .controller('MedicoController', MedicoController);

    MedicoController.$inject = ['Medico'];

    function MedicoController(Medico) {

        var vm = this;

        vm.medicos = [];

        loadAll();

        function loadAll() {
            Medico.query(function(result) {
                vm.medicos = result;
                vm.searchQuery = null;
            });
        }
    }
})();
