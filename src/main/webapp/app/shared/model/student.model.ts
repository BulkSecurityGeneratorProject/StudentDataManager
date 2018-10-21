export interface IStudent {
    id?: number;
    firstName?: string;
    middleName?: string;
    lastName?: string;
    currentGradeLevel?: number;
    active?: boolean;
}

export class Student implements IStudent {
    constructor(
        public id?: number,
        public firstName?: string,
        public middleName?: string,
        public lastName?: string,
        public currentGradeLevel?: number,
        public active?: boolean
    ) {
        this.active = this.active || false;
    }
}
