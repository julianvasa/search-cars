package com.search.cars;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.client.HttpClientErrorException;

import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class TestController {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;


    @Test
    public void whenSearchingWithVin_thenReturnVehicleHistoryRecordsSize() {
        try {
            this.mockMvc.perform(
                get("/vin/VSSZZZ6JZ9R056308")
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.records", hasSize(5)));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void whenSearchingWithVin_thenVerifyOdometerRollback() {
        try {
            this.mockMvc.perform(
                get("/vin/VSSZZZ6JZ9R056308")
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(
                    MockMvcResultMatchers.jsonPath("$.records[3].odometer_rollback")
                        .value("Odometer Rollback")
                );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void whenSearchingWithVin_thenVerifyRecordsAfterOdometerRollbackRemainUntouched() {
        try {
            this.mockMvc.perform(
                get("/vin/VSSZZZ6JZ9R056308")
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(
                    MockMvcResultMatchers.jsonPath("$.records[2].odometer_rollback")
                        .doesNotExist()
                ).andExpect(
                MockMvcResultMatchers.jsonPath("$.records[4].odometer_rollback")
                    .doesNotExist()
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void whenSearchingForUnknownVin_thenErrorCodeAndDescription() {
        try {
           this.mockMvc.perform(
                get("/vin/111111111111")
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", equalToIgnoringCase("Vehicle with vin 111111111111 was not found!")))
               .andExpect(MockMvcResultMatchers.jsonPath("$.details", equalToIgnoringCase("uri=/vin/111111111111")))
               .andExpect(MockMvcResultMatchers.jsonPath("$.errorCode", equalToIgnoringCase("404")));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
