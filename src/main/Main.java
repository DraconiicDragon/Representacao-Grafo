package main;

import java.util.Scanner;
import java.io.FileNotFoundException; 
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import visual.Janela;

public class Main {

    public static final int ALTURA = 120;
    public static final int LARGURA = 120;
    public static final int ESCALA = 5;
    
    public static void main(String[] args) {
        Janela janela = new Janela(LARGURA, ALTURA, ESCALA);
        /*
        Grafo grafo = new Grafo();
        File arquivo = new File("src/main/teste.txt");
        grafo.importarArquivo(arquivo);
        grafo.removerAresta(0, 1);
        grafo.imprimirGrafo();
        if(grafo.criarArquivoExportacao("src/main/Saida.txt")) {
            if(!grafo.exportarArquivo("src/main/Saida.txt")) {
                System.out.println("Erro ao exportar o grafo para arquivo!");
            }
        } else {
            System.out.println("Erro ao criar o arquivo de exportação!");
        }
        */
    }
    
}
