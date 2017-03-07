(function() {
    'use strict';

    angular
        .module('hospitalApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('medico', {
            parent: 'entity',
            url: '/medico',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Medicos'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/medico/medicos.html',
                    controller: 'MedicoController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('medico-detail', {
            parent: 'medico',
            url: '/medico/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Medico'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/medico/medico-detail.html',
                    controller: 'MedicoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Medico', function($stateParams, Medico) {
                    return Medico.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'medico',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('medico-detail.edit', {
            parent: 'medico-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/medico/medico-dialog.html',
                    controller: 'MedicoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Medico', function(Medico) {
                            return Medico.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('medico.new', {
            parent: 'medico',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/medico/medico-dialog.html',
                    controller: 'MedicoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                crm: null,
                                nome: null,
                                especialidade: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('medico', null, { reload: 'medico' });
                }, function() {
                    $state.go('medico');
                });
            }]
        })
        .state('medico.edit', {
            parent: 'medico',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/medico/medico-dialog.html',
                    controller: 'MedicoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Medico', function(Medico) {
                            return Medico.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('medico', null, { reload: 'medico' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('medico.delete', {
            parent: 'medico',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/medico/medico-delete-dialog.html',
                    controller: 'MedicoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Medico', function(Medico) {
                            return Medico.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('medico', null, { reload: 'medico' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
