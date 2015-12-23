#!/bin/ksh
HDATA=/home/ygong/hdata
HOSTS='1 2 3'
for x in ${HOSTS}
do
  HSERVER="hserver${x}"
  echo "Update hadoop site: ${HSERVER}"  
  ssh $HSERVER "pkill java"
  ssh $HSERVER "chmod u+x ~/.profile"
  ssh $HSERVER "source ~/.profile; rm -fr ${HDATA}/*"
  ssh $HSERVER "source ~/.profile; mkdir -p ${HDATA}/dfs/data; mkdir -p ${HDATA}/dfs/name"
  ssh $HSERVER "source ~/.profile; mkdir -p ${HDATA}/mapred/system; mkdir -p ${HDATA}/mapred/local"
  ssh $HSERVER "source ~/.profile; mkdir -p ${HDATA}/tmp"
  ssh $HSERVER "source ~/.profile; mkdir -p ${HDATA}/journal"  
  ssh $HSERVER "source ~/.profile; mkdir -p ${HDATA}/yarn/local; mkdir -p ${HDATA}/yarn/log; mkdir -p ${HDATA}/yarn/remote"
  ssh $HSERVER "source ~/.profile; mkdir -p ${HDATA}/zookeeper/data; mkdir -p ${HDATA}/zookeeper/log"
  if [ $x -eq 1 ]
  then
    ssh $HSERVER "source ~/.profile; echo $x > ${HDATA}/zookeeper/data/myid"  
  fi
  if [ $x -eq 2 ]
  then
    ssh $HSERVER "source ~/.profile; echo $x > ${HDATA}/zookeeper/data/myid"  
  fi
  if [ $x -eq 3 ]
  then
    ssh $HSERVER "source ~/.profile; echo $x > ${HDATA}/zookeeper/data/myid"  
  fi
  #ssh $HSERVER "source ~/.profile; hdfs namenode -format"
done
