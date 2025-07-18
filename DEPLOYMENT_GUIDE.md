# 우분투 톰캣 스프링부트 SVN 프로젝트 배포 가이드

## 1. 사전 준비사항

### 1.1 필요한 소프트웨어 설치
```bash
# Java 17 설치
sudo apt update
sudo apt install openjdk-17-jdk

# Maven 설치
sudo apt install maven

# Tomcat 설치
sudo apt install tomcat9

# SVN 클라이언트 설치
sudo apt install subversion

# Git 설치 (선택사항)
sudo apt install git
```

### 1.2 환경 변수 설정
```bash
# ~/.bashrc 파일에 추가
export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
export CATALINA_HOME=/usr/share/tomcat9
export PATH=$PATH:$JAVA_HOME/bin:$CATALINA_HOME/bin
```

## 2. SVN 프로젝트 체크아웃 및 빌드

### 2.1 SVN 저장소 체크아웃
```bash
# 프로젝트 디렉토리 생성
mkdir -p /opt/eri-project
cd /opt/eri-project

# SVN 체크아웃
svn checkout svn://172.23.214.159/eri eri-backend
cd eri-backend
```

### 2.2 프로젝트 빌드
```bash
# Maven 빌드 (WAR 파일 생성)
mvn clean package -Pprod

# 또는 JAR 파일로 빌드
mvn clean package -Pprod -Dspring.profiles.active=dev
mvn clean package -Pprod -Dspring.profiles.active=prod
```

## 3. 톰캣 배포 설정

### 3.1 톰캣 설정 파일 수정
```bash
# 톰캣 설정 파일 편집
sudo nano /etc/tomcat9/server.xml
```

#### server.xml 설정 예시:
```xml
<Host name="localhost" appBase="webapps" unpackWARs="true" autoDeploy="true">
    <Context path="/eri" docBase="/opt/eri-project/eri-backend/target/ERI-0.0.1-SNAPSHOT.war" />
</Host>
```

### 3.2 톰캣 사용자 권한 설정
```bash
# 톰캣 사용자 생성
sudo useradd -r -s /bin/false tomcat

# 프로젝트 디렉토리 권한 설정
sudo chown -R tomcat:tomcat /opt/eri-project
sudo chmod -R 755 /opt/eri-project
```

## 4. 자동 배포 스크립트 작성

### 4.1 배포 스크립트 생성
```bash
sudo nano /opt/eri-project/deploy.sh
```

#### deploy.sh 내용:
```bash
#!/bin/bash

# 배포 스크립트
PROJECT_DIR="/opt/eri-project/eri-backend"
TOMCAT_HOME="/usr/share/tomcat9"
WAR_FILE="ERI-0.0.1-SNAPSHOT.war"
APP_NAME="eri"

echo "=== ERI 프로젝트 배포 시작 ==="

# 1. SVN 업데이트
echo "1. SVN 업데이트 중..."
cd $PROJECT_DIR
svn update

# 2. 기존 애플리케이션 중지
echo "2. 기존 애플리케이션 중지 중..."
sudo systemctl stop tomcat9

# 3. Maven 빌드
echo "3. Maven 빌드 중..."
mvn clean package -Pprod -DskipTests

# 4. 기존 WAR 파일 백업
echo "4. 기존 WAR 파일 백업 중..."
if [ -f "$TOMCAT_HOME/webapps/$WAR_FILE" ]; then
    sudo mv $TOMCAT_HOME/webapps/$WAR_FILE $TOMCAT_HOME/webapps/${WAR_FILE}.backup.$(date +%Y%m%d_%H%M%S)
fi

# 5. 새 WAR 파일 복사
echo "5. 새 WAR 파일 복사 중..."
sudo cp $PROJECT_DIR/target/$WAR_FILE $TOMCAT_HOME/webapps/

# 6. 톰캣 시작
echo "6. 톰캣 시작 중..."
sudo systemctl start tomcat9

# 7. 배포 확인
echo "7. 배포 확인 중..."
sleep 10
if curl -f http://localhost:8080/eri/actuator/health > /dev/null 2>&1; then
    echo "=== 배포 성공! ==="
else
    echo "=== 배포 실패! 로그를 확인하세요. ==="
    sudo tail -n 50 $TOMCAT_HOME/logs/catalina.out
fi
```

