package com.fitLifeBuddy.Controller;

import com.fitLifeBuddy.Entity.*;
import com.fitLifeBuddy.Entity.DTO.PlanDTO;
import com.fitLifeBuddy.Entity.DTO.PredictionResponse;
import com.fitLifeBuddy.Entity.Enum.*;
import com.fitLifeBuddy.Service.*;
import com.fitLifeBuddy.Service.impl.CalculationService;
import com.fitLifeBuddy.Service.impl.FlaskClientService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@CrossOrigin
@RestController
@RequestMapping("/api/plans")
@Api(tags = "Plan", value = "Service Web RESTFul de Plans")
public class PlanController {

    private static final Logger logger = LoggerFactory.getLogger(PlanController.class);

    @Autowired
    private IPlanService planService;

    @Autowired
    private IPacientService pacientService;

    @Autowired
    private IPacientHistoryService pacientHistoryService;

    @Autowired
    private CalculationService calculationService;

    @Autowired
    private FlaskClientService flaskClientService;

    @Autowired
    private IDailyService dailyService;

    @Autowired
    private IMealService mealService;

    @Autowired
    private IRoutineService routineService;

    @Autowired
    private IFoodService foodService;

    @Autowired
    private IExerciseService exerciseService;

    @Autowired
    private IMealFoodService mealFoodService;

    @Autowired
    private IRoutineExerciseService routineExerciseService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Listar Plans", notes = "Método para listar a todos los Plans")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Plans encontrados o lista vacía"),
            @ApiResponse(code = 500, message = "Error interno del servidor")
    })
    public ResponseEntity<List<Plan>> findAll(){
        try {
            List<Plan> plans = planService.getAll();
            return new ResponseEntity<List<Plan>>(plans, HttpStatus.OK);
        } catch (Exception ex){
            return new ResponseEntity<List<Plan>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Buscar Plan por Id", notes = "Métodos para encontrar un Plan por su respectivo Id")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Plan encontrado"),
            @ApiResponse(code = 404, message = "Plan no encontrado")
    })
    public ResponseEntity<Plan> findById(@PathVariable("id") Long id) {
        try {
            Optional<Plan> plan = planService.getById(id);
            if (!plan.isPresent())
                return new ResponseEntity<Plan>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<Plan>(plan.get(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Plan>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Actualización de datos de Plan", notes = "Metodo que actualiza los datos de Plan")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Datos de Plan actualizados"),
            @ApiResponse(code = 404, message = "Plan no encontrado")
    })
    public ResponseEntity<Plan> updatePlan(
            @PathVariable("id") Long id, @Valid @RequestBody Plan plan) {
        try {
            Optional<Plan> planUp = planService.getById(id);
            if (!planUp.isPresent())
                return new ResponseEntity<Plan>(HttpStatus.NOT_FOUND);
            plan.setIdPlan(id);
            planService.save(plan);
            return new ResponseEntity<Plan>(plan, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<Plan>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @PostMapping(value = "{idPacient}",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Registro de Plan", notes = "Método que registra Plans en BD")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Plan creado"),
            @ApiResponse(code = 404, message = "Plan no creado")
    })
    public ResponseEntity<Plan> insertPlan(@PathVariable("idPacient") Long idPacient, @RequestBody PlanDTO planDTO) {
        logger.debug("Starting insertPlan with idPacient: {}", idPacient);
        try {
            Optional<Pacient> pacientOpt = pacientService.getById(idPacient);
            if (!pacientOpt.isPresent()) {
                logger.error("Pacient not found with id: {}", idPacient);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            List<Plan> activePlans = planService.findActivedPlanByPacientId(idPacient);
            for (Plan activePlan : activePlans) {
                activePlan.setStatus(Status.CANCELED);
                planService.save(activePlan);
            }

            Optional<PacientHistory> latestHistoryOpt = pacientHistoryService.findLatestByPacientId(idPacient);
            if (!latestHistoryOpt.isPresent()) {
                logger.error("Latest PacientHistory not found for pacient id: {}", idPacient);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            PacientHistory latestHistory = latestHistoryOpt.get();
            Plan plan = new Plan();

            logger.debug("Setting frecuently from DTO");
            plan.setFrecuently(planDTO.getFrecuently());
            plan.setGoalType(planDTO.getGoalType());
            plan.setPacient(pacientOpt.get());
            logger.debug("Setting diet type from latest history");

            plan.setDietType(latestHistory.getDietType());
            plan.setAbdominal_circumference(latestHistory.getAbdominalCircumference().floatValue());
            plan.setStatus(Status.ACTIVED);
            logger.debug("Calculating TMB and other metrics");
            float tmb = calculationService.calcularTMB(latestHistory.getWeight(), latestHistory.getHeight(), latestHistory.getAge(), latestHistory.getGender(), latestHistory.getPhysicalActivity());
            float deficit = calculationService.calcularDeficit(tmb);
            CalculationService.Macronutrientes macronutrientes = calculationService.calcularMacronutrientes(deficit, latestHistory.getTypeHealthCondition());
            float metaMensual = calculationService.calcularMetaMensual(latestHistory.getWeight());
            ClassificationIMC classificationIMC = calculationService.calcularClasificacionIMCEnum(latestHistory.getWeight(), latestHistory.getHeight());
            plan.setClassificationIMC(classificationIMC);
            plan.setPhysical_activity((long) calculationService.mapearActividadNumerica(latestHistory.getPhysicalActivity()));


            plan.setTmb(tmb);
            plan.setCalorieDeficit(deficit);
            plan.setWeightLose(metaMensual);
            plan.setCarbohydrates_g(macronutrientes.carbohidratos);
            plan.setProteins_g(macronutrientes.proteinas);
            plan.setFats_g(macronutrientes.grasas);
            plan.setDesayuno_carbs(macronutrientes.desayunoCarbs);
            plan.setDesayuno_proten(macronutrientes.desayunoProten);
            plan.setDesayuno_grasas(macronutrientes.desayunoGrasas);
            plan.setAlmuerzo_carbs(macronutrientes.almuerzoCarbs);
            plan.setAlmuerzo_proten(macronutrientes.almuerzoProten);
            plan.setAlmuerzo_grasas(macronutrientes.almuerzoGrasas);
            plan.setCena_carbs(macronutrientes.cenaCarbs);
            plan.setCena_proten(macronutrientes.cenaProten);
            plan.setCena_grasas(macronutrientes.cenaGrasas);

            logger.debug("Saving the plan");
            Plan savedPlan = planService.save(plan);

            int numberOfDays = planDTO.getFrecuently().equals(Frecuently.MONTLY) ? 30 :
                    planDTO.getFrecuently().equals(Frecuently.BIMONTLY) ? 60 : 90;

            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, 1); // Comienza desde el día siguiente

            for (int i = 1; i <= numberOfDays; i++) {
                Daily daily = new Daily();
                daily.setDate(calendar.getTime());
                daily.setDateNumber(i);
                daily.setPlan(savedPlan);
                dailyService.save(daily);
                calendar.add(Calendar.DATE, 1); // Incrementa un día

                // Enviar datos al servicio Flask y obtener predicciones
                PredictionResponse flaskResponse = flaskClientService.sendPlanData(savedPlan);

                // Crear y guardar Meals y sus MealFoods según predicciones
                Meal breakfast = new Meal();
                breakfast.setTimeMeal(TimeMeal.Breakfast);
                breakfast.setDaily(daily);
                mealService.save(breakfast);
                createMealFoods(flaskResponse, breakfast, "desayuno");

                Meal lunch = new Meal();
                lunch.setTimeMeal(TimeMeal.Lunch);
                lunch.setDaily(daily);
                mealService.save(lunch);
                createMealFoods(flaskResponse, lunch, "almuerzo");

                Meal dinner = new Meal();
                dinner.setTimeMeal(TimeMeal.Dinner);
                dinner.setDaily(daily);
                mealService.save(dinner);
                createMealFoods(flaskResponse, dinner, "cena");

                // Crear y guardar Routine y sus RoutineExercises según predicciones
                Routine routine = new Routine();
                routine.setDaily(daily);
                routineService.save(routine);
                createRoutineExercises(flaskResponse, routine);
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(savedPlan);
        } catch (Exception e) {
            logger.error("Error occurred while creating a plan", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Método helper para crear MealFoods basado en predicciones
    private void createMealFoods(PredictionResponse flaskResponse, Meal meal, String mealType) {
        for (int j = 1; j <= 3; j++) { // Asumiendo 3 predicciones por Meal, excepto cena que tiene 2
            try {
                String foodName = flaskResponse.getPredictions().get(mealType + "_" + j);
                List<Food> foods = foodService.findByNameFood(foodName); // Asume método para encontrar Food por nombre
                if (!foods.isEmpty()) {
                    MealFood mealFood = new MealFood();
                    mealFood.setMeal(meal);
                    mealFood.setFood(foods.get(0)); // Tomar el primer resultado
                    mealFoodService.save(mealFood);
                } else {
                    logger.warn("No food found for name: {}", foodName);
                }
            } catch (Exception e) {
                logger.error("Error creating MealFood for food name: {}", flaskResponse.getPredictions().get(mealType + "_" + j), e);
            }
        }
    }

    // Método helper para crear RoutineExercises basado en predicciones
    private void createRoutineExercises(PredictionResponse flaskResponse, Routine routine) {
        String exerciseName = null;
        for (int j = 1; j <= 5; j++) {
            try {
                exerciseName = flaskResponse.getPredictions().get("ejercicio_" + j);
                List<Exercise> exercises = exerciseService.findByWorkout(exerciseName);
                if (!exercises.isEmpty()) {
                    RoutineExercise routineExercise = new RoutineExercise();
                    routineExercise.setRoutine(routine);
                    routineExercise.setExercise(exercises.get(0));
                    routineExerciseService.save(routineExercise);
                } else {
                    logger.warn("No exercise found for workout: {}", exerciseName);
                }
            } catch (Exception e) {
                logger.error("Error creating RoutineExercise for workout: {}", exerciseName, e);
                // Continuar con el siguiente elemento
            }
        }
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Eliminación de Plan", notes = "Metodo que elimina los datos de Plan")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Datos de Plan eliminados"),
            @ApiResponse(code = 404, message = "Plan no encontrado")
    })
    public ResponseEntity<Plan> deletePlan(@PathVariable("id") Long id) {
        try {
            Optional<Plan> planDelete = planService.getById(id);
            if (!planDelete.isPresent())
                return new ResponseEntity<Plan>(HttpStatus.NOT_FOUND);
            planService.delete(id);
            return new ResponseEntity<Plan>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Plan>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("searchDailiesByIdPlan/{idPlan}")
    @ApiOperation(value = "Buscar Dailies por Plan", notes = "Métodos para encontrar Dailies por su respectivo Plan")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Dailies encontrados o no existen Dailies para este Plan"),
            @ApiResponse(code = 500, message = "Error interno del servidor")
    })
    public ResponseEntity<List<Daily>> findDailiesByIdPlan(@PathVariable("idPlan") Long idPlan) {
        try {
            List<Daily> dailies = planService.findDailiesByIdPlan(idPlan);
            return new ResponseEntity<List<Daily>>(dailies, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<List<Daily>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("searchByFrecuently/{frecuently}")
    @ApiOperation(value = "Buscar Plans por frecuently", notes = "Métodos para encontrar Plans por su respectivo frecuently")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Plans encontrados o no existen Plans con esta frecuencia"),
            @ApiResponse(code = 500, message = "Error interno del servidor")
    })
    public ResponseEntity<List<Plan>> findByFrecuently(@PathVariable("frecuently") Frecuently frecuently) {
        try {
            List<Plan> plans = planService.findByFrecuently(frecuently);
            return new ResponseEntity<List<Plan>>(plans, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<List<Plan>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("searchByDietType/{dietType}")
    @ApiOperation(value = "Buscar Plans por dietType", notes = "Métodos para encontrar Plans por su respectivo dietType")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Plans encontrados o no existen Plans para este tipo de dieta"),
            @ApiResponse(code = 500, message = "Error interno del servidor")
    })
    public ResponseEntity<List<Plan>> findByDietType(@PathVariable("dietType") DietType dietType) {
        try {
            List<Plan> plans = planService.findByDietType(dietType);
            return new ResponseEntity<List<Plan>>(plans, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<List<Plan>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
