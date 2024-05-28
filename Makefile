JAVAC = javac
ANTLR_COMPLETE = lib/antlr-4.13.1-complete.jar
JAVAC_FLAGS = -cp $(ANTLR_COMPLETE) -Xlint:deprecation -d out
SRC_DIR = src
BIN_DIR = out
MAIN_CLASS = Main
SOURCES = $(wildcard $(SRC_DIR)/*.java)
GRAMMARS = $(SRC_DIR)/Python3Lexer.g4 $(SRC_DIR)/Python3Parser.g4
ANTLR_OUTPUT = $(SRC_DIR)/Python3Lexer.java $(SRC_DIR)/Python3Parser.java $(SRC_DIR)/Python3ParserListener.java $(SRC_DIR)/Python3ParserBaseListener.java 
DATE = $(shell date +%Y%m%d-%H%M%S)
ARGS = 

all: $(SOURCES) $(ANTLR_OUTPUT)
	$(JAVAC) $(JAVAC_FLAGS) $^

$(ANTLR_OUTPUT): $(GRAMMARS)
	java -jar lib/antlr-4.13.1-complete.jar $^

run:
	java -cp $(ANTLR_COMPLETE):$(BIN_DIR) $(MAIN_CLASS) $(ARGS)

runall:
	java -cp $(ANTLR_COMPLETE):$(BIN_DIR) ParseAll $(ARGS)

clean:
	rm -rf $(BIN_DIR)/* trees/*

release: clean
	zip -r ../python3-miniparser-$(DATE).zip .

.PHONY: all run clean release runall
