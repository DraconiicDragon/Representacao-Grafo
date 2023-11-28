package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Ellipse2D;


public class Vertice extends Componente {
    public static final int TAMANHO = 20;
    private int id;
    private String nome;
    private boolean selecionado;
    private Ellipse2D vertice;
    
    public Vertice(int id, int x, int y, String nome) {
        this.id = id;
        this.cor = new Color(0, 0, 0);
        this.nome = nome;    
        setXY(x, y);
    }
    
    public Vertice(int id, int x, int y) {
        this.id = id;
        this.cor = new Color(0, 0, 0);
        this.nome = String.valueOf(id);
        setXY(x, y);
    }
       
    public void desenharVertice(Graphics g) {                
        g.setColor(getCor());
        g.fillOval(getX(), getY(), TAMANHO, TAMANHO);
        g.drawString(nome, getX(), getY());
    }
    
    public void setXY(int x, int y) {    
        vertice = new Ellipse2D.Double(x, y, TAMANHO, TAMANHO);
    }   
    
    public Ellipse2D getVertice() {                      
        return vertice;
    }
    
    public int getId() {
        return id;
    }
        
    public int getX() {
        return (int)vertice.getX();
    }
    
    public void setX(int x) {
        setXY(x, (int)vertice.getY());
    }

    public int getY() {
        return (int)vertice.getY();
    }

    public void setY(int y) {
        setXY((int)vertice.getX(), y);
    }
    
    public void setSelecionado(boolean selecionado) {
        this.selecionado = selecionado;
    }
    
    public boolean isSelecionado() {
        return selecionado;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public String getNome() {
        return this.nome;
    }
    
    @Override
    public String toString() {
        return getId() + " " + getX() + " " + getY() + " " + getNome();
    }
      
}
