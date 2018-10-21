package com.mgnle.studentdatamanager.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mgnle.studentdatamanager.domain.StudentGoal;
import com.mgnle.studentdatamanager.repository.StudentGoalRepository;
import com.mgnle.studentdatamanager.web.rest.errors.BadRequestAlertException;
import com.mgnle.studentdatamanager.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing StudentGoal.
 */
@RestController
@RequestMapping("/api")
public class StudentGoalResource {

    private final Logger log = LoggerFactory.getLogger(StudentGoalResource.class);

    private static final String ENTITY_NAME = "studentGoal";

    private StudentGoalRepository studentGoalRepository;

    public StudentGoalResource(StudentGoalRepository studentGoalRepository) {
        this.studentGoalRepository = studentGoalRepository;
    }

    /**
     * POST  /student-goals : Create a new studentGoal.
     *
     * @param studentGoal the studentGoal to create
     * @return the ResponseEntity with status 201 (Created) and with body the new studentGoal, or with status 400 (Bad Request) if the studentGoal has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/student-goals")
    @Timed
    public ResponseEntity<StudentGoal> createStudentGoal(@Valid @RequestBody StudentGoal studentGoal) throws URISyntaxException {
        log.debug("REST request to save StudentGoal : {}", studentGoal);
        if (studentGoal.getId() != null) {
            throw new BadRequestAlertException("A new studentGoal cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StudentGoal result = studentGoalRepository.save(studentGoal);
        return ResponseEntity.created(new URI("/api/student-goals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /student-goals : Updates an existing studentGoal.
     *
     * @param studentGoal the studentGoal to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated studentGoal,
     * or with status 400 (Bad Request) if the studentGoal is not valid,
     * or with status 500 (Internal Server Error) if the studentGoal couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/student-goals")
    @Timed
    public ResponseEntity<StudentGoal> updateStudentGoal(@Valid @RequestBody StudentGoal studentGoal) throws URISyntaxException {
        log.debug("REST request to update StudentGoal : {}", studentGoal);
        if (studentGoal.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StudentGoal result = studentGoalRepository.save(studentGoal);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, studentGoal.getId().toString()))
            .body(result);
    }

    /**
     * GET  /student-goals : get all the studentGoals.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of studentGoals in body
     */
    @GetMapping("/student-goals")
    @Timed
    public List<StudentGoal> getAllStudentGoals() {
        log.debug("REST request to get all StudentGoals");
        return studentGoalRepository.findAll();
    }

    /**
     * GET  /student-goals/:id : get the "id" studentGoal.
     *
     * @param id the id of the studentGoal to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the studentGoal, or with status 404 (Not Found)
     */
    @GetMapping("/student-goals/{id}")
    @Timed
    public ResponseEntity<StudentGoal> getStudentGoal(@PathVariable Long id) {
        log.debug("REST request to get StudentGoal : {}", id);
        Optional<StudentGoal> studentGoal = studentGoalRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(studentGoal);
    }

    /**
     * DELETE  /student-goals/:id : delete the "id" studentGoal.
     *
     * @param id the id of the studentGoal to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/student-goals/{id}")
    @Timed
    public ResponseEntity<Void> deleteStudentGoal(@PathVariable Long id) {
        log.debug("REST request to delete StudentGoal : {}", id);

        studentGoalRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
