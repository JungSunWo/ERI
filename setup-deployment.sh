#!/bin/bash

# ERI 프로젝트 배포 환경 자동 설정 스크립트
# 우분투 서버에서 실행하여 필요한 환경을 자동으로 구성

set -e

echo "=== ERI 프로젝트 배포 환경 설정 시작 ==="

# 1. 필요한 패키지 설치
echo "1. 필요한 패키지 설치 중..."
sudo apt update
sudo apt install -y openjdk-17-jdk maven tomcat9 subversion curl

# 2. 환경 변수 설정
echo "2. 환경 변수 설정 중..."
if ! grep -q "JAVA_HOME" ~/.bashrc; then
    echo 'export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64' >> ~/.bashrc
    echo 'export CATALINA_HOME=/usr/share/tomcat9' >> ~/.bashrc
    echo 'export PATH=$PATH:$JAVA_HOME/bin:$CATALINA_HOME/bin' >> ~/.bashrc
    source ~/.bashrc
fi

# 3. 톰캣 사용자 설정
echo "3. 톰캣 사용자 설정 중..."
if ! id "tomcat" &>/dev/null; then
    sudo useradd -r -s /bin/false tomcat
fi

# 4. 프로젝트 디렉토리 권한 설정
echo "4. 프로젝트 디렉토리 권한 설정 중..."
PROJECT_DIR="$(pwd)"
sudo chown -R tomcat:tomcat $PROJECT_DIR
sudo chmod -R 755 $PROJECT_DIR

# 5. 톰캣 설정 파일 백업 및 수정
echo "5. 톰캣 설정 파일 수정 중..."
sudo cp /etc/tomcat9/server.xml /etc/tomcat9/server.xml.backup

# Context 설정 추가
if ! grep -q "path=\"/eri\"" /etc/tomcat9/server.xml; then
    sudo sed -i '/<Host name="localhost"/a\    <Context path="/eri" docBase="'$PROJECT_DIR'/target/ERI-0.0.1-SNAPSHOT.war" />' /etc/tomcat9/server.xml
fi

# 6. 톰캣 메모리 설정
echo "6. 톰캣 메모리 설정 중..."
sudo tee -a /etc/default/tomcat9 << EOF

# ERI 프로젝트 메모리 설정
JAVA_OPTS="-Xms512m -Xmx1024m -XX:MaxPermSize=256m"
EOF

# 7. 스크립트 실행 권한 부여
echo "7. 스크립트 실행 권한 부여 중..."
chmod +x deploy.sh
chmod +x check-and-deploy.sh

# 8. Actuator 의존성 추가 확인
echo "8. Actuator 의존성 확인 중..."
if ! grep -q "spring-boot-starter-actuator" pom.xml; then
    echo "Actuator 의존성을 추가해야 합니다."
    echo "pom.xml에 다음을 추가하세요:"
    echo "<dependency>"
    echo "    <groupId>org.springframework.boot</groupId>"
    echo "    <artifactId>spring-boot-starter-actuator</artifactId>"
    echo "</dependency>"
fi

# 9. Cron Job 설정
echo "9. Cron Job 설정 중..."
CRON_JOB="*/5 * * * * cd $PROJECT_DIR && ./check-and-deploy.sh"

if ! crontab -l 2>/dev/null | grep -q "check-and-deploy.sh"; then
    (crontab -l 2>/dev/null; echo "$CRON_JOB") | crontab -
    echo "Cron Job이 설정되었습니다: 5분마다 SVN 업데이트 확인"
else
    echo "Cron Job이 이미 설정되어 있습니다."
fi

# 10. 방화벽 설정
echo "10. 방화벽 설정 중..."
sudo ufw allow 8080/tcp
sudo ufw allow 22/tcp
sudo ufw --force enable

# 11. 톰캣 서비스 시작
echo "11. 톰캣 서비스 시작 중..."
sudo systemctl enable tomcat9
sudo systemctl start tomcat9

# 12. 설정 완료 메시지
echo ""
echo "=== 배포 환경 설정 완료! ==="
echo ""
echo "다음 단계를 수행하세요:"
echo "1. pom.xml에 Actuator 의존성 추가 (필요시)"
echo "2. application.properties에 Actuator 설정 추가:"
echo "   management.endpoints.web.exposure.include=health,info"
echo "   management.endpoint.health.show-details=always"
echo "3. 수동으로 첫 배포 실행: ./deploy.sh"
echo "4. 애플리케이션 확인: http://localhost:8080/eri/"
echo ""
echo "자동 배포는 5분마다 SVN 변경사항을 확인합니다."
echo "로그 확인: tail -f /tmp/eri-auto-deploy.log" 