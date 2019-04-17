package chatserver;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;

// 服务器主函数
public class Main extends JFrame implements ChatServerListener {
	ChatServerImpl server = ChatServerImpl.getInstance();
	JTextArea textArea;
	JMenuBar menuBar;
	//JToolBar toolBar;
	StarServerAction startAction = new StarServerAction();
	StopServerAction stopAction = new StopServerAction();
	
	public static void main(String[] args) {
		Main main = new Main();
		main.show();
	}
	
	public Main() {
		super("聊天室-服务器");
		try {
			LocateRegistry.createRegistry(1099);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		setSize(300, 500);
		layoutComponents();
		String s1 = this.getClass().getResource("").getPath();
		System.out.println(s1);
	}
	
	private void layoutComponents() {
		setupMenu();
		textArea = new JTextArea();
		textArea.setSize(2000, 3000);
		textArea.setEditable(true);
		JScrollPane scrollPane = new JScrollPane(textArea);
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}
	
	private void setupMenu() {
		menuBar = new JMenuBar();
		JMenuItem startServer = new JMenuItem(startAction);
		JMenuItem stopServer = new JMenuItem(stopAction);
		JMenu server = new JMenu("菜单");
		server.add(startServer);
		server.add(stopServer);
		//server.add(exit);
		menuBar.add(server);
		setJMenuBar(menuBar);
	}

	public void serverEvent(ChatServerEvent evt) {
		textArea.append(evt.getMessage() + "\n");
	}
	
	class StarServerAction extends AbstractAction {
		public StarServerAction() {
			super("启动");
			putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control A"));
		}
		
		public void actionPerformed(ActionEvent evt) {
			try {
				server.addListener(Main.this);
				textArea.setText("");
				server.start();
				stopAction.setEnabled(true);
				this.setEnabled(false);
			} catch (Exception ex) {
				textArea.append("服务器启动错误\n");
				server.removeListense(Main.this);
				ex.printStackTrace();
				return;
			}
		}
	}
	
	class StopServerAction extends AbstractAction {
		public StopServerAction() {
			super("停止");
			putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
			this.setEnabled(false);
		}
		
		public void actionPerformed(ActionEvent arg0) {
			try {
				server.stop();
				server.removeListense(Main.this);
				startAction.setEnabled(true);
				this.setEnabled(false);
			} catch (Exception e) {
				textArea.append("服务器停止错误\n");
				e.printStackTrace();
				return;
			}
		}
	}
}
