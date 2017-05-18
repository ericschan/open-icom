import java.awt.FlowLayout;
import javax.swing.JFrame;

class LogonPrompt {
	
	public UserCredential loginDialog() {
		UserCredential credential = new UserCredential();
		JFrame frame = new JFrame("Login Form");
		LoginDialog dialog = new LoginDialog(frame, credential);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300, 100);
		frame.setLayout(new FlowLayout());
		dialog.setVisible(true);
		return credential;
	}
	
	public UserCredential prompt() throws Exception {
        LoadCredential lc = new LoadCredential();
        UserCredential uc = lc.load();
        if (uc != null) {
            return uc;
        }
		return loginDialog();
	}

}
