package io;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class MazeLoader {
    
    // Uma classe interna simples para empacotar o resultado
    public static class Labirinto {
        public char[][] mapa;
        public int linhas;
        public int colunas;
    }

    public static Labirinto carregar(String caminho) {
        try {
            // Lê todas as linhas de uma vez para a RAM
            List<String> linhasArquivo = Files.readAllLines(Paths.get(caminho));
            
            // Remove possíveis linhas em branco geradas no final do .txt
            linhasArquivo.removeIf(String::isBlank);

            Labirinto lab = new Labirinto();
            lab.linhas = linhasArquivo.size();
            lab.colunas = linhasArquivo.get(0).trim().length();
            lab.mapa = new char[lab.linhas][lab.colunas];

            // Converte as Strings para a Matriz Primitiva (Extremamente rápido)
            for (int l = 0; l < lab.linhas; l++) {
                String linhaStr = linhasArquivo.get(l).trim();
                for (int c = 0; c < lab.colunas; c++) {
                    lab.mapa[l][c] = linhaStr.charAt(c);
                }
            }

            return lab;

        } catch (Exception e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
            System.exit(1);
            return null;
        }
    }
}