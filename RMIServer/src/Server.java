import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class Server {

	public static void main(String[] args) {
		
		try {
			FeelingLucky fl = new FeelingLucky();
			LocateRegistry.createRegistry(1234);
			Naming.rebind("//localhost:1234/Server", fl);
			
			System.out.println("Server je startovan");
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
