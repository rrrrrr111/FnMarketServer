::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
::
:: Script for Maven Artifact Installation 
::
:: To stop CMD process enter Ctrl+C
::
::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::

set q="
set MAVEN_HOME=C:\01_work\61_JavaLib\apache-maven-3.2.5\
set COMMAND=%MAVEN_HOME%bin\mvn.bat install:install-file


::set JAR_FILE=C:\01_work\sdfgsdfg\poi-ooxml-schemas-iteco-3.8\poi-ooxml-schemas-iteco-3.8.jar
::::set ARTIFACT_POMFILE=
::set ARTIFACT_GROUP=org.apache.poi
::set ARTIFACT_ID=poi-ooxml-schemas-iteco
::set ARTIFACT_VERSION=3.8
::set ARTIFACT_PACKAGING=jar
::set ARTIFACT_GENERATEPOM=true
::set ARTIFACT_CREATECHECKSUMM=true

set JAR_FILE=C:\01_work\diff\ojdbc6-11.2.0.4.jar
set ARTIFACT_POMFILE=C:\01_work\diff\ojdbc6-11.2.0.4.pom
set ARTIFACT_GROUP=com.oracle
set ARTIFACT_ID=ojdbc6
set ARTIFACT_VERSION=11.2.0.4
set ARTIFACT_PACKAGING=jar
set ARTIFACT_GENERATEPOM=false
set ARTIFACT_CREATECHECKSUMM=true

call %COMMAND% -Dfile=%JAR_FILE% -DgroupId=%ARTIFACT_GROUP% -DartifactId=%ARTIFACT_ID% -Dversion=%ARTIFACT_VERSION% -Dpackaging=%ARTIFACT_PACKAGING% -DgeneratePom=%ARTIFACT_GENERATEPOM% -DcreateChecksum=%ARTIFACT_CREATECHECKSUMM% -DpomFile=%ARTIFACT_POMFILE%

::pause
exit