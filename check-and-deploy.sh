#!/bin/bash

# SVN 변경사항 감지 및 자동 배포 스크립트
# Cron Job으로 실행하여 주기적으로 SVN 업데이트를 확인

# 설정 변수
PROJECT_DIR="$(pwd)/.."
LAST_REV_FILE="/tmp/last_svn_revision"
LOG_FILE="/tmp/eri-auto-deploy.log"

# 로그 함수
log() {
    echo "[$(date '+%Y-%m-%d %H:%M:%S')] $1" | tee -a $LOG_FILE
}

# SVN 저장소인지 확인
if [ ! -d "$PROJECT_DIR/.svn" ]; then
    log "ERROR: SVN 저장소를 찾을 수 없습니다: $PROJECT_DIR/.svn"
    exit 1
fi

cd $PROJECT_DIR

# 현재 리비전 확인
CURRENT_REV=$(svn info --show-item revision 2>/dev/null || echo "0")

# 이전 리비전 확인
if [ -f "$LAST_REV_FILE" ]; then
    LAST_REV=$(cat $LAST_REV_FILE)
else
    LAST_REV=0
fi

log "현재 리비전: $CURRENT_REV, 이전 리비전: $LAST_REV"

# 리비전이 변경되었으면 배포
if [ "$CURRENT_REV" -gt "$LAST_REV" ]; then
    log "새로운 커밋 감지: $CURRENT_REV"
    log "자동 배포를 시작합니다..."
    
    # 배포 스크립트 실행
    DEPLOY_SCRIPT="$PROJECT_DIR/ERI/deploy-tomcat.sh"
    if [ -f "$DEPLOY_SCRIPT" ]; then
        cd "$PROJECT_DIR/ERI"
        ./deploy-tomcat.sh
        if [ $? -eq 0 ]; then
            log "자동 배포 성공!"
            echo $CURRENT_REV > $LAST_REV_FILE
        else
            log "자동 배포 실패!"
        fi
    else
        log "ERROR: deploy-tomcat.sh 스크립트를 찾을 수 없습니다."
    fi
else
    log "새로운 커밋이 없습니다."
fi 