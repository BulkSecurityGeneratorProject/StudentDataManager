package com.mgnle.studentdatamanager.web.rest;

import com.mgnle.studentdatamanager.StudentDataManagerApp;

import com.mgnle.studentdatamanager.domain.StudentGoal;
import com.mgnle.studentdatamanager.domain.Student;
import com.mgnle.studentdatamanager.repository.StudentGoalRepository;
import com.mgnle.studentdatamanager.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;


import static com.mgnle.studentdatamanager.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mgnle.studentdatamanager.domain.enumeration.GoalTrackingMethod;
/**
 * Test class for the StudentGoalResource REST controller.
 *
 * @see StudentGoalResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = StudentDataManagerApp.class)
public class StudentGoalResourceIntTest {

    private static final String DEFAULT_CATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY = "BBBBBBBBBB";

    private static final GoalTrackingMethod DEFAULT_TRACKING_METHOD = GoalTrackingMethod.DATA_COLLECTION;
    private static final GoalTrackingMethod UPDATED_TRACKING_METHOD = GoalTrackingMethod.WORK_SAMPLES;

    private static final String DEFAULT_OBJECTIVE = "AAAAAAAAAA";
    private static final String UPDATED_OBJECTIVE = "BBBBBBBBBB";

    private static final String DEFAULT_MASTERY_CRITERIA = "AAAAAAAAAA";
    private static final String UPDATED_MASTERY_CRITERIA = "BBBBBBBBBB";

    private static final Integer DEFAULT_ACADEMIC_YEAR = 1900;
    private static final Integer UPDATED_ACADEMIC_YEAR = 1901;

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final Boolean DEFAULT_MASTERED = false;
    private static final Boolean UPDATED_MASTERED = true;

    private static final Integer DEFAULT_PROGRESS_REPORTS_COMPLETED = 0;
    private static final Integer UPDATED_PROGRESS_REPORTS_COMPLETED = 1;

    @Autowired
    private StudentGoalRepository studentGoalRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restStudentGoalMockMvc;

    private StudentGoal studentGoal;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StudentGoalResource studentGoalResource = new StudentGoalResource(studentGoalRepository);
        this.restStudentGoalMockMvc = MockMvcBuilders.standaloneSetup(studentGoalResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StudentGoal createEntity(EntityManager em) {
        StudentGoal studentGoal = new StudentGoal()
            .category(DEFAULT_CATEGORY)
            .trackingMethod(DEFAULT_TRACKING_METHOD)
            .objective(DEFAULT_OBJECTIVE)
            .masteryCriteria(DEFAULT_MASTERY_CRITERIA)
            .academicYear(DEFAULT_ACADEMIC_YEAR)
            .active(DEFAULT_ACTIVE)
            .mastered(DEFAULT_MASTERED)
            .progressReportsCompleted(DEFAULT_PROGRESS_REPORTS_COMPLETED);
        // Add required entity
        Student student = StudentResourceIntTest.createEntity(em);
        em.persist(student);
        em.flush();
        studentGoal.setStudent(student);
        return studentGoal;
    }

    @Before
    public void initTest() {
        studentGoal = createEntity(em);
    }

    @Test
    @Transactional
    public void createStudentGoal() throws Exception {
        int databaseSizeBeforeCreate = studentGoalRepository.findAll().size();

        // Create the StudentGoal
        restStudentGoalMockMvc.perform(post("/api/student-goals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentGoal)))
            .andExpect(status().isCreated());

        // Validate the StudentGoal in the database
        List<StudentGoal> studentGoalList = studentGoalRepository.findAll();
        assertThat(studentGoalList).hasSize(databaseSizeBeforeCreate + 1);
        StudentGoal testStudentGoal = studentGoalList.get(studentGoalList.size() - 1);
        assertThat(testStudentGoal.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testStudentGoal.getTrackingMethod()).isEqualTo(DEFAULT_TRACKING_METHOD);
        assertThat(testStudentGoal.getObjective()).isEqualTo(DEFAULT_OBJECTIVE);
        assertThat(testStudentGoal.getMasteryCriteria()).isEqualTo(DEFAULT_MASTERY_CRITERIA);
        assertThat(testStudentGoal.getAcademicYear()).isEqualTo(DEFAULT_ACADEMIC_YEAR);
        assertThat(testStudentGoal.isActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testStudentGoal.isMastered()).isEqualTo(DEFAULT_MASTERED);
        assertThat(testStudentGoal.getProgressReportsCompleted()).isEqualTo(DEFAULT_PROGRESS_REPORTS_COMPLETED);
    }

    @Test
    @Transactional
    public void createStudentGoalWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = studentGoalRepository.findAll().size();

        // Create the StudentGoal with an existing ID
        studentGoal.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStudentGoalMockMvc.perform(post("/api/student-goals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentGoal)))
            .andExpect(status().isBadRequest());

        // Validate the StudentGoal in the database
        List<StudentGoal> studentGoalList = studentGoalRepository.findAll();
        assertThat(studentGoalList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCategoryIsRequired() throws Exception {
        int databaseSizeBeforeTest = studentGoalRepository.findAll().size();
        // set the field null
        studentGoal.setCategory(null);

        // Create the StudentGoal, which fails.

        restStudentGoalMockMvc.perform(post("/api/student-goals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentGoal)))
            .andExpect(status().isBadRequest());

        List<StudentGoal> studentGoalList = studentGoalRepository.findAll();
        assertThat(studentGoalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTrackingMethodIsRequired() throws Exception {
        int databaseSizeBeforeTest = studentGoalRepository.findAll().size();
        // set the field null
        studentGoal.setTrackingMethod(null);

        // Create the StudentGoal, which fails.

        restStudentGoalMockMvc.perform(post("/api/student-goals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentGoal)))
            .andExpect(status().isBadRequest());

        List<StudentGoal> studentGoalList = studentGoalRepository.findAll();
        assertThat(studentGoalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkObjectiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = studentGoalRepository.findAll().size();
        // set the field null
        studentGoal.setObjective(null);

        // Create the StudentGoal, which fails.

        restStudentGoalMockMvc.perform(post("/api/student-goals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentGoal)))
            .andExpect(status().isBadRequest());

        List<StudentGoal> studentGoalList = studentGoalRepository.findAll();
        assertThat(studentGoalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMasteryCriteriaIsRequired() throws Exception {
        int databaseSizeBeforeTest = studentGoalRepository.findAll().size();
        // set the field null
        studentGoal.setMasteryCriteria(null);

        // Create the StudentGoal, which fails.

        restStudentGoalMockMvc.perform(post("/api/student-goals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentGoal)))
            .andExpect(status().isBadRequest());

        List<StudentGoal> studentGoalList = studentGoalRepository.findAll();
        assertThat(studentGoalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAcademicYearIsRequired() throws Exception {
        int databaseSizeBeforeTest = studentGoalRepository.findAll().size();
        // set the field null
        studentGoal.setAcademicYear(null);

        // Create the StudentGoal, which fails.

        restStudentGoalMockMvc.perform(post("/api/student-goals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentGoal)))
            .andExpect(status().isBadRequest());

        List<StudentGoal> studentGoalList = studentGoalRepository.findAll();
        assertThat(studentGoalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = studentGoalRepository.findAll().size();
        // set the field null
        studentGoal.setActive(null);

        // Create the StudentGoal, which fails.

        restStudentGoalMockMvc.perform(post("/api/student-goals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentGoal)))
            .andExpect(status().isBadRequest());

        List<StudentGoal> studentGoalList = studentGoalRepository.findAll();
        assertThat(studentGoalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMasteredIsRequired() throws Exception {
        int databaseSizeBeforeTest = studentGoalRepository.findAll().size();
        // set the field null
        studentGoal.setMastered(null);

        // Create the StudentGoal, which fails.

        restStudentGoalMockMvc.perform(post("/api/student-goals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentGoal)))
            .andExpect(status().isBadRequest());

        List<StudentGoal> studentGoalList = studentGoalRepository.findAll();
        assertThat(studentGoalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkProgressReportsCompletedIsRequired() throws Exception {
        int databaseSizeBeforeTest = studentGoalRepository.findAll().size();
        // set the field null
        studentGoal.setProgressReportsCompleted(null);

        // Create the StudentGoal, which fails.

        restStudentGoalMockMvc.perform(post("/api/student-goals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentGoal)))
            .andExpect(status().isBadRequest());

        List<StudentGoal> studentGoalList = studentGoalRepository.findAll();
        assertThat(studentGoalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStudentGoals() throws Exception {
        // Initialize the database
        studentGoalRepository.saveAndFlush(studentGoal);

        // Get all the studentGoalList
        restStudentGoalMockMvc.perform(get("/api/student-goals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(studentGoal.getId().intValue())))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].trackingMethod").value(hasItem(DEFAULT_TRACKING_METHOD.toString())))
            .andExpect(jsonPath("$.[*].objective").value(hasItem(DEFAULT_OBJECTIVE.toString())))
            .andExpect(jsonPath("$.[*].masteryCriteria").value(hasItem(DEFAULT_MASTERY_CRITERIA.toString())))
            .andExpect(jsonPath("$.[*].academicYear").value(hasItem(DEFAULT_ACADEMIC_YEAR)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].mastered").value(hasItem(DEFAULT_MASTERED.booleanValue())))
            .andExpect(jsonPath("$.[*].progressReportsCompleted").value(hasItem(DEFAULT_PROGRESS_REPORTS_COMPLETED)));
    }
    
    @Test
    @Transactional
    public void getStudentGoal() throws Exception {
        // Initialize the database
        studentGoalRepository.saveAndFlush(studentGoal);

        // Get the studentGoal
        restStudentGoalMockMvc.perform(get("/api/student-goals/{id}", studentGoal.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(studentGoal.getId().intValue()))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY.toString()))
            .andExpect(jsonPath("$.trackingMethod").value(DEFAULT_TRACKING_METHOD.toString()))
            .andExpect(jsonPath("$.objective").value(DEFAULT_OBJECTIVE.toString()))
            .andExpect(jsonPath("$.masteryCriteria").value(DEFAULT_MASTERY_CRITERIA.toString()))
            .andExpect(jsonPath("$.academicYear").value(DEFAULT_ACADEMIC_YEAR))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.mastered").value(DEFAULT_MASTERED.booleanValue()))
            .andExpect(jsonPath("$.progressReportsCompleted").value(DEFAULT_PROGRESS_REPORTS_COMPLETED));
    }

    @Test
    @Transactional
    public void getNonExistingStudentGoal() throws Exception {
        // Get the studentGoal
        restStudentGoalMockMvc.perform(get("/api/student-goals/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStudentGoal() throws Exception {
        // Initialize the database
        studentGoalRepository.saveAndFlush(studentGoal);

        int databaseSizeBeforeUpdate = studentGoalRepository.findAll().size();

        // Update the studentGoal
        StudentGoal updatedStudentGoal = studentGoalRepository.findById(studentGoal.getId()).get();
        // Disconnect from session so that the updates on updatedStudentGoal are not directly saved in db
        em.detach(updatedStudentGoal);
        updatedStudentGoal
            .category(UPDATED_CATEGORY)
            .trackingMethod(UPDATED_TRACKING_METHOD)
            .objective(UPDATED_OBJECTIVE)
            .masteryCriteria(UPDATED_MASTERY_CRITERIA)
            .academicYear(UPDATED_ACADEMIC_YEAR)
            .active(UPDATED_ACTIVE)
            .mastered(UPDATED_MASTERED)
            .progressReportsCompleted(UPDATED_PROGRESS_REPORTS_COMPLETED);

        restStudentGoalMockMvc.perform(put("/api/student-goals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedStudentGoal)))
            .andExpect(status().isOk());

        // Validate the StudentGoal in the database
        List<StudentGoal> studentGoalList = studentGoalRepository.findAll();
        assertThat(studentGoalList).hasSize(databaseSizeBeforeUpdate);
        StudentGoal testStudentGoal = studentGoalList.get(studentGoalList.size() - 1);
        assertThat(testStudentGoal.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testStudentGoal.getTrackingMethod()).isEqualTo(UPDATED_TRACKING_METHOD);
        assertThat(testStudentGoal.getObjective()).isEqualTo(UPDATED_OBJECTIVE);
        assertThat(testStudentGoal.getMasteryCriteria()).isEqualTo(UPDATED_MASTERY_CRITERIA);
        assertThat(testStudentGoal.getAcademicYear()).isEqualTo(UPDATED_ACADEMIC_YEAR);
        assertThat(testStudentGoal.isActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testStudentGoal.isMastered()).isEqualTo(UPDATED_MASTERED);
        assertThat(testStudentGoal.getProgressReportsCompleted()).isEqualTo(UPDATED_PROGRESS_REPORTS_COMPLETED);
    }

    @Test
    @Transactional
    public void updateNonExistingStudentGoal() throws Exception {
        int databaseSizeBeforeUpdate = studentGoalRepository.findAll().size();

        // Create the StudentGoal

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStudentGoalMockMvc.perform(put("/api/student-goals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentGoal)))
            .andExpect(status().isBadRequest());

        // Validate the StudentGoal in the database
        List<StudentGoal> studentGoalList = studentGoalRepository.findAll();
        assertThat(studentGoalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStudentGoal() throws Exception {
        // Initialize the database
        studentGoalRepository.saveAndFlush(studentGoal);

        int databaseSizeBeforeDelete = studentGoalRepository.findAll().size();

        // Get the studentGoal
        restStudentGoalMockMvc.perform(delete("/api/student-goals/{id}", studentGoal.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<StudentGoal> studentGoalList = studentGoalRepository.findAll();
        assertThat(studentGoalList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StudentGoal.class);
        StudentGoal studentGoal1 = new StudentGoal();
        studentGoal1.setId(1L);
        StudentGoal studentGoal2 = new StudentGoal();
        studentGoal2.setId(studentGoal1.getId());
        assertThat(studentGoal1).isEqualTo(studentGoal2);
        studentGoal2.setId(2L);
        assertThat(studentGoal1).isNotEqualTo(studentGoal2);
        studentGoal1.setId(null);
        assertThat(studentGoal1).isNotEqualTo(studentGoal2);
    }
}
