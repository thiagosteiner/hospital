(function() {
    'use strict';

    angular
        .module('hospitalApp')
        .controller('EnfermeiroController', EnfermeiroController);

    EnfermeiroController.$inject = ['Enfermeiro'];

    function EnfermeiroController(Enfermeiro) {

        var vm = this;

        vm.enfermeiros = [];

        loadAll();

        function loadAll() {
            Enfermeiro.query(function(result) {
                vm.enfermeiros = result;
                vm.searchQuery = null;
            });
        }
    }
})();
