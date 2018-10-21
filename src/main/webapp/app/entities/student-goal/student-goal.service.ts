import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IStudentGoal } from 'app/shared/model/student-goal.model';

type EntityResponseType = HttpResponse<IStudentGoal>;
type EntityArrayResponseType = HttpResponse<IStudentGoal[]>;

@Injectable({ providedIn: 'root' })
export class StudentGoalService {
    public resourceUrl = SERVER_API_URL + 'api/student-goals';

    constructor(private http: HttpClient) {}

    create(studentGoal: IStudentGoal): Observable<EntityResponseType> {
        return this.http.post<IStudentGoal>(this.resourceUrl, studentGoal, { observe: 'response' });
    }

    update(studentGoal: IStudentGoal): Observable<EntityResponseType> {
        return this.http.put<IStudentGoal>(this.resourceUrl, studentGoal, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IStudentGoal>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IStudentGoal[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
