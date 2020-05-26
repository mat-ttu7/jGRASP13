import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.sql.*;

public class Tela extends JFrame implements ActionListener {
	private JFrame frame = new JFrame("Temperaturas");
   private JPanel panelPesquisa = new JPanel(new FlowLayout(FlowLayout.LEADING));
   private JTextField pesquisa = new JTextField(15);
   private JButton botaoPesquisa = new JButton("Pesquisar");
	private JTextArea texto = new JTextArea(17, 25);
	private JScrollPane scrollPane = new JScrollPane(texto);
   private Temperatura temperatura;
   private Temperatura[] temps;
   private Connection conn;
   
   public Tela() {
      texto.setLineWrap(true);
      texto.setWrapStyleWord(true);
      texto.setEditable(false);
      scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
      panelPesquisa.add(pesquisa);
      botaoPesquisa.addActionListener(this);
      panelPesquisa.add(botaoPesquisa);
		panelPesquisa.add(scrollPane);
      frame.add(panelPesquisa);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(292, 342);
      frame.setResizable(false);
      frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
   
   public void actionPerformed(ActionEvent e) {
      if(e.getSource() == botaoPesquisa) {
         texto.setText("");
         try {
            ConexaoBD bd = new ConexaoBD();
            conn = bd.conectar();
            conn.setAutoCommit(false);
            int numero = Integer.parseInt(pesquisa.getText());
         
            for(int i = 0; i < numero; i++) {
           	   temperatura = new Temperatura();
               temperatura.setValor(40*Math.random());
           	   temperatura.incluir(conn);
            }
            conn.commit();
            Termometro termo = new Termometro();
            Temperatura[] temps = termo.ultimosDias(conn, numero);
            for(int i = 0; i < temps.length; i++){
               texto.append(temps[i] + "\n");
            }
            texto.append("Media: " + termo.media(temps));
            texto.append("\nMaior medicao: " + termo.maior(temps));
            texto.append("\nMenor Medicao: " + termo.menor(temps));
         } 
         catch (Exception e1) {
            e1.printStackTrace();
            if (conn != null) {
               try {
                  conn.rollback();
               } 
               catch (SQLException e2) {
                  System.out.print(e2.getStackTrace());
               }
            }
         } 
         finally {
            if (conn != null) {
               try {
                  conn.close();
               } 
               catch (SQLException e1) {
                  System.out.print(e1.getStackTrace());
               }
            }
         }
      }
   }
   
   public static void main(String [] args) {
      new Tela();
   }
}