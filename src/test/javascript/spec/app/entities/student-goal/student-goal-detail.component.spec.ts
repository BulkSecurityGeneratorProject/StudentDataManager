/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { StudentDataManagerTestModule } from '../../../test.module';
import { StudentGoalDetailComponent } from 'app/entities/student-goal/student-goal-detail.component';
import { StudentGoal } from 'app/shared/model/student-goal.model';

describe('Component Tests', () => {
    describe('StudentGoal Management Detail Component', () => {
        let comp: StudentGoalDetailComponent;
        let fixture: ComponentFixture<StudentGoalDetailComponent>;
        const route = ({ data: of({ studentGoal: new StudentGoal(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [StudentDataManagerTestModule],
                declarations: [StudentGoalDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(StudentGoalDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(StudentGoalDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.studentGoal).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
