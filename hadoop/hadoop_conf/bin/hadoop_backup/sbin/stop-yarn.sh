#!/usr/bin/env bash

# Licensed to the Apache Software Foundation (ASF) under one or more
# contributor license agreements.  See the NOTICE file distributed with
# this work for additional information regarding copyright ownership.
# The ASF licenses this file to You under the Apache License, Version 2.0
# (the "License"); you may not use this file except in compliance with
# the License.  You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.


# Stop all yarn daemons.  Run this on master node.
###
function printTrace()
{
  if [ "${HADOOP_DEBUG}" == "true" ]
  then
    ENTER_FUNCTION="Entering"
    if [ ${ENTER_FUNCTION} == `echo "${0}" |cut -d" " -f1` ]
    then
       echo "++++++, $*"
    else
       echo "------, $*" 
    fi
    
  fi
}

printTrace "Entering $0"

echo "stopping yarn daemons"

printTrace "BASH_SOURCE=${BASH_SOURCE}"

bin=`dirname "${BASH_SOURCE-$0}"`
bin=`cd "$bin"; pwd`

DEFAULT_LIBEXEC_DIR="$bin"/../libexec
HADOOP_LIBEXEC_DIR=${HADOOP_LIBEXEC_DIR:-$DEFAULT_LIBEXEC_DIR}

printTrace "bin=${bin}, HADOOP_LIBEXEC_DIR=${HADOOP_LIBEXEC_DIR}"
printTrace  "Executeing . $HADOOP_LIBEXEC_DIR/yarn-config.sh"
. $HADOOP_LIBEXEC_DIR/yarn-config.sh
printTrace ""
printTrace "-------Stop components--------------"
printTrace ""
# stop resourceManager
printTrace "$bin/yarn-daemon.sh --config $YARN_CONF_DIR  stop resourcemanager"
"$bin"/yarn-daemon.sh --config $YARN_CONF_DIR  stop resourcemanager

# stop nodeManager
printTrace "$bin/yarn-daemon.sh --config $YARN_CONF_DIR  stop nodemanager"
for slave in `cat ${HADOOP_HOME}/etc/hadoop/slaves`
do
  echo "ssh ${slave} ${bin}/yarn-daemon.sh --config $YARN_CONF_DIR  stop nodemanager" 
  ssh ${slave} ${bin}/yarn-daemon.sh --config $YARN_CONF_DIR  stop nodemanager
done

# stop proxy server
printTrace "$bin/yarn-daemon.sh --config $YARN_CONF_DIR  stop proxyserver"
"$bin"/yarn-daemon.sh --config $YARN_CONF_DIR  stop proxyserver
