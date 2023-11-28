/*
package main;

import java.io.File;
import java.util.Scanner;
import visual.Janela;
import visual.PainelGrafo;

public class Principal {
    public static final int ALTURA = 120;
    public static final int LARGURA = 120;
    public static final int ESCALA = 5;
    public static final int OFF_SET = 20;
    
    private PainelGrafo painel;
    private Scanner s;
    private Janela janela;
    private Grafo grafo;
    
    private int menuPrincipal() {
        int opcao = 0; 
        
        do {         
            System.out.println("------------ Menu Principal ------------");
            System.out.println("| 1 . Importar grafo de um arquivo     |");
            System.out.println("| 2 . Criar um grafo vazio             |");
            System.out.println("----------------------------------------");
            System.out.println("Digite a opção escolhida: ");
            opcao = s.nextInt();
        } while(opcao < 1 || opcao > 2);
        
        return opcao;
    }
    
    private void menuTipoDoGrafo() {
        int opcao = 0;
        
        do {
            System.out.println("------ Tipo do Grafo ------");
            System.out.println("| 1 . Direcionado         |");
            System.out.println("| 2 . Não direcionado     |");
            System.out.println("---------------------------");
            System.out.println("Digite a opção escolhida: ");
            opcao = s.nextInt();
        } while(opcao < 1 || opcao > 2);
        
        if(opcao == 1) {
            grafo.setDirecionado(true);
        } else {
            grafo.setDirecionado(false);
        }
        
        do {
            System.out.println("Digite a quantidade de vertices do grafo: ");
            opcao = s.nextInt();
        }  while(opcao < 0);
        
        grafo.criarGrafoVazio();
        
        int i = 0;
        int x, y;
        while(i < opcao) {
            System.out.println("Digite a coordenada X do vertice " + i + ": ");
            x = s.nextInt();
            if(x < 0 || x > 100) {
                System.out.println("Coordenada invalida!");
                continue;
            }

            System.out.println("Digite a coordenada Y do vertice " + i + ": ");
            y = s.nextInt();
            if(y < 0 || y > 100) {
                System.out.println("Coordenada invalida!");
                continue;
            }
            i++;
            grafo.inserirVertice(x, y);
        }
    }
    
    private int menuOperacoes() {
        int opcao = 0;     
        
        do {
            System.out.println("---------------------- Operações -----------------------");
            System.out.println("|  1 . Exibir as adjacências                           |");
            System.out.println("|  2 . Consultar se um vértice é adjacente a outro     |");
            System.out.println("|  3 . Inserir nova aresta                             |");
            System.out.println("|  4 . Remover aresta                                  |");
            System.out.println("|  5 . Editar a coordenada do vértice                  |");
            System.out.println("|  6 . Consultar o primeiro adjacente um vértice       |");
            System.out.println("|  7 . Consultar o próximo adjacente de um vértice     |");
            System.out.println("|  8 . Consultar a lista completa de adjacentes        |");
            System.out.println("|  9 . Exportar o grafo para um arquivo de texto       |");
            System.out.println("| 10 . Exibir o grafo                                  |");
            System.out.println("| 11 . Sair                                            |");
            System.out.println("--------------------------------------------------------");
            System.out.println("Digite a opção escolhida: ");
            opcao = s.nextInt();
        } while(opcao < 1 || opcao > 11);
        
        return opcao;
    }
    
    private void imprimirGrafo() {
        grafo.imprimirGrafo();
    }
    
    private boolean verticeValido(int vertice) {
        if(vertice < 0 || vertice >= grafo.getQntVertice()) {
            return false;
        }
        return true;
    }

    private void adjacentes() {
        int origem, destino;
        
        System.out.println("Digite a origem: ");
        origem = s.nextInt();
        if(!verticeValido(origem)) {
            System.out.println("Vertice invalido");
            return;
        }
        
        System.out.println("Digite o destino: ");
        destino = s.nextInt();
        if(!verticeValido(destino)) {
            System.out.println("Vertice invalido!");
            return;
        }
        
        if(grafo.adjacentes(origem, destino)) {
            System.out.println("São adjacentes!");
            return;
        }
        System.out.println("Não são adjacentes!");
    }
    
    private void inserirArresta() {
        int origem, destino, peso;
        
        System.out.println("Digite a origem:");
        origem = s.nextInt();       
        if(!verticeValido(origem)) {
            System.out.println("Vertice invalido");
            return;
        }
        
        System.out.println("Digite o destino: ");
        destino = s.nextInt();
        if(!verticeValido(destino)) {
            System.out.println("Vertice invalido!");
            return;
        }
        
        System.out.println("Digite o peso: ");
        peso = s.nextInt();
        if(peso < 0) {
            System.out.println("Peso invalido!");
            return;
        }
        
        grafo.inserirAresta(origem, destino, peso, false);
        if(!grafo.isDirecionado()) {
            grafo.inserirAresta(origem, destino, peso, true);
        }
        System.out.println("Arresta inserida!");
    }
    
    private void removerArresta() {
        int origem, destino;
        
        System.out.println("Digite a origem:");
        origem = s.nextInt();       
        if(!verticeValido(origem)) {
            System.out.println("Vertice invalido");
            return;
        }
        
        System.out.println("Digite o destino: ");
        destino = s.nextInt();
        if(!verticeValido(destino)) {
            System.out.println("Vertice invalido!");
            return;
        }
        
        grafo.removerAresta(origem, destino);
        System.out.println("Arresta removida!");
    }
    
    private void editarCoordenada() {
        int vertice, x, y;
        
        System.out.println("Digite a origem:");
        vertice = s.nextInt();       
        if(!verticeValido(vertice)) {
            System.out.println("Vertice invalido");
            return;
        }
        
        System.out.println("Digite a coordenada X do vertice: ");
        x = s.nextInt();
        if(x < 0 || x > 100) {
            System.out.println("Coordenada invalida!");
            return;
        }
        
        System.out.println("Digite a coordenada Y do vertice: ");
        y = s.nextInt();
        if(y < 0 || y > 100) {
            System.out.println("Coordenada invalida!");
            return;
        }
        
        grafo.editarCoordenadasVertice(vertice, x, y);
        System.out.println("Coordenadas editadas!");
    }
    
    private void primeiroAdjacente() {
        int origem;
        
        System.out.println("Digite a origem:");
        origem = s.nextInt();       
        if(!verticeValido(origem)) {
            System.out.println("Vertice invalido");
            return;
        }
        
        int adjacente = grafo.primeiroAdjacente(origem);
        
        if(adjacente == -1) {
            System.out.println("Esse vertice não possui adjacentes");
            return;
        }
        
        System.out.println("O primeiro adjacente do vertice " + origem + " é o vertice " + adjacente);        
    }
    
    private void proximoAdjacente() {
        int origem, destino;
        
        System.out.println("Digite a origem:");
        origem = s.nextInt();       
        if(!verticeValido(origem)) {
            System.out.println("Vertice invalido");
            return;
        }
        
        System.out.println("Digite o destino: ");
        destino = s.nextInt();
        if(!verticeValido(destino)) {
            System.out.println("Vertice invalido!");
            return;
        }
        
        int adjacente = grafo.proximoAdjacente(origem, destino);
        if(adjacente == -1) {
            System.out.println("Esse vertice não possui adjacentes depois do vertice " + destino);
        }
        System.out.println("O próximo adjacente do vertice " + origem + " depois do vertice " + destino + " é o vertice " + adjacente);
    }
    
    private void listaAdjacentes() {
        int origem;
        
        System.out.println("Digite a origem:");
        origem = s.nextInt();       
        if(!verticeValido(origem)) {
            System.out.println("Vertice invalido");
            return;
        }
        
        grafo.listaAdjacentes(origem);
    }
    
    private void exportarGrafo() {
        if(grafo.criarArquivoExportacao("src/txt/Saida.txt")) {
            if(!grafo.exportarArquivo("src/txt/Saida.txt")) {
                System.out.println("Erro ao exportar o grafo para arquivo!");
            }
        } else {
            System.out.println("Erro ao criar o arquivo de exportação!");
        }
    }
    
    private void exibirGrafo() {
        janela = new Janela(LARGURA, ALTURA, ESCALA);
    }
    
    public void iniciar() {
        boolean continua = true;
        int opcao = 0;
        
        grafo.setDirecionado(true);
        grafo.criarGrafoVazio();
        exibirGrafo();
        
        //opcao = menuPrincipal();
        opcao = 2;
        /*
        if(opcao == 1) {
            File arquivo = new File("src/txt/Entrada.txt");  
            grafo.importarArquivo(arquivo);
        } else {
            menuTipoDoGrafo();
        }
        
        /*
        do {
            opcao = menuOperacoes();
            switch(opcao) {
                case 1:
                    imprimirGrafo();
                    break;
                case 2:
                    adjacentes();
                    break;
                case 3:
                    inserirArresta();
                    break;
                case 4:
                    removerArresta();
                    break;
                case 5:
                    editarCoordenada();
                    break;
                case 6:
                    primeiroAdjacente();
                    break;
                case 7:
                    proximoAdjacente();
                    break;
                case 8:
                    listaAdjacentes();
                    break;
                case 9:
                    exportarGrafo();
                    break;
                case 10:
                    exibirGrafo();
                    break;
                case 11:
                    continua = false;
                    break;
            }
        } while(continua);
        *//*
    }
    
    public Principal() {                        
        s = new Scanner(System.in);
        grafo = new Grafo();
        painel = new PainelGrafo(grafo);                                      
    }
}

*/