/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { StudentDataManagerTestModule } from '../../../test.module';
import { StudentGoalUpdateComponent } from 'app/entities/student-goal/student-goal-update.component';
import { StudentGoalService } from 'app/entities/student-goal/student-goal.service';
import { StudentGoal } from 'app/shared/model/student-goal.model';

describe('Component Tests', () => {
    describe('StudentGoal Management Update Component', () => {
        let comp: StudentGoalUpdateComponent;
        let fixture: ComponentFixture<StudentGoalUpdateComponent>;
        let service: StudentGoalService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [StudentDataManagerTestModule],
                declarations: [StudentGoalUpdateComponent]
            })
                .overrideTemplate(StudentGoalUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(StudentGoalUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StudentGoalService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new StudentGoal(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.studentGoal = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new StudentGoal();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.studentGoal = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
