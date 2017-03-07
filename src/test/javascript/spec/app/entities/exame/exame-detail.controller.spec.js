'use strict';

describe('Controller Tests', function() {

    describe('Exame Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockExame, MockAtendimento, MockPaciente, MockMedico;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockExame = jasmine.createSpy('MockExame');
            MockAtendimento = jasmine.createSpy('MockAtendimento');
            MockPaciente = jasmine.createSpy('MockPaciente');
            MockMedico = jasmine.createSpy('MockMedico');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Exame': MockExame,
                'Atendimento': MockAtendimento,
                'Paciente': MockPaciente,
                'Medico': MockMedico
            };
            createController = function() {
                $injector.get('$controller')("ExameDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'hospitalApp:exameUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
