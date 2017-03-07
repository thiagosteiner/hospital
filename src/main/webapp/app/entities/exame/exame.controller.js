(function() {
    'use strict';

    angular
        .module('hospitalApp')
        .controller('ExameController', ExameController);

    ExameController.$inject = ['Exame'];

    function ExameController(Exame) {

        var vm = this;

        vm.exames = [];

        loadAll();

        function loadAll() {
            Exame.query(function(result) {
                vm.exames = result;
                vm.searchQuery = null;
            });
        }
    }
})();
