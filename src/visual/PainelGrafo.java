package visual;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import main.Arresta;
import main.Componente;
import main.Vertice;

public class PainelGrafo extends JPanel implements MouseListener, MouseMotionListener, KeyListener, Runnable {
        
    private Vertice verticeSelecionado;
    private Arresta arestaSelecionada;
    
    private ArrayList<Componente> listaAnimacao = new ArrayList<>();
    
    private double velocidade = 1;
    private boolean animando = false;
    private int frameAnimacao = 0;
    private Thread thread;
    
    private JTextField campoPeso;
    private JTextField campoNome;
    
    private Janela janela;
       
    public PainelGrafo(Janela janela) {
        this.janela = janela;
        this.addMouseListener(this);
        this.addMouseMotionListener(this);              
    }
    
    private void editar(int x, int y) {
        Componente componente = janela.getGrafo().getVerticeClicado(x, y);
        if(componente == null) {
            componente = janela.getGrafo().getArestaClicada(x, y);
        }
        if(componente != null) {
            if(componente.getClass().equals(Vertice.class)) {
                verticeSelecionado = (Vertice)componente;
                gerarCampoNome(verticeSelecionado.getX(), verticeSelecionado.getY());
            } else if(componente.getClass().equals(Arresta.class)) {
                arestaSelecionada = (Arresta)componente;
                gerarCampoPeso((int)arestaSelecionada.getAresta().getX1(), 
                        (int)arestaSelecionada.getAresta().getY1(), 
                        (int)arestaSelecionada.getAresta().getX2(), 
                        (int)arestaSelecionada.getAresta().getY2());
            }        
        }            
    }
    
    private void selecionarVertice(int x, int y) {
        Vertice vertice = janela.getGrafo().getVerticeClicado(x, y);
        
        // Se o clique foi em cima de um vertice
        if (vertice != null) {
            vertice.setSelecionado(true);
            
            // Se o clique foi em cima de um vertice diferente daquele já selecionado
            if(verticeSelecionado != null && !verticeSelecionado.equals(vertice)) {
                verticeSelecionado.setSelecionado(false);
            }
            verticeSelecionado = vertice;
            
        } 
        // Se existe um vertice selecionado e o clique foi em um lugar vazio
        else if(verticeSelecionado != null) {
            desselecionar();
        }
        
    }
    
    public boolean isSelecionado() {
        return verticeSelecionado != null;
    }
    
    public void adicionarVertice(int x, int y) {
        janela.getGrafo().inserirVertice(x, y);       
    }
    
    public void adicionarAresta(int x, int y) {                             
        Vertice destino;
        
        // Se existe um vertice selecionado
        if(verticeSelecionado != null) {
            destino = janela.getGrafo().getVerticeClicado(x, y);
            if(destino != null) {
                
                if(!janela.getGrafo().adjacentes(verticeSelecionado.getId(), destino.getId())) {                   
                    janela.getGrafo().inserirAresta(verticeSelecionado.getId(), destino.getId(), 0);
                    arestaSelecionada = janela.getGrafo().getAresta(verticeSelecionado.getId(), destino.getId());
                    gerarCampoPeso(verticeSelecionado.getX(), verticeSelecionado.getY(), destino.getX(), destino.getY());                    
                }                
            }
            desselecionar();

        } 
        // Caso nenhum vertice esteja selecionado
        else {
            selecionarVertice(x, y);
        }      
    }
    
    private void gerarCampoPeso(int origemX, int origemY, int destinoX, int destinoY) {
        campoPeso = new JTextField();
        campoPeso.setBounds((origemX+destinoX)/2, (origemY+destinoY)/2, 30, 20);
              
        campoPeso.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                campoPesoFocusLost(evt);
            }
        });
        campoPeso.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                campoPesoKeyPressed(evt);
            }
        });
        
        this.add(campoPeso); 
        campoPeso.requestFocus();
    }
    
    private void gerarCampoNome(int x, int y) {
        campoNome = new JTextField(verticeSelecionado.getNome());
        campoNome.setBounds(x, y-Vertice.TAMANHO, 100, 20);
        
        
        campoNome.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                campoNomeFocusLost(evt);
            }
        });
        campoNome.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                campoNomeKeyPressed(evt);
            }
        });
        
        this.add(campoNome);
        campoNome.requestFocus();
    }
    
    public void desselecionar() {    
        verticeSelecionado.setSelecionado(false);
        verticeSelecionado = null;
        pintarPreto();
    }
    
    public void moverVertice(int x, int y) {
        // Se existe um vertice selecionado
        if(verticeSelecionado != null) {
            if(janela.getGrafo().getVerticeClicado(x, y) == null) {
                janela.getGrafo().editarCoordenadasVertice(verticeSelecionado, x, y);
            }
        } else {
            selecionarVertice(x, y);
        }
    }
    
    public void removerAresta(int x, int y) {
        Arresta aresta = janela.getGrafo().getArestaClicada(x, y);
        if(aresta != null) {
            janela.getGrafo().removerAresta(aresta.getOrigem(), aresta.getDestino());
        }
    }
    
    public void primeiroAdjacente(int x, int y) {
        selecionarVertice(x, y);
        
        if(verticeSelecionado != null) {            
            listaAnimacao = janela.getGrafo().primeiroAdjacente(verticeSelecionado.getId());
            pintarVermelho();
        }
    }
    
    public void proximoAdjacente(int x, int y) {              
        if(verticeSelecionado != null) {            
            Vertice destino = janela.getGrafo().getVerticeClicado(x, y);
            if(destino != null) {
                listaAnimacao = janela.getGrafo().proximoAdjacente(verticeSelecionado.getId(), destino.getId());
                pintarVermelho();
            } else {
                desselecionar();
            }
        } else {
            selecionarVertice(x, y);
        }
    }
    
    public void todosAdjacentes(int x, int y) {
        selecionarVertice(x, y);
        
        if(verticeSelecionado != null) {            
            if(verticeSelecionado != null) {            
                listaAnimacao = janela.getGrafo().todosAdjacentes(verticeSelecionado.getId());
                pintarVermelho();
            }
        }
    }
    
    public void buscaEmProfundidade(int x, int y) {
        Vertice vertice = janela.getGrafo().getVerticeClicado(x, y);
        if(vertice != null) {
            listaAnimacao = janela.getGrafo().buscaEmProfundidade(vertice.getId());
            animar();
        }     
    }
    
    // TODO descobrir pq tem um atraso grande nos 2 ultimos vertices, possivelmente componentes repetidos na lista
    public void buscaEmLargura(int x, int y) {
        Vertice vertice = janela.getGrafo().getVerticeClicado(x, y);
        if(vertice != null) {
            listaAnimacao = janela.getGrafo().buscaEmLargura(vertice.getId());
            animar();
        }  
    }
    
    public void arvoreGeradoraMinima(int x, int y) {
        Vertice vertice = janela.getGrafo().getVerticeClicado(x, y);
        if(vertice != null) {
            listaAnimacao = janela.getGrafo().arvoreGeradoraMinima(vertice.getId());
            animar();
        }
    }    
    
    public void menorCaminho(int x, int y) {
        if(verticeSelecionado == null) {
            selecionarVertice(x, y);
            this.repaint();
            return;
        }
        Vertice vertice = janela.getGrafo().getVerticeClicado(x, y);
        if(vertice != null) {           
            listaAnimacao = janela.getGrafo().menorCaminho(verticeSelecionado.getId(), vertice.getId());
            animar();
        } else {
            desselecionar();
            this.repaint();
        }
    }
    
    private void animar() {
        animando = true;
        frameAnimacao = 0;
        janela.ativarBotoes();
        thread = new Thread(this);       
    }
       
    @Override
    public void paintComponent(Graphics g) {
        if(animando) {
            desenharComponente(g);
        } else {  
            super.paintComponent(g);        
            janela.getGrafo().desenharArrestas(g);
            janela.getGrafo().desenharVertices(g);
            pintarPreto();

            g.setColor(Color.blue);
            if(verticeSelecionado != null) {
                g.drawRect(verticeSelecionado.getX()-1, verticeSelecionado.getY()-1, Vertice.TAMANHO+2, Vertice.TAMANHO+2);
            }
        }
    }
    
    private void pintarVermelho() {
        for(Componente i : listaAnimacao) {
            i.setCor(255, 0, 0);
        }
    }
    
    private void pintarPreto() {
        for(Componente i : listaAnimacao) {
            i.setCor(0, 0, 0);
        }
    }
    
    //TODO descobrir pq o primeiro frame da animação não chega aqui
    private void desenharComponente(Graphics g) {
        System.out.println(frameAnimacao);
        Componente i = listaAnimacao.get(frameAnimacao);       
        if(i.getClass().equals(Vertice.class)) {
            ((Vertice)i).desenharVertice(g);    
        } else if(i.getClass().equals(Arresta.class)) {
            Arresta aresta = (Arresta)i;
            aresta.desenharArresta((int)janela.getGrafo().getVertice(aresta.getOrigem()).getVertice().getCenterX(),
                    (int)janela.getGrafo().getVertice(aresta.getOrigem()).getVertice().getCenterY(),
                    (int)janela.getGrafo().getVertice(aresta.getDestino()).getVertice().getCenterX(),
                    (int)janela.getGrafo().getVertice(aresta.getDestino()).getVertice().getCenterY(),
                    g, janela.getGrafo().isDirecionado());
        }       
    }


    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {  
        if(animando) {
            return;
        }
        
        switch(PainelFerramentas.getBotaoSelecionado()) {
            case PainelFerramentas.BOTAO_SELECIONAR:
                selecionarVertice(e.getX(), e.getY());
                break;
            case PainelFerramentas.BOTAO_ADICIONAR_VERTICE:
                adicionarVertice(e.getX(), e.getY());
                break;
            case PainelFerramentas.BOTAO_ADICIONAR_ARESTA:
                adicionarAresta(e.getX(), e.getY());
                break;
            case PainelFerramentas.BOTAO_MOVER_VERTICE:
                moverVertice(e.getX(), e.getY());
                break;
            case PainelFerramentas.BOTAO_REMOVER_ARESTA:
                removerAresta(e.getX(), e.getY());
                break;
            case PainelFerramentas.BOTAO_PRIMEIRO_ADJACENTE:
                primeiroAdjacente(e.getX(), e.getY());
                break;
            case PainelFerramentas.BOTAO_PROXIMO_ADJACENTE:
                proximoAdjacente(e.getX(), e.getY());
                break;
            case PainelFerramentas.BOTAO_TODOS_ADJACENTES:
                todosAdjacentes(e.getX(), e.getY());
                break;
            case PainelFerramentas.BOTAO_EDITAR:
                editar(e.getX(), e.getY());
                break;
            case PainelFerramentas.BOTAO_BUSCA_PROFUNDIDADE:
                buscaEmProfundidade(e.getX(), e.getY());
                return;
            case PainelFerramentas.BOTAO_BUSCA_LARGURA:
                buscaEmLargura(e.getX(), e.getY());
                return;
            case PainelFerramentas.BOTAO_ARVORE_GERADORA_MINIMA:
                arvoreGeradoraMinima(e.getX(), e.getY());
                return;
            case PainelFerramentas.BOTAO_MENOR_CAMINHO:
                menorCaminho(e.getX(), e.getY());
                return;  
        }
        this.repaint();
    }
    
    public void campoPesoKeyPressed(KeyEvent evt) {
        if(evt.getKeyChar() == KeyEvent.VK_ENTER) {
            campoPeso.setFocusable(false);
        }
    }
    
    public void campoPesoFocusLost(FocusEvent evt) {
        try {
            janela.getGrafo().editarPesoAresta(arestaSelecionada, Integer.parseInt(campoPeso.getText()));
        } catch(Exception e) {
            
        }
        arestaSelecionada = null;
        this.remove(campoPeso);
        this.repaint();
    }
    
    public void campoNomeKeyPressed(KeyEvent evt) {
        if(evt.getKeyChar() == KeyEvent.VK_ENTER) {
            campoNome.setFocusable(false);
        }
    }
    
    public void campoNomeFocusLost(FocusEvent evt) {
        try {
            verticeSelecionado.setNome(campoNome.getText());
        } catch(Exception e) {
            
        }
        verticeSelecionado = null;
        this.remove(campoNome);
        this.repaint();
    }
    
    public void setVelocidade(double velocidade) {
        this.velocidade = velocidade;
    }
    
    public void cancelar() {
        thread.interrupt();
        animando = false;
        janela.desativarBotoes();
        frameAnimacao = 0;
        listaAnimacao = new ArrayList<>();
        this.repaint();
    }
    
    // por algum motivo ele deixa a contagem de frames passar do tamanho do vetor
    public void play() {
        if(!thread.isAlive()) {
           thread = new Thread(this);
           thread.start(); 
        }
    }
    
    // nao funciona
    public void pausar() {
        
    }
    
    // nao limpa a tela antes de recomeçar a animação
    public void reiniciar() {
        animando = false;
        this.repaint();      
        frameAnimacao = 0;
        animando = true;
    }
    
    public void avancarFrame() {
        if(frameAnimacao < listaAnimacao.size()-1) {
            frameAnimacao++;
        }
        this.repaint();
    }
    
    // Não utilizado
    public void retrocederFrame() {
        if(frameAnimacao >= 0) {
            frameAnimacao--;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseDragged(MouseEvent e) {}

    @Override
    public void mouseMoved(MouseEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void run() {
        double timePerFrame = 1000000000.0 / velocidade;
        long lastTime = System.nanoTime();
        long now = System.nanoTime();
        int frames = 0;
        long lastCheck = System.currentTimeMillis();            
           
        while(frameAnimacao < listaAnimacao.size()-1) {
            now = System.nanoTime();
            if(now - lastTime >= timePerFrame) {                
                this.repaint();               
                lastTime = now;
                frames++;
                frameAnimacao++;
            }

            if(System.currentTimeMillis() - lastCheck >= 1000) {
                lastCheck = System.currentTimeMillis();
                frames = 0;
            }
        }
    }
}
