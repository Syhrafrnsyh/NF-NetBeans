/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nf.view;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Paint;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;

/**
 *
 * @author ACER
 */
public class BackgroundPaneDesktop extends JDesktopPane {

    private static final Color BACKGROUND = Color.BLUE;
    private static final Color BACKGROUND_2 = Color.WHITE;

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D graphics = (Graphics2D) g.create();
        
         int midY = 100;

        Paint topPaint = new GradientPaint(0, 0, BACKGROUND,
                0, midY, BACKGROUND_2);
        graphics.setPaint(topPaint);
        graphics.fillRect(0, 0, getWidth(), midY);
        
        Paint bottomPaint = new GradientPaint(0, midY + 1, BACKGROUND_2,
                0, getHeight(), BACKGROUND);
        graphics.setPaint(bottomPaint);
        graphics.fillRect(0, midY, getWidth(), getHeight());

        Image img = new ImageIcon(getClass().getResource("/nf/view/bckgrnd.jpg")).getImage();
        //jDesktopPane =  new nf.view.JdesktopTest();
        //new javax.swing.JDesktopPane();

        int imgX = img.getWidth(null);
        int imgY = img.getHeight(null);
        graphics.drawImage(img, (getWidth() - imgX) / 2, (getHeight() - imgY) / 2, imgX, imgY, null);


        graphics.dispose();
    }
    
}
