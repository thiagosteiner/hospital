(function() {
    'use strict';

    angular
        .module('hospitalApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('atendimento', {
            parent: 'entity',
            url: '/atendimento',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Atendimentos'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/atendimento/atendimentos.html',
                    controller: 'AtendimentoController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('atendimento-detail', {
            parent: 'atendimento',
            url: '/atendimento/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Atendimento'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/atendimento/atendimento-detail.html',
                    controller: 'AtendimentoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Atendimento', function($stateParams, Atendimento) {
                    return Atendimento.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'atendimento',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('atendimento-detail.edit', {
            parent: 'atendimento-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/atendimento/atendimento-dialog.html',
                    controller: 'AtendimentoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Atendimento', function(Atendimento) {
                            return Atendimento.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('atendimento.new', {
            parent: 'atendimento',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/atendimento/atendimento-dialog.html',
                    controller: 'AtendimentoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                data: null,
                                descricao: null,
                                prescricao: null,
                                tipoAtendimento: null,
                                classificacaoRisco: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('atendimento', null, { reload: 'atendimento' });
                }, function() {
                    $state.go('atendimento');
                });
            }]
        })
        .state('atendimento.edit', {
            parent: 'atendimento',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/atendimento/atendimento-dialog.html',
                    controller: 'AtendimentoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Atendimento', function(Atendimento) {
                            return Atendimento.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('atendimento', null, { reload: 'atendimento' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('atendimento.delete', {
            parent: 'atendimento',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/atendimento/atendimento-delete-dialog.html',
                    controller: 'AtendimentoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Atendimento', function(Atendimento) {
                            return Atendimento.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('atendimento', null, { reload: 'atendimento' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
