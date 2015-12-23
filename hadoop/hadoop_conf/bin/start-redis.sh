#!/bin/ksh
for i in `ls -a $REDIS_HOME/redis*.conf`
do
   echo "start redis: redis-server $i"
   redis-server $i
done
