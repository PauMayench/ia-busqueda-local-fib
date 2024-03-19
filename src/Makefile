SRC_DIR := .
OUT_DIR := ../EXE
LIB_DIR := ../lib
LIBS := $(LIB_DIR)/AIMA.jar:$(LIB_DIR)/DistributedFS.jar


all: compile

compile: $(wildcard *.java)
	javac -d $(OUT_DIR) -cp $(LIBS) $^

#no toqueu del run fins despres de @ que sino no funciona el   	make run param1 param2 ...
.PHONY: run
run: ARGS = $(filter-out $@,$(MAKECMDGOALS))
run:
	java -cp $(OUT_DIR):$(LIBS) Main $(ARGS)

%:
	@:


# Elimina todos los .class
clean:
	find $(OUT_DIR) -name "*.class" -exec rm -rf {} +
