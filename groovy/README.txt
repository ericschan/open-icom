
This folder contains sample groovy scripts.

To use these scripts, please download the latest groovy runtime from 
http://dist.groovy.codehaus.org/distributions/groovy-binary-1.8.0.zip

Unzip to folder C:\Groovy\groovy-1.8.0

set GROOVY_HOME=C:\Groovy\groovy-1.8.0
 
set PATH=%GROOVY_HOME%\bin;%PATH%

set JAVA_OPTS=-javaagent:lib\icom-weaver.jar

Download OpenICOM runtime and groovy examples from 
http://java.net/projects/open-icom/downloads/download/icomGroovy.tar.gz

Unzip and untar to folder C:\Groovy\icomGroovy

cd C:\Groovy\icomGroovy

run "setclasspath_1.bat" to setup the class paths 
(run "setclasspath_2.bat" if testing from SVN source directory)

For convenience, you can use the following utility to store your credentials 
in a file to associate with a pseudonym and a secret key.

groovy storeCredential.groovy

Set the pseudonym and secret key in the credential.store and credential.key properties.
For example:

set JAVA_OPTS=-javaagent:lib/icom-weaver.jar -Xmx1024m -Dcredential.store="mypseudonym" -Dcredential.key="mysecretkey" 

The following sample script prompts for your userid/password, signs on, and 
prints the list of folders in your personal workspace. 

groovy signon.groovy

Additional test scripts can be invoked directly as follows:

groovy createSpace.groovy
groovy deleteSpace.groovy
groovy uploadDocument.groovy
groovy applyTag.groovy
groovy sendMessage.groovy
groovy versionControl.groovy
groovy queryDocuments.groovy
groovy queryMessages.groovy
groovy queryUsers.groovy

