#!/bin/bash
CUR_DIR=`pwd`

PATH=$PATH:/c/Program\ Files/Java/jdk-21/bin
FX_PATH=/d/software/openjfx/javafx-sdk-21.0.2
TARGET_NAME=hello.jar
PKG_NAME=demo
MAIN_CLASS=fx.Main
BASE=/d/project/fx-hello
OUTPUT_BASE=$BASE/out
DIST_DIR=$OUTPUT_BASE/dist_bash
COMPILED_DIR=$OUTPUT_BASE/production/fx-hello
PKGED_JAR_DIR=$OUTPUT_BASE/artifacts/hello
INPUT_HOME=$PKGED_JAR_DIR
function copyFxDynamicLib() {
  	local FX_DYNAMIC_LIB_PATH=$FX_PATH/bin
  	local DIST_DIR_BIN=$DIST_DIR/$PKG_NAME/runtime/bin
    cp -n $FX_DYNAMIC_LIB_PATH/* $DIST_DIR_BIN
}
function copyFxJar() {
	if [[ ! -x $PKGED_JAR_DIR ]] then
	  mkdir -p $PKGED_JAR_DIR
	fi
	local FX_LIBS_PATH=$FX_PATH/lib
	for cj in `ls $FX_LIBS_PATH`
	do
	  if [[ $cj == *".jar" ]] then
	    cp -n $FX_LIBS_PATH/$cj $PKGED_JAR_DIR
	  fi
  done
}
function pkgCompiledJar() {
  if [[ -f $PKGED_JAR_DIR/$TARGET_NAME ]] then
      local cmd
      echo 'jar exists ,y to re-pkg jar '
      read cmd
      if [[ $cmd != 'y' ]] then
        echo 'no repackage jar'
        return
      fi
  fi
  cd $PKGED_JAR_DIR
  rm -f ./$TARGET_NAME
  jar -cvf $TARGET_NAME -C $COMPILED_DIR .
}
function buildImage(){
  if [[ -d $DIST_DIR/$PKG_NAME ]] then
      local cmd
      echo 'image exists,y to rebuild image'
      read cmd
      if [[ $cmd != 'y' ]] then
        echo 'no build img'
        return
      fi
  fi
  if [[ ! -d $DIST_DIR ]] then
  	mkdir -p $DIST_DIR
  fi
  cd $DIST_DIR
  rm -rf ./$PKG_NAME
  jpackage --type app-image \
  	--name $PKG_NAME \
  	--input $INPUT_HOME \
  	--main-jar $TARGET_NAME \
  	--main-class $MAIN_CLASS \
  	--java-options -Xmx1G \
  	--icon $BASE/useless/icon.ico \
  	--win-console
  copyFxDynamicLib
}
copyFxJar
pkgCompiledJar
buildImage

cd $CUR_DIR