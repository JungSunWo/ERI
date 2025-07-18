#!/bin/bash

# 톰캣 배포 설정 파일
# 이 파일을 수정하여 환경에 맞게 설정하세요

# 톰캣 설치 경로 (필요시 수정)
export TOMCAT_HOME="/opt/apache-tomcat-10.1.19"

# 애플리케이션 설정
export APP_NAME="eri"
export CONTEXT_PATH="eri"
export APP_PORT="8080"

# JVM 설정 (톰캣의 setenv.sh에 추가할 내용)
export JAVA_OPTS="-Xms512m -Xmx1024m -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=256m"

# 데이터베이스 설정 (실제 운영 환경에 맞게 수정)
export DB_HOST="172.18.16.1"
export DB_PORT="5432"
export DB_NAME="eri_db"
export DB_USER="nicdb"
export DB_PASSWORD="Nicdb2023!"

# 암호화 데이터베이스 설정
export ENC_DB_HOST="172.18.16.1"
export ENC_DB_PORT="5432"
export ENC_DB_NAME="eri_enc_db"
export ENC_DB_USER="nicdb"
export ENC_DB_PASSWORD="Nicdb2023!"

# 로그 설정
export LOG_LEVEL="INFO"
export LOG_FILE="/tmp/eri-tomcat-deploy.log"

echo "톰캣 배포 설정이 로드되었습니다."
echo "TOMCAT_HOME: $TOMCAT_HOME"
echo "CONTEXT_PATH: $CONTEXT_PATH"
echo "APP_PORT: $APP_PORT" 