#!/bin/bash

# 톰캣 환경변수 설정 파일
# 이 파일을 /opt/apache-tomcat-10.1.19/bin/setenv.sh로 복사하여 사용

# JVM 메모리 설정
export JAVA_OPTS="-Xms512m -Xmx1024m -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=256m"

# Spring Boot 프로파일 설정 (개발 환경)
export CATALINA_OPTS="-Dspring.profiles.active=dev"

# 데이터베이스 환경변수 설정 (보안을 위해 환경변수로 관리)
# 개발 환경: 172.18.16.1
export SPRING_DATASOURCE_URL="jdbc:postgresql://172.18.16.1:5432/eri_db"
export SPRING_DATASOURCE_USERNAME="nicdb"
export SPRING_DATASOURCE_PASSWORD="Nicdb2023!"

# 암호화 데이터베이스 환경변수 설정
export SPRING_DATASOURCE_ENCRYPTION_URL="jdbc:postgresql://172.18.16.1:5432/eri_enc_db"
export SPRING_DATASOURCE_ENCRYPTION_USERNAME="nicdb"
export SPRING_DATASOURCE_ENCRYPTION_PASSWORD="Nicdb2023!"

# 로그 레벨 설정 (개발 환경에서는 더 상세한 로그)
export LOGGING_LEVEL_COM_ERI_DEMO="INFO"
export LOGGING_LEVEL_ROOT="INFO"

echo "톰캣 환경변수가 설정되었습니다."
echo "Spring Profile: dev"
echo "Database URL: $SPRING_DATASOURCE_URL" 