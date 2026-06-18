package algorithms;

import java.util.Comparator;
import java.util.PriorityQueue;

public class Dijkstra {

    private static final int[][] DIRECOES = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    public static boolean executar(char[][] mapa) {
        int linhas = mapa.length;
        int colunas = mapa[0].length;

        // 1. Encontrar a Entrada ('E')
        int linhaAtual = -1, colunaAtual = -1;
        buscarEntrada:
        for (int l = 0; l < linhas; l++) {
            for (int c = 0; c < colunas; c++) {
                if (mapa[l][c] == 'E') {
                    linhaAtual = l;
                    colunaAtual = c;
                    break buscarEntrada;
                }
            }
        }

        if (linhaAtual == -1) return false;

        //  A Fila de Prioridades (Min-Heap)
        // Ensinamos o Java a comparar sempre o índice [2] do nosso array, que é o Custo.
        PriorityQueue<int[]> filaPrioridade = new PriorityQueue<>(linhas * colunas, Comparator.comparingInt(no -> no[2]));

        // Adiciona a entrada com custo zero: {linha, coluna, custo}
        filaPrioridade.add(new int[]{linhaAtual, colunaAtual, 0});

        // 3. Executar a Busca
        while (!filaPrioridade.isEmpty()) {

            // Tira o nó MAIS BARATO de toda a fila
            int[] noAtual = filaPrioridade.poll();
            int l = noAtual[0];
            int c = noAtual[1];
            int custoAtual = noAtual[2];

            char celula = mapa[l][c];

            // Se já foi visitado por um caminho mais barato antes, ignora (Lazy Dijkstra)
            if (celula == 'V') {
                continue;
            }

            // Se for a saída, termina
            if (celula == 'S') {
                return true;
            }

            // Somente agora, ao ter certeza de que esse é o caminho mais curto até esta célula, marca como Visitado
            if (celula == '1' || celula == 'E') {
                mapa[l][c] = 'V';
            }

            // Explora os vizinhos
            for (int[] dir : DIRECOES) {
                int novaLinha = l + dir[0];
                int novaColuna = c + dir[1];

                if (novaLinha >= 0 && novaLinha < linhas && novaColuna >= 0 && novaColuna < colunas) {
                    char celulaVizinha = mapa[novaLinha][novaColuna];

                    // Se for caminho livre ou a saída, joga na fila de prioridades com Custo + 1
                    if (celulaVizinha == '1' || celulaVizinha == 'S') {
                        filaPrioridade.add(new int[]{novaLinha, novaColuna, custoAtual + 1});
                    }
                }
            }
        }

        return false;
    }
}