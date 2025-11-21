package frm.utn.global.service;

import java.util.regex.Pattern;

public class DnaValidator {

    // Expresión regular para validar solo A, T, C, G
    private static final Pattern VALID_DNA_PATTERN = Pattern.compile("^[ATCG]+$");

    public static boolean isValid(String[] dna) {
        // 1. Chequear si es nulo o vacío
        if (dna == null || dna.length == 0) {
            return false;
        }

        int n = dna.length;

        // 2. Recorrer cada fila
        for (String row : dna) {
            // Chequear que no sea nula
            if (row == null) return false;

            // Chequear que sea cuadrada (NxN)
            if (row.length() != n) return false;

            // Chequear que solo tenga letras válidas (A, T, C, G)
            if (!VALID_DNA_PATTERN.matcher(row).matches()) {
                return false;
            }
        }

        return true; // Si pasó todo, es válido
    }
}