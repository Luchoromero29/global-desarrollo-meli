package frm.utn.global.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "dna_records")
public class DnaRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 512)
    private String hash; // Hash único del ADN

    @Column(nullable = false, length = 2000)
    private String dna; // ADN almacenado como un solo string

    @Column(nullable = false)
    private boolean isMutant;

    public boolean isMutant(){
        return isMutant;
    }

    public DnaRecord(String hash, String dna, boolean isMutant) {
        this.hash = hash;
        this.dna = dna;
        this.isMutant = isMutant;
    }






}
