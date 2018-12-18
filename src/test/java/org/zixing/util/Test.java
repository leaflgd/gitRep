package org.zixing.util;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Test extends JFrame {  
    private JLabel jlb = new JLabel();  
    private ImageIcon image;  
    private int width = 400, height = 400;  
  
    public  Test() {  
        this.setSize(800, 600);  
        this.setLayout(null);  
  
        image = new ImageIcon("192.168.100.156:8080/images/image/patient/C327019/B~A/proc/C327019.B~A.8/grypoc/littlebaby.jpg");  
        // image.setImage(image.getImage().getScaledInstance(width, height,Image.SCALE_DEFAULT));  
        Image img = image.getImage();  
        img = img.getScaledInstance(width, height, Image.SCALE_DEFAULT);  
        image.setImage(img);  
        jlb.setIcon(image);  
  
        this.add(jlb);  
        jlb.setSize(width, height);  
        this.setVisible(true);  
    }  
  
    public static void main(String[] args) {  
        new Test();  
    }  
}  