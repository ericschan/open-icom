package util;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class CredentialDialog extends JDialog {
    
    private static final long serialVersionUID = 1L;

    private JTextField tfUsername;
    private JPasswordField pfPassword;
    private JTextField tfHost;
    private JTextField tfPseudonym;
    private JTextField tfKey;
    private JLabel lbUsername;
    private JLabel lbPassword;
    private JLabel lbHost;
    private JLabel lbPseudonym;
    private JLabel lbKey;
    private JButton btnSubmit;
    private JButton btnCancel;

    public CredentialDialog(Frame parent, final Credential credential) {
        super(parent, "Credential", true);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints cs = new GridBagConstraints();

        cs.fill = GridBagConstraints.HORIZONTAL;

        lbUsername = new JLabel("User name: ");
        cs.gridx = 0;
        cs.gridy = 0;
        cs.gridwidth = 1;
        panel.add(lbUsername, cs);

        tfUsername = new JTextField(20);
        cs.gridx = 1;
        cs.gridy = 0;
        cs.gridwidth = 2;
        panel.add(tfUsername, cs);

        lbPassword = new JLabel("Password: ");
        cs.gridx = 0;
        cs.gridy = 1;
        cs.gridwidth = 1;
        panel.add(lbPassword, cs);

        pfPassword = new JPasswordField(20);
        cs.gridx = 1;
        cs.gridy = 1;
        cs.gridwidth = 2;
        panel.add(pfPassword, cs);
        panel.setBorder(new LineBorder(Color.GRAY));
        
        lbHost = new JLabel("Host: ");
        cs.gridx = 0;
        cs.gridy = 2;
        cs.gridwidth = 1;
        panel.add(lbHost, cs);

        tfHost = new JTextField(20);
        cs.gridx = 1;
        cs.gridy = 2;
        cs.gridwidth = 2;
        panel.add(tfHost, cs);
        
        lbPseudonym = new JLabel("Pseudonym: ");
        cs.gridx = 0;
        cs.gridy = 3;
        cs.gridwidth = 1;
        panel.add(lbPseudonym, cs);

        tfPseudonym = new JTextField(20);
        cs.gridx = 1;
        cs.gridy = 3;
        cs.gridwidth = 2;
        panel.add(tfPseudonym, cs);
        
        lbKey = new JLabel("Secret Key (min 8 chars): ");
        cs.gridx = 0;
        cs.gridy = 4;
        cs.gridwidth = 1;
        panel.add(lbKey, cs);

        tfKey = new JTextField(20);
        cs.gridx = 1;
        cs.gridy = 4;
        cs.gridwidth = 2;
        panel.add(tfKey, cs);

        btnSubmit = new JButton("Submit");

        btnSubmit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	credential.userName = getUsername();
            	credential.password = getPassword().toCharArray();
            	credential.host = getHost();
            	credential.pseudonym = getPseudonym();
            	credential.passcode = getKey();
            	dispose();
            }
        });
        
        btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	credential.userName = "guest";
            	credential.password = "welcome".toCharArray();
            	credential.host = "a.b.com";
                credential.pseudonym = "smith";
                credential.passcode = "secretkey";
                dispose();
            }
        });
        
        JPanel bp = new JPanel();
        bp.add(btnSubmit);
        bp.add(btnCancel);

        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(bp, BorderLayout.PAGE_END);

        pack();
        setResizable(false);
        setLocationRelativeTo(parent);
    }

    public String getUsername() {
        return tfUsername.getText().trim();
    }

    public String getPassword() {
        return new String(pfPassword.getPassword());
    }
    
    public String getHost() {
        return new String(tfHost.getText().trim());
    }
    
    public String getPseudonym() {
        return new String(tfPseudonym.getText().trim());
    }
    
    public String getKey() {
        return new String(tfKey.getText().trim());
    }
    
    static void credentialDialog(Credential credential) {
        JFrame frame = new JFrame("Credential Form");
        CredentialDialog dialog = new CredentialDialog(frame, credential);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 100);
        frame.setLayout(new FlowLayout());
        dialog.setVisible(true);
    }

}
