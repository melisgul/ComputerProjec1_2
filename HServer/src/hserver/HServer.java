package hserver;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.PriorityQueue;
import java.util.TreeMap;
import java.io.ObjectInputStream;
import java.io.Serializable;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class HServer extends JFrame implements Serializable {
	private static final long serialVersionUID = 1L;
	public static TreeMap<Character, String> codes = new TreeMap<>();
    static PriorityQueue<HuffmanObject> nodeQueue = new PriorityQueue<>((objectA, objectB) -> (objectA.frequency < objectB.frequency) ? -1 : 1);
	private JPanel serverFrame;
	private static ServerSocket ss;	
	private static Socket s;
	private static BufferedReader br;
	private static InputStreamReader isr;
	private static String Encodedmessage ="";
    private static ObjectInputStream oi;
    private static Integer counter = 0;
    private static String ip;
    private static String username;
	private static final String FILENAME = "username.txt";
	private static BufferedWriter bw = null;
	private static FileWriter fw = null;
	/**
	 * Launch the application.
	 * @throws ClassNotFoundException 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws ClassNotFoundException, InterruptedException {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HServer frame = new HServer();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		try {
			File file = new File(FILENAME);
			if(!file.exists())
				file.createNewFile();
			
			while(true)
			{
			ss = new ServerSocket(12002);
			System.out.println("Server is running at port 12002");
			s = ss.accept();
			nodeQueue.clear();
			isr = new InputStreamReader(s.getInputStream());
			br = new BufferedReader(isr);
			counter++;
			if (counter == 1){
				username = br.readLine();
				System.out.println(username);
				String line;
				boolean result = false;
				BufferedReader bf = new BufferedReader(new FileReader(FILENAME));
				
				
				while((line = bf.readLine()) != null && !result) {
					result = line.indexOf(username) >= 0;
					}
				bf.close();	
				if (result == false){	
					fw = new FileWriter(FILENAME);
					bw = new BufferedWriter(fw);
					bw.append(username);
					bw.newLine();
					bw.close();
					fw.close();
					}
				
			}
			else if (counter ==2){
				Encodedmessage = br.readLine();
				System.out.println(Encodedmessage);
				counter = 0;
			}

			br.close();
			isr.close();
			s.close();
			ss.close();

			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

	
	
		
	public HServer() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		serverFrame = new JPanel();
		serverFrame.setBorder(new EmptyBorder(5, 5, 5, 5));
		serverFrame.setLayout(new BorderLayout(0, 0));
		setContentPane(serverFrame);
	}

}




class HuffmanObject implements Serializable {

	private static final long serialVersionUID = 1L;
	String character;
    double frequency;
    HuffmanObject left, right;

    public HuffmanObject(HuffmanObject left, HuffmanObject right) {

        character = right.character + left.character;
        frequency = right.frequency + left.frequency;


        if (right.frequency <= left.frequency) {
            this.right = left;
            this.left = right;

        }

        else {
            this.right = right;
            this.left = left;
        }
    }

    public HuffmanObject(double fr, String ch) {

        character = ch;
        frequency = fr;

        left = null;
        right = null;
    }


}