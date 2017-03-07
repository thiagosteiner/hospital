(function() {
    'use strict';

    angular
        .module('hospitalApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('exame', {
            parent: 'entity',
            url: '/exame',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Exames'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/exame/exames.html',
                    controller: 'ExameController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('exame-detail', {
            parent: 'exame',
            url: '/exame/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Exame'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/exame/exame-detail.html',
                    controller: 'ExameDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Exame', function($stateParams, Exame) {
                    return Exame.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'exame',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('exame-detail.edit', {
            parent: 'exame-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/exame/exame-dialog.html',
                    controller: 'ExameDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Exame', function(Exame) {
                            return Exame.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('exame.new', {
            parent: 'exame',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/exame/exame-dialog.html',
                    controller: 'ExameDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nome: null,
                                laudo: null,
                                dataSolicitacao: null,
                                dataLaudo: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('exame', null, { reload: 'exame' });
                }, function() {
                    $state.go('exame');
                });
            }]
        })
        .state('exame.edit', {
            parent: 'exame',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/exame/exame-dialog.html',
                    controller: 'ExameDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Exame', function(Exame) {
                            return Exame.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('exame', null, { reload: 'exame' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('exame.delete', {
            parent: 'exame',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/exame/exame-delete-dialog.html',
                    controller: 'ExameDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Exame', function(Exame) {
                            return Exame.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('exame', null, { reload: 'exame' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
