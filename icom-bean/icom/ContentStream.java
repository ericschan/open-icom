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
package icom;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

import java.util.UUID;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.persistence.PersistenceException;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="ContentStream", namespace="http://docs.oasis-open.org/ns/icom/content/201008")
@XmlTransient
public class ContentStream implements Cloneable {
    
    public static int dataSize = 1024;
	
	static String directoryPath;
	
	static {
        String currentDir = System.getProperty("user.dir");
        directoryPath = currentDir + "/" + "contentStreams";
        File directoryfile = new File(directoryPath);
        directoryfile.mkdir();
    }
	
	static boolean encryptContentStreamCache;
	
	static {
	    String encryptContentStreamCacheStr = System.getProperty("encrypt.content.stream.cache");
	    if (encryptContentStreamCacheStr != null && encryptContentStreamCacheStr.length() != 0) {
	        encryptContentStreamCache = Boolean.valueOf(encryptContentStreamCacheStr);
	    } else {
	        encryptContentStreamCache = true;
	    }
	}
	
	static SecretKey cypherKey;
	
	static {
	    try {
	       cypherKey = KeyGenerator.getInstance("DES").generateKey();
	    } catch (Exception ex) {
	        ex.printStackTrace();
	        encryptContentStreamCache = false;
	    }
	}
	
	Object pojo;
	
	InputStream remoteInputStream;
	
	String fileName;
	String filePath;
	
	ContentStream() {
		super();
		initFileName();
	}
	
	public ContentStream(Object pojo) {
		this.pojo = pojo;
		initFileName();
	}
	
	void initFileName() {
		fileName = UUID.randomUUID().toString();
		filePath = directoryPath + "/" + fileName;
	}
	
	public File downloadRemoteInputStreamToFile() {
		if (remoteInputStream == null) {
			return null;
		}
		try {
			clear();
			File file = new File(filePath);
			OutputStream outputStream = null;
			FileOutputStream fileOutputStream = new FileOutputStream(file);
			if (encryptContentStreamCache) {
			    try {
    			    Cipher cipher = Cipher.getInstance("DES");
    	            cipher.init(Cipher.ENCRYPT_MODE, cypherKey);
    			    outputStream = new CipherOutputStream(fileOutputStream, cipher);
			    } catch (Exception ex) {
			        ex.printStackTrace();
			        outputStream = fileOutputStream;
			    }
			} else {
			    outputStream = fileOutputStream;
			}
			int readDataLength = 0;
			byte[] data = new byte[dataSize];
			do {
		        readDataLength = remoteInputStream.read(data);
				if (readDataLength > 0) {
					outputStream.write(data, 0, readDataLength);
				}
	        } while (readDataLength > 0);
			remoteInputStream.close();
			outputStream.flush();
			outputStream.close();
			return file;
        } catch (IOException ex) {
        	throw new PersistenceException(ex);
        } finally {
        	remoteInputStream = null;
        }
	}
	
	public InputStream getFileInputStream() {
		try {
			File file = null;
			if (remoteInputStream != null) {
				file = downloadRemoteInputStreamToFile();
			} else {
				file = new File(filePath);
			}
			InputStream inputStream = null;
			FileInputStream fileInputStream = new FileInputStream(file);
			if (encryptContentStreamCache) {
                try {
                    Cipher cipher = Cipher.getInstance("DES");
                    cipher.init(Cipher.DECRYPT_MODE, cypherKey);
                    inputStream = new CipherInputStream(fileInputStream, cipher);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    inputStream = fileInputStream;
                }
            } else {
                inputStream = fileInputStream;
            }
			return inputStream;
		} catch (IOException ex) {
        	throw new PersistenceException(ex);
        }
	}
	
	public OutputStream getFileOutputStream() {
		try {
		    OutputStream outputStream = null;
			File file = new File(filePath);
			FileOutputStream fileOutputStream = new FileOutputStream(file);
			if (encryptContentStreamCache) {
                try {
                    Cipher cipher = Cipher.getInstance("DES");
                    cipher.init(Cipher.ENCRYPT_MODE, cypherKey);
                    outputStream = new CipherOutputStream(fileOutputStream, cipher);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    outputStream = fileOutputStream;
                }
            } else {
                outputStream = fileOutputStream;
            }
			return outputStream;
		} catch (IOException ex) {
        	throw new PersistenceException(ex);
        }
	}
	
	public File copyFile(File source) {
        try {
            File file = new File(filePath);
            OutputStream outputStream = null;
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            if (encryptContentStreamCache) {
                try {
                    Cipher cipher = Cipher.getInstance("DES");
                    cipher.init(Cipher.ENCRYPT_MODE, cypherKey);
                    outputStream = new CipherOutputStream(fileOutputStream, cipher);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    outputStream = fileOutputStream;
                }
            } else {
                outputStream = fileOutputStream;
            }
            int readDataLength = 0;
            byte[] data = new byte[dataSize];
            FileInputStream sourceInputStream = new FileInputStream(source);
            do {
                readDataLength = sourceInputStream.read(data);
                if (readDataLength > 0) {
                    outputStream.write(data, 0, readDataLength);
                }
            } while (readDataLength > 0);
            sourceInputStream.close();
            outputStream.flush();
            outputStream.close();
            return file;
        } catch (IOException ex) {
            throw new PersistenceException(ex);
        }
    }
	
	public void setDataFile(File source) {
		if (!(filePath.equals(source.getPath()) && fileName.equals(source.getName()))) {
		    if (filePath.startsWith(directoryPath)) { // file is a scratch copy
    			try {
    				File prevFile = new File(filePath);
    				prevFile.delete();
    			} catch (Exception ex) {
    				// TODO
    			}
		    }
		    if (encryptContentStreamCache) {
		        copyFile(source);
		    } else {
			    this.fileName = source.getName();
			    this.filePath = source.getPath();
		    }
		}
	}
	
	public void clear() throws IOException {
	    if (filePath.startsWith(directoryPath)) { // file is a scratch copy
    		try {
    			File file = new File(filePath);
    			file.delete();
    		} catch (Exception ex) {
    			// TODO
    		} 
	    }
	}
	
	void copyFile(File srcfile, File destfile) {
		try {
			FileInputStream srcfileStream = new FileInputStream(srcfile);
			FileOutputStream destfileStream = new FileOutputStream(destfile);
			int readDataLength = 0;
			byte[] data = new byte[dataSize];
			do {
		        readDataLength = srcfileStream.read(data);
				if (readDataLength > 0) {
					destfileStream.write(data, 0, readDataLength);
				}
	        } while (readDataLength > 0);
			srcfileStream.close();
			destfileStream.flush();
			destfileStream.close();
        } catch (IOException ex) {
        	throw new PersistenceException(ex);
        }
	}
        
    public String getContent(String characterEncoding) {
        Writer writer = new StringWriter();
        InputStream input = getFileInputStream();
        try {
            if (characterEncoding == null || characterEncoding.length() == 0) {
                characterEncoding = "UTF-8";
            }
            InputStreamReader streamreader = new InputStreamReader(input, characterEncoding);
            Reader reader = new BufferedReader(streamreader); 
            int len1 = 10240; // input.available();
            char[] data = new char[len1];
            int readDataLength = reader.read(data);              
            while (readDataLength > 0) {
                writer.write(data, 0, readDataLength);  
                readDataLength = reader.read(data);  
            } 
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new PersistenceException(ex);
        } finally {
            try {
                writer.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
                throw new PersistenceException(ex);
            }
            try {
                input.close();
            } catch (IOException ex) {
                ex.printStackTrace();
                throw new PersistenceException(ex);
            } 
        }
        return writer.toString();
    }
	
	public ContentStream clone() {
		try {
			ContentStream clone = (ContentStream) this.getClass().newInstance();
			clone.fileName = UUID.randomUUID().toString();
			clone.filePath = directoryPath + "/" + clone.fileName;
			File srcfile = new File(filePath);	
			File destfile = new File(clone.filePath);
			copyFile(srcfile, destfile);
			return clone;
		} catch (IllegalAccessException ex) {
			throw new PersistenceException("illegal access exception of identifiable class", ex);
		} catch (InstantiationException ex) {
			throw new PersistenceException("instantiation exception of identifiable class", ex);
		}
	}
	
}
