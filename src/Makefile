SRC_DIR := .
OUT_DIR := ../EXE
LIBS := ../lib/AIMA.jar


all: compile

compile: $(wildcard *.java)
	javac -d $(OUT_DIR) -cp $(LIBS) $^

run:
	java -cp $(OUT_DIR) Main


# Elimina todos los .class
clean:
	find $(OUT_DIR) -name "*.class" -exec rm -rf {} +