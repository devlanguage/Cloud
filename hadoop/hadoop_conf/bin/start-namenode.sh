#!/bin/ksh
HDATA=/home/ygong/hdata
HOSTS='hserver1 hserver2'
for x in ${HOSTS}
do
  echo "start node site: ${x}"  
  ssh $x "source ~/.profile; hadoop-daemon.sh start namenode"
done
# hadoop-daemon.sh journalnode
