import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { StudentDataManagerStudentModule } from './student/student.module';
import { StudentDataManagerStudentGoalModule } from './student-goal/student-goal.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        StudentDataManagerStudentModule,
        StudentDataManagerStudentGoalModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class StudentDataManagerEntityModule {}
