import algorithms.AStar;
import algorithms.DFS;
import algorithms.BFS;
import algorithms.Dijkstra;
import io.MazeLoader;
import metrics.Profiler;
import metrics.BenchmarkResult;
import java.util.function.Function;

public class Main {
    public static void main(String[] args) {

        if (args.length < 1) {
            System.out.println("Uso: java Main mazes/labirinto.txt");
            return;
        }

        String caminho = args[0];
        MazeLoader.Labirinto lab = MazeLoader.carregar(caminho);

        if (lab == null || lab.mapa == null) {
            System.out.println("Erro fatal: Falha ao carregar o labirinto.");
            return;
        }

        int linhas = lab.linhas;
        int colunas = lab.colunas;
        int celulas = linhas * colunas;

        System.out.println("=========================================================================");
        System.out.printf(" Mapa: %d x %d | Celulas: %d\n", linhas, colunas, celulas);
        System.out.println("=========================================================================\n");

        String[] nomes = {"DFS", "BFS", "Dijkstra", "A*"};
        
        // Array de Funções para rodar em loop
        @SuppressWarnings("unchecked")
        Function<char[][], Boolean>[] algoritmos = new Function[]{
            (Function<char[][], Boolean>) DFS::executar,
            (Function<char[][], Boolean>) BFS::executar,
            (Function<char[][], Boolean>) Dijkstra::executar,
            (Function<char[][], Boolean>) AStar::executar
        };

        System.out.printf("%-10s | %-10s | %-10s | %-10s | %-10s | %-12s\n", 
                "Algoritmo", "Media (ms)", "Min (ms)", "Max (ms)", "IPS", "Memoria (B)");
        System.out.println("-------------------------------------------------------------------------");

        for (int i = 0; i < 4; i++) {
            
            // Cria clones virgens fresquinhos para não haver contaminação cruzada
            char[][] mapaOficial = new char[linhas][colunas];
            char[][] mapaWarmUp = new char[linhas][colunas];

            for (int l = 0; l < linhas; l++) {
                System.arraycopy(lab.mapa[l], 0, mapaOficial[l], 0, colunas);
                System.arraycopy(lab.mapa[l], 0, mapaWarmUp[l], 0, colunas);
            }

            BenchmarkResult res = Profiler.avaliar(algoritmos[i], mapaOficial, mapaWarmUp);

            double ips = res.mediaMilis > 0 ? 1000.0 / res.mediaMilis : 0.0;

            System.out.printf("%-10s | %-10.4f | %-10.4f | %-10.4f | %-10.2f | %-12d\n", 
                    nomes[i], res.mediaMilis, res.minMilis, res.maxMilis, ips, res.memoriaBytes);
        }

        System.out.println("=========================================================================");
    }
}