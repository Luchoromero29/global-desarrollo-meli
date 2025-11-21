package frm.utn.global.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;


@Data
public class DnaRequest {
    @NotEmpty
    private List<String> dna;





}
