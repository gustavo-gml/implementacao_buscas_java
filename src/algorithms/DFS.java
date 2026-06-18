package algorithms;

import java.util.ArrayDeque;
import java.util.Stack;

public class DFS {

    // Vetores de direção: Cima, Baixo, Esquerda, Direita
    private static final int[][] DIRECOES = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    public static boolean executar(char[][] mapa) {
        int linhas = mapa.length;
        int colunas = mapa[0].length;

        // 1. Encontrar as coordenadas da Entrada ('E')
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

        if (linhaAtual == -1) return false; // Não têm entrada

        // 2. Inicializar a Pilha (Stack)
        ArrayDeque<int[]> pilha = new ArrayDeque<>(linhas * colunas);
        
        pilha.push(new int[]{linhaAtual, colunaAtual});
        // A própria letra 'E' já impede que a entrada seja revisitada

        // 3. Executar a Busca
        while (!pilha.isEmpty()) {
            int[] noAtual = pilha.pop();

            int l = noAtual[0];
            int c = noAtual[1];

            // Verifica se chegou na saída
            if (mapa[l][c] == 'S') {
                return true;
            }

            // Explora os vizinhos
            for (int[] dir : DIRECOES) {
                int novaLinha = l + dir[0];
                int novaColuna = c + dir[1];

                // Valida os limites da matriz
                if (novaLinha >= 0 && novaLinha < linhas && novaColuna >= 0 && novaColuna < colunas) {
                    char celulaVizinha = mapa[novaLinha][novaColuna];

                    // Só adiciona na pilha se for um caminho livre ou a própria saída
                    if (celulaVizinha == '1' || celulaVizinha == 'S') {

                        // Modificação in-place: "picha" o mapa para não andar em círculos
                        if (celulaVizinha == '1') { //se for 'S', a verificação inicial cobre.
                            mapa[novaLinha][novaColuna] = 'V';
                        }

                        pilha.push(new int[]{novaLinha, novaColuna});
                    }
                }
            }
        }

        return false; // Explorou tudo e não achou a saída
    }
}