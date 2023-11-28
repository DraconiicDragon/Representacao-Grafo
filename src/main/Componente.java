/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import java.awt.Color;

/**
 *
 * @author filip
 */
public class Componente {
    protected Color cor;
    
    public void setCor(int r, int g, int b) {
        this.cor = new Color(r, g, b);
    }
    
    public void setCor(Color cor) {
        this.cor = cor;
    }
    
    public Color getCor() {
        return this.cor;
    }
}
