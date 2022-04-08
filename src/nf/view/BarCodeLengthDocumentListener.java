/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nf.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

/**
 *
 * @author ACER
 */
  public class BarCodeLengthDocumentListener implements DocumentListener {

        private final ActionListener actionListener;
        private final int barCodeLength;

        public BarCodeLengthDocumentListener(int barCodeLength, ActionListener actionListener) {
            this.actionListener = actionListener;
            this.barCodeLength = barCodeLength;
        }

        @Override
        public void insertUpdate(DocumentEvent e) {
            doCheck(e);
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            doCheck(e);
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            doCheck(e);
        }

        protected void doCheck(DocumentEvent e) {
            Document doc = e.getDocument();
            if (doc.getLength() >= barCodeLength) {
                try {
                    String text = doc.getText(0, doc.getLength());
                    ActionEvent evt = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, text);
                    actionListener.actionPerformed(evt);
                } catch (BadLocationException exp) {
                    exp.printStackTrace();
                    ActionEvent evt = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null);
                    actionListener.actionPerformed(evt);
                }
            }
        }

    }