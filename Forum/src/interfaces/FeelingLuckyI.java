package interfaces;
import java.rmi.Remote;

public interface FeelingLuckyI extends Remote {

	public int getRandomNumber(int max);
}
