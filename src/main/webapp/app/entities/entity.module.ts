import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { StudentDataManagerStudentModule } from './student/student.module';
import { StudentDataManagerGoalModule } from './goal/goal.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        StudentDataManagerStudentModule,
        StudentDataManagerGoalModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class StudentDataManagerEntityModule {}
