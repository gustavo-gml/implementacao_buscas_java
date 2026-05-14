package algorithms;

import java.util.Comparator;
import java.util.PriorityQueue;

public class AStar {

    private static final int[][] DIRECOES = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    public static boolean executar(char[][] mapa) {
        int linhas = mapa.length;
        int colunas = mapa[0].length;

        int linhaInicio = -1, colunaInicio = -1;
        int linhaDestino = -1, colunaDestino = -1;

        // 1. Encontrar a Entrada ('E') e a Saída ('S')
        for (int l = 0; l < linhas; l++) {
            for (int c = 0; c < colunas; c++) {
                if (mapa[l][c] == 'E') {
                    linhaInicio = l;
                    colunaInicio = c;
                } else if (mapa[l][c] == 'S') {
                    linhaDestino = l;
                    colunaDestino = c;
                }
            }
        }

        if (linhaInicio == -1 || linhaDestino == -1) return false;

        // 2. A Fila de Prioridades: Agora ensina o Java a comparar o índice [3], que é o f(n)
        PriorityQueue<int[]> filaPrioridade = new PriorityQueue<>(Comparator.comparingInt(no -> no[3]));

        // Calcula a heurística inicial (h) usando Distância de Manhattan
        int hInicial = Math.abs(linhaInicio - linhaDestino) + Math.abs(colunaInicio - colunaDestino);

        // Array: {linha, coluna, custo_G, custo_F}
        filaPrioridade.add(new int[]{linhaInicio, colunaInicio, 0, hInicial});

        // 3. Executar a Busca
        while (!filaPrioridade.isEmpty()) {

            // Tira o nó com o MENOR custo_F de toda a fila
            int[] noAtual = filaPrioridade.poll();
            int l = noAtual[0];
            int c = noAtual[1];
            int gAtual = noAtual[2];

            char celula = mapa[l][c];

            // Lazy A*: Ignora se já achou um atalho mais rápido para cá antes
            if (celula == 'V') continue;

            // Se for a saída, terminou
            if (celula == 'S') return true;

            // Marca como Visitado apenas ao retirar da fila (confirmação do melhor caminho)
            if (celula == '1' || celula == 'E') {
                mapa[l][c] = 'V';
            }

            // Explora os vizinhos
            for (int[] dir : DIRECOES) {
                int novaLinha = l + dir[0];
                int novaColuna = c + dir[1];

                if (novaLinha >= 0 && novaLinha < linhas && novaColuna >= 0 && novaColuna < colunas) {
                    char celulaVizinha = mapa[novaLinha][novaColuna];

                    if (celulaVizinha == '1' || celulaVizinha == 'S') {

                        // g(n): Custo real até o vizinho (custo atual + 1 passo)
                        int novoG = gAtual + 1;

                        // h(n): Palpite de distância do vizinho até a Saída
                        int novoH = Math.abs(novaLinha - linhaDestino) + Math.abs(novaColuna - colunaDestino);

                        // f(n): A soma
                        int novoF = novoG + novoH;

                        // Adiciona na fila com os novos cálculos
                        filaPrioridade.add(new int[]{novaLinha, novaColuna, novoG, novoF});
                    }
                }
            }
        }

        return false;
    }
}