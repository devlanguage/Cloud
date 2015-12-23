#!/bin/ksh
HDATA=/home/ygong/hdata
HOSTS='hserver1 hserver2 hserver3'
TMPFILE=/tmp/printhserver.TMP
echo >${TMPFILE}
for x in ${HOSTS}
do
  echo "==========Server Status: ${x}============"  
  ssh $x "source ~/.profile; jps|grep -v Jps|sort -k 2" 
done
# hadoop-daemon.sh journalnode
#### forcefence and forceactive flags not supported with auto-failover enabled.
# hdfs haadmin ¨Cfailover ¨Cforceactive nn1 nn2