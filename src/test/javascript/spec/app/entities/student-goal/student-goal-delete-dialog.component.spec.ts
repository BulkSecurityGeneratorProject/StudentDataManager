/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { StudentDataManagerTestModule } from '../../../test.module';
import { StudentGoalDeleteDialogComponent } from 'app/entities/student-goal/student-goal-delete-dialog.component';
import { StudentGoalService } from 'app/entities/student-goal/student-goal.service';

describe('Component Tests', () => {
    describe('StudentGoal Management Delete Component', () => {
        let comp: StudentGoalDeleteDialogComponent;
        let fixture: ComponentFixture<StudentGoalDeleteDialogComponent>;
        let service: StudentGoalService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [StudentDataManagerTestModule],
                declarations: [StudentGoalDeleteDialogComponent]
            })
                .overrideTemplate(StudentGoalDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(StudentGoalDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StudentGoalService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
