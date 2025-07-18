#!/bin/bash

# ERI 데이터베이스 초기화 스크립트
# PostgreSQL 데이터베이스 및 테이블 생성

echo "=== ERI 데이터베이스 초기화 시작 ==="

# 환경 변수 설정
DB_USER="nicdb"
DB_PASSWORD="Nicdb2023!"
MAIN_DB="eri_db"
ENCRYPT_DB="eri_enc_db"

# 1. 메인 데이터베이스 초기화
echo "1. 메인 데이터베이스 ($MAIN_DB) 초기화 중..."
PGPASSWORD=$DB_PASSWORD psql -h localhost -U $DB_USER -d $MAIN_DB -f database/ERI_DDL_PostgreSQL.sql

# 2. 암호화 데이터베이스 초기화
echo "2. 암호화 데이터베이스 ($ENCRYPT_DB) 초기화 중..."
PGPASSWORD=$DB_PASSWORD psql -h localhost -U $DB_USER -d $ENCRYPT_DB -f database/ERI_ENCRYPTION_DDL_PostgreSQL.sql

# 3. Spring Batch 메타데이터 테이블 생성
echo "3. Spring Batch 메타데이터 테이블 생성 중..."
PGPASSWORD=$DB_PASSWORD psql -h localhost -U $DB_USER -d $MAIN_DB -f database/SPRING_BATCH_METADATA_PostgreSQL.sql

# 4. 테이블 생성 확인
echo "4. 테이블 생성 확인 중..."
echo "=== 메인 데이터베이스 테이블 목록 ==="
PGPASSWORD=$DB_PASSWORD psql -h localhost -U $DB_USER -d $MAIN_DB -c "\dt"

echo "=== 암호화 데이터베이스 테이블 목록 ==="
PGPASSWORD=$DB_PASSWORD psql -h localhost -U $DB_USER -d $ENCRYPT_DB -c "\dt"

echo "=== ERI 데이터베이스 초기화 완료 ===" 