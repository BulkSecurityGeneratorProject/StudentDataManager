import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { StudentGoal } from 'app/shared/model/student-goal.model';
import { StudentGoalService } from './student-goal.service';
import { StudentGoalComponent } from './student-goal.component';
import { StudentGoalDetailComponent } from './student-goal-detail.component';
import { StudentGoalUpdateComponent } from './student-goal-update.component';
import { StudentGoalDeletePopupComponent } from './student-goal-delete-dialog.component';
import { IStudentGoal } from 'app/shared/model/student-goal.model';

@Injectable({ providedIn: 'root' })
export class StudentGoalResolve implements Resolve<IStudentGoal> {
    constructor(private service: StudentGoalService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((studentGoal: HttpResponse<StudentGoal>) => studentGoal.body));
        }
        return of(new StudentGoal());
    }
}

export const studentGoalRoute: Routes = [
    {
        path: 'student-goal',
        component: StudentGoalComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'StudentGoals'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'student-goal/:id/view',
        component: StudentGoalDetailComponent,
        resolve: {
            studentGoal: StudentGoalResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'StudentGoals'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'student-goal/new',
        component: StudentGoalUpdateComponent,
        resolve: {
            studentGoal: StudentGoalResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'StudentGoals'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'student-goal/:id/edit',
        component: StudentGoalUpdateComponent,
        resolve: {
            studentGoal: StudentGoalResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'StudentGoals'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const studentGoalPopupRoute: Routes = [
    {
        path: 'student-goal/:id/delete',
        component: StudentGoalDeletePopupComponent,
        resolve: {
            studentGoal: StudentGoalResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'StudentGoals'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
