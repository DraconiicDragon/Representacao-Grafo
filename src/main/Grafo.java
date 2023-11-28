package main;

import java.awt.Color;
import java.awt.Graphics;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

import java.util.Scanner;

public class Grafo {
    private ArrayList<ArrayList<Arresta>> grafo = new ArrayList<>();
    private ArrayList<Vertice> vertices = new ArrayList<>();
    private int qntVertice;
    private int qntAresta;
    private boolean direcionado;
    
    public Grafo() {
    }
    
    public Grafo(boolean direcionado) {
        this.direcionado = direcionado;
    }
    
    
    
    public boolean importarArquivo(File arquivo) {       
        try {          
            Scanner s = new Scanner(arquivo);
            int qntAresta;
            int qntVertice;
            
            String direcionado = s.nextLine();                 
            this.direcionado = !direcionado.equals("nao");
            
            qntVertice = s.nextInt();            
            criarGrafoVazio();
            
            int idAux = 0;
            for(int i = 0; i < qntVertice; i++) {                              
                int id, x, y;
                String nome;
                
                id = s.nextInt();
                x = s.nextInt();
                y = s.nextInt();
                s.skip(" ");
                nome = s.nextLine();
                
                if(id != idAux) {
                    return false;
                }
                
                inserirVertice(x, y, nome); 
                idAux++;
            }
            
            qntAresta = s.nextInt();
            
            for(int i = 0; i < qntAresta; i++) {
                int origem, destino, peso;
                origem = s.nextInt();
                destino = s.nextInt();
                peso = s.nextInt();
                
                inserirAresta(origem, destino, peso);
            }
            return true;
            
        } catch(FileNotFoundException e) {
            System.out.println("Falha ao abrir o arquivo");
            return false;
        }
    }
    
    public boolean criarArquivoExportacao(String endereco) throws Exception {
        try {
            File arquivo = new File(endereco);
            arquivo.createNewFile();
            return true;
        } catch(IOException e) {
            throw e;
        }
    }
    
    public boolean exportarArquivo(String endereco) throws Exception {
        try {
            FileWriter arquivo = new FileWriter(endereco);
            
            if(direcionado) {
                arquivo.write("sim");
            } else {
                arquivo.write("nao");
            }
            
            arquivo.write("\n");
            arquivo.write(String.valueOf(qntVertice));
            arquivo.write("\n");
            
            for(Vertice i : vertices) {
                arquivo.write(i.toString());
                arquivo.write("\n");
            }
            
            arquivo.write(String.valueOf(qntAresta));
            arquivo.write("\n");
            
            for(int i = 0; i < qntVertice; i++) {
                for(Arresta j : grafo.get(i)) {
                    if(!j.isDuplicada()) {
                        arquivo.write(i + " " + j.getDestino() + " " + j.getPeso());
                        arquivo.write("\n");
                    }
                }
            }
            
            arquivo.close();
            return true;
        } catch(IOException e) {
            throw e;
        }
    }
    
    public void inserirAresta(int origem, int destino, int peso) {      
        grafo.get(origem).add(new Arresta(origem, destino, peso, false));
        if(!direcionado) {
            grafo.get(destino).add(new Arresta(destino, origem, peso, true));
        }
        qntAresta++;
    }
    
    public boolean removerAresta(int origem, int destino) {
        if(!direcionado) {
            for(Arresta j : grafo.get(destino)) {
                if(j.getDestino() == origem) {
                    grafo.get(destino).remove(j);
                    break;
                }
            }
        }
        for(Arresta i : grafo.get(origem)) {
            if(i.getDestino() == destino) {
                grafo.get(origem).remove(i);
                qntAresta--;
                return true;
            }
        }      
        return false;
    }
    
    public Arresta getAresta(int origem, int destino) {
        for(Arresta i : grafo.get(origem)) {
            if(i.getDestino() == destino) {
                return i;
            }
        }
        return null;
    }
    
    public void editarCoordenadasVertice(Vertice vertice, int x, int y) {
        vertice.setXY(x, y);
    }
    
    public void editarPesoAresta(Arresta aresta, int peso) {
        aresta.setPeso(peso);
        if(!direcionado) {
            getAresta(aresta.getDestino(), aresta.getOrigem()).setPeso(peso);
        }
    }
    
    public void imprimirGrafo() {
        for(int i = 0; i < qntVertice; i ++) {
            System.out.println("Origem " + i + ": ");
            for(Arresta j : grafo.get(i)) {
                System.out.println(j.getDestino() + ", " + j.getPeso());
            }
        }
    }
    
    public void criarGrafoVazio() {
        qntAresta = 0;
        qntVertice = 0;     
    }
    
    public void inserirVertice(int x, int y, String nome) {
        Vertice vertice = new Vertice(qntVertice, x, y, nome);
        vertices.add(vertice);
        grafo.add(new ArrayList<>());
        qntVertice++;
    }
    
    public void inserirVertice(int x, int y) {
        Vertice vertice = new Vertice(qntVertice, x, y);
        vertices.add(vertice);
        grafo.add(new ArrayList<>());
        qntVertice++;
    }
       
    public boolean adjacentes(int origem, int destino) {
        for(Arresta i : grafo.get(origem)) {
            if(i.getDestino() == destino) {
                return true;
            }
        }
        
        return false;
    }
    
    public ArrayList<Componente> primeiroAdjacente(int origem) {
        ArrayList<Componente> lista = new ArrayList<>();
        lista.add(vertices.get(origem));
        for(Arresta i : grafo.get(origem)) {
            lista.add(vertices.get(i.getDestino()));
            if(i.isDuplicada()) {
                i = getAresta(i.getDestino(), origem);
            }
            lista.add(i);          
            break;
        }
        return lista;
    }
    
    public ArrayList<Componente> proximoAdjacente(int origem, int adjacente) {
        ArrayList<Componente> lista = new ArrayList<>();
        boolean achou = false;
        
        lista.add(vertices.get(origem));
        for(Arresta i : grafo.get(origem)) {
            if(i.getDestino() == adjacente) {
                achou = true;
                continue;
            }
            if(achou) {
                lista.add(vertices.get(i.getDestino()));
                if(i.isDuplicada()) {
                    i = getAresta(i.getDestino(), origem);
                }                
                lista.add(i);                
                break;
            }
        }
        return lista;
    }
    
    public ArrayList<Componente> todosAdjacentes(int origem) {
        ArrayList<Componente> lista = new ArrayList<>();
        lista.add(vertices.get(origem));
        for(Arresta i : grafo.get(origem)) {
            lista.add(vertices.get(i.getDestino()));
            if(i.isDuplicada()) {
                i = getAresta(i.getDestino(), origem);
            }
            lista.add(i);
        }
        return lista;
    }
    
    public ArrayList<Componente> buscaEmProfundidade(int origem) {
        ArrayList<Componente> lista = new ArrayList<>();
        ArrayList<Boolean> visitado = new ArrayList<>(qntVertice);
        for(int i = 0; i < qntVertice; i++) {
            visitado.add(false);                 
        }
        
        // Caso não precisar buscar vertices que não estão ligados a origem
        visitaEmProfundidade(lista, origem, visitado);
        
        /*
        // Caso precisar visitar todos os vertices, origem fixa no vertice 0
        for(int i = 0; i < qntVertice; i++) {
            if(!visitado.get(i)) {
                visitaEmProfundidade(lista, i, visitado);
            }
        }
        */
        return lista;
    }
          
    private void visitaEmProfundidade(ArrayList<Componente> lista, int origem, ArrayList<Boolean> visitado) {
        Vertice vertice = copiarVertice(vertices.get(origem));
        vertice.setCor(Color.gray);
        lista.add(vertice);
        
        visitado.set(origem, true);
        for(Arresta i : grafo.get(origem)) {
            if(!visitado.get(i.getDestino())) {
                Arresta aresta = copiarAresta(i);
                aresta.setCor(Color.red);
                lista.add(aresta);
                
                visitaEmProfundidade(lista, i.getDestino(), visitado);
            }
        }
        vertice = copiarVertice(vertice);
        vertice.setCor(Color.red);
        lista.add(vertice);
    }
    
    public ArrayList<Componente> buscaEmLargura(int origem) {
        ArrayList<Componente> lista = new ArrayList<>();
        ArrayList<Boolean> visitado = new ArrayList<>();
        
        for(int i = 0; i < qntVertice; i++) {
            visitado.add(false);
        }
        
        // Caso não precisar buscar vertices que não estão ligados a origem
        visitaEmLargura(lista, origem, visitado);
        
        /*
        // Caso precisar visitar todos os vertices, origem fixa no vertice 0
        for(int i = 0; i < qntVertice; i++) {
            if(!visitado.get(i)) {
                visitaEmLargura(lista, i, visitado);
            }
        }
        */
        
        return lista;
    }
    
    private void visitaEmLargura(ArrayList<Componente> lista, int origem, ArrayList<Boolean> visitado) {
        Vertice vertice;
        Queue<Integer> fila = new LinkedList<>();
        fila.add(origem);
        
        while(!fila.isEmpty()) {
            origem = fila.poll(); 
            
            if(!visitado.get(origem)) {
                vertice = copiarVertice(vertices.get(origem));
                vertice.setCor(Color.gray);
                lista.add(vertice);
            }
            
            for(Arresta i : grafo.get(origem)) {
                if(!visitado.get(i.getDestino())) {   
                    fila.add(i.getDestino()); 
                    
                    Arresta aresta = copiarAresta(i);
                    aresta.setCor(Color.red);
                    lista.add(aresta);                                                  
                }
            }
            
            if(!visitado.get(origem)) {
                visitado.set(origem, true);            
                vertice = copiarVertice(vertices.get(origem));
                vertice.setCor(Color.red);
                lista.add(vertice);
            }
            
        }
    }
    
    // Algoritmo de Prim
    public ArrayList<Componente> arvoreGeradoraMinima(int origem) {
        ArrayList<Componente> lista = new ArrayList<>();       
        ArrayList<Boolean> visitado = new ArrayList<>();
        PriorityQueue<Arresta> fila = new PriorityQueue<>(new ComparadorAresta());
        
        
        int soma = 0;
        
        for(int i = 0; i < qntVertice; i++) {
            visitado.add(false);
        }
        
        visitado.set(origem, true);       
        for(Arresta i : grafo.get(origem)) {        
            fila.add(i);
        }
        Vertice vertice = copiarVertice(vertices.get(origem));
        vertice.setCor(Color.gray);
        lista.add(vertice);
        
        while(!fila.isEmpty()) {
            Arresta aresta = fila.poll();
            
            if(!visitado.get(aresta.getDestino())) {
                soma += aresta.getPeso();
                visitado.set(aresta.getDestino(), true);
                for(Arresta i : grafo.get(aresta.getDestino())) {
                    if(!visitado.get(i.getDestino())) {
                        fila.add(i);
                    }
                }
                aresta = copiarAresta(aresta);
                aresta.setCor(Color.red);
                lista.add(aresta);
                
                vertice = copiarVertice(vertices.get(aresta.getDestino()));
                vertice.setCor(Color.red);
                lista.add(vertice);
            }
        }
        System.out.println(soma);         
        return lista;
    }
    
    // Algoritmo de Dijkstra
    public ArrayList<Componente> menorCaminho(int origem, int destino) {
        ArrayList<Componente> lista = new ArrayList<>();
        ArrayList<Integer> caminhos = new ArrayList<>();
        ArrayList<Integer> antecessor = new ArrayList<>();
        ArrayList<Boolean> visitado = new ArrayList<>();
        
        for(int i = 0; i < qntVertice; i++) {
            caminhos.add(Integer.MAX_VALUE);
            antecessor.add(-1);
            visitado.add(false);
        }
        
        PriorityQueue<Arresta> fila = new PriorityQueue<>(new ComparadorAresta());
        caminhos.set(origem, 0);
        visitado.set(origem, true);
        antecessor.set(origem, -1);
        for(Arresta i : grafo.get(origem)) {
            fila.add(i);
        }
        
        while(!fila.isEmpty()) {
            Arresta aresta = fila.poll();
            if(aresta.getPeso()+caminhos.get(aresta.getOrigem()) < caminhos.get(aresta.getDestino())) {
                caminhos.set(aresta.getDestino(), aresta.getPeso()+caminhos.get(aresta.getOrigem()));
                antecessor.set(aresta.getDestino(), aresta.getOrigem());
            }
            if(!visitado.get(aresta.getDestino())) {
                visitado.set(aresta.getDestino(), true);                
                for(Arresta i : grafo.get(aresta.getDestino())) {
                    fila.add(i);
                }
            }
        }       
        
        
        
        int aux = destino;
        if(antecessor.get(aux) == -1) {
            return lista;
        }
        
        Vertice vertice = copiarVertice(vertices.get(destino));
        vertice.setCor(Color.red);
        lista.add(vertice);
        
        while(true) {
            if(antecessor.get(aux) == origem) {
                Arresta aresta = copiarAresta(getAresta(antecessor.get(aux), aux));
                aresta.setCor(Color.red);
                lista.add(aresta);
                vertice = copiarVertice(vertices.get(antecessor.get(aux)));
                vertice.setCor(Color.red);
                lista.add(vertice);
                break;
            } else {
                Arresta aresta = copiarAresta(getAresta(antecessor.get(aux), aux));
                aresta.setCor(Color.red);
                lista.add(aresta);
                aux = antecessor.get(aux);
                vertice = copiarVertice(vertices.get(aux));
                vertice.setCor(Color.red);
                lista.add(vertice);
            }
            
        }
        System.out.println(caminhos.get(destino));
        return lista;
    }
    
    class ComparadorAresta implements Comparator<Arresta> {

        @Override
        public int compare(Arresta a1, Arresta a2) {
            if(a1.getPeso() < a2.getPeso()) {
                return -1;
            } if(a1.getPeso() > a2.getPeso()) {
                return 1;
            }
            return 0;
        }
        
    }
    
    private Vertice copiarVertice(Vertice vertice) {
        return new Vertice(vertice.getId(), vertice.getX(), vertice.getY(), vertice.getNome());
    }
    
    private Arresta copiarAresta(Arresta aresta) {
        return new Arresta(aresta.getOrigem(), aresta.getDestino(), aresta.getPeso(), false);
    }
    
    public void desenharVertices(Graphics g) {        
        
        for(Vertice i : vertices) {
            i.desenharVertice(g);           
        }
        
    }
    
    public Vertice getVerticeClicado(int x, int y) {
        for(Vertice i : vertices) {
            if(i.getVertice().contains(x, y)) {                             
                return i;
            }
        }
        return null;
    }
    
    public Arresta getArestaClicada(int x, int y) {
        for(ArrayList<Arresta> i : grafo) {
            for(Arresta j : i) {
                Arresta aresta = j.getArestaClicada(x, y);
                if(aresta != null) {                   
                    return aresta;
                }
            }
        }
        return null;
    }
    
    public void desenharArrestas(Graphics g) {
        for(int i = 0; i < qntVertice; i++) {            
            for(Arresta j : grafo.get(i)) {
                j.desenharArresta((int)vertices.get(i).getVertice().getCenterX(), 
                        (int)vertices.get(i).getVertice().getCenterY(), 
                        (int)vertices.get(j.getDestino()).getVertice().getCenterX(), 
                        (int)vertices.get(j.getDestino()).getVertice().getCenterY(), 
                        g,
                        direcionado);
            }
        }
    }
    
    public Vertice getVertice(int id) {
        return vertices.get(id);
    }

    public int getQntVertice() {
        return qntVertice;
    }

    public int getQntAresta() {
        return qntAresta;
    }

    public boolean isDirecionado() {
        return direcionado;
    }

    public void setDirecionado(boolean direcionado) {
        this.direcionado = direcionado;
    }
    
}
