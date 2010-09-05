.PHONY: wolverinegame.jar
wolverinegame.jar:
	javac Wolverine.java
	jar cfm wolverinegame.jar manifest.txt *.class *.java img *.properties *.wol

