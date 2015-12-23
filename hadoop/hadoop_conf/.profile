# .bash_profile

# Get the aliases and functions
if [ -f ~/.bashrc ]; then
	. ~/.bashrc
fi

# User specific environment and startup programs
PATH=/usr/kerberos/bin:/usr/local/bin:/sbin:/bin:/usr/bin:/usr/sbin:.
HHOST=${HOSTNAME}
UHOME=$HOME
export MAVEN_HOME=$HOME/apache-maven-3.2.5
export JAVA_HOME=$UHOME/jdk1.7.0_75
export PATH=$UHOME/bin:$JAVA_HOME/bin:$MAVEN_HOME/bin:.:$PATH

HDATA=$HOME/hdata
export HADOOP_DEBUG=true

export ZOOKEEPER_HOME=$HOME/zookeeper-3.4.6
#export ZOO_DATADIR=${HDATA}/zookeeper/data
#export ZOO_LOGDIR=${HDATA}/zookeeper/log
#export ZOOCFGDIR=$ZOOKEEPER_HOME/conf

export HADOOP_HOME=$HOME/hadoop-2.5.2
export HADOOP_COMMON_HOME=$HADOOP_HOME
export HADOOP_HDFS_HOME=$HADOOP_HOME
export HADOOP_MAPRED_HOME=$HADOOP_HOME
export HADOOP_YARN_HOME=$HADOOP_HOME
export HADOOP_CONF_DIR=$HADOOP_HOME/etc/hadoop
export HADOOP_COMMON_LIB_NATIVE_DIR=$HADOOP_HOME/lib/native
export HADOOP_OPTS="-Djava.library.path=$HADOOP_HOME/lib"
export PATH=$ZOOKEEPER_HOME/bin:$HADOOP_HOME/bin:$HADOOP_HOME/sbin:$HADOOP_HOME/lib:$PATH


export HBASE_HOME=$UHOME/hbase-1.0.0
export HIVE_HOME=$UHOME/hive-1.0.0
export REDIS_HOME=$UHOME/redis3

export PATH=$HBASE_HOME/bin:$HIVE_HOME/bin:$PATH
export PS1='${USER}@${HHOST}:${PWD}> '
