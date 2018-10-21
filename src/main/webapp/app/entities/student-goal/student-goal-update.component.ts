import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IStudentGoal } from 'app/shared/model/student-goal.model';
import { StudentGoalService } from './student-goal.service';
import { IStudent } from 'app/shared/model/student.model';
import { StudentService } from 'app/entities/student';

@Component({
    selector: 'jhi-student-goal-update',
    templateUrl: './student-goal-update.component.html'
})
export class StudentGoalUpdateComponent implements OnInit {
    studentGoal: IStudentGoal;
    isSaving: boolean;

    students: IStudent[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private studentGoalService: StudentGoalService,
        private studentService: StudentService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ studentGoal }) => {
            this.studentGoal = studentGoal;
        });
        this.studentService.query().subscribe(
            (res: HttpResponse<IStudent[]>) => {
                this.students = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.studentGoal.id !== undefined) {
            this.subscribeToSaveResponse(this.studentGoalService.update(this.studentGoal));
        } else {
            this.subscribeToSaveResponse(this.studentGoalService.create(this.studentGoal));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IStudentGoal>>) {
        result.subscribe((res: HttpResponse<IStudentGoal>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackStudentById(index: number, item: IStudent) {
        return item.id;
    }
}
