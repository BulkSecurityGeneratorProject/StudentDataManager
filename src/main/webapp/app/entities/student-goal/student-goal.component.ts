import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IStudentGoal } from 'app/shared/model/student-goal.model';
import { Principal } from 'app/core';
import { StudentGoalService } from './student-goal.service';

@Component({
    selector: 'jhi-student-goal',
    templateUrl: './student-goal.component.html'
})
export class StudentGoalComponent implements OnInit, OnDestroy {
    studentGoals: IStudentGoal[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private studentGoalService: StudentGoalService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.studentGoalService.query().subscribe(
            (res: HttpResponse<IStudentGoal[]>) => {
                this.studentGoals = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInStudentGoals();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IStudentGoal) {
        return item.id;
    }

    registerChangeInStudentGoals() {
        this.eventSubscriber = this.eventManager.subscribe('studentGoalListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
