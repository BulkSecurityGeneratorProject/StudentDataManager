import { IStudent } from 'app/shared/model//student.model';

export const enum GoalTrackingMethod {
    DATA_COLLECTION = 'DATA_COLLECTION',
    WORK_SAMPLES = 'WORK_SAMPLES'
}

export interface IStudentGoal {
    id?: number;
    category?: string;
    trackingMethod?: GoalTrackingMethod;
    objective?: string;
    masteryCriteria?: string;
    academicYear?: number;
    active?: boolean;
    mastered?: boolean;
    progressReportsCompleted?: number;
    student?: IStudent;
}

export class StudentGoal implements IStudentGoal {
    constructor(
        public id?: number,
        public category?: string,
        public trackingMethod?: GoalTrackingMethod,
        public objective?: string,
        public masteryCriteria?: string,
        public academicYear?: number,
        public active?: boolean,
        public mastered?: boolean,
        public progressReportsCompleted?: number,
        public student?: IStudent
    ) {
        this.active = this.active || false;
        this.mastered = this.mastered || false;
    }
}
