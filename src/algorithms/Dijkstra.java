package algorithms;

import java.util.Comparator;
import java.util.PriorityQueue;

public class Dijkstra {

    private static final int[][] DIRECOES = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    public static boolean executar(char[][] mapa) {
        int linhas = mapa.length;
        int colunas = mapa[0].length;

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

        // Array [0] = custo, [1] = ID 1D
        PriorityQueue<long[]> filaPrioridade = new PriorityQueue<>(linhas * colunas, Comparator.comparingLong(a -> a[0]));

        long idInicio = (long) linhaAtual * colunas + colunaAtual;
        filaPrioridade.add(new long[]{0, idInicio});

        while (!filaPrioridade.isEmpty()) {

            long[] noAtual = filaPrioridade.poll();
            long custoAtual = noAtual[0];
            int id = (int) noAtual[1];
            
            int l = id / colunas;
            int c = id % colunas;

            char celula = mapa[l][c];

            if (celula == 'V') continue;

            if (celula == 'S') return true;

            if (celula == '1' || celula == 'E') {
                mapa[l][c] = 'V';
            }

            for (int[] dir : DIRECOES) {
                int novaLinha = l + dir[0];
                int novaColuna = c + dir[1];

                if (novaLinha >= 0 && novaLinha < linhas && novaColuna >= 0 && novaColuna < colunas) {
                    char celulaVizinha = mapa[novaLinha][novaColuna];

                    if (celulaVizinha == '1' || celulaVizinha == 'S') {
                        long novoCusto = custoAtual + 1;
                        long novoId = (long) novaLinha * colunas + novaColuna;
                        filaPrioridade.add(new long[]{novoCusto, novoId});
                    }
                }
            }
        }
        return false;
    }
}