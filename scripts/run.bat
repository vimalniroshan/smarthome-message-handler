@ECHO OFF

"%JAVA_HOME%\bin\java" -classpath .;../target/smarthome-message-handler-1.0-SNAPSHOT-jar-with-dependencies.jar com.sparg.java.smarthome.handler.SmartHomeProcessor %*