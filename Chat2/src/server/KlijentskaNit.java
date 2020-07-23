package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;

import common.DatabaseManager;
import common.Odjava;
import common.Poruka;
import common.Prijava;
import common.StatusPrijave;
import common.Util;

public class KlijentskaNit extends Thread 
{
	
	HashMap<String, BufferedWriter> sviKorisnici;
	
	// IO tokovi ka korisniku cije zahteve nit obradjuje
	BufferedReader br;
	BufferedWriter bw;
	
	boolean running = true;
	
	// Korisnicko ime
	String name;
	String password;
	
	public KlijentskaNit(HashMap<String, BufferedWriter> sviKorisnici,
			Socket socket)
	{
		try
		{
			this.sviKorisnici = sviKorisnici;
			
			br = Util.getBuffredReader(socket);
			bw = Util.getBuffredWriter(socket);
			
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void run()
	{
		while(running)
		{
			String poruka;
			
			try 
			{
				poruka = br.readLine();
				obradiPoruku(poruka);	
			} 
			catch (IOException e) 
			{
				
				System.out.println(name + " " + e.getMessage());
				odjaviKorisnika(name);
			}
		}
		
		
		try {
			bw.close();
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void obradiPoruku(String xml) throws IOException
	{
		Object o = Util.getObjectFromString(xml);
		
		if(o instanceof Prijava)
		{
			Prijava p = (Prijava)o;
			name = p.getName();
			password = p.getPassword();
						
			synchronized (sviKorisnici) 
			{
				DatabaseManager dm = new DatabaseManager();
				boolean flag = dm.loginUser(name, password);
				if(flag == false)
				{
					StatusPrijave statusPrijave = new StatusPrijave(false, "Wrong credentials! Try again.");
					bw.write(statusPrijave + "");
					bw.flush();
				}
				else // Ako korisnicko ime ne postoji u listi dodaj korisnika
				{
					// Posalji poruku da je sve u redu
					StatusPrijave statusPrijave = new StatusPrijave(true);
					bw.write(statusPrijave + "");
					bw.flush();
					
					// Posalji novi spisak korisnika, svim korisnicima na mrezi
					sviKorisnici.put(name, bw);
					posaljiSvimaSpisakKoriosnika();
				}
			}
		}
		else if(o instanceof Poruka)
		{
			Poruka poruka = (Poruka)o;
			
			// Ako je poruka privatna, posalji je samo korisnuku kome je upucena
			if(poruka.isPrivatna())
			{
				BufferedWriter bwKorisnikaKomeJePorukaPoslata = sviKorisnici.get(poruka.getName());
				
				bwKorisnikaKomeJePorukaPoslata.write(poruka + "");
				bwKorisnikaKomeJePorukaPoslata.flush();
				
			}
			else // Ako poruka nije privatna, posalji je svim korisnicima
			{
				for(BufferedWriter bw : sviKorisnici.values())
				{
					bw.write(poruka + "");
					bw.flush();
				}
			}
		}
		else if(o instanceof Odjava)
		{
			Odjava odjava = (Odjava)o;
			
			odjaviKorisnika(odjava.getName());
		}
	}

	private void odjaviKorisnika(String name) 
	{
		
		Poruka poruka = new Poruka(false, "", name + " left the chat!");
		
		for(BufferedWriter bw : sviKorisnici.values())
		{
			try 
			{
				bw.write(poruka + "");
				bw.flush();
			} 
			catch (IOException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		sviKorisnici.remove(name);
		
		posaljiSvimaSpisakKoriosnika();
		
		running = false;
		
	}

	private synchronized void posaljiSvimaSpisakKoriosnika() 
	{
		try 
		{
			String xml = Util.getXmlFromObject(sviKorisnici.keySet().toArray());
			
			for(BufferedWriter bw : sviKorisnici.values())
			{
				bw.write(xml);
				bw.flush();
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
	}
}
