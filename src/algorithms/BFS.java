package algorithms;

public class BFS {

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

        // Pré-alocação bruta (Nenhum objeto criado no loop)
        int tamanhoMaximo = linhas * colunas;
        int[] fila = new int[tamanhoMaximo];
        int inicio = 0;
        int fim = 0;

        // Achatamento 2D para 1D
        fila[fim++] = linhaAtual * colunas + colunaAtual;

        while (inicio < fim) {
            
            // Tira da fila e desempacota
            int idAtual = fila[inicio++];
            int l = idAtual / colunas;
            int c = idAtual % colunas;

            if (mapa[l][c] == 'S') return true;

            for (int[] dir : DIRECOES) {
                int novaLinha = l + dir[0];
                int novaColuna = c + dir[1];

                if (novaLinha >= 0 && novaLinha < linhas && novaColuna >= 0 && novaColuna < colunas) {
                    char celulaVizinha = mapa[novaLinha][novaColuna];

                    if (celulaVizinha == '1' || celulaVizinha == 'S') {
                        if (celulaVizinha == '1') {
                            mapa[novaLinha][novaColuna] = 'V';
                        }
                        
                        // Empacota e joga no fim da fila
                        fila[fim++] = novaLinha * colunas + novaColuna;
                    }
                }
            }
        }
        return false;
    }
}