package metrics;

public class BenchmarkResult {
    public final boolean encontrouSaida;
    public final double mediaMilis;
    public final double minMilis;
    public final double maxMilis;
    public final int iteracoes;
    public final long memoriaBytes;

    public BenchmarkResult(boolean encontrouSaida, double mediaMilis, double minMilis, double maxMilis, int iteracoes, long memoriaBytes) {
        this.encontrouSaida = encontrouSaida;
        this.mediaMilis = mediaMilis;
        this.minMilis = minMilis;
        this.maxMilis = maxMilis;
        this.iteracoes = iteracoes;
        this.memoriaBytes = memoriaBytes;
    }
}