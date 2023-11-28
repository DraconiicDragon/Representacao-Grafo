package visual;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.File;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import main.Grafo;

public class Janela extends JFrame {
    
    
    private PainelFerramentas painelFerramentas;
    private PainelGrafo painelGrafo;
    private PainelMenu painelMenu;
;
    
    private Grafo grafo;
    private String endereco = "src/txt/Saida.txt";
    
    public Janela(int largura, int altura, int escala) {     
        this.setTitle("Representação Grafo");
        
        this.setSize(largura * escala, altura * escala);
        this.setResizable(true);
        
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        grafo = new Grafo();
        
        JPanel painelPrincipal = (JPanel)this.getContentPane();
        painelPrincipal.setLayout(new GridBagLayout());
        
        painelFerramentas(painelPrincipal);
        painelGrafo(painelPrincipal);
        painelMenu(painelPrincipal);
        
        
        desativarBotoes();
        this.setVisible(true);
    }
    
    private void painelFerramentas(JPanel painelPrincipal) {
        GridBagConstraints c = new GridBagConstraints();       
        painelFerramentas = new PainelFerramentas();      
        painelFerramentas.setPreferredSize(new Dimension(250, 100));
        painelFerramentas.setLayout(new FlowLayout());
        painelFerramentas.setBackground(Color.BLACK);                           
        c.fill = GridBagConstraints.VERTICAL;
        c.gridx = 0;
        c.gridy = 1;
        painelPrincipal.add(painelFerramentas, c);
    }
    
    private void painelGrafo(JPanel painelPrincipal) {
        GridBagConstraints c = new GridBagConstraints();
        painelGrafo = new PainelGrafo(this);                
        painelGrafo.setBackground(Color.WHITE);
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 1;
        c.gridx = 1;
        c.gridy = 1;
        painelPrincipal.add(painelGrafo, c);
    }
    
    private void painelMenu(JPanel painelPrincipal) {
        GridBagConstraints c = new GridBagConstraints();
        painelMenu = new PainelMenu(this);
        painelMenu.setBackground(Color.DARK_GRAY);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 0;
        painelPrincipal.add(painelMenu, c);
    }
    
    public void novoGrafo() {
        String[] opcoes = {"Sim", "Não"};
        int resultado = JOptionPane.showOptionDialog(this, "Criar um grafo direcionado?", "Tipo do Grafo", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcoes, opcoes[0]);        
        boolean tipo = false;
        if(resultado == JOptionPane.YES_OPTION) {
            tipo = true;
        } else if(resultado == JOptionPane.NO_OPTION) {
            tipo = false;
        }        
        grafo = new Grafo(tipo);
        painelGrafo.repaint();
        if(painelGrafo.isSelecionado()) {
           painelGrafo.desselecionar(); 
        }
    }
    
    public Grafo getGrafo() {
        return grafo;
    }
    
    public void importarGrafo() {
        grafo = new Grafo();
        grafo.importarArquivo(new File("src/txt/Entrada.txt"));
        painelGrafo.repaint();
    }
    
    public void exportarGrafo() {
        
        try {
            grafo.criarArquivoExportacao(endereco);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao criar o arquivo");
        }
        
        try {
            grafo.exportarArquivo(endereco);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao exportar o grafo para arquivo");
        }
    
    }
    
    public void desativarBotoes() {
        painelMenu.desativarBotoes();
    }
    
    public void ativarBotoes() {
        painelMenu.ativarBotoes();
    }
    
    public void play() {
        painelGrafo.play();
    }
    
    public void pausar() {
        painelGrafo.pausar();
    }
    
    public void avancar() {
        painelGrafo.avancarFrame();
    }
    
    public void reiniciar() {
        painelGrafo.reiniciar();
    }
    
    public void cancelar() {
        painelGrafo.cancelar();
    }
    
    public void velocidade1x() {
        painelGrafo.setVelocidade(1);
    }
    
    public void velocidade2x() {
        painelGrafo.setVelocidade(2);
    }
}
