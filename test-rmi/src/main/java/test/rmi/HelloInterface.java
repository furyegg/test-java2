package test.rmi;

public interface HelloInterface extends java.rmi.Remote {
	public void sayHello(String from) throws java.rmi.RemoteException;
}