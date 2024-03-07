#!/bin/bash

SEP=:
PATH_INFO=
JAR_DIR=/d/software/openjfx/javafx-sdk-21.0.2/lib
for file in `ls $JAR_DIR`
do
  PATH_INFO=$JAR_DIR/$file$SEP$PATH_INFO
done
PATH_INFO=$PATH_INFO/d/project/fx-hello/out/production/fx-hello
echo $PATH_INFO
java -classpath $PATH_INFO fx.Main