(function() {
    'use strict';

    angular
        .module('hospitalApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('enfermeiro', {
            parent: 'entity',
            url: '/enfermeiro',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Enfermeiros'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/enfermeiro/enfermeiros.html',
                    controller: 'EnfermeiroController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('enfermeiro-detail', {
            parent: 'enfermeiro',
            url: '/enfermeiro/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Enfermeiro'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/enfermeiro/enfermeiro-detail.html',
                    controller: 'EnfermeiroDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Enfermeiro', function($stateParams, Enfermeiro) {
                    return Enfermeiro.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'enfermeiro',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('enfermeiro-detail.edit', {
            parent: 'enfermeiro-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/enfermeiro/enfermeiro-dialog.html',
                    controller: 'EnfermeiroDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Enfermeiro', function(Enfermeiro) {
                            return Enfermeiro.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('enfermeiro.new', {
            parent: 'enfermeiro',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/enfermeiro/enfermeiro-dialog.html',
                    controller: 'EnfermeiroDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                cre: null,
                                nome: null,
                                tipoEnfermeiro: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('enfermeiro', null, { reload: 'enfermeiro' });
                }, function() {
                    $state.go('enfermeiro');
                });
            }]
        })
        .state('enfermeiro.edit', {
            parent: 'enfermeiro',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/enfermeiro/enfermeiro-dialog.html',
                    controller: 'EnfermeiroDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Enfermeiro', function(Enfermeiro) {
                            return Enfermeiro.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('enfermeiro', null, { reload: 'enfermeiro' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('enfermeiro.delete', {
            parent: 'enfermeiro',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/enfermeiro/enfermeiro-delete-dialog.html',
                    controller: 'EnfermeiroDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Enfermeiro', function(Enfermeiro) {
                            return Enfermeiro.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('enfermeiro', null, { reload: 'enfermeiro' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
