pack:
	mkdir -p out
	cp src/app/solution/Seminar2.java out/Seminar2.java
	sed -i '1c//package app.solution;' out/Seminar2.java
	javac out/Seminar2.java

clean:
	rm -r out

run: 
	java -cp out Seminar2 inputs/1.txt

packf:
	mkdir -p out
	cp src/app/solution/Fetcher.java out/Fetcher.java
	sed -i '1c//package app.solution;' out/Fetcher.java
	javac out/Fetcher.java