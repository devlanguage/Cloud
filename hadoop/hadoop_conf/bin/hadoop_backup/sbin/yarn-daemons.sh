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
# Run a Yarn command on all slave hosts.
printTrace "Entering yarn-daemons.sh. $*"

usage="Usage: yarn-daemons.sh [--config confdir] [--hosts hostlistfile] [start
|stop] command args..."

# if no args specified, show usage
if [ $# -le 1 ]; then
  echo $usage
  exit 1
fi

bin=`dirname "${BASH_SOURCE-$0}"`
bin=`cd "$bin"; pwd`

DEFAULT_LIBEXEC_DIR="$bin"/../libexec
HADOOP_LIBEXEC_DIR=${HADOOP_LIBEXEC_DIR:-$DEFAULT_LIBEXEC_DIR}
. $HADOOP_LIBEXEC_DIR/yarn-config.sh

printTrace "Executeing: exec \"$bin/slaves.sh\" --config $YARN_CONF_DIR cd \"$HADOOP_YARN_HOME\" \\; \"$bin/yarn-daemon.sh\" --config $YARN_CONF_DIR \"$@\" "
exec "$bin/slaves.sh" --config $YARN_CONF_DIR cd "$HADOOP_YARN_HOME" \; "$bin/yarn-daemon.sh" --config $YARN_CONF_DIR "$@"

