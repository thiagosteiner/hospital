(function() {
    'use strict';

    angular
        .module('hospitalApp')
        .controller('AtendimentoController', AtendimentoController);

    AtendimentoController.$inject = ['Atendimento'];

    function AtendimentoController(Atendimento) {

        var vm = this;

        vm.atendimentos = [];

        loadAll();

        function loadAll() {
            Atendimento.query(function(result) {
                vm.atendimentos = result;
                vm.searchQuery = null;
            });
        }
    }
})();
