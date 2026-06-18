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

        //TODO:  1. Validação de Segurança -> MELHORAR A MENSAGEM DE ERRO 
        if (args.length < 2) {
            System.out.println("Erro: Você precisa informar o algoritmo e o caminho do arquivo.");
            System.out.println("Uso: java Main <dfs|bfs|dijkstra|astar>");
            return;
        }

        String nomeAlgoritmo = args[0].toLowerCase();
        String caminho = args[1];


        // Futuramente, args[1] será o caminho do arquivo .txt
        // String caminhoArquivo = args.length > 1 ? args[1] : "mapa_padrao.txt";

        MazeLoader.Labirinto lab = MazeLoader.carregar(caminho);

        if (lab == null || lab.mapa == null) {
            System.out.println("Erro fatal: Falha ao carregar o labirinto. Verifique o caminho e tente novamente.");
            return;
        }

        
        char[][] mapaWarmUp = new char[lab.linhas][lab.colunas]; //aquecimento do jvm
        char[][] mapaOficial = lab.mapa;

        for (int i = 0; i < lab.linhas; i++) {
            System.arraycopy(lab.mapa[i], 0, mapaWarmUp[i], 0, lab.colunas);
        }

        // 2. O Roteador de Algoritmos
        // Usamos a interface Function para guardar a referência do metodo escolhido
        Function<char[][], Boolean> algoritmoEscolhido;

        switch (nomeAlgoritmo) {
            case "dfs":
                algoritmoEscolhido = DFS::executar;
                break;
            case "bfs":
                algoritmoEscolhido = BFS::executar;
                break;
            case "dijkstra":
                algoritmoEscolhido = Dijkstra::executar;
                break;
            case "astar":
                algoritmoEscolhido = AStar::executar;
                break;
            default:
                System.out.println("Erro: Algoritmo '" + nomeAlgoritmo + "' não reconhecido.");
                return;
        }

        System.out.println("--- Iniciando Teste: " + nomeAlgoritmo.toUpperCase() + " ---");

        // 3. Execução Padronizada
        BenchmarkResult resultado = Profiler.avaliar(algoritmoEscolhido, mapaOficial, mapaWarmUp);

        // 4. Saída formatada (Idealmente, no futuro, isso imprimirá em formato CSV)
        System.out.println(nomeAlgoritmo.toUpperCase() + " -> Encontrou: " + resultado.encontrouSaida +
                " | Tempo: " + resultado.tempoMilis + " ms" +
                " | Memória: " + resultado.memoriaBytes + " bytes");
    }
}