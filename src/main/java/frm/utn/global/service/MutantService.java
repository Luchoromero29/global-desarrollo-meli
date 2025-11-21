package frm.utn.global.service;

import frm.utn.global.entity.DnaRecord;
import frm.utn.global.repository.DnaRecordRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

@Service
public class MutantService {
    private final DnaRecordRepository repository;
    private final MutantDetector detector;

    public MutantService(DnaRecordRepository repository, MutantDetector detector) {
        this.repository = repository;
        this.detector = detector;
    }

    @Transactional
    public boolean processDna(String[] dna) throws Exception {

        boolean isValid = DnaValidator.isValid(dna);

        if (!isValid) {
            throw new Exception("DNA NOT VALID");
        }

        String joined = String.join(", ", dna);
        String hash = hashDna(joined);


        //DNA ejemplo = ["TACG","CGTA","TTTT","AAAA"]
        // evitar duplicado
        if (repository.findByHash(hash).isPresent()) {
            return repository.findByHash(hash).get().isMutant();
        }


        boolean isMutant = detector.isMutant(dna);

        // guardar en BD
        DnaRecord rec = DnaRecord.builder()
                .hash(hash)
                .dna(joined)
                .isMutant(isMutant)
                .build();
        repository.save(rec);

        return isMutant;
    }

    // hash SHA-256
    private String hashDna(String raw) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] h = digest.digest(raw.getBytes(StandardCharsets.UTF_8));

        StringBuilder sb = new StringBuilder();
        for (byte b : h) sb.append(String.format("%02x", b));
        return sb.toString();
    }
}