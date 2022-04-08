/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nf.view;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

/**
 *
 * @author ACER
 */
   public class Simulator implements Runnable {

        @Override
        public void run() {
            try {
                Robot bot = new Robot();
                type(KeyEvent.VK_R, bot);
                type(KeyEvent.VK_E, bot);
                type(KeyEvent.VK_T, bot);
                type(KeyEvent.VK_U, bot);
                type(KeyEvent.VK_R, bot);
                type(KeyEvent.VK_N, bot);
            } catch (AWTException ex) {
                
            }
        }

        protected void type(int keyStoke, Robot bot) {
            bot.keyPress(keyStoke);
            bot.keyRelease(keyStoke);
        }

    }
