import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Ascoltatore2 implements ActionListener{
	private String msg_output;
	private boolean connesso;
	
	public Ascoltatore2(String msg_output, boolean connesso) {
		this.msg_output = msg_output;
		this.connesso = connesso;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Shutdown")) {
			if(connesso) {
				msg_output = "SHUTDOWN";
			}else {
				System. exit(0);
			}
		}
	}

}
