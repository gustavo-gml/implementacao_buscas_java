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

        // Array [0] = custo_F, [1] = custo_G, [2] = ID 1D
        PriorityQueue<long[]> filaPrioridade = new PriorityQueue<>(linhas * colunas, Comparator.comparingLong(a -> a[0]));

        long hInicial = Math.abs(linhaInicio - linhaDestino) + Math.abs(colunaInicio - colunaDestino);
        long idInicio = (long) linhaInicio * colunas + colunaInicio;
        
        filaPrioridade.add(new long[]{hInicial, 0, idInicio});

        while (!filaPrioridade.isEmpty()) {

            long[] noAtual = filaPrioridade.poll();
            long gAtual = noAtual[1];
            int id = (int) noAtual[2];
            
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

                        long novoG = gAtual + 1;
                        long novoH = Math.abs(novaLinha - linhaDestino) + Math.abs(novaColuna - colunaDestino);
                        long novoF = novoG + novoH;
                        long novoId = (long) novaLinha * colunas + novaColuna;

                        filaPrioridade.add(new long[]{novoF, novoG, novoId});
                    }
                }
            }
        }
        return false;
    }
}