package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

import common.Util;

/*
 * Serverska strana aplikacije za caskanje
 * 
 * */

public class Server 
{
	public static void main(String[] args) 
	{
		int port = 9090;
		try 
		{
			// Kreiraj serverski soket na portu 9090
			ServerSocket serverSocket = new ServerSocket(9090);
			
			// Spisak izlaznih tokova ka svim korisnicima koji su aktivni u caskanju
			HashMap<String, BufferedWriter> sviKorisnici = new HashMap<>();
			
			System.out.println("Server je startovan na portu 9090");
			
			// Za svakog korisnika koji se pojavi, 
			// kreiraj posebnu nit koja će obradjivati njegove zahteve
			while(true)
			{
				Socket socket = serverSocket.accept();
				new KlijentskaNit(sviKorisnici, socket).start();
			}
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
