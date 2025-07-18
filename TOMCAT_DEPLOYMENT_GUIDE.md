# ERI 프로젝트 톰캣 배포 가이드

## 개요
이 문서는 ERI 프로젝트를 Apache Tomcat에 배포하는 방법을 설명합니다.

## 사전 요구사항

### 1. 톰캣 설치
```bash
# 톰캣 다운로드 (예: Tomcat 10.0)
wget https://downloads.apache.org/tomcat/tomcat-10/v10.0.27/bin/apache-tomcat-10.0.27.tar.gz

# 압축 해제
tar -xzf apache-tomcat-10.0.27.tar.gz

# 톰캣 디렉토리 이동
sudo mv apache-tomcat-10.0.27 /opt/tomcat

# 권한 설정
sudo chown -R $USER:$USER /opt/tomcat
chmod +x /opt/tomcat/bin/*.sh
```

### 2. Java 설치 확인
```bash
java -version
# Java 17 이상 필요
```

### 3. Maven 설치 확인
```bash
mvn -version
```

## 배포 방법

### 1. 자동 배포 (권장)
```bash
# 배포 스크립트 실행
./deploy-tomcat.sh
```

### 2. 수동 배포
```bash
# 1. 프로젝트 빌드
mvn clean package -Pprod -DskipTests

# 2. 톰캣 중지
/opt/tomcat/bin/shutdown.sh

# 3. 기존 애플리케이션 제거
rm -rf /opt/tomcat/webapps/eri
rm -f /opt/tomcat/webapps/eri.war

# 4. WAR 파일 복사
cp target/ERI-0.0.1-SNAPSHOT.war /opt/tomcat/webapps/eri.war

# 5. 톰캣 시작
/opt/tomcat/bin/startup.sh
```

## 설정 파일

### 1. 톰캣 JVM 설정
`/opt/tomcat/bin/setenv.sh` 파일 생성:
```bash
#!/bin/bash
export JAVA_OPTS="-Xms512m -Xmx1024m -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=256m"
export CATALINA_OPTS="-Dspring.profiles.active=prod"
```

### 2. 애플리케이션 설정
`src/main/resources/application-prod.properties` 확인:
```properties
# 데이터베이스 설정
spring.datasource.url=jdbc:postgresql://localhost:5432/eri_db
spring.datasource.username=eri_user
spring.datasource.password=eri_password

# 로깅 설정
logging.level.com.ERI=INFO
logging.file.name=/opt/tomcat/logs/eri-app.log
```

## 배포 확인

### 1. 애플리케이션 접속
- URL: `http://localhost:8080/eri/`
- 헬스체크: `http://localhost:8080/eri/actuator/health`

### 2. 로그 확인
```bash
# 톰캣 로그
tail -f /opt/tomcat/logs/catalina.out

# 애플리케이션 로그
tail -f /opt/tomcat/logs/localhost.log

# 배포 로그
tail -f /tmp/eri-tomcat-deploy.log
```

## 문제 해결

### 1. 포트 충돌
```bash
# 포트 사용 확인
netstat -tlnp | grep 8080

# 프로세스 종료
sudo kill -9 <PID>
```

### 2. 권한 문제
```bash
# 톰캣 디렉토리 권한 확인
ls -la /opt/tomcat/

# 권한 수정
sudo chown -R $USER:$USER /opt/tomcat
chmod +x /opt/tomcat/bin/*.sh
```

### 3. 메모리 부족
```bash
# JVM 메모리 설정 수정
export JAVA_OPTS="-Xms1024m -Xmx2048m"
```

## 주의사항

1. **프로덕션 환경**: 실제 운영 환경에서는 보안 설정을 추가하세요.
2. **백업**: 배포 전 기존 애플리케이션을 백업하세요.
3. **로깅**: 적절한 로그 레벨을 설정하세요.
4. **모니터링**: 애플리케이션 상태를 모니터링하세요.

## 추가 설정

### 1. 톰캣 서비스 등록 (선택사항)
```bash
# systemd 서비스 파일 생성
sudo nano /etc/systemd/system/tomcat.service

[Unit]
Description=Apache Tomcat
After=network.target

[Service]
Type=forking
User=your_user
Group=your_group
Environment=JAVA_HOME=/usr/lib/jvm/java-17-openjdk
Environment=CATALINA_HOME=/opt/tomcat
ExecStart=/opt/tomcat/bin/startup.sh
ExecStop=/opt/tomcat/bin/shutdown.sh
Restart=on-failure

[Install]
WantedBy=multi-user.target

# 서비스 활성화
sudo systemctl enable tomcat
sudo systemctl start tomcat
```

### 2. 방화벽 설정
```bash
# 8080 포트 허용
sudo ufw allow 8080
``` 