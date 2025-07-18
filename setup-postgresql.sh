#!/bin/bash

# PostgreSQL 설치 및 설정 스크립트
# ERI 프로젝트용 로컬 PostgreSQL 설정

echo "=== PostgreSQL 설치 및 설정 시작 ==="

# 1. PostgreSQL 설치
echo "1. PostgreSQL 설치 중..."
sudo apt update
sudo apt install -y postgresql postgresql-contrib

# 2. PostgreSQL 서비스 시작
echo "2. PostgreSQL 서비스 시작 중..."
sudo systemctl start postgresql
sudo systemctl enable postgresql

# 3. PostgreSQL 상태 확인
echo "3. PostgreSQL 서비스 상태 확인..."
sudo systemctl status postgresql

# 4. 데이터베이스 및 사용자 생성
echo "4. 데이터베이스 및 사용자 생성 중..."
sudo -u postgres psql << EOF
-- 사용자 생성
CREATE USER nicdb WITH PASSWORD 'Nicdb2023!';

-- 메인 데이터베이스 생성
CREATE DATABASE eri_db OWNER nicdb;

-- 암호화 데이터베이스 생성
CREATE DATABASE eri_enc_db OWNER nicdb;

-- 권한 부여
GRANT ALL PRIVILEGES ON DATABASE eri_db TO nicdb;
GRANT ALL PRIVILEGES ON DATABASE eri_enc_db TO nicdb;

-- 연결 확인
\l
\q
EOF

# 5. PostgreSQL 설정 파일 수정 (외부 접근 허용)
echo "5. PostgreSQL 설정 수정 중..."
sudo sed -i "s/#listen_addresses = 'localhost'/listen_addresses = '*'/" /etc/postgresql/*/main/postgresql.conf

# 6. 클라이언트 인증 설정
echo "6. 클라이언트 인증 설정 중..."
sudo tee -a /etc/postgresql/*/main/pg_hba.conf << EOF

# ERI 애플리케이션 접근 허용
host    all             nicdb           127.0.0.1/32            md5
host    all             nicdb           ::1/128                 md5
EOF

# 7. PostgreSQL 재시작
echo "7. PostgreSQL 재시작 중..."
sudo systemctl restart postgresql

# 8. 연결 테스트
echo "8. 연결 테스트 중..."
PGPASSWORD=Nicdb2023! psql -h localhost -U nicdb -d eri_db -c "SELECT version();"
PGPASSWORD=Nicdb2023! psql -h localhost -U nicdb -d eri_enc_db -c "SELECT version();"

echo "=== PostgreSQL 설치 및 설정 완료 ==="
echo "데이터베이스 정보:"
echo "- 메인 DB: eri_db (localhost:5432)"
echo "- 암호화 DB: eri_enc_db (localhost:5432)"
echo "- 사용자: nicdb"
echo "- 비밀번호: Nicdb2023!" 