package simplecalculator;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.*;

public class SimpleCalculator extends JFrame {
    /**
     * @param args the command line arguments
     */
    private JLabel result = new JLabel("Default start in RPN mode");
    private JButton[] numButtons;
    private JButton butPlus = new JButton("+");
    private JButton butMinus = new JButton("-");
    private JButton butTimes = new JButton("X");
    private JButton butDivide = new JButton("/");
    private JButton butEqual = new JButton("=");
    private JButton butDec = new JButton(".");
    private JButton butRDN = new JButton("RPN");
    private JButton butINFIX = new JButton("INFIX");
    private JButton butClear = new JButton("Clear");
    private JButton butEnter = new JButton("Enter");
    private float ans;
    private float num;
    private String opr;
    private String mode="RPN";
    private int factor;
    
    public void enableNums(boolean e){
        for (int i=0; i<numButtons.length; i++){
            numButtons[i].setEnabled(e);
        }
    }
    public void clearAll(){
        opr=" ";
        ans=0;
        num=0;
        factor=1;
        enableNums(true);
        butDec.setEnabled(false);
        butEnter.setEnabled(false);
        butEqual.setEnabled(false);
        enableOps(false);
        result.setText("Memory cleared");
    }
    public void enableOps(boolean e){
        butPlus.setEnabled(e);
        butMinus.setEnabled(e);
        butDivide.setEnabled(e);
        butTimes.setEnabled(e);
        if(mode.equals("RPN")){
            butEqual.setEnabled(e);
        }
    }
    public void preformOpr(String nxtOpr){
        if (mode.equals("INFIX")){
            opr=nxtOpr;
        }
        if(opr.equals("+")){
            ans=ans+num;
        }
        else if(opr.equals("X")){
            ans = ans*num;
        }
        else if (opr.equals("/")){
            ans=ans/num;
        }
        else if (opr.equals("-")){
            ans=ans-num;
        }
        else if (opr.equals("=")){
            ans=ans;
        }
        else{
            ans=num;
        }
        result.setText(String.valueOf(ans));
        factor=1;
        num=0;
        opr=nxtOpr;
        enableOps(false);
        butEnter.setEnabled(false);
    }
    
    SimpleCalculator(){
        ans=0;
        num=0;
        opr=" ";
        factor=1;
        numButtons = new JButton[10];
        JPanel resultPanel = new JPanel();
        JPanel numPanel = new JPanel(new GridLayout(4,3));
        JPanel oprPanel = new JPanel(new GridLayout(4,2));
        resultPanel.add(result);
        for (int i=9; i>=0; i--){
            numButtons[i]= new JButton(String.valueOf(i));
            numButtons[i].setBackground(Color.white);
            numButtons[i].setFont(new Font("Arial", Font.PLAIN, 40));
            numPanel.add(numButtons[i]);
        }
        butClear.setBackground(Color.black); //this block is all just adding and formatting buttons
        butClear.setForeground(Color.white);
        butClear.setFont(new Font("Arial", Font.PLAIN, 20));
        oprPanel.add(butClear);
        butEnter.setBackground(Color.black);
        butEnter.setForeground(Color.white);
        butEnter.setFont(new Font("Arial", Font.PLAIN, 20));
        butEnter.setEnabled(false);
        oprPanel.add(butEnter);
        oprPanel.add(butPlus);
        butPlus.setBackground(Color.black);
        butPlus.setForeground(Color.white);
        butPlus.setFont(new Font("Arial", Font.PLAIN, 40));
        oprPanel.add(butMinus);
        butMinus.setBackground(Color.black);
        butMinus.setForeground(Color.white);
        butMinus.setFont(new Font("Arial", Font.PLAIN, 40));
        oprPanel.add(butTimes);
        butTimes.setBackground(Color.black);
        butTimes.setForeground(Color.white);
        butTimes.setFont(new Font("Arial", Font.PLAIN, 40));
        oprPanel.add(butDivide);
        butDivide.setBackground(Color.black);
        butDivide.setForeground(Color.white);
        butDivide.setFont(new Font("Arial", Font.PLAIN, 40));
        oprPanel.add(butDec);
        butDec.setBackground(Color.black);
        butDec.setForeground(Color.white);
        butDec.setFont(new Font("Arial", Font.PLAIN, 40));
        butDec.setEnabled(false);
        oprPanel.add(butEqual);
        butEqual.setBackground(Color.black);
        butEqual.setForeground(Color.white);
        butEqual.setFont(new Font("Arial", Font.PLAIN, 40));
        numPanel.add(butRDN);
        butRDN.setBackground(Color.white);
        butRDN.setFont(new Font("Arial", Font.PLAIN, 20));
        butRDN.setEnabled(false);
        numPanel.add(butINFIX);
        butINFIX.setBackground(Color.white);
        butINFIX.setFont(new Font("Arial", Font.PLAIN, 20));
        
        enableOps(false);
        JPanel layout = new JPanel(new GridLayout(1,2));
        layout.setAlignmentY(0);
        layout.setMinimumSize(new Dimension(500,500));
        layout.add(numPanel);
        layout.add(oprPanel);
        add(layout, BorderLayout.CENTER);
 	add(resultPanel, BorderLayout.NORTH);
        for (int i=0; i<10; i++){
            JButton test = numButtons[i];
            test.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if(factor==1){
                        num=num*10+Float.parseFloat(test.getText());
                        butDec.setEnabled(true);
                    }
                    else{
                        float a =Float.parseFloat(test.getText()); //done in two steps to minimize rounding error
                        num=num+(a/factor);
                        factor=factor*10;
                        butDec.setEnabled(false);
                    }
                    if(mode.equals("INFIX")&&opr.equals(" ")){
                        butEnter.setEnabled(true);
                    }
                    if(mode.equals("INFIX")&&(!opr.equals(" "))){
                        enableOps(true);
                    }
                    else if(mode.equals("RPN")){
                        enableOps(true);
                    }        
                    result.setText(String.valueOf(num));
                    System.out.println(num);
                }
            });
        }
        butPlus.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                preformOpr(butPlus.getText());
                enableNums(true);
                butDec.setEnabled(false);
            }
        });
        butMinus.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                preformOpr(butMinus.getText());
                enableNums(true);
                butDec.setEnabled(false);
            }
        });
        butTimes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                preformOpr(butTimes.getText());
                enableNums(true);
                butDec.setEnabled(false);
            }
        });
        butDivide.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                preformOpr(butDivide.getText());
                enableNums(true);
                butDec.setEnabled(false);
            }
        });
        butDec.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                factor=10;
                result.setText(String.valueOf(num));
                butDec.setEnabled(false);
            }
        });
        butEqual.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                preformOpr("=");
                enableNums(false);
                butDec.setEnabled(false);
                enableOps(true);
            }
        }); 
        butClear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clearAll();
            }
        });
        butEnter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                preformOpr("enter");
            }
        });
        butRDN.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                butRDN.setEnabled(false);
                butINFIX.setEnabled(true);
                mode="RPN";
                clearAll();
            }
        }); 
        butINFIX.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                butINFIX.setEnabled(false);
                butRDN.setEnabled(true);
                mode="INFIX";
                clearAll();
            }
        });    
    }
    public static void main(String[] args) {
        SimpleCalculator frame = new SimpleCalculator();
        frame.pack();
	frame.setTitle("Simple Calculator");
	frame.setLocationRelativeTo(null);
        frame.setSize(600,400);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.setVisible(true);
    }  
}