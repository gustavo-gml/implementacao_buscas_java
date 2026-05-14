import algorithms.AStar;
import algorithms.DFS;
import algorithms.BFS;
import algorithms.Dijkstra;
import metrics.Profiler;
import metrics.BenchmarkResult;
import java.util.function.Function;

public class Main {
    public static void main(String[] args) {

        // 1. Validação de Segurança --> futuramente adicionar arquivo separado ?
        if (args.length < 1) {
            System.out.println("Erro: Você precisa informar o algoritmo.");
            System.out.println("Uso: java Main <dfs|bfs|dijkstra|astar>");
            return;
        }

        String nomeAlgoritmo = args[0].toLowerCase();

        // Futuramente, args[1] será o caminho do arquivo .txt
        // String caminhoArquivo = args.length > 1 ? args[1] : "mapa_padrao.txt";

        // Mapas de mentira apenas para testes
        char[][] mapaWarmUp = {{'E', '1', '1', '1', 'S'}}; //aquecimento do jvm
        char[][] mapaOficial = {
                {'E', '1', '1', '1', '0'},
                {'0', '0', '0', '1', '0'},
                {'S', '1', '0', '1', '0'},
                {'0', '1', '1', '1', '0'}
        };

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