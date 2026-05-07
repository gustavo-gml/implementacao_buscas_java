package metrics;

import java.util.function.Function;

public class Profiler {

    // O parâmetro 'algoritmo' aceita qualquer método que receba char[][] e retorne Boolean
    public static BenchmarkResult avaliar(Function<char[][], Boolean> algoritmo, char[][] mapaOficial, char[][] mapaWarmUp) {

        // 1. WARM-UP (Prepara a JVM em silêncio)
        for (int i = 0; i < 1000; i++) {
            char[][] cloneWarmUp = clonarMatriz(mapaWarmUp);
            algoritmo.apply(cloneWarmUp);
        }

        // 2. PREPARAÇÃO (Limpeza de memória)
        Runtime runtime = Runtime.getRuntime();
        System.gc();

        // 3. INÍCIO DA CAPTURA
        long memoriaAntes = runtime.totalMemory() - runtime.freeMemory();
        long tempoInicio = System.nanoTime();

        // 4. EXECUÇÃO DO ALGORITMO (In-place)
        boolean encontrouSaida = algoritmo.apply(mapaOficial);

        // 5. FIM DA CAPTURA
        long tempoFim = System.nanoTime();
        long memoriaDepois = runtime.totalMemory() - runtime.freeMemory();

        // 6. CÁLCULO DOS RESULTADOS
        long tempoExecucaoNanos = tempoFim - tempoInicio;
        long consumoMemoriaBytes = memoriaDepois - memoriaAntes;
        if (consumoMemoriaBytes < 0) consumoMemoriaBytes = 0;

        return new BenchmarkResult(encontrouSaida, tempoExecucaoNanos / 1_000_000.0, consumoMemoriaBytes);
    }

    // Função utilitária mantida apenas para o Warm-up
    private static char[][] clonarMatriz(char[][] original) {
        char[][] clone = new char[original.length][original[0].length];
        for (int i = 0; i < original.length; i++) {
            System.arraycopy(original[i], 0, clone[i], 0, original[i].length);
        }
        return clone;
    }
}