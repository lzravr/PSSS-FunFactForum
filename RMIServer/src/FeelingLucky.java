import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Random;

import interfaces.FeelingLuckyI;

public class FeelingLucky extends UnicastRemoteObject implements FeelingLuckyI {

	private static final long serialVersionUID = 1L;
	
	protected FeelingLucky() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getRandomNumber(int max) {
		
		return new Random().nextInt(max) + 1;
	}

}