### 4.2 스크립트 실행 권한 부여
```bash
sudo chmod +x /opt/eri-project/deploy.sh
```

## 5. SVN 커밋 후 자동 배포 설정

### 5.1 SVN Hook 설정 (선택사항)
```bash
# SVN 저장소의 hooks 디렉토리에 post-commit 스크립트 생성
sudo nano /path/to/svn/repository/hooks/post-commit
```

#### post-commit 스크립트 내용:
```bash
#!/bin/bash
REPOS="$1"
REV="$2"

# 배포 서버에 배포 명령 전송
ssh user@deploy-server "/opt/eri-project/deploy.sh"
```

### 5.2 Cron Job 설정 (권장)
```bash
# crontab 편집
crontab -e

# 5분마다 SVN 업데이트 확인 및 배포
*/5 * * * * /opt/eri-project/check-and-deploy.sh
```

#### check-and-deploy.sh 스크립트:
```bash
#!/bin/bash

PROJECT_DIR="/opt/eri-project/eri-backend"
LAST_REV_FILE="/tmp/last_svn_revision"

cd $PROJECT_DIR

# 현재 리비전 확인
CURRENT_REV=$(svn info --show-item revision)

# 이전 리비전 확인
if [ -f "$LAST_REV_FILE" ]; then
    LAST_REV=$(cat $LAST_REV_FILE)
else
    LAST_REV=0
fi

# 리비전이 변경되었으면 배포
if [ "$CURRENT_REV" -gt "$LAST_REV" ]; then
    echo "새로운 커밋 감지: $CURRENT_REV"
    /opt/eri-project/deploy.sh
    echo $CURRENT_REV > $LAST_REV_FILE
fi
```

## 6. 모니터링 및 로그 관리

### 6.1 로그 설정
```bash
# 톰캣 로그 확인
sudo tail -f /usr/share/tomcat9/logs/catalina.out

# 애플리케이션 로그 확인
sudo tail -f /usr/share/tomcat9/logs/localhost.log
```

### 6.2 헬스체크 엔드포인트 추가
application.properties에 추가:
```properties
# Actuator 설정
management.endpoints.web.exposure.include=health,info
management.endpoint.health.show-details=always
```

## 7. 배포 확인 방법

### 7.1 웹 브라우저에서 확인
```
http://your-server-ip:8080/eri/
```

### 7.2 헬스체크 확인
```bash
curl http://localhost:8080/eri/actuator/health
```

### 7.3 로그 확인
```bash
# 톰캣 로그
sudo tail -f /usr/share/tomcat9/logs/catalina.out

# 애플리케이션 로그
sudo journalctl -u tomcat9 -f
```

## 8. 문제 해결

### 8.1 권한 문제
```bash
# 톰캣 사용자 권한 재설정
sudo chown -R tomcat:tomcat /usr/share/tomcat9
sudo chown -R tomcat:tomcat /opt/eri-project
```

### 8.2 포트 충돌
```bash
# 포트 사용 확인
sudo netstat -tlnp | grep :8080

# 톰캣 포트 변경 (필요시)
sudo nano /etc/tomcat9/server.xml
```

### 8.3 메모리 설정
```bash
# 톰캣 메모리 설정
sudo nano /etc/default/tomcat9

# 추가할 내용:
JAVA_OPTS="-Xms512m -Xmx1024m -XX:MaxPermSize=256m"
```

## 9. 보안 설정

### 9.1 방화벽 설정
```bash
# 필요한 포트만 열기
sudo ufw allow 8080/tcp
sudo ufw allow 22/tcp
sudo ufw enable
```

### 9.2 SSL 설정 (선택사항)
```bash
# SSL 인증서 설정
sudo nano /etc/tomcat9/server.xml
```

이 가이드를 따라하면 SVN 커밋 후 자동으로 소스가 반영되고 배포되는 환경을 구축할 수 있습니다. 