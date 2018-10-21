package com.mgnle.studentdatamanager.web.rest;

import com.mgnle.studentdatamanager.StudentDataManagerApp;

import com.mgnle.studentdatamanager.domain.Goal;
import com.mgnle.studentdatamanager.repository.GoalRepository;
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
 * Test class for the GoalResource REST controller.
 *
 * @see GoalResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = StudentDataManagerApp.class)
public class GoalResourceIntTest {

    private static final String DEFAULT_CATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY = "BBBBBBBBBB";

    private static final GoalTrackingMethod DEFAULT_TRACKING_METHOD = GoalTrackingMethod.DATA_COLLECTION;
    private static final GoalTrackingMethod UPDATED_TRACKING_METHOD = GoalTrackingMethod.WORK_SAMPLES;

    private static final String DEFAULT_OBJECTIVE = "AAAAAAAAAA";
    private static final String UPDATED_OBJECTIVE = "BBBBBBBBBB";

    private static final String DEFAULT_MASTERY_CRITERIA = "AAAAAAAAAA";
    private static final String UPDATED_MASTERY_CRITERIA = "BBBBBBBBBB";

    @Autowired
    private GoalRepository goalRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restGoalMockMvc;

    private Goal goal;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GoalResource goalResource = new GoalResource(goalRepository);
        this.restGoalMockMvc = MockMvcBuilders.standaloneSetup(goalResource)
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
    public static Goal createEntity(EntityManager em) {
        Goal goal = new Goal()
            .category(DEFAULT_CATEGORY)
            .trackingMethod(DEFAULT_TRACKING_METHOD)
            .objective(DEFAULT_OBJECTIVE)
            .masteryCriteria(DEFAULT_MASTERY_CRITERIA);
        return goal;
    }

    @Before
    public void initTest() {
        goal = createEntity(em);
    }

    @Test
    @Transactional
    public void createGoal() throws Exception {
        int databaseSizeBeforeCreate = goalRepository.findAll().size();

        // Create the Goal
        restGoalMockMvc.perform(post("/api/goals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(goal)))
            .andExpect(status().isCreated());

        // Validate the Goal in the database
        List<Goal> goalList = goalRepository.findAll();
        assertThat(goalList).hasSize(databaseSizeBeforeCreate + 1);
        Goal testGoal = goalList.get(goalList.size() - 1);
        assertThat(testGoal.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testGoal.getTrackingMethod()).isEqualTo(DEFAULT_TRACKING_METHOD);
        assertThat(testGoal.getObjective()).isEqualTo(DEFAULT_OBJECTIVE);
        assertThat(testGoal.getMasteryCriteria()).isEqualTo(DEFAULT_MASTERY_CRITERIA);
    }

    @Test
    @Transactional
    public void createGoalWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = goalRepository.findAll().size();

        // Create the Goal with an existing ID
        goal.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGoalMockMvc.perform(post("/api/goals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(goal)))
            .andExpect(status().isBadRequest());

        // Validate the Goal in the database
        List<Goal> goalList = goalRepository.findAll();
        assertThat(goalList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCategoryIsRequired() throws Exception {
        int databaseSizeBeforeTest = goalRepository.findAll().size();
        // set the field null
        goal.setCategory(null);

        // Create the Goal, which fails.

        restGoalMockMvc.perform(post("/api/goals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(goal)))
            .andExpect(status().isBadRequest());

        List<Goal> goalList = goalRepository.findAll();
        assertThat(goalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTrackingMethodIsRequired() throws Exception {
        int databaseSizeBeforeTest = goalRepository.findAll().size();
        // set the field null
        goal.setTrackingMethod(null);

        // Create the Goal, which fails.

        restGoalMockMvc.perform(post("/api/goals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(goal)))
            .andExpect(status().isBadRequest());

        List<Goal> goalList = goalRepository.findAll();
        assertThat(goalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkObjectiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = goalRepository.findAll().size();
        // set the field null
        goal.setObjective(null);

        // Create the Goal, which fails.

        restGoalMockMvc.perform(post("/api/goals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(goal)))
            .andExpect(status().isBadRequest());

        List<Goal> goalList = goalRepository.findAll();
        assertThat(goalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMasteryCriteriaIsRequired() throws Exception {
        int databaseSizeBeforeTest = goalRepository.findAll().size();
        // set the field null
        goal.setMasteryCriteria(null);

        // Create the Goal, which fails.

        restGoalMockMvc.perform(post("/api/goals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(goal)))
            .andExpect(status().isBadRequest());

        List<Goal> goalList = goalRepository.findAll();
        assertThat(goalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGoals() throws Exception {
        // Initialize the database
        goalRepository.saveAndFlush(goal);

        // Get all the goalList
        restGoalMockMvc.perform(get("/api/goals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(goal.getId().intValue())))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].trackingMethod").value(hasItem(DEFAULT_TRACKING_METHOD.toString())))
            .andExpect(jsonPath("$.[*].objective").value(hasItem(DEFAULT_OBJECTIVE.toString())))
            .andExpect(jsonPath("$.[*].masteryCriteria").value(hasItem(DEFAULT_MASTERY_CRITERIA.toString())));
    }
    
    @Test
    @Transactional
    public void getGoal() throws Exception {
        // Initialize the database
        goalRepository.saveAndFlush(goal);

        // Get the goal
        restGoalMockMvc.perform(get("/api/goals/{id}", goal.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(goal.getId().intValue()))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY.toString()))
            .andExpect(jsonPath("$.trackingMethod").value(DEFAULT_TRACKING_METHOD.toString()))
            .andExpect(jsonPath("$.objective").value(DEFAULT_OBJECTIVE.toString()))
            .andExpect(jsonPath("$.masteryCriteria").value(DEFAULT_MASTERY_CRITERIA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingGoal() throws Exception {
        // Get the goal
        restGoalMockMvc.perform(get("/api/goals/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGoal() throws Exception {
        // Initialize the database
        goalRepository.saveAndFlush(goal);

        int databaseSizeBeforeUpdate = goalRepository.findAll().size();

        // Update the goal
        Goal updatedGoal = goalRepository.findById(goal.getId()).get();
        // Disconnect from session so that the updates on updatedGoal are not directly saved in db
        em.detach(updatedGoal);
        updatedGoal
            .category(UPDATED_CATEGORY)
            .trackingMethod(UPDATED_TRACKING_METHOD)
            .objective(UPDATED_OBJECTIVE)
            .masteryCriteria(UPDATED_MASTERY_CRITERIA);

        restGoalMockMvc.perform(put("/api/goals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedGoal)))
            .andExpect(status().isOk());

        // Validate the Goal in the database
        List<Goal> goalList = goalRepository.findAll();
        assertThat(goalList).hasSize(databaseSizeBeforeUpdate);
        Goal testGoal = goalList.get(goalList.size() - 1);
        assertThat(testGoal.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testGoal.getTrackingMethod()).isEqualTo(UPDATED_TRACKING_METHOD);
        assertThat(testGoal.getObjective()).isEqualTo(UPDATED_OBJECTIVE);
        assertThat(testGoal.getMasteryCriteria()).isEqualTo(UPDATED_MASTERY_CRITERIA);
    }

    @Test
    @Transactional
    public void updateNonExistingGoal() throws Exception {
        int databaseSizeBeforeUpdate = goalRepository.findAll().size();

        // Create the Goal

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGoalMockMvc.perform(put("/api/goals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(goal)))
            .andExpect(status().isBadRequest());

        // Validate the Goal in the database
        List<Goal> goalList = goalRepository.findAll();
        assertThat(goalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGoal() throws Exception {
        // Initialize the database
        goalRepository.saveAndFlush(goal);

        int databaseSizeBeforeDelete = goalRepository.findAll().size();

        // Get the goal
        restGoalMockMvc.perform(delete("/api/goals/{id}", goal.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Goal> goalList = goalRepository.findAll();
        assertThat(goalList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Goal.class);
        Goal goal1 = new Goal();
        goal1.setId(1L);
        Goal goal2 = new Goal();
        goal2.setId(goal1.getId());
        assertThat(goal1).isEqualTo(goal2);
        goal2.setId(2L);
        assertThat(goal1).isNotEqualTo(goal2);
        goal1.setId(null);
        assertThat(goal1).isNotEqualTo(goal2);
    }
}
