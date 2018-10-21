package com.mgnle.studentdatamanager.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

import com.mgnle.studentdatamanager.domain.enumeration.GoalTrackingMethod;

/**
 * A StudentGoal.
 */
@Entity
@Table(name = "student_goal")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class StudentGoal extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "category", nullable = false)
    private String category;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tracking_method", nullable = false)
    private GoalTrackingMethod trackingMethod;

    @NotNull
    @Column(name = "objective", nullable = false)
    private String objective;

    @NotNull
    @Column(name = "mastery_criteria", nullable = false)
    private String masteryCriteria;

    @NotNull
    @Min(value = 1900)
    @Max(value = 9999)
    @Column(name = "academic_year", nullable = false)
    private Integer academicYear;

    @NotNull
    @Column(name = "active", nullable = false)
    private Boolean active;

    @NotNull
    @Column(name = "mastered", nullable = false)
    private Boolean mastered;

    @NotNull
    @Min(value = 0)
    @Column(name = "progress_reports_completed", nullable = false)
    private Integer progressReportsCompleted;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("")
    private Student student;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public StudentGoal category(String category) {
        this.category = category;
        return this;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public GoalTrackingMethod getTrackingMethod() {
        return trackingMethod;
    }

    public StudentGoal trackingMethod(GoalTrackingMethod trackingMethod) {
        this.trackingMethod = trackingMethod;
        return this;
    }

    public void setTrackingMethod(GoalTrackingMethod trackingMethod) {
        this.trackingMethod = trackingMethod;
    }

    public String getObjective() {
        return objective;
    }

    public StudentGoal objective(String objective) {
        this.objective = objective;
        return this;
    }

    public void setObjective(String objective) {
        this.objective = objective;
    }

    public String getMasteryCriteria() {
        return masteryCriteria;
    }

    public StudentGoal masteryCriteria(String masteryCriteria) {
        this.masteryCriteria = masteryCriteria;
        return this;
    }

    public void setMasteryCriteria(String masteryCriteria) {
        this.masteryCriteria = masteryCriteria;
    }

    public Integer getAcademicYear() {
        return academicYear;
    }

    public StudentGoal academicYear(Integer academicYear) {
        this.academicYear = academicYear;
        return this;
    }

    public void setAcademicYear(Integer academicYear) {
        this.academicYear = academicYear;
    }

    public Boolean isActive() {
        return active;
    }

    public StudentGoal active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean isMastered() {
        return mastered;
    }

    public StudentGoal mastered(Boolean mastered) {
        this.mastered = mastered;
        return this;
    }

    public void setMastered(Boolean mastered) {
        this.mastered = mastered;
    }

    public Integer getProgressReportsCompleted() {
        return progressReportsCompleted;
    }

    public StudentGoal progressReportsCompleted(Integer progressReportsCompleted) {
        this.progressReportsCompleted = progressReportsCompleted;
        return this;
    }

    public void setProgressReportsCompleted(Integer progressReportsCompleted) {
        this.progressReportsCompleted = progressReportsCompleted;
    }

    public Student getStudent() {
        return student;
    }

    public StudentGoal student(Student student) {
        this.student = student;
        return this;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        StudentGoal studentGoal = (StudentGoal) o;
        if (studentGoal.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), studentGoal.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "StudentGoal{" +
            "id=" + getId() +
            ", category='" + getCategory() + "'" +
            ", trackingMethod='" + getTrackingMethod() + "'" +
            ", objective='" + getObjective() + "'" +
            ", masteryCriteria='" + getMasteryCriteria() + "'" +
            ", academicYear=" + getAcademicYear() +
            ", active='" + isActive() + "'" +
            ", mastered='" + isMastered() + "'" +
            ", progressReportsCompleted=" + getProgressReportsCompleted() +
            "}";
    }
}
