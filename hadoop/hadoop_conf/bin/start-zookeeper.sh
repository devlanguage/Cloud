#!/bin/ksh
HDATA=/home/ygong/hdata
HOSTS='hserver1 hserver2 hserver3'
TYPE=$1
if [ "X${TYPE}" == "X" -o "${TYPE}" == "server" ]
then
  TYPE=server
elif [ "${TYPE}" == "client" ]
then
  TYPE=client
else
  echo invalid
  exit;
fi

for x in ${HOSTS}
do
  echo "start node site: ${x}"
  
  
    ###QuorumPeerMain
    echo "  ${ZOOKEEPER_HOME}/bin/zkServer.sh start"  
    ssh $x "source ~/.profile; ${ZOOKEEPER_HOME}/bin/zkServer.sh start"
  
  
  
done
# hadoop-daemon.sh journalnode
#### forcefence and forceactive flags not supported with auto-failover enabled.
