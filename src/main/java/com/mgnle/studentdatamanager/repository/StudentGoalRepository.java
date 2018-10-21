package com.mgnle.studentdatamanager.repository;

import com.mgnle.studentdatamanager.domain.StudentGoal;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the StudentGoal entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StudentGoalRepository extends JpaRepository<StudentGoal, Long> {

}
