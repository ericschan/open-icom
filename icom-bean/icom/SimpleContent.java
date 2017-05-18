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

import icom.annotation.LocaleAdapter;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.util.Locale;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


@javax.persistence.Entity
@XmlType(name="SimpleContent", namespace="http://docs.oasis-open.org/ns/icom/content/201008")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(namespace="http://docs.oasis-open.org/ns/icom/content/201008")
public class SimpleContent extends Content {
	
	String characterEncoding;
	String contentEncoding;

	@XmlElement(type=String.class)
	@XmlJavaTypeAdapter(LocaleAdapter.class)
	Locale contentLanguage;
	
	Integer contentLength;
	
	//transient InputStream inputStream;
	transient byte[] partIdentifier;
	ContentStream contentBody;
	
	private static final long serialVersionUID = 1L;
	
	public SimpleContent() {
		super();
	}

	public String getContentEncoding() {
		return contentEncoding;
	}
	
	public void setContentEncoding(String contentEncoding) {
		this.contentEncoding = contentEncoding;
	}

	public String getCharacterEncoding() {
		return characterEncoding;
	}
	
	public void setCharacterEncoding(String characterEncoding) {
		this.characterEncoding = characterEncoding;
	}
	
	public Integer getContentLength() {
		return contentLength;
	}
	
	public void setContentLength(Integer contentLength) {
		this.contentLength = contentLength;
	}

	public Locale getContentLanguage() {
		return contentLanguage;
	}
	
	public void setContentLanguage(Locale contentLanguage) {
		this.contentLanguage = contentLanguage;
	}
	
	ContentStream getContentBody() {
		return contentBody;
	}

	void setContentBody(ContentStream contentBody) {
		if (this.contentBody != null) {
			try {
				this.contentBody.clear();
			} catch (IOException ex) {
				
			}
		}
		this.contentBody = contentBody;
	}
	
	public InputStream getInputStream() {
		ContentStream body = getContentBody();
		if (body != null) {
			return body.getFileInputStream();
		} else {
			return null;
		}
	}
	
	public OutputStream getOutputStream() {
		ContentStream body = getContentBody();
		if (body == null) {
			body = new ContentStream(this);
			setContentBody(body);
		}
		return body.getFileOutputStream();
	}

	public void setDataFile(File file) {
		ContentStream body = new ContentStream(this);
		body.setDataFile(file);
		setContentBody(body);
	}

	public byte[] getPartIdentifier() {
		return partIdentifier;
	}

	public void setPartIdentifier(byte[] partIdentifier) {
		this.partIdentifier = partIdentifier;
	}
        
    public String getcontent() {
        ContentStream body = getContentBody();
        if (body != null) {
            return body.getContent(getCharacterEncoding());
        } else {
            return null;
        }
    }

	public SimpleContent clone() {
		SimpleContent clone = (SimpleContent) super.clone();
		clone.characterEncoding = characterEncoding;
		clone.contentEncoding = contentEncoding;
		clone.contentLanguage = contentLanguage;
		clone.contentLength = contentLength;
		if (contentBody != null) {
			clone.contentBody = contentBody.clone();
			clone.contentBody.pojo = clone;
		}
		return clone;
	}
	
}
