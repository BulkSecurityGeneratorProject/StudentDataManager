import { NgModule } from '@angular/core';

import { StudentDataManagerSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [StudentDataManagerSharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [StudentDataManagerSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class StudentDataManagerSharedCommonModule {}
