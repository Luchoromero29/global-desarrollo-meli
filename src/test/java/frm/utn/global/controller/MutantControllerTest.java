package frm.utn.global.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import frm.utn.global.controller.MutantController;
import frm.utn.global.dto.DnaRequest;
import frm.utn.global.service.MutantService;
import frm.utn.global.service.StatsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MutantController.class)
class MutantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MutantService mutantService;

    @MockBean
    private StatsService statsService;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void testMutantReturnsTrue() throws Exception {
        when(mutantService.processDna(any())).thenReturn(true);

        DnaRequest req = new DnaRequest();
        req.setDna(java.util.List.of("AAAA", "TTTT", "CCCC", "GGGG"));

        mockMvc.perform(post("/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(req)))
                .andExpect(status().isOk());
    }

    @Test
    void testNonMutantReturns403() throws Exception {
        when(mutantService.processDna(any())).thenReturn(false);

        DnaRequest req = new DnaRequest();
        req.setDna(java.util.List.of("ATGC", "CAGT", "TTAT", "AGAC"));

        mockMvc.perform(post("/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(req)))
                .andExpect(status().isForbidden());
    }

    @Test
    void testInvalidJsonReturns400() throws Exception {
        mockMvc.perform(post("/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{dna:[}"))
                .andExpect(status().isBadRequest());
    }


    @Test
    void testStatsReturns200() throws Exception {
        when(statsService.getStats()).thenReturn(
                new frm.utn.global.dto.StatsResponse(40, 100)
        );

        mockMvc.perform(get("/stats"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count_mutant_dna").value(40))
                .andExpect(jsonPath("$.count_human_dna").value(100))
                .andExpect(jsonPath("$.ratio").value(0.4));
    }

    @Test
    void testEmptyDnaReturnsBadRequest() throws Exception {
        DnaRequest req = new DnaRequest();
        req.setDna(null);

        mockMvc.perform(post("/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(req)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testProcessThrowsException() throws Exception {
        when(mutantService.processDna(any())).thenThrow(new RuntimeException());

        DnaRequest req = new DnaRequest();
        req.setDna(java.util.List.of("AAAA", "AAAA", "AAAA", "AAAA"));

        mockMvc.perform(post("/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(req)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testStatsCallsService() throws Exception {
        when(statsService.getStats()).thenReturn(
                new frm.utn.global.dto.StatsResponse(0, 0)
        );

        mockMvc.perform(get("/stats"))
                .andExpect(status().isOk());

        verify(statsService, times(1)).getStats();
    }

    @Test
    void testMutantControllerLoads() {
        Assertions.assertNotNull(mockMvc);
    }
}
