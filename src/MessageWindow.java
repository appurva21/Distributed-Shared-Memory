
import java.awt.Dimension;
import javax.swing.JFrame;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author himanshu2936
 */
public class MessageWindow extends JFrame{
    
    
    javax.swing.JTextArea tf_address;
    public MessageWindow(String s) {
        setTitle(s);
        tf_address = new javax.swing.JTextArea();
        tf_address.setText("  ");   
        tf_address.setEditable(false);
        add(tf_address);
        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    
    public void set(String s){
        String str = tf_address.getText()+"\n  "+s;
        tf_address.setText(str);
    }
    
}
