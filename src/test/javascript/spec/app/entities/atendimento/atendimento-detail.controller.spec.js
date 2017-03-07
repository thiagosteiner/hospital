'use strict';

describe('Controller Tests', function() {

    describe('Atendimento Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockAtendimento, MockExame, MockMedico, MockEnfermeiro, MockPaciente;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockAtendimento = jasmine.createSpy('MockAtendimento');
            MockExame = jasmine.createSpy('MockExame');
            MockMedico = jasmine.createSpy('MockMedico');
            MockEnfermeiro = jasmine.createSpy('MockEnfermeiro');
            MockPaciente = jasmine.createSpy('MockPaciente');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Atendimento': MockAtendimento,
                'Exame': MockExame,
                'Medico': MockMedico,
                'Enfermeiro': MockEnfermeiro,
                'Paciente': MockPaciente
            };
            createController = function() {
                $injector.get('$controller')("AtendimentoDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'hospitalApp:atendimentoUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
