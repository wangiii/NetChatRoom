package chatclient;

import java.rmi.RemoteException;

import chat.*;

// �ͻ�������ӿ�
public class ChatterImpl extends java.rmi.server.UnicastRemoteObject implements Chatter {
	ChatClient client;
	
	public ChatterImpl(ChatClient client) throws java.rmi.RemoteException {
		this.client = client;
	}
	
	public void receiveEnter(String name, Chatter chatter, boolean hasEntered) throws java.rmi.RemoteException {
		client.receiveEnter(name, chatter, hasEntered);
	}
	
	public void receiveExit(String name) throws java.rmi.RemoteException {
		client.receiveExit(name);
	}
	
	public void receiveChat(String name, String message) throws java.rmi.RemoteException {
		client.receiveChat(name, message);
	}
	
	public void receiveWhisper(String name, String message) throws java.rmi.RemoteException {
		client.receiveWhisper(name, message);
	}
	
	public void serverStop() throws RemoteException {
		client.serverStop();
	}
}
