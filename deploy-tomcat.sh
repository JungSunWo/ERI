#!/bin/bash

# ERI 프로젝트 톰캣 배포 스크립트
# 사용법: ./deploy-tomcat.sh

# 설정 파일 로드
if [ -f "tomcat-deploy-config.sh" ]; then
    source tomcat-deploy-config.sh
else
    echo "WARNING: tomcat-deploy-config.sh 파일을 찾을 수 없습니다. 기본 설정을 사용합니다."
fi

# 설정 변수 (기본값)
PROJECT_DIR="$(pwd)"
WAR_FILE="ERI-0.0.1-SNAPSHOT.war"
APP_NAME="${APP_NAME:-eri}"
LOG_FILE="${LOG_FILE:-/tmp/eri-tomcat-deploy.log}"
TOMCAT_HOME="${TOMCAT_HOME:-/opt/apache-tomcat-10.1.19}"
TOMCAT_WEBAPPS="$TOMCAT_HOME/webapps"
TOMCAT_BIN="$TOMCAT_HOME/bin"
APP_PORT="${APP_PORT:-8080}"
CONTEXT_PATH="${CONTEXT_PATH:-eri}"

# 로그 함수
log() {
    echo "[$(date '+%Y-%m-%d %H:%M:%S')] $1" | tee -a $LOG_FILE
}

# 에러 처리
set -e

log "=== ERI 프로젝트 톰캣 배포 시작 ==="

# 톰캣 설치 확인
if [ ! -d "$TOMCAT_HOME" ]; then
    log "ERROR: 톰캣이 설치되지 않았습니다. TOMCAT_HOME을 확인하세요: $TOMCAT_HOME"
    exit 1
fi

# 권한 확인 및 수정
log "0. 권한 확인 및 수정 중..."
if [ ! -w "$TOMCAT_WEBAPPS" ]; then
    log "톰캣 webapps 디렉토리 권한을 수정합니다."
    sudo chown -R $USER:$USER "$TOMCAT_WEBAPPS"
fi

# 로그 파일 권한 확인
if [ ! -w "$LOG_FILE" ]; then
    log "로그 파일 권한을 수정합니다."
    sudo touch "$LOG_FILE" 2>/dev/null || true
    sudo chown $USER:$USER "$LOG_FILE" 2>/dev/null || true
fi

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
mvn clean package -DskipTests
log "Maven 빌드 완료"

# 3. WAR 파일 확인
if [ ! -f "$PROJECT_DIR/target/$WAR_FILE" ]; then
    log "ERROR: WAR 파일을 찾을 수 없습니다: $PROJECT_DIR/target/$WAR_FILE"
    exit 1
fi

# 4. 톰캣 중지 (systemctl 우선, 직접 실행 백업)
log "4. 톰캣 중지 중..."
if systemctl is-active --quiet tomcat10 2>/dev/null; then
    log "systemctl을 사용하여 톰캣을 중지합니다."
    sudo systemctl stop tomcat10
    sleep 10
elif [ -f "$TOMCAT_BIN/shutdown.sh" ]; then
    log "직접 shutdown.sh를 사용하여 톰캣을 중지합니다."
    sudo "$TOMCAT_BIN/shutdown.sh"
    sleep 10
else
    log "WARNING: 톰캣 중지 방법을 찾을 수 없습니다."
fi

# 톰캣 프로세스 강제 종료 확인
TOMCAT_PID=$(ps aux | grep tomcat | grep -v grep | awk '{print $2}' | head -1)
if [ ! -z "$TOMCAT_PID" ]; then
    log "톰캣 프로세스($TOMCAT_PID)를 강제 종료합니다."
    sudo kill -9 $TOMCAT_PID
    sleep 5
fi

# 5. 기존 애플리케이션 제거
log "5. 기존 애플리케이션 제거 중..."
if [ -d "$TOMCAT_WEBAPPS/$CONTEXT_PATH" ]; then
    sudo rm -rf "$TOMCAT_WEBAPPS/$CONTEXT_PATH"
    log "기존 애플리케이션 디렉토리 제거 완료"
fi

if [ -f "$TOMCAT_WEBAPPS/$CONTEXT_PATH.war" ]; then
    sudo rm -f "$TOMCAT_WEBAPPS/$CONTEXT_PATH.war"
    log "기존 WAR 파일 제거 완료"
fi

# 6. WAR 파일 복사
log "6. WAR 파일 복사 중..."
sudo cp "$PROJECT_DIR/target/$WAR_FILE" "$TOMCAT_WEBAPPS/$CONTEXT_PATH.war"
log "WAR 파일 복사 완료: $TOMCAT_WEBAPPS/$CONTEXT_PATH.war"

# 7. 톰캣 시작 (systemctl 우선, 직접 실행 백업)
log "7. 톰캣 시작 중..."
if command -v systemctl >/dev/null 2>&1 && systemctl list-unit-files | grep -q tomcat10; then
    log "systemctl을 사용하여 톰캣을 시작합니다."
    sudo systemctl start tomcat10
    log "톰캣 시작 완료"
elif [ -f "$TOMCAT_BIN/startup.sh" ]; then
    log "직접 startup.sh를 사용하여 톰캣을 시작합니다."
    sudo "$TOMCAT_BIN/startup.sh"
    log "톰캣 시작 완료"
else
    log "ERROR: 톰캣 시작 방법을 찾을 수 없습니다."
    exit 1
fi

# 8. 배포 확인
log "8. 배포 확인 중..."
sleep 20

# 헬스체크 시도
for i in {1..15}; do
    if curl -f "http://localhost:$APP_PORT/$CONTEXT_PATH/actuator/health" > /dev/null 2>&1; then
        log "=== 톰캣 배포 성공! ==="
        log "애플리케이션 URL: http://localhost:$APP_PORT/$CONTEXT_PATH/"
        log "톰캣 로그: $TOMCAT_HOME/logs/catalina.out"
        log "배포 로그: $LOG_FILE"
        exit 0
    else
        log "배포 확인 시도 $i/15..."
        sleep 5
    fi
done

# 배포 실패 시 톰캣 로그 출력
log "=== 톰캣 배포 실패! 로그를 확인하세요. ==="
if [ -f "$TOMCAT_HOME/logs/catalina.out" ]; then
    log "톰캣 로그 (마지막 50줄):"
    sudo tail -n 50 "$TOMCAT_HOME/logs/catalina.out" | tee -a $LOG_FILE
fi

if [ -f "$TOMCAT_HOME/logs/localhost.log" ]; then
    log "애플리케이션 로그 (마지막 30줄):"
    sudo tail -n 30 "$TOMCAT_HOME/logs/localhost.log" | tee -a $LOG_FILE
fi

exit 1 