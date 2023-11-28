package visual;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class PainelMenu extends JPanel {    
    private final int TAMANHO_BOTOES = 20;
       
    private JButton botaoSalvarGrafo;
    private JButton botaoImportarGrafo;
    private JButton botaoCriarGrafo;
    
    private JButton botaoPlay;
    private JButton botaoPausar;
    private JButton botaoAvancar;
    private JButton botaoCancelar;
    private JButton botaoReiniciar;
    private JButton botaoVelocidade1x;
    private JButton botaoVelocidade2x;
    
    private Janela janela;
    
    public PainelMenu(Janela janela) {
        this.janela = janela;
        iniciarComponentes();
    }
    
    private void iniciarComponentes() {
        botaoSalvarGrafo = new JButton();
        botaoImportarGrafo = new JButton();
        botaoCriarGrafo = new JButton();
        
        botaoPlay = new JButton();
        botaoPausar = new JButton();
        botaoAvancar = new JButton();        
        botaoCancelar = new JButton();
        botaoReiniciar = new JButton();       
        botaoVelocidade1x = new JButton();       
        botaoVelocidade2x = new JButton();
                
        adicionarBotao(botaoSalvarGrafo, "/icones/Salvar.png");
        botaoSalvarGrafo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                botaoSalvarGrafoActionPerformed(evt);
            }
        });
        
        adicionarBotao(botaoImportarGrafo, "/icones/Importar.png");
        botaoImportarGrafo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                botaoImportarGrafoActionPerformed(evt);
            }
        });
        
        adicionarBotao(botaoCriarGrafo, "/icones/NovoArquivo.png");
        botaoCriarGrafo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                botaoCriarGrafoActionPerformed(evt);
            }
        });
        
        adicionarBotao(botaoPlay, "/icones/Play.png");
        botaoPlay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                botaoPlayActionPerformed(evt);
            }
        });
        
        adicionarBotao(botaoPausar, "/icones/Pausar.png");
        botaoPausar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                botaoPausarActionPerformed(evt);
            }
        });
        
        adicionarBotao(botaoAvancar, "/icones/Avancar.png");
        botaoAvancar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                botaoAvancarActionPerformed(evt);
            }
        });
        
        adicionarBotao(botaoReiniciar, "/icones/Reiniciar.png");
        botaoReiniciar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                botaoReiniciarActionPerformed(evt);
            }
        });
        
        adicionarBotao(botaoCancelar, "/icones/Cancelar.png");
        botaoCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                botaoCancelarActionPerformed(evt);
            }
        });
        
        adicionarBotao(botaoVelocidade1x, "/icones/Velocidade1x.png");
        botaoVelocidade1x.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                botaoVelocidade1xActionPerformed(evt);
            }
        });
        
        adicionarBotao(botaoVelocidade2x, "/icones/Velocidade2x.png");
        botaoPlay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                botaoVelocidade2xActionPerformed(evt);
            }
        });
    }
    
    private void adicionarBotao(JButton botao, String icone) {
        botao.setPreferredSize(new Dimension(TAMANHO_BOTOES, TAMANHO_BOTOES));
        try {
            botao.setIcon(new ImageIcon(ImageIO.read(getClass().getResource(icone))));
        } catch(IOException e) {
            e.printStackTrace();
        }       
        this.add(botao);
    }
    
    public void desativarBotoes() {
        botaoPlay.setEnabled(false);
        botaoPausar.setEnabled(false);
        botaoAvancar.setEnabled(false);
        botaoReiniciar.setEnabled(false);
        botaoCancelar.setEnabled(false);
        botaoVelocidade1x.setEnabled(false);
        botaoVelocidade2x.setEnabled(false);
    }
    
    public void ativarBotoes() {
        botaoPlay.setEnabled(true);
        botaoPausar.setEnabled(true);
        botaoAvancar.setEnabled(true);
        botaoReiniciar.setEnabled(true);
        botaoCancelar.setEnabled(true);
        botaoVelocidade1x.setEnabled(true);
        botaoVelocidade2x.setEnabled(true);
    }
    
    private void botaoSalvarGrafoActionPerformed(ActionEvent evt) {
        janela.exportarGrafo();
    }
    
    private void botaoImportarGrafoActionPerformed(ActionEvent evt) {
        janela.importarGrafo();
    }
    
    private void botaoCriarGrafoActionPerformed(ActionEvent evt) {
        janela.novoGrafo();
    }
    
    private void botaoPlayActionPerformed(ActionEvent evt) {
        janela.play();
    }
    
    private void botaoPausarActionPerformed(ActionEvent evt) {
        janela.pausar();
    }
    
    private void botaoAvancarActionPerformed(ActionEvent evt) {
        janela.avancar();
    }
    
    private void botaoReiniciarActionPerformed(ActionEvent evt) {
        janela.reiniciar();
    }
    
    private void botaoCancelarActionPerformed(ActionEvent evt) {
        janela.cancelar();
    }
    
    private void botaoVelocidade1xActionPerformed(ActionEvent evt) {
        janela.velocidade1x();
    }
    
    private void botaoVelocidade2xActionPerformed(ActionEvent evt) {
        janela.velocidade2x();
    }
}
