package util;
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

public class FileUtil {

	static String directoryPath;
	
	static {
        String currentDir = System.getProperty("user.dir");
        directoryPath = currentDir + "/" + "contentStreams";
        File directoryfile = new File(directoryPath);
        directoryfile.mkdir();
    }
	
	static public String getDirectory() {
        return directoryPath;
	}
	
	static public String getDefaultContent() {
		return "<html><body>"
				+ "This is the first chunk of content.\n" 
				+ "This is the second chunk of content created at " + new Date()
				+ "</body></html>";
	}
	
	static public File createFile(String content) throws IOException {
		String directoryPath = getDirectory();
        String fileName = UUID.randomUUID().toString();
		String filePath = directoryPath + "/" + fileName;
		File file = new File(filePath);
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(file);
			byte[] data = content.getBytes();
			fileOutputStream.write(data);
			fileOutputStream.close();
		} catch (IOException ex) {
			throw ex;
		}
		return file;
	}
	
	static public File createFile() {
		String content = getDefaultContent(); 
        try {
        	File file = createFile(content);
    		return file;
        } catch (IOException ex) {
        	ex.printStackTrace();
        	return null;
        }  
	}
	
}
