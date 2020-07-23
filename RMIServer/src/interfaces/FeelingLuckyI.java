package interfaces;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface FeelingLuckyI extends Remote {

	public int getRandomNumber(int max) throws RemoteException;
}
