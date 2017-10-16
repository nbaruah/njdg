#!/usr/bin/env bash

##############################################################
#  
# This script will run the web Service
# 
# Author(s)
# ----------
# Nayan Mani Baruah (nayanmanibaruah@gmail.com)
#
##################################################################

# Getting the path
function get_canonical_dir() {
  target="$1"
  canonical_name=`readlink -f ${target} 2>/dev/null`
  if [[ $? -eq 0 ]]; then
    canonical_dir=`dirname $canonical_name`
    echo ${canonical_dir}
    return
  fi

  # Mac has no readlink -f
  cd `dirname ${target}`
  target=`basename ${target}`

  # chase down the symlinks
  while [ -L ${target} ]; do
    target=`readlink ${target}`
    cd `dirname ${target}`
    target=`basename ${target}`
  done

  canonical_dir=`pwd -P`
  ret=${canonical_dir}
  echo $ret
}

bin=$(get_canonical_dir "$0")
bin=`cd "$bin"; pwd`

BASE_DIR="`pwd | sed -e 's/\/bin//'`" 
LOG_DIR=$BASE_DIR/app_log
CONFIG_DIR=$BASE_DIR/config
JAR_DIR=$BASE_DIR/lib
JAR_FILE=`ls $JAR_DIR/njdg-*.jar`
MAIN_CLASS="ghc.njdg.core.App"

APP_CLASSPATH=`echo "$JAR_DIR"/*.jar | sed 's/ /:/g'`
APP_OPTS="-Dlog4j.configurationFile=$CONFIG_DIR/log4j2.xml -Dlog.dir=$LOG_DIR"
APP_CONFIG="-c $CONFIG_DIR/config.properties"

java $APP_OPTS -cp $APP_CLASSPATH $MAIN_CLASS $APP_CONFIG $@

if [ $? -eq 0 ]
then
  echo "[INFO] successfull."
else
  echo "[ERROR] exited with -1"
  exit 1
fi