import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IStudentGoal } from 'app/shared/model/student-goal.model';
import { StudentGoalService } from './student-goal.service';

@Component({
    selector: 'jhi-student-goal-delete-dialog',
    templateUrl: './student-goal-delete-dialog.component.html'
})
export class StudentGoalDeleteDialogComponent {
    studentGoal: IStudentGoal;

    constructor(
        private studentGoalService: StudentGoalService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.studentGoalService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'studentGoalListModification',
                content: 'Deleted an studentGoal'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-student-goal-delete-popup',
    template: ''
})
export class StudentGoalDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ studentGoal }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(StudentGoalDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.studentGoal = studentGoal;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
