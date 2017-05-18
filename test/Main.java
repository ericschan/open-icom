/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER
 * 
 * Copyright (c) 2010, Oracle Corporation All Rights Reserved.
 * 
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License ("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can obtain
 * a copy of the License at http://openjdk.java.net/legal/gplv2+ce.html.
 * See the License for the specific language governing permission and
 * limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at openICOM/bootstrap/legal/LICENSE.txt.
 * Oracle designates this particular file as subject to the "Classpath" exception
 * as provided by Oracle in the GPL Version 2 section of the License file that
 * accompanied this code.  If applicable, add the following below the License
 * Header, with the fields enclosed by brackets [ ] replaced by your own
 * identifying information:  "Portions Copyrighted [year]
 * [name of copyright owner].
 *
 * Contributor(s): Oracle Corporation
 * 
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */

import icom.jpa.rt.EntityManagerFactoryProvider;
import icom.jpa.rt.UserContext;

import java.awt.FlowLayout;
import java.io.Console;
import java.util.HashMap;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.swing.JFrame;

import util.LoginDialog;
import util.UserCredential;

public class Main {

	static String emName = "bdkEntityManager";
	static HashMap<String, String> map = new HashMap<String, String>();
	static {
		map.put("VendorBeanUtil", "icom.beehive.BeehiveBeanUtil");
		map.put("DataAccessConnectorFactory", "icom.jpa.bdk.BdkConnectorFactory");
		map.put("Host", "a.b.com");
		map.put("Port", "7777");
		map.put("Protocol", "http");
	}
	
	static Main m; 
	
	EntityManagerFactoryProvider provider;
	EntityManagerFactory factory;
	EntityManager manager;
	UserCredential credential;
	UserContext userContext;
	Session session;
	
	public void loginDialog() {
		JFrame frame = new JFrame("Login Form");
		LoginDialog dialog = new LoginDialog(frame, credential);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 100);
        frame.setLayout(new FlowLayout());
        dialog.setVisible(true);
	}
	
	public String readUserName(String prompt) {
		String userName = System.console().readLine("%s", prompt);
		return userName;
	}

	public char[] readPassword(String prompt) {
		char[] password = System.console().readPassword("%s", prompt);
		return password;
	}
	
	public void getUserCredentials() {
		Console cons = System.console();
		if (cons != null) {
			credential.userName = readUserName("Enter user id: ");
			credential.password = readPassword("Enter password: ");
		} else {
			loginDialog();
		}
	}

	public void initialize() {
		credential = new UserCredential();
		getUserCredentials();
		provider = new EntityManagerFactoryProvider();
		factory = provider.createEntityManagerFactory(emName, map);
		manager = factory.createEntityManager();
		session = new Session(manager);
		session.setupUserContext(credential.userName, credential.password);
	}
	
	public void test1() {
		UnifiedMessaging.testUnifiedMessaging(session);
	}
		 
	public static void main(String[] args) throws Exception {
		for (int i = 0; i < args.length - 1; i = i + 2) {
			if (args[i].equalsIgnoreCase("-host")) {
				String host = args[i+1];
				map.put("Host", host);
			} else if (args[i].equalsIgnoreCase("-port")) {
				String port = args[i+1];
				map.put("Port", port);
			} else if (args[i].equalsIgnoreCase("-protocol")) {
				String protocol = args[i+1];
				map.put("Protocol", protocol);
			}  
		}
		m = new Main();
		m.initialize();
		m.test1();
	}
}
