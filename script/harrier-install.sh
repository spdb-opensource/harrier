#!/bin/bash
localZip=$1
shift

remoteDir=$1
shift

BIN_DIR=`dirname $0`
BIN_DIR=`cd "$BIN_DIR"; pwd`
HARRIER_HOME=`cd "$BIN_DIR/.."; pwd`

export HARRIER_HOME=${HARRIER_HOME//'\'/'/'}
export HARRIER_CONF_DIR=$HARRIER_HOME/conf
export HARRIER_LIB_JARS=$HARRIER_HOME/lib/*

echo $HARRIER_HOME

if [ "$remoteDir" != "" ]; then
  echo "remoteDir $remoteDir"
fi


if [ "$localZip" != "" ]; then
  echo "localZip $localZip"
fi


if [ !$JAVA_HOME ]; then
export JAVA_HOME=${JAVA_HOME//'\'/'/'}
echo JAVA_HOME:$JAVA_HOME
else
export JAVA_HOME=$HARRIER_HOME/jre
echo JAVA_HOME:$JAVA_HOME
fi 

$JAVA_HOME/bin/java -version;

$JAVA_HOME/bin/java -server -classpath $HARRIER_CONF_DIR:$HARRIER_LIB_JARS cn.spdb.harrier.common.script.InstallScript $HARRIER_HOME $localZip $remoteDir;