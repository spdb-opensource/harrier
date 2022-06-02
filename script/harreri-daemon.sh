#!/bin/bash
usage="Usage: harrier-daemon.sh (start|stop|status) <api-server|alarm-server|master-server|worker-server|monitor-server|scheduler-server|standalone-server|> "

# if no args specified, show usage
if [ $# -le 1 ]; then
  echo $usage
  exit 1
fi

startStop=$1
shift
command=$1
shift

echo "Begin $startStop $command......"

BIN_DIR=`dirname $0`
BIN_DIR=`cd "$BIN_DIR"; pwd`
HARRIER_HOME=`cd "$BIN_DIR/.."; pwd`

source /etc/profile

export HARRIER_PID_DIR=$HARRIER_HOME/pid
export HARRIER_LOG_DIR=$HARRIER_HOME/logs
export HARRIER_CONF_DIR=$HARRIER_HOME/conf
export HARRIER_LIB_JARS=$HARRIER_HOME/lib/*

export STOP_TIMEOUT=5

if [ ! -d "$HARRIER_LOG_DIR" ]; then
  mkdir $HARRIER_LOG_DIR
fi

log=$HARRIER_LOG_DIR/harrier-$command-$HOSTNAME.out
pid=$HARRIER_PID_DIR/harrier-$command.pid

cd $HARRIER_HOME

export HARRIER_OPTS="-server -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=128m -Xss512k -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:+CMSParallelRemarkEnabled -XX:LargePageSizeInBytes=128m -XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=70 -XX:+PrintGCDetails -Xloggc:$HARRIER_LOG_DIR/gc.log -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=dump.hprof -XshowSettings:vm $HARRIER_OPTS"


if [ "$command" = "api-server" ]; then
  CLASS=cn.spdb.harrier.api.ApiServer
  HEAP_OPTS="-Xms1g -Xmx1g -Xmn512m"
  export HARRIER_OPTS="$HEAP_OPTS $HARRIER_OPTS $API_SERVER_OPTS"
elif [ "$command" = "master-server" ]; then
  CLASS=cn.spdb.harrier.server.master.UdsMasterServer
  HEAP_OPTS="-Xms4g -Xmx4g -Xmn2g"
  export HARRIER_OPTS="$HEAP_OPTS $HARRIER_OPTS $MASTER_SERVER_OPTS"
elif [ "$command" = "worker-server" ]; then
  CLASS=cn.spdb.harrier.server.worker.UdsWorkerServer
  HEAP_OPTS="-Xms2g -Xmx2g -Xmn1g"
  export HARRIER_OPTS="$HEAP_OPTS $HARRIER_OPTS $WORKER_SERVER_OPTS"
elif [ "$command" = "alarm-server" ]; then
  CLASS=cn.spdb.harrier.alarm.AlarmServer
  HEAP_OPTS="-Xms1g -Xmx1g -Xmn512m"
  export HARRIER_OPTS="$HEAP_OPTS $HARRIER_OPTS $ALARM_SERVER_OPTS"
elif [ "$command" = "monitor-server" ]; then
  HEAP_OPTS="-Xms2g -Xmx2g -Xmn1g"
  CLASS=cn.spdb.harrier.api.MonitorApplication
  export HARRIER_OPTS="$HEAP_OPTS $HARRIER_OPTS $MONITOR_SERVER_OPTS"
elif [ "$command" = "standalone-server" ]; then
  HEAP_OPTS="-Xms6g -Xmx6g -Xmn3g"
  CLASS=cn.spdb.harrier.standalone.StandaloneServer
  export HARRIER_OPTS="$HEAP_OPTS $HARRIER_OPTS $STANDALONE_SERVER_OPTS"
else
  echo "Error: No command named '$command' was found."
  exit 1
fi

case $startStop in
  (start)
    if [ "$DOCKER" = "true" ]; then
      echo start $command in docker
      export HARRIER_OPTS="$HARRIER_OPTS -XX:-UseContainerSupport"
      exec_command="$LOG_FILE $HARRIER_OPTS -classpath $HARRIER_CONF_DIR:$HARRIER_LIB_JARS $CLASS"
      $JAVA_HOME/bin/java $exec_command
    else
      [ -w "$HARRIER_PID_DIR" ] || mkdir -p "$HARRIER_PID_DIR"

      if [ -f $pid ]; then
        if kill -0 `cat $pid` > /dev/null 2>&1; then
          echo $command running as process `cat $pid`.  Stop it first.
          exit 1
        fi
      fi

      echo starting $command, logging to $log
      exec_command="$LOG_FILE $HARRIER_OPTS -classpath $HARRIER_CONF_DIR:$HARRIER_LIB_JARS $CLASS"
      echo "nohup $JAVA_HOME/bin/java $exec_command > $log 2>&1 &"
      nohup $JAVA_HOME/bin/java $exec_command > $log 2>&1 &
      echo $! > $pid
    fi
    ;;

  (stop)

      if [ -f $pid ]; then
        TARGET_PID=`cat $pid`
        if kill -0 $TARGET_PID > /dev/null 2>&1; then
          echo stopping $command
          kill $TARGET_PID
          sleep $STOP_TIMEOUT
          if kill -0 $TARGET_PID > /dev/null 2>&1; then
            echo "$command did not stop gracefully after $STOP_TIMEOUT seconds: killing with kill -9"
            kill -9 $TARGET_PID
          fi
        else
          echo no $command to stop
        fi
        rm -f $pid
      else
        echo no $command to stop
      fi
      ;;

  (status)
    # more details about the status can be added later
    serverCount=`ps -ef |grep "$CLASS" |grep -v "grep" |wc -l`
    state="STOP"
    #  font color - red
    state="[ \033[1;31m $state \033[0m ]"
    if [[ $serverCount -gt 0 ]];then
      state="RUNNING"
      # font color - green
      state="[ \033[1;32m $state \033[0m ]"
    fi
    echo -e "$command  $state"
    ;;

  (*)
    echo $usage
    exit 1
    ;;

esac

echo "End $startStop $command."
