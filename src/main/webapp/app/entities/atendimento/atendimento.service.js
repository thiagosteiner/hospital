(function() {
    'use strict';
    angular
        .module('hospitalApp')
        .factory('Atendimento', Atendimento);

    Atendimento.$inject = ['$resource', 'DateUtils'];

    function Atendimento ($resource, DateUtils) {
        var resourceUrl =  'api/atendimentos/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.data = DateUtils.convertDateTimeFromServer(data.data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
