SRC_DIR := ./src
OUT_DIR := ./EXE
LIB_DIR := ./lib
LIBS := $(LIB_DIR)/AIMA.jar:$(LIB_DIR)/DistributedFS.jar


all: compile

JAVA_FILES := $(wildcard $(SRC_DIR)/*.java)

compile: $(JAVA_FILES)
	javac -d $(OUT_DIR) -cp $(LIBS) $^

#no toqueu del run fins despres de @ que sino no funciona el   	make run param1 param2 ...
.PHONY: run
run: ARGS = $(filter-out $@,$(MAKECMDGOALS))
run:
	java -cp $(OUT_DIR):$(LIBS) Main $(ARGS)

%:
	@:

# Per executar amb python
pymain:
	python3 src/main.py


# Elimina todos los .class
clean:
	find $(OUT_DIR) -name "*.class" -exec rm -rf {} +
