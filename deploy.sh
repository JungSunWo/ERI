#!/bin/bash

# ERI 프로젝트 자동 배포 스크립트
# 사용법: ./deploy.sh

# 설정 변수
PROJECT_DIR="$(pwd)"
TOMCAT_HOME="/usr/share/tomcat9"
WAR_FILE="ERI-0.0.1-SNAPSHOT.war"
APP_NAME="eri"
LOG_FILE="/tmp/eri-deploy.log"

# 로그 함수
log() {
    echo "[$(date '+%Y-%m-%d %H:%M:%S')] $1" | tee -a $LOG_FILE
}

# 에러 처리
set -e

log "=== ERI 프로젝트 배포 시작 ==="

# 1. SVN 업데이트
log "1. SVN 업데이트 중..."
if [ -d ".svn" ]; then
    svn update
    log "SVN 업데이트 완료"
else
    log "SVN 저장소가 아닙니다. 건너뜁니다."
fi

# 2. Maven 빌드
log "2. Maven 빌드 중..."
mvn clean package -Pprod -DskipTests
log "Maven 빌드 완료"

# 3. 톰캣 서비스 상태 확인
log "3. 톰캣 서비스 상태 확인 중..."
if systemctl is-active --quiet tomcat9; then
    log "톰캣이 실행 중입니다. 중지합니다."
    sudo systemctl stop tomcat9
    sleep 5
else
    log "톰캣이 이미 중지되어 있습니다."
fi

# 4. 기존 WAR 파일 백업
log "4. 기존 WAR 파일 백업 중..."
if [ -f "$TOMCAT_HOME/webapps/$WAR_FILE" ]; then
    BACKUP_NAME="${WAR_FILE}.backup.$(date +%Y%m%d_%H%M%S)"
    sudo mv "$TOMCAT_HOME/webapps/$WAR_FILE" "$TOMCAT_HOME/webapps/$BACKUP_NAME"
    log "백업 완료: $BACKUP_NAME"
else
    log "기존 WAR 파일이 없습니다."
fi

# 5. 새 WAR 파일 복사
log "5. 새 WAR 파일 복사 중..."
if [ -f "$PROJECT_DIR/target/$WAR_FILE" ]; then
    sudo cp "$PROJECT_DIR/target/$WAR_FILE" "$TOMCAT_HOME/webapps/"
    log "WAR 파일 복사 완료"
else
    log "ERROR: WAR 파일을 찾을 수 없습니다: $PROJECT_DIR/target/$WAR_FILE"
    exit 1
fi

# 6. 톰캣 시작
log "6. 톰캣 시작 중..."
sudo systemctl start tomcat9
log "톰캣 시작 완료"

# 7. 배포 확인
log "7. 배포 확인 중..."
sleep 15

# 헬스체크 시도
for i in {1..10}; do
    if curl -f http://localhost:8080/$APP_NAME/actuator/health > /dev/null 2>&1; then
        log "=== 배포 성공! ==="
        log "애플리케이션 URL: http://localhost:8080/$APP_NAME/"
        exit 0
    else
        log "배포 확인 시도 $i/10..."
        sleep 5
    fi
done

# 배포 실패 시 로그 출력
log "=== 배포 실패! 로그를 확인하세요. ==="
log "톰캣 로그 (마지막 50줄):"
sudo tail -n 50 $TOMCAT_HOME/logs/catalina.out | tee -a $LOG_FILE

exit 1 