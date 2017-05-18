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
package icom.jpa.rt;

/**
 * Monitor that checks the time to live and remove old objects from the cache.
 */
public abstract class ObjectCacheAbstractMonitor extends Thread {
    /**
     * The time to live in millisconds 
     */
    private long timeToLive;

    /**
     * The check interval in milliseconds
     */
    private long checkInterval;

    /**
     * The internal time
     */
    private long internalTime = -1;

    private void initialize() {
        timeToLive = 120 * 1000;
        checkInterval = 60 * 1000;
    }

    public ObjectCacheAbstractMonitor() {
        super("CacheMonitor");
        initialize();
    }

    public ObjectCacheAbstractMonitor(String daemonName) {
        super(daemonName);
        setDaemon(true);
        initialize();
    }

    /**
     * @param timeToLive the time to live in milliseconds
     */
    public void setTimeToLive(long timeToLive) {
        this.timeToLive = timeToLive;
    }

    /**
     * @return the timeToLive in milliseconds
     */
    public long getTimeToLive() {
        return timeToLive;
    }

    /**
     * @param checkInterval the check cache interval in milliseconds
     */
    public void setCheckInterval(long checkInterval) {
        this.checkInterval = checkInterval;
    }

    /**
     * @return the check cache interval in milliseconds
     */
    public long getCheckInterval() {
        return checkInterval;
    }

    /**
     * @return the current internalTime
     */
    public long getInternalTime() {
        return internalTime;
    }

    /**
     * The time sequence for time to live operations
     */
    public long getCacheTime() {
        return internalTime == -1 ? -1 : timeToLive + internalTime;
    }

    /**
     * Check the cache if there is any objects to remove
     */
    protected abstract void monitorCache();

    /**
     * The run mehtod
     */
    public void run() {
        internalTime = 0;
        while (true) {
            try {
                Thread.sleep(checkInterval);
                internalTime += checkInterval;
                monitorCache();
            } catch (InterruptedException ex) {
            }
        }
    }

}
