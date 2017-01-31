build:
	mkdir bin && javac -d bin -g Demo.java

clean:
	rm -rf bin

run:
	java -cp bin Demo

all:	clean build run

.PHONY: build clean run all
