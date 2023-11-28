package visual;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

public class PainelFerramentas extends JPanel {
    
    private final int TAMANHO_BOTOES = 75;
    
    public static final int BOTAO_SELECIONAR = 0;
    public static final int BOTAO_ADICIONAR_VERTICE = 1;
    public static final int BOTAO_ADICIONAR_ARESTA = 2;   
    public static final int BOTAO_MOVER_VERTICE = 3;
    public static final int BOTAO_REMOVER_ARESTA = 4;
    public static final int BOTAO_PRIMEIRO_ADJACENTE = 5;
    public static final int BOTAO_PROXIMO_ADJACENTE = 6;
    public static final int BOTAO_TODOS_ADJACENTES = 7;
    public static final int BOTAO_EDITAR = 8;
    public static final int BOTAO_BUSCA_PROFUNDIDADE = 9;
    public static final int BOTAO_BUSCA_LARGURA = 10;
    public static final int BOTAO_ARVORE_GERADORA_MINIMA = 11;
    public static final int BOTAO_MENOR_CAMINHO = 12;
    
    
    private static int botaoSelecionado = 0;
    
    private ButtonGroup grupoBotoes;
    
    private JToggleButton botaoSelecionar;
    private JToggleButton botaoAdicionarVertice;  
    private JToggleButton botaoAdicionarAresta;
    private JToggleButton botaoMoverVertice;
    private JToggleButton botaoRemoverAresta;
    private JToggleButton botaoPrimeiroAdjacente;
    private JToggleButton botaoProximoAdjacente;
    private JToggleButton botaoTodosAdjacentes;
    private JToggleButton botaoEditar;
    private JToggleButton botaoBuscaProfundidade;
    private JToggleButton botaoBuscaLargura;
    private JToggleButton botaoArvoreGeradoraMinima;
    private JToggleButton botaoMenorCaminho;
       
    public PainelFerramentas() { 
        iniciarComponentes();
        botaoSelecionar.setSelected(true);
    }
    
    private void iniciarComponentes() {
        botaoSelecionar = new JToggleButton();
        botaoAdicionarVertice = new JToggleButton();        
        botaoAdicionarAresta = new JToggleButton();
        botaoMoverVertice = new JToggleButton();
        botaoRemoverAresta = new JToggleButton();
        botaoPrimeiroAdjacente = new JToggleButton();
        botaoProximoAdjacente = new JToggleButton();
        botaoTodosAdjacentes = new JToggleButton();
        
        botaoEditar = new JToggleButton();
        botaoBuscaProfundidade  = new JToggleButton();
        botaoBuscaLargura = new JToggleButton();
        botaoArvoreGeradoraMinima = new JToggleButton();
        botaoMenorCaminho = new JToggleButton();
                
        grupoBotoes = new ButtonGroup();
        
        //Configurações dos botões        
        adicionarBotao(botaoSelecionar, "/icones/Selecionar.png");
        botaoSelecionar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                botaoSelecionarActionPerformed(evt);
            }
        });
        
        adicionarBotao(botaoAdicionarVertice, "/icones/AdicionarVertice.png");
        botaoAdicionarVertice.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                botaoAdicionarVerticeActionPerformed(evt);
            }
        });
        
        adicionarBotao(botaoAdicionarAresta, "/icones/AdicionarAresta.png");
        botaoAdicionarAresta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                botaoAdicionarArestaActionPerformed(evt);
            }
        });

        adicionarBotao(botaoMoverVertice, "/icones/MoverVertice.png");
        botaoMoverVertice.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                botaoMoverVerticeActionPerformed(evt);
            }
        });
        
        adicionarBotao(botaoRemoverAresta, "/icones/RemoverAresta.png");
        botaoRemoverAresta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                botaoRemoverArestaActionPerformed(evt);
            }
        });
        
        adicionarBotao(botaoPrimeiroAdjacente, "/icones/PrimeiroAdjacente.png");
        botaoPrimeiroAdjacente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                botaoPrimeiroAdjacenteActionPerformed(evt);
            }
        });
        
        adicionarBotao(botaoProximoAdjacente, "/icones/ProximoAdjacente.png");
        botaoProximoAdjacente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                botaoProximoAdjacenteActionPerformed(evt);
            }
        });
        
        adicionarBotao(botaoTodosAdjacentes, "/icones/TodosAdjacentes.png");
        botaoTodosAdjacentes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                botaoTodosAdjacentesActionPerformed(evt);
            }
        });
        
        adicionarBotao(botaoEditar, "/icones/Editar.png");
        botaoEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                botaoEditarActionPerformed(evt);
            }
        });
        
        adicionarBotao(botaoBuscaProfundidade, "/icones/BuscaProfundidade.png");
        botaoBuscaProfundidade.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                botaoBuscaProfundidadeActionPerformed(evt);
            }
        });
        
        adicionarBotao(botaoBuscaLargura, "/icones/BuscaLargura.png");
        botaoBuscaLargura.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                botaoBuscaLarguraActionPerformed(evt);
            }
        });
        
        adicionarBotao(botaoArvoreGeradoraMinima, "/icones/ArvoreGeradoraMinima.png");
        botaoArvoreGeradoraMinima.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                botaoArvoreGeradoraMinimaActionPerformed(evt);
            }
        });
        
        adicionarBotao(botaoMenorCaminho, "/icones/MenorCaminho.png");
        botaoMenorCaminho.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                botaoMenorCaminhoActionPerformed(evt);
            }
        });
    }
    
    private void adicionarBotao(JToggleButton botao, String icone) {
        botao.setPreferredSize(new Dimension(TAMANHO_BOTOES, TAMANHO_BOTOES));
        try {
            botao.setIcon(new ImageIcon(ImageIO.read(getClass().getResource(icone))));
        } catch(IOException e) {
            e.printStackTrace();
        }
        
        this.add(botao);
        grupoBotoes.add(botao);
    }
    
    public void botaoAdicionarVerticeActionPerformed(ActionEvent evt) {
        botaoSelecionado = BOTAO_ADICIONAR_VERTICE;
    }
    
    public void botaoSelecionarActionPerformed(ActionEvent evt) {
        botaoSelecionado = BOTAO_SELECIONAR;
    }
    
    public void botaoAdicionarArestaActionPerformed(ActionEvent evt) {
        botaoSelecionado = BOTAO_ADICIONAR_ARESTA;
    }
    
    public void botaoMoverVerticeActionPerformed(ActionEvent evt) {
        botaoSelecionado = BOTAO_MOVER_VERTICE;
    }
    
    public void botaoRemoverArestaActionPerformed(ActionEvent evt) {
        botaoSelecionado = BOTAO_REMOVER_ARESTA;
    }
    
    public void botaoPrimeiroAdjacenteActionPerformed(ActionEvent evt) {
        botaoSelecionado = BOTAO_PRIMEIRO_ADJACENTE;
    }
    
    public void botaoProximoAdjacenteActionPerformed(ActionEvent evt) {
        botaoSelecionado = BOTAO_PROXIMO_ADJACENTE;
    }
    
    public void botaoTodosAdjacentesActionPerformed(ActionEvent evt) {
        botaoSelecionado = BOTAO_TODOS_ADJACENTES;
    }
    
    public void botaoEditarActionPerformed(ActionEvent evt) {
        botaoSelecionado = BOTAO_EDITAR;
    }
    
    public void botaoBuscaProfundidadeActionPerformed(ActionEvent evt) {
        botaoSelecionado = BOTAO_BUSCA_PROFUNDIDADE;
    }
    
    public void botaoBuscaLarguraActionPerformed(ActionEvent evt) {
        botaoSelecionado = BOTAO_BUSCA_LARGURA;
    }
    
    public void botaoArvoreGeradoraMinimaActionPerformed(ActionEvent evt) {
        botaoSelecionado = BOTAO_ARVORE_GERADORA_MINIMA;
    }
    
    public void botaoMenorCaminhoActionPerformed(ActionEvent evt) {
        botaoSelecionado = BOTAO_MENOR_CAMINHO;
    }
        
    public static int getBotaoSelecionado() {
        return botaoSelecionado;
    }
    
}
