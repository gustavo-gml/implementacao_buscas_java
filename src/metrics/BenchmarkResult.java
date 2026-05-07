package metrics;

public class BenchmarkResult {
    public final boolean encontrouSaida;
    public final double tempoMilis;
    public final long memoriaBytes;

    public BenchmarkResult(boolean encontrouSaida, double tempoMilis, long memoriaBytes) {
        this.encontrouSaida = encontrouSaida;
        this.tempoMilis = tempoMilis;
        this.memoriaBytes = memoriaBytes;
    }
}