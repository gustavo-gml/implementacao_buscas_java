package metrics;

import java.util.function.Function;

public class Profiler {

    private static final double TEMPO_LIMITE_SEGUNDOS = 1.0;

    public static BenchmarkResult avaliar(Function<char[][], Boolean> algoritmo, char[][] mapaOficial, char[][] mapaWarmUp) {

        // 1. WARM-UP (Reduzido para 3 rodadas para aquecer o JIT Compiler da JVM de forma justa)
        for (int i = 0; i < 3; i++) {
            char[][] cloneWarmUp = clonarMatriz(mapaWarmUp);
            algoritmo.apply(cloneWarmUp);
        }

        double statsMin = Double.MAX_VALUE;
        double statsMax = 0.0;
        long tempoAcumuladoNanos = 0;
        int iteracoes = 0;
        boolean encontrouSaida = false;

        // 2. PREPARAÇÃO (Limpeza de memória)
        System.gc(); // Exige que o Java recolha o lixo do warmup
        try { Thread.sleep(10); } catch(Exception ignored){} // Respira por 10ms
        
        Runtime runtime = Runtime.getRuntime();
        long memoriaAntes = runtime.totalMemory() - runtime.freeMemory();

        // 3. LOOP ESTILO BENCHEE (Catraca de 1 Segundo)
        long inicioGlobal = System.nanoTime();
        long limiteNanos = (long) (TEMPO_LIMITE_SEGUNDOS * 1_000_000_000L);

        while ((System.nanoTime() - inicioGlobal) < limiteNanos) {
            
            // Restaura o mapa FORA do relógio (Técnica do Sanduíche)
            char[][] cloneOficial = clonarMatriz(mapaOficial);

            // ⏱️ LIGA O CRONÔMETRO
            long inicioIteracao = System.nanoTime();
            
            encontrouSaida = algoritmo.apply(cloneOficial);
            
            // ⏱️ DESLIGA O CRONÔMETRO
            long fimIteracao = System.nanoTime();

            long duracaoNanos = fimIteracao - inicioIteracao;
            double duracaoMilis = duracaoNanos / 1_000_000.0;

            if (duracaoMilis < statsMin) statsMin = duracaoMilis;
            if (duracaoMilis > statsMax) statsMax = duracaoMilis;
            
            tempoAcumuladoNanos += duracaoNanos;
            iteracoes++;
        }

        // 4. FOTO DA MEMÓRIA DEPOIS (Sem chamar o GC, para ver o lixo que o algoritmo gerou)
        long memoriaDepois = runtime.totalMemory() - runtime.freeMemory();

        // 5. CÁLCULOS MATEMÁTICOS FINAIS
        double mediaMilis = (tempoAcumuladoNanos / 1_000_000.0) / Math.max(1, iteracoes);
        long consumoMemoriaBytes = memoriaDepois - memoriaAntes;
        if (consumoMemoriaBytes < 0) consumoMemoriaBytes = 0;

        if (iteracoes == 0) {
            statsMin = 0;
            statsMax = 0;
        }

        return new BenchmarkResult(encontrouSaida, mediaMilis, statsMin, statsMax, iteracoes, consumoMemoriaBytes);
    }

    private static char[][] clonarMatriz(char[][] original) {
        char[][] clone = new char[original.length][original[0].length];
        for (int i = 0; i < original.length; i++) {
            System.arraycopy(original[i], 0, clone[i], 0, original[i].length);
        }
        return clone;
    }
}