package klijent;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import common.Prijava;
import common.StatusPrijave;
import common.Util;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

public class ProzorZaPrijavu extends JFrame {

	private JPanel contentPane;
	private JTextField txtName;
	private String host = "localhost";
	
	int port = 9090;
	Socket socket = null;
	BufferedReader br = null;
	BufferedWriter bw = null;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ProzorZaPrijavu frame = new ProzorZaPrijavu();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ProzorZaPrijavu() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 255, 127);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNadimak = new JLabel("Password:");
		lblNadimak.setBounds(10, 36, 66, 14);
		contentPane.add(lblNadimak);
		
		JLabel lblAdresa = new JLabel("Username:");
		lblAdresa.setBounds(10, 11, 66, 14);
		contentPane.add(lblAdresa);
		
		txtName = new JTextField();
		txtName.setBounds(76, 8, 160, 20);
		contentPane.add(txtName);
		txtName.setColumns(10);
	
		passwordField = new JPasswordField();
		passwordField.setBounds(76, 33, 160, 20);
		contentPane.add(passwordField);
		
		JButton btnPrijaviSe = new JButton("Log in");
		btnPrijaviSe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				try 
				{
					if(socket == null)
					{
						// Otvori konekciju ka serveru
						socket = new Socket(InetAddress.getByName(host), port);
						
						br = Util.getBuffredReader(socket);
						bw = Util.getBuffredWriter(socket);
					}
					
					// Posalji prijavu
					Prijava p = new Prijava(txtName.getText(), new String(passwordField.getPassword()));
					bw.write(p.toString());
					bw.flush();
					
					String xmlStatusPrijave = br.readLine();
					
					StatusPrijave statusPrijave = (StatusPrijave)Util.getObjectFromString(xmlStatusPrijave);
					
					if(statusPrijave.isPrijaveJeUspesna())
					{
						setVisible(false);
						new ProzorZaCaskanje(p.getName(), br, bw).show();
					}
					else {
						JOptionPane.showConfirmDialog(null, statusPrijave.getPoruka(),"Error!", JOptionPane.YES_OPTION);
						txtName.setText("");
						passwordField.setText("");
					}
					
				} catch (Exception e) {
					// TODO: handle exception
				}
				
			}
		});
		btnPrijaviSe.setBounds(147, 64, 89, 23);
		contentPane.add(btnPrijaviSe);
		
	}
}
