package ru.leguenko.supplierdeliveries;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import jakarta.annotation.Resource;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestcontainersConfig.class)
class ReportApiTest {

    @Resource MockMvc mockMvc;

    @Test
    void reportShouldReturnTotals() throws Exception {
        mockMvc.perform(get("/api/reports/supplies")
                        .param("from", "2026-02-01")
                        .param("to", "2026-02-28"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.from").value("2026-02-01"))
                .andExpect(jsonPath("$.to").value("2026-02-28"))
                .andExpect(jsonPath("$.suppliers").isArray())
                .andExpect(jsonPath("$.totalWeightKg").exists())
                .andExpect(jsonPath("$.totalAmount").exists());
    }
}
