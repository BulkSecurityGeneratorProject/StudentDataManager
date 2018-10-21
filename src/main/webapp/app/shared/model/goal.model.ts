export const enum GoalTrackingMethod {
    DATA_COLLECTION = 'DATA_COLLECTION',
    WORK_SAMPLES = 'WORK_SAMPLES'
}

export interface IGoal {
    id?: number;
    category?: string;
    trackingMethod?: GoalTrackingMethod;
    objective?: string;
    masteryCriteria?: string;
}

export class Goal implements IGoal {
    constructor(
        public id?: number,
        public category?: string,
        public trackingMethod?: GoalTrackingMethod,
        public objective?: string,
        public masteryCriteria?: string
    ) {}
}
