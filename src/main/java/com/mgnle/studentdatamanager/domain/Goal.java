package com.mgnle.studentdatamanager.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

import com.mgnle.studentdatamanager.domain.enumeration.GoalTrackingMethod;

/**
 * A Goal.
 */
@Entity
@Table(name = "goal")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Goal extends AbstractAuditingEntity implements Serializable {

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

    public Goal category(String category) {
        this.category = category;
        return this;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public GoalTrackingMethod getTrackingMethod() {
        return trackingMethod;
    }

    public Goal trackingMethod(GoalTrackingMethod trackingMethod) {
        this.trackingMethod = trackingMethod;
        return this;
    }

    public void setTrackingMethod(GoalTrackingMethod trackingMethod) {
        this.trackingMethod = trackingMethod;
    }

    public String getObjective() {
        return objective;
    }

    public Goal objective(String objective) {
        this.objective = objective;
        return this;
    }

    public void setObjective(String objective) {
        this.objective = objective;
    }

    public String getMasteryCriteria() {
        return masteryCriteria;
    }

    public Goal masteryCriteria(String masteryCriteria) {
        this.masteryCriteria = masteryCriteria;
        return this;
    }

    public void setMasteryCriteria(String masteryCriteria) {
        this.masteryCriteria = masteryCriteria;
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
        Goal goal = (Goal) o;
        if (goal.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), goal.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Goal{" +
            "id=" + getId() +
            ", category='" + getCategory() + "'" +
            ", trackingMethod='" + getTrackingMethod() + "'" +
            ", objective='" + getObjective() + "'" +
            ", masteryCriteria='" + getMasteryCriteria() + "'" +
            "}";
    }
}
