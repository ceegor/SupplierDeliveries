package ru.leguenko.supplierdeliveries;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import jakarta.annotation.Resource;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestcontainersConfig.class)
class SupplyApiTest {

    @Resource MockMvc mockMvc;
    @Resource ObjectMapper objectMapper;

    @Test
    void createSupply_shouldReturnIdAndTotals() throws Exception {
        var body = """
      {
        "supplierId": 1,
        "supplyDate": "2026-02-07T10:15:00",
        "lines": [
          { "productId": 1, "weightKg": 10.5 },
          { "productId": 3, "weightKg": 2.0 }
        ]
      }
      """;

        mockMvc.perform(post("/api/supplies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.lines").isArray())
                .andExpect(jsonPath("$.totalWeightKg").exists())
                .andExpect(jsonPath("$.totalAmount").exists());
    }

    @Test
    void getSupplyById_shouldReturnLines() throws Exception {
        var body = """
      {
        "supplierId": 1,
        "supplyDate": "2026-02-07T10:15:00",
        "lines": [
          { "productId": 1, "weightKg": 1.0 }
        ]
      }
      """;

        var res = mockMvc.perform(post("/api/supplies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andReturn();

        var json = res.getResponse().getContentAsString();
        long id = objectMapper.readTree(json).get("id").asLong();

        mockMvc.perform(get("/api/supplies/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.lines").isArray())
                .andExpect(jsonPath("$.lines[0].productId").isNumber());
    }
}

