import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IStudentGoal } from 'app/shared/model/student-goal.model';

@Component({
    selector: 'jhi-student-goal-detail',
    templateUrl: './student-goal-detail.component.html'
})
export class StudentGoalDetailComponent implements OnInit {
    studentGoal: IStudentGoal;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ studentGoal }) => {
            this.studentGoal = studentGoal;
        });
    }

    previousState() {
        window.history.back();
    }
}
