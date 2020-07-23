package klijent;

import java.awt.EventQueue;
import java.awt.HeadlessException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import common.Odjava;
import common.Poruka;
import common.Util;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ProzorZaCaskanje extends JFrame {

	private JPanel contentPane;
	
	boolean running = true;
	
	String name;
	BufferedReader br;
	BufferedWriter bw;
	private JTextField txtPoruka;
	JLabel lblNadimak;
	
	JList listKorisnici;
	JList listSvePoruke;
	
	DefaultListModel<String> dlm = new DefaultListModel<>();
	DefaultListModel<String> dlmPoruke = new DefaultListModel<>();
	
	public ProzorZaCaskanje(String name, BufferedReader br, BufferedWriter bw) throws HeadlessException {
		
		
		this();
		this.name = name;
		lblNadimak.setText("Username : " + name);
		this.br = br;
		this.bw = bw;
		
		new Thread(new Runnable() {
			
			@Override
			public void run() 
			{
				obradiPorukeSaServera();
			}

		}).start();
		
		Runtime.getRuntime().addShutdownHook(new Thread()
		{
		    @Override
		    public void run()
		    {
		    	try {
		    		
		    		running = false;
		    		
		    		Odjava o = new Odjava(name);
					bw.write(o.toString());
					bw.flush();
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    	
		    }
		});
	}
	
	private void obradiPorukeSaServera() 
	{
		while(running)
		{
			try 
			{
				String xml = br.readLine();
				
				Object o = Util.getObjectFromString(xml);
				
				if(o instanceof Object[]) 
				{
					listKorisnici.removeAll();
					dlm.removeAllElements();
					
					Object[] korisnici = (Object[])o;
					
					for(Object k : korisnici)
						dlm.addElement(k + "");
				}
				else if (o instanceof Poruka)
				{
					Poruka p = (Poruka)o;
					dlmPoruke.addElement(p.getSadrzajPoruke());
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				running = false;
			}
		}
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() 
			{
				try 
				{
					ProzorZaCaskanje frame = new ProzorZaCaskanje();
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
	public ProzorZaCaskanje() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 754, 486);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblNadimak = new JLabel("Username:");
		lblNadimak.setBounds(10, 11, 300, 14);
		contentPane.add(lblNadimak);
		
		JLabel lblNewLabelNadimak = new JLabel("");
		lblNewLabelNadimak.setBounds(65, 11, 46, 14);
		contentPane.add(lblNewLabelNadimak);
		
		listSvePoruke = new JList(dlmPoruke);
		listSvePoruke.setBounds(10, 36, 549, 338);
		contentPane.add(listSvePoruke);
		
		listKorisnici = new JList(dlm);
		listKorisnici.setBounds(580, 36, 145, 338);
		contentPane.add(listKorisnici);
		
		JLabel lblListaKorisnika = new JLabel("Active users:");
		lblListaKorisnika.setBounds(576, 11, 110, 14);
		contentPane.add(lblListaKorisnika);
		
		txtPoruka = new JTextField();
		txtPoruka.setBounds(10, 407, 549, 20);
		contentPane.add(txtPoruka);
		txtPoruka.setColumns(10);
		
		JLabel lblPoruka = new JLabel("Message:");
		lblPoruka.setBounds(10, 385, 56, 14);
		contentPane.add(lblPoruka);
		
		JButton btnPosaljiPrivatno = new JButton("Whisper");
		btnPosaljiPrivatno.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				String sadrzajPoruke = name + " [private] : " + txtPoruka.getText();        
				
				if(listKorisnici.getSelectedIndex() >= 0)
				{
				
					String komeSaljemo = listKorisnici.getSelectedValue().toString();
					
					Poruka poruka = new Poruka(true, komeSaljemo, sadrzajPoruke);
					
					txtPoruka.setText("");
					
					try 
					{
						bw.write(poruka + "");
						bw.flush();
						
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				else
				{
					JOptionPane.showConfirmDialog(null, "Select the user you want to send a private message.","Error!",JOptionPane.YES_OPTION);
				}
			}
		});
		btnPosaljiPrivatno.setBounds(580, 385, 145, 23);
		contentPane.add(btnPosaljiPrivatno);
		
		JButton buttonPosaljiSvima = new JButton("Send to everyone");
		buttonPosaljiSvima.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				String sadrzajPoruke = name + " : " + txtPoruka.getText();        
				Poruka poruka = new Poruka(false, null, sadrzajPoruke);
				
				txtPoruka.setText("");
				
				try 
				{
					bw.write(poruka + "");
					bw.flush();
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		buttonPosaljiSvima.setBounds(580, 419, 145, 23);
		contentPane.add(buttonPosaljiSvima);
	}
}
