#!/usr/bin/env bash
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