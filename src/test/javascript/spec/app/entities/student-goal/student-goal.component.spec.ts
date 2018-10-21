/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { StudentDataManagerTestModule } from '../../../test.module';
import { StudentGoalComponent } from 'app/entities/student-goal/student-goal.component';
import { StudentGoalService } from 'app/entities/student-goal/student-goal.service';
import { StudentGoal } from 'app/shared/model/student-goal.model';

describe('Component Tests', () => {
    describe('StudentGoal Management Component', () => {
        let comp: StudentGoalComponent;
        let fixture: ComponentFixture<StudentGoalComponent>;
        let service: StudentGoalService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [StudentDataManagerTestModule],
                declarations: [StudentGoalComponent],
                providers: []
            })
                .overrideTemplate(StudentGoalComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(StudentGoalComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StudentGoalService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new StudentGoal(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.studentGoals[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
