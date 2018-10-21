import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { StudentDataManagerSharedModule } from 'app/shared';
import {
    GoalComponent,
    GoalDetailComponent,
    GoalUpdateComponent,
    GoalDeletePopupComponent,
    GoalDeleteDialogComponent,
    goalRoute,
    goalPopupRoute
} from './';

const ENTITY_STATES = [...goalRoute, ...goalPopupRoute];

@NgModule({
    imports: [StudentDataManagerSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [GoalComponent, GoalDetailComponent, GoalUpdateComponent, GoalDeleteDialogComponent, GoalDeletePopupComponent],
    entryComponents: [GoalComponent, GoalUpdateComponent, GoalDeleteDialogComponent, GoalDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class StudentDataManagerGoalModule {}
