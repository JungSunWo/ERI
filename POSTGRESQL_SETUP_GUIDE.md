# PostgreSQL 설치 및 설정 가이드

## 개요
ERI 프로젝트를 위한 로컬 PostgreSQL 데이터베이스 설치 및 설정 방법입니다.

## 1. PostgreSQL 설치

### Ubuntu/Debian
```bash
# 패키지 업데이트
sudo apt update

# PostgreSQL 설치
sudo apt install -y postgresql postgresql-contrib

# 서비스 시작
sudo systemctl start postgresql
sudo systemctl enable postgresql
```

### CentOS/RHEL
```bash
# PostgreSQL 설치
sudo yum install -y postgresql-server postgresql-contrib

# 데이터베이스 초기화
sudo postgresql-setup initdb

# 서비스 시작
sudo systemctl start postgresql
sudo systemctl enable postgresql
```

## 2. 데이터베이스 및 사용자 생성

```bash
# PostgreSQL 관리자로 접속
sudo -u postgres psql

# 사용자 생성
CREATE USER nicdb WITH PASSWORD 'Nicdb2023!';

# 데이터베이스 생성
CREATE DATABASE eri_db OWNER nicdb;
CREATE DATABASE eri_enc_db OWNER nicdb;

# 권한 부여
GRANT ALL PRIVILEGES ON DATABASE eri_db TO nicdb;
GRANT ALL PRIVILEGES ON DATABASE eri_enc_db TO nicdb;

# 종료
\q
```

## 3. PostgreSQL 설정 수정

### 외부 접근 허용
```bash
# postgresql.conf 수정
sudo nano /etc/postgresql/*/main/postgresql.conf
# listen_addresses = '*' 추가

# pg_hba.conf 수정
sudo nano /etc/postgresql/*/main/pg_hba.conf
# 다음 라인 추가:
# host    all             nicdb           127.0.0.1/32            md5
# host    all             nicdb           ::1/128                 md5

# PostgreSQL 재시작
sudo systemctl restart postgresql
```

## 4. 데이터베이스 스키마 생성

### 자동 스크립트 실행
```bash
# 스크립트 실행 권한 부여
chmod +x setup-postgresql.sh
chmod +x init-database.sh

# PostgreSQL 설치 및 설정
./setup-postgresql.sh

# 데이터베이스 초기화
./init-database.sh
```

### 수동 실행
```bash
# 메인 데이터베이스 스키마 생성
PGPASSWORD=Nicdb2023! psql -h localhost -U nicdb -d eri_db -f database/ERI_DDL_PostgreSQL.sql

# 암호화 데이터베이스 스키마 생성
PGPASSWORD=Nicdb2023! psql -h localhost -U nicdb -d eri_enc_db -f database/ERI_ENCRYPTION_DDL_PostgreSQL.sql

# Spring Batch 메타데이터 테이블 생성
PGPASSWORD=Nicdb2023! psql -h localhost -U nicdb -d eri_db -f database/SPRING_BATCH_METADATA_PostgreSQL.sql
```

## 5. 연결 테스트

### 명령어로 테스트
```bash
# 메인 데이터베이스 연결 테스트
PGPASSWORD=Nicdb2023! psql -h localhost -U nicdb -d eri_db -c "SELECT version();"

# 암호화 데이터베이스 연결 테스트
PGPASSWORD=Nicdb2023! psql -h localhost -U nicdb -d eri_enc_db -c "SELECT version();"
```

### 웹 인터페이스로 테스트
```
http://localhost:8080/db-test
```

## 6. 데이터베이스 정보

| 항목 | 값 |
|------|-----|
| 메인 데이터베이스 | eri_db |
| 암호화 데이터베이스 | eri_enc_db |
| 호스트 | localhost |
| 포트 | 5432 |
| 사용자 | nicdb |
| 비밀번호 | Nicdb2023! |

## 7. 문제 해결

### 연결 실패 시
1. PostgreSQL 서비스 상태 확인: `sudo systemctl status postgresql`
2. 포트 확인: `sudo netstat -tulpn | grep 5432`
3. 로그 확인: `sudo tail -f /var/log/postgresql/postgresql-*.log`

### 권한 오류 시
1. 사용자 권한 확인: `sudo -u postgres psql -c "\du"`
2. 데이터베이스 권한 확인: `sudo -u postgres psql -c "\l"`

## 8. 환경 변수 설정 (선택사항)

애플리케이션 실행 시 환경 변수로 데이터베이스 정보를 설정할 수 있습니다:

```bash
export SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/eri_db
export SPRING_DATASOURCE_USERNAME=nicdb
export SPRING_DATASOURCE_PASSWORD=Nicdb2023!
export SPRING_DATASOURCE_ENCRYPTION_URL=jdbc:postgresql://localhost:5432/eri_enc_db
export SPRING_DATASOURCE_ENCRYPTION_USERNAME=nicdb
export SPRING_DATASOURCE_ENCRYPTION_PASSWORD=Nicdb2023!
``` 