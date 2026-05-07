package algorithms;

import java.util.ArrayDeque;
import java.util.Queue;

public class BFS {

    // Ordem natural e direta para FIFO: Cima, Baixo, Esquerda, Direita
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

        // 2. Inicializar a Fila (Queue) com ArrayDeque para máxima performance
        Queue<int[]> fila = new ArrayDeque<>();
        fila.add(new int[]{linhaAtual, colunaAtual});

        // 3. Executar a Busca
        while (!fila.isEmpty()) {
            int[] noAtual = fila.poll(); // poll() tira e retorna o PRIMEIRO da fila
            int l = noAtual[0];
            int c = noAtual[1];

            if (mapa[l][c] == 'S') {
                return true;
            }

            for (int[] dir : DIRECOES) {
                int novaLinha = l + dir[0];
                int novaColuna = c + dir[1];

                if (novaLinha >= 0 && novaLinha < linhas && novaColuna >= 0 && novaColuna < colunas) {
                    char celulaVizinha = mapa[novaLinha][novaColuna];

                    if (celulaVizinha == '1' || celulaVizinha == 'S') {

                        // Modificação in-place para não criar matriz auxiliar
                        if (celulaVizinha == '1') {
                            mapa[novaLinha][novaColuna] = 'V';
                        }

                        // Agenda o vizinho no FINAL da fila
                        fila.add(new int[]{novaLinha, novaColuna});
                    }
                }
            }
        }

        return false;
    }
}