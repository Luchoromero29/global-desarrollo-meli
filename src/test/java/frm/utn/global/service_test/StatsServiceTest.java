package frm.utn.global.service_test;


import frm.utn.global.dto.StatsResponse;
import frm.utn.global.repository.DnaRecordRepository;
import frm.utn.global.service.StatsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class StatsServiceTest {

    private DnaRecordRepository repository;
    private StatsService service;

    @BeforeEach
    void setup() {
        repository = mock(DnaRecordRepository.class);
        service = new StatsService(repository);
    }

    @Test
    void testStatsCorrectValues() {
        when(repository.countMutants()).thenReturn(40L);
        when(repository.countHumans()).thenReturn(100L);

        StatsResponse stats = service.getStats();

        assertEquals(40, stats.getCount_mutant_dna());
        assertEquals(100, stats.getCount_human_dna());
        assertEquals(0.4, stats.getRatio());
    }

    @Test
    void testRatioZeroHumans() {
        when(repository.countMutants()).thenReturn(10L);
        when(repository.countHumans()).thenReturn(0L);

        StatsResponse stats = service.getStats();

        assertEquals(0, stats.getRatio());
    }

    @Test
    void testZeroValues() {
        when(repository.countMutants()).thenReturn(0L);
        when(repository.countHumans()).thenReturn(0L);

        StatsResponse stats = service.getStats();

        assertEquals(0, stats.getCount_mutant_dna());
        assertEquals(0, stats.getCount_human_dna());
    }

    @Test
    void testOnlyMutants() {
        when(repository.countMutants()).thenReturn(50L);
        when(repository.countHumans()).thenReturn(0L);

        StatsResponse stats = service.getStats();
        assertEquals(50, stats.getCount_mutant_dna());
    }

    @Test
    void testOnlyHumans() {
        when(repository.countMutants()).thenReturn(0L);
        when(repository.countHumans()).thenReturn(20L);

        StatsResponse stats = service.getStats();
        assertEquals(20, stats.getCount_human_dna());
    }

    @Test
    void testRepositoryCalled() {
        service.getStats();
        verify(repository, times(1)).countMutants();
        verify(repository, times(1)).countHumans();
    }
}
