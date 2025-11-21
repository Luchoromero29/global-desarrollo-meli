package frm.utn.global.dto;

public class StatsResponse {
    private long count_mutant_dna;
    private long count_human_dna;
    private double ratio;

    public StatsResponse(long mutants, long humans) {
        this.count_mutant_dna = mutants;
        this.count_human_dna = humans;
        this.ratio = humans == 0 ? 0 : (double) mutants / humans;
    }

    public long getCount_mutant_dna() { return count_mutant_dna; }
    public long getCount_human_dna() { return count_human_dna; }
    public double getRatio() { return ratio; }
}
