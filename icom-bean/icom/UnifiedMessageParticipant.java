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

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@javax.persistence.Embeddable
@XmlType(name="UnifiedMessageParticipant", namespace="http://docs.oasis-open.org/ns/icom/message/201008")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(namespace="http://docs.oasis-open.org/ns/icom/message/201008")
public class UnifiedMessageParticipant extends Participant {
    
	static final long serialVersionUID = 1L;
	
	String fullAddress;
	String localPart;
    String domainPart;
    byte[] displayPart;
    String displayCharacterSet;

	UnifiedMessageParticipant() {
		
	}
	
	public UnifiedMessageParticipant(Addressable addressable) {
		super(addressable);
	}
	
	public UnifiedMessageParticipant(String fullAddress) {
		this.fullAddress = fullAddress;
	}
	
	public UnifiedMessageParticipant(String localPart, String domainPart) {
		if (localPart != null) {
			localPart = localPart.trim();
			if (localPart.length() == 0) {
				this.localPart = null;
			} else {
				this.localPart = localPart;
			}
		} else {
			this.localPart = null;
		}
		
		if (domainPart != null) {
			domainPart = domainPart.trim();
			if (domainPart.length() == 0) {
				this.domainPart = null;
			} else {
				this.domainPart = domainPart;
			}
		} else {
			this.domainPart = null;
		}
		
		if (this.localPart == null && this.domainPart != null) {
            this.localPart = this.domainPart;
            this.domainPart = null;
        }
		
		try {
			if (this.localPart != null && this.domainPart != null) {
				this.address = new URI(AddressSchemeEnum.MAILTO.name(), getCanonicalAddress(), (String) null);
			}
			// TODO handle other URI schemas
		} catch (URISyntaxException ex) {
			
		}	
		
	}

	public UnifiedMessageParticipant(String localPart, String domainPart, byte[] displayPart, String displayCharacterSet) {
		this(localPart, domainPart);
		this.displayPart = displayPart;
		this.displayCharacterSet = displayCharacterSet;
	}
	
	public UnifiedMessageParticipant(String localPart, String domainPart, String displayName) {
		this(localPart, domainPart, displayName.getBytes(), "UTF-8");
	}
	
	public String getLocalPart() {
		return localPart;
	}

	public String getDomainPart() {
		return domainPart;
	}

	public String getCanonicalAddress() {
		String localPart = getLocalPart();
		String domainPart = getDomainPart();
        return domainPart == null ? localPart : (localPart + "@" + domainPart);
    }
	
	public String getDisplayName() {
		if (displayPart != null) {
			if (displayCharacterSet != null) {
				try {
					return new String(displayPart, displayCharacterSet);
				} catch (UnsupportedEncodingException ex) {
					return displayPart.toString();
				}
			} else {
				return displayPart.toString();
			}
		} else {
			return null;
		}  
    }
	
	public String getFullAddress() {
		if (fullAddress != null) {
			return fullAddress;
		} else {
			String displayName = getDisplayName();
	        if (displayName != null) {
	        	return "\"" + displayName +"\"" +  "<" + getCanonicalAddress() + ">";
	        } else {
	        	return getCanonicalAddress();
	        }
		}
	}
        
    public String getParticipantName() {
        return getFullAddress();
    }
	
	public UnifiedMessageParticipant clone() {
		UnifiedMessageParticipant clone = (UnifiedMessageParticipant) super.clone();
		clone.fullAddress = fullAddress;
		clone.localPart = localPart;
	    clone.domainPart = domainPart;
	    if (displayPart != null) {
	    	clone.displayPart = displayPart.clone();
	    }
	    clone.displayCharacterSet = displayCharacterSet;
		return clone;
	}
	
}
