(function() {
    'use strict';

    angular
        .module('hospitalApp')
        .controller('PacienteController', PacienteController);

    PacienteController.$inject = ['Paciente'];

    function PacienteController(Paciente) {

        var vm = this;

        vm.pacientes = [];

        loadAll();

        function loadAll() {
            Paciente.query(function(result) {
                vm.pacientes = result;
                vm.searchQuery = null;
            });
        }
    }
})();
