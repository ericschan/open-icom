import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class LoginDialog extends JDialog {

	private JTextField tfHostname;
    private JTextField tfUsername;
    private JPasswordField pfPassword;
	private JLabel lbHostname;
    private JLabel lbUsername;
    private JLabel lbPassword;
    private JButton btnLogin;
    private JButton btnCancel;

    public LoginDialog(Frame parent, final UserCredential credential) {
        super(parent, "Login", Dialog.ModalityType.DOCUMENT_MODAL);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints cs = new GridBagConstraints();

        cs.fill = GridBagConstraints.HORIZONTAL;
		
		lbHostname = new JLabel("Host: ");
		cs.gridx = 0;
		cs.gridy = 0;
		cs.gridwidth = 1;
		panel.add(lbHostname, cs);

		tfHostname = new JTextField(20);
		cs.gridx = 1;
		cs.gridy = 0;
		cs.gridwidth = 2;
		panel.add(tfHostname, cs);

        lbUsername = new JLabel("Username: ");
        cs.gridx = 0;
        cs.gridy = 1;
        cs.gridwidth = 1;
        panel.add(lbUsername, cs);

        tfUsername = new JTextField(20);
        cs.gridx = 1;
        cs.gridy = 1;
        cs.gridwidth = 2;
        panel.add(tfUsername, cs);

        lbPassword = new JLabel("Password: ");
        cs.gridx = 0;
        cs.gridy = 2;
        cs.gridwidth = 1;
        panel.add(lbPassword, cs);

        pfPassword = new JPasswordField(20);
        cs.gridx = 1;
        cs.gridy = 2;
        cs.gridwidth = 2;
        panel.add(pfPassword, cs);
        panel.setBorder(new LineBorder(Color.GRAY));

        btnLogin = new JButton("Login");

        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
				credential.hostName = getHostname();
            	credential.userName = getUsername();
            	credential.password = getPassword().toCharArray();
            	dispose();
            }
        });
        
        btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
				credential.hostName = "beehive.acme.com";
            	credential.userName = "guest";
            	credential.password = "welcome".toCharArray();
                dispose();
            }
        });
        
        JPanel bp = new JPanel();
        bp.add(btnLogin);
        bp.add(btnCancel);

        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(bp, BorderLayout.PAGE_END);

        pack();
        setResizable(false);
        setLocationRelativeTo(parent);
    }
	
	public String getHostname() {
		return tfHostname.getText().trim();
	}

    public String getUsername() {
        return tfUsername.getText().trim();
    }

    public String getPassword() {
        return new String(pfPassword.getPassword());
    }

}