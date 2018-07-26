#!/bin/sh
#待运行jar信息
#JAR_NAME=`ls *.jar|head -1`
JAR_NAME=$2
if [ "$JAR_NAME" = "" ]
then
	echo "warn: 请指定需要运行的jar包: $0 {start|stop|restart|status} jar_name [port]"
        exit 1
fi

APP_NAME=`basename $JAR_NAME .jar`
#JVM参数
#-server 使用server模式运行jvm
#-Xms300m 堆最小内存为300M
#-Xmx 堆最大内存为300M 如果-Xmx值与-Xms值相等，表示为固定堆
#-XX:NewRatio=2 新生代与老年代比例=1:2
#-XX:SurvivorRatio=8 单个Survivor与Eden的空间比 新生代=Eden+单个Survivor
#-XX:+DisableExplicitGC 禁止System.gc()执行FullGC
#-XX:MetaspaceSize=128m 初始Metaspace大小
#-XX:+UseG1GC 使用g1收集器
#-XX:+PrintGCDetails 打印垃圾回收详细信息
#-XX:+HeapDumpOnOutOfMemoryError 下面这两个参数表示在发生OOM时记录dump信息
#-XX:HeapDumpPath=./$APP_NAME.dump
#-Xloggc:./gc.log 记录gc日志到指定文件
JVM_OPTS="-server -Xms300m -Xmx300m -XX:NewRatio=2 -XX:SurvivorRatio=8 -XX:+DisableExplicitGC -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=256m -XX:+UseG1GC -Xloggc:./gc.log -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=./$APP_NAME.dump -XX:+PrintGCTimeStamps"
#支持指定端口运行
PORT=$3

#echo $JAVA_CMD
APP_VERSION=`echo $JAR_NAME | grep -o -E '[0-9][0-9\.]{1,6}[0-9]'`

#echo $JAR_NAME
#echo $APP_NAME
#echo $APP_VERSION

###################################
#初始化psid变量（全局）
psid=0

#用于检查程序是否处于运行状态
checkpid() {
   javaps=`$JAVA_HOME/bin/jps -l | grep $JAR_NAME`
 
   if [ -n "$javaps" ]; then
      psid=`echo $javaps | awk '{print $1}'`
   else
      psid=0
   fi
}

##################启动#################
start() {
   checkpid
   
   if [ $psid -ne 0 ]; then
      echo "================================"
      echo "warn: $APP_NAME already started! (pid=$psid)"
      echo "================================"
   else
      echo "Starting $APP_NAME ..."
      #su - 
      #$RUNNING_USER -c "$JAVA_CMD"
      if [ "$PORT" = "" ]
	then
    	    nohup $JAVA_HOME/bin/java $JVM_OPTS -jar $JAR_NAME >/dev/null 2>&1 &
	else
            nohup $JAVA_HOME/bin/java $JVM_OPTS -jar $JAR_NAME --server.port=$PORT >/dev/null 2>&1 &
	fi
      checkpid     
      if [ $psid -ne 0 ]
        then
        	echo "[`date`] Startup $APP_NAME $APP_VERSION success.(pid=$psid)"
	        return 0
        else
        	echo "[`date`] Startup $APP_NAME fail."
	        return 1
        fi

   fi
}

#################停止##################
stop() {
   checkpid
 
   if [ $psid -ne 0 ]; then
      echo -n "Stopping $APP_NAME ...(pid=$psid) "
      #su - 
      kill -9 $psid
      if [ $? -eq 0 ]; then
         echo "[OK]"
      else
         echo "[Failed]"
      fi
 
      checkpid
      if [ $psid -ne 0 ]; then
         stop
      fi
   else
      echo "================================"
      echo "warn: $APP_NAME is not running"
      echo "================================"
   fi
}


#################运行状态##################
status() {
   checkpid

   if [ $psid -ne 0 ];  then
      echo "$APP_NAME is running! (pid=$psid)"
   else
      echo "$APP_NAME is not running"
   fi
}



case "$1" in
	'start')
		start
		;;
	'stop')
		stop
		;;
	'restart')
		stop
		start
		;;
	'status')
		status
		;;
      *)
		echo "Usage $0 {start|stop|restart|status} jar_name [port]"

exit 1
	;;
esac
