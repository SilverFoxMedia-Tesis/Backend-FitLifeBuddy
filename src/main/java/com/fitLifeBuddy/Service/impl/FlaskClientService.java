package com.fitLifeBuddy.Service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitLifeBuddy.Entity.DTO.PredictionRequest;
import com.fitLifeBuddy.Entity.DTO.PredictionResponse;
import com.fitLifeBuddy.Entity.Plan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Collections;

@Service
public class FlaskClientService {
    private final RestTemplate restTemplate;
    private final String FLASK_API_URL = "http://127.0.0.1:5000/predict";
    private static final Logger logger = LoggerFactory.getLogger(FlaskClientService.class);


    @Autowired
    public FlaskClientService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public PredictionResponse sendPlanData(Plan plan) {
        PredictionRequest request = new PredictionRequest(plan);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        HttpEntity<PredictionRequest> entity = new HttpEntity<>(request, headers);

        ResponseEntity<PredictionResponse> response = restTemplate.postForEntity(FLASK_API_URL, entity, PredictionResponse.class);
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            logger.debug("Parsed PredictionResponse: {}", response.getBody());
            return response.getBody();
        } else {
            logger.error("No predictions returned from Flask API or an error occurred");
            throw new RuntimeException("No predictions returned from Flask API or an error occurred");
        }
    }


}
