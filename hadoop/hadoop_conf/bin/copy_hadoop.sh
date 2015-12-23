#!/bin/ksh
HOSTS='hserver2 hserver3'
UHOME=/home/ygong
HDATA=${UHOME}/hdata

for HSERVER in ${HOSTS}
do
  echo "Update hadoop site: ${HSERVER}"
  scp -pr ~/.profile $HSERVER:~
  ssh $HSERVER "source ~/.profile; mkdir -p ${HDATA}/dfs/data; mkdir -p ${HDATA}/dfs/name"
  ssh $HSERVER "source ~/.profile; mkdir -p ${HDATA}/mapred/system; mkdir -p ${HDATA}/mapred/local"
  ssh $HSERVER "source ~/.profile; mkdir -p ${HDATA}/tmp"
  ssh $HSERVER "source ~/.profile; mkdir -p ${HDATA}/journal"  
  ssh $HSERVER "source ~/.profile; mkdir -p ${HDATA}/yarn/local; mkdir -p ${HDATA}/yarn/log; mkdir -p ${HDATA}/yarn/remote"
  ssh $HSERVER "source ~/.profile; mkdir -p ${HDATA}/zookeeper/data; mkdir -p ${HDATA}/zookeeper/log"
  ssh $HSERVER "source ~/.profile; mkdir -p ${HDATA}/hbase/log; mkdir -p ${HDATA}/hbase/pids"
  
  
  scp -pr ${UHOME}/bin/* $HSERVER:${UHOME}/bin
  scp -pr ${HADOOP_HOME}/etc/hadoop/* $HSERVER:${HADOOP_HOME}/etc/hadoop
  scp -pr ${HADOOP_HOME}/sbin/* $HSERVER:${HADOOP_HOME}/sbin
  scp -pr ${HADOOP_HOME}/bin/* $HSERVER:${HADOOP_HOME}/bin
  scp -pr ${ZOOKEEPER_HOME}/conf/* $HSERVER:${ZOOKEEPER_HOME}/conf
  scp -pr ${HBASE_HOME}/conf/* $HSERVER:${HBASE_HOME}/conf
  scp -pr ${HBASE_HOME}/bin/* $HSERVER:${HBASE_HOME}/bin
  scp -pr ${HIVE_HOME}/conf/* $HSERVER:${HIVE_HOME}/conf
  
done

