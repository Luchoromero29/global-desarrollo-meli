package frm.utn.global.service;

import org.springframework.stereotype.Service;

@Service
public class MutantDetector {
    // ALGORITMO isMutant
    public boolean isMutant(String[] dna) {
        int n = dna.length;
        int found = 0;

        // matriz
        char[][] m = new char[n][n];
        for (int i = 0; i < n; i++) m[i] = dna[i].toCharArray();

        // buscar secuencias horizontales, verticales y diagonales
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {

                char c = m[i][j];

                // derecha —
                if (j + 3 < n &&
                        c == m[i][j+1] &&
                        c == m[i][j+2] &&
                        c == m[i][j+3]) {
                    if (++found > 1) return true;
                }

                // abajo |
                if (i + 3 < n &&
                        c == m[i+1][j] &&
                        c == m[i+2][j] &&
                        c == m[i+3][j]) {
                    if (++found > 1) return true;
                }

                // diagonal ↘
                if (i + 3 < n && j + 3 < n &&
                        c == m[i+1][j+1] &&
                        c == m[i+2][j+2] &&
                        c == m[i+3][j+3]) {
                    if (++found > 1) return true;
                }

                // diagonal inversa ↙
                if (i + 3 < n && j - 3 >= 0 &&
                        c == m[i+1][j-1] &&
                        c == m[i+2][j-2] &&
                        c == m[i+3][j-3]) {
                    if (++found > 1) return true;
                }
            }
        }
        return false;
    }
}
