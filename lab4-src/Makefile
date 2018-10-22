# JavaCC Makefile for CS352
# Lab 3
JAVAC=javac
JAVACC=/homes/cs352/javacc/bin/javacc

SRC=src
BIN=bin
BIN_OUTPUT=$(BIN)
PARSE_OUTPUT=$(BIN_OUTPUT)/parse
BASE=$(SRC)

JJ_SOURCE=\
	$(BASE)/SimpleC.jj

JJ_PARSE_DIR=$(BASE)/parse

JJ_JAVA_SOURCE_=\
	$(JJ_SOURCE:.jj=.java) \
	$(JJ_SOURCE:.jj=Constants.java) \
	$(JJ_SOURCE:.jj=TokenManager.java)
JJ_JAVA_SOURCE=$(subst $(BASE), $(JJ_PARSE_DIR), $(JJ_JAVA_SOURCE_))

JJ_JAVA_EXTRAS=\
	$(JJ_PARSE_DIR)/TokenMgrError.java \
	$(JJ_PARSE_DIR)/SimpleCharStream.java \
	$(JJ_PARSE_DIR)/Token.java \
	$(JJ_PARSE_DIR)/ParseException.java

JAVA_SOURCE= \
	$(BASE)/AST.java \
	$(BASE)/Scope.java 

JAVA_BASES=$(JAVA_SOURCE:.java=)

BASEDEP=bin/parse/SimpleC.class

all: git-commit $(BASEDEP)

$(JJ_PARSE_DIR)/SimpleC.java: $(BASE)/SimpleC.jj
	$(JAVACC) -OUTPUT_DIRECTORY=$(JJ_PARSE_DIR) $<

$(BASEDEP): $(JJ_PARSE_DIR)/SimpleC.java $(JAVA_SOURCE)
	$(JAVAC) -d $(BIN) `find $(BASE) -name '*.java'`

git-commit:
	git add src/*.jj src/*.java Makefile >> .local.git.out  || echo
	git commit -a -m 'Commit' >> .local.git.out || echo

%.class: $(BASEDEP)

clean:
	@for n in `find $(BASE) -name '*.java' | sed -E 's/$(SRC)([^\.]+)\.java/$(BIN)\1/g'`; do \
	    rm -f "$$n".class "$$n"\$$*.class; \
	done
	@rm -f $(JJ_JAVA_SOURCE)
	@rm -f $(JJ_JAVA_EXTRAS)
	@rm -f bin/simplec/*.class
	@rm -f bin/simplec/parse/*.class
	@rm -f $(PARSE_OUTPUT)/*.class
.SECONDARY:
