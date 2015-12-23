#!/bin/ksh
HDATA=/home/ygong/hdata
HOSTS='hserver1 hserver2 hserver3'
for x in ${HOSTS}
do
  echo "start node site: ${x}"  
  ssh $x "source ~/.profile; hadoop-daemon.sh start journalnode"
done
# hadoop-daemon.sh journalnode
#### forcefence and forceactive flags not supported with auto-failover enabled.
# hdfs haadmin ¨Cfailover ¨Cforceactive nn1 nn2