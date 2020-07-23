package utils;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import interfaces.FeelingLuckyI;

public class FeelingLucky {

	public static int getRand(int max) {
		
		
		try {
			FeelingLuckyI fli = (FeelingLuckyI)Naming.lookup("rmi://localhost:1234/Server");
			
			return fli.getRandomNumber(max);
			
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return -1;
	}
}
