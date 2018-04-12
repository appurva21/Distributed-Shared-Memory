
import java.awt.Dimension;
import javax.swing.JFrame;

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
