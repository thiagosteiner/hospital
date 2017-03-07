(function() {
    'use strict';
    angular
        .module('hospitalApp')
        .factory('Paciente', Paciente);

    Paciente.$inject = ['$resource'];

    function Paciente ($resource) {
        var resourceUrl =  'api/pacientes/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
