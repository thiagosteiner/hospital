(function() {
    'use strict';
    angular
        .module('hospitalApp')
        .factory('Enfermeiro', Enfermeiro);

    Enfermeiro.$inject = ['$resource'];

    function Enfermeiro ($resource) {
        var resourceUrl =  'api/enfermeiros/:id';

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
