package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Line2D;

public class Arresta extends Componente {
    private int origem;
    private int destino;
    private int peso;
    private boolean duplicada = false;
    private Line2D.Double aresta;

    public Arresta(int origem, int destino, int peso, boolean duplicada) {
        this.origem = origem;
        this.destino = destino;
        this.peso = peso;
        this.duplicada = duplicada;
        this.cor = Color.BLACK;        
    }
    
    public void drawArrow(Graphics g, int x0, int y0, int x1,
        int y1, int headLength, int headAngle) {
        double offs = headAngle * Math.PI / 180.0;
        double angle = Math.atan2(y0 - y1, x0 - x1);
        int[] xs = { x1 + (int) (headLength * Math.cos(angle + offs)), x1,
                x1 + (int) (headLength * Math.cos(angle - offs)) };
        int[] ys = { y1 + (int) (headLength * Math.sin(angle + offs)), y1,
                y1 + (int) (headLength * Math.sin(angle - offs)) };
        g.drawLine(x0, y0, x1, y1);
        g.drawPolyline(xs, ys, 3);
    }
    
    public void atualizarAresta(int origemX, int origemY, int destinoX, int destinoY) {
        aresta = new Line2D.Double(origemX, origemY, destinoX, destinoY);
    }
    
    /*
    public void desenharArresta(Graphics g) {
        g.setColor(getCor());
        g.drawLine((int)aresta.x1, (int)aresta.y1, (int)aresta.x2, (int)aresta.y2);
        g.drawString(String.valueOf(peso), ((int)aresta.x1+(int)aresta.x2)/2+5, ((int)aresta.y1+(int)aresta.y2)/2);
    }
*/
    
    public void desenharArresta(int origemX, int origemY, int destinoX, int destinoY, Graphics g, boolean direcionado) {
        atualizarAresta(origemX, origemY, destinoX, destinoY);
        if(isDuplicada()) {
            return;
        }
          
        g.setColor(getCor());
                        
        double angulo =  Math.atan2(Math.abs(origemX-destinoX), Math.abs(origemY-destinoY));
        
        if(direcionado) {
            drawArrow(g, origemX, origemY, destinoX, destinoY, 20, 10);
        } else {
            g.drawLine(origemX, origemY, destinoX, destinoY);
        }
        
        if(angulo < 0.5) {
            g.drawString(String.valueOf(peso), (origemX+destinoX)/2+5, (origemY+destinoY)/2);
        } else if(angulo < 1) {
            g.drawString(String.valueOf(peso), (origemX+destinoX)/2-6, (origemY+destinoY)/2-5);
        } else {
            g.drawString(String.valueOf(peso), (origemX+destinoX)/2, (origemY+destinoY)/2-10);
        }
        
    }
    
    public Arresta getArestaClicada(int x, int y) {
        double distancia = Line2D.ptSegDist(aresta.getX1(), aresta.getY1(), aresta.getX2(), aresta.getY2(), x, y);
        if(distancia < 2) {
            return this;
        }
        return null;
    }
    
    public Line2D getAresta() {
        return aresta;
    }

    public int getOrigem() {
        return origem;
    }
    
    public void setOrigem(int origem) {
        this.origem = origem;
    }
    
    public int getDestino() {
        return destino;
    }

    public void setDestino(int destino) {
        this.destino = destino;
    }

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    public boolean isDuplicada() {
        return duplicada;
    }

    public void setDuplicada(boolean duplicada) {
        this.duplicada = duplicada;
    }
    
}
