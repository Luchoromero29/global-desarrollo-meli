package frm.utn.global.controller;


import frm.utn.global.dto.DnaRequest;
import frm.utn.global.dto.StatsResponse;
import frm.utn.global.service.MutantService;
import frm.utn.global.service.StatsService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MutantController {

    private final MutantService mutantService;
    private final StatsService statsService;

    public MutantController(MutantService mutantService,
                            StatsService statsService) {
        this.mutantService = mutantService;
        this.statsService = statsService;
    }

    @PostMapping("/mutant")
    public ResponseEntity<?> isMutant(@RequestBody DnaRequest request) {
        try {
            boolean mutant = mutantService.processDna(
                    request.getDna().toArray(new String[0])
            );
            return mutant ? ResponseEntity.ok().build()
                    : ResponseEntity.status(403).build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/stats")
    public ResponseEntity<StatsResponse> stats() {
        return ResponseEntity.ok(statsService.getStats());
    }
}
