(function() {
    'use strict';
    angular
        .module('hospitalApp')
        .factory('Exame', Exame);

    Exame.$inject = ['$resource', 'DateUtils'];

    function Exame ($resource, DateUtils) {
        var resourceUrl =  'api/exames/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.dataSolicitacao = DateUtils.convertDateTimeFromServer(data.dataSolicitacao);
                        data.dataLaudo = DateUtils.convertDateTimeFromServer(data.dataLaudo);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
