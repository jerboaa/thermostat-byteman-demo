build:
	mkdir bin && javac -d bin -g Demo.java

clean:
	rm -rf bin

run:
	java -cp bin Demo

.PHONY: build clean run
