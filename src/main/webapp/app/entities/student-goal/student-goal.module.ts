import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { StudentDataManagerSharedModule } from 'app/shared';
import {
    StudentGoalComponent,
    StudentGoalDetailComponent,
    StudentGoalUpdateComponent,
    StudentGoalDeletePopupComponent,
    StudentGoalDeleteDialogComponent,
    studentGoalRoute,
    studentGoalPopupRoute
} from './';

const ENTITY_STATES = [...studentGoalRoute, ...studentGoalPopupRoute];

@NgModule({
    imports: [StudentDataManagerSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        StudentGoalComponent,
        StudentGoalDetailComponent,
        StudentGoalUpdateComponent,
        StudentGoalDeleteDialogComponent,
        StudentGoalDeletePopupComponent
    ],
    entryComponents: [StudentGoalComponent, StudentGoalUpdateComponent, StudentGoalDeleteDialogComponent, StudentGoalDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class StudentDataManagerStudentGoalModule {}
