MAKEFLAGS += --no-print-directory
SRC_DIR := ./src
OUT_DIR := ./EXE
LIB_DIR := ./lib
LIBS := $(LIB_DIR)/AIMA.jar:$(LIB_DIR)/DistributedFS.jar


all: compile

JAVA_FILES := $(wildcard $(SRC_DIR)/*.java)

compile: $(JAVA_FILES)
	javac -d $(OUT_DIR) -cp $(SRC_DIR):$(LIBS) $^

#no toqueu del run fins despres de @ que sino no funciona el   	make run param1 param2 ...
.PHONY: run
run: ARGS = $(filter-out $@,$(MAKECMDGOALS))
run: 
	@java -cp $(OUT_DIR):$(LIBS) Main $(ARGS)

%:
	@:

# Per executar amb python
pymain:
	python3 experiments/main.py

#amb aixo podem cridar qualsevol python que estigui a experiments amb: make pye-{script_name}
#exemple amb exp1.py: make pye-exp1
.PHONY: pye
pye-%:
	@python3 experiments/$*.py

#amb aixo podem cridar qualsevol python que estigui a experiments amb: make pyg-{script_name}
#exemple amb exp1.py: make pyg-exp1
.PHONY: pyg
pyg-%:
	@python3 plots/$*.py


# Elimina todos los .class
clean:
	find $(OUT_DIR) -name "*.class" -exec rm -rf {} +
