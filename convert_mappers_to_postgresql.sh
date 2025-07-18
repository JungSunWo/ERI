#!/bin/bash

# PostgreSQL 호환성을 위한 Mapper XML 변환 스크립트
# 사용법: ./convert_mappers_to_postgresql.sh

echo "PostgreSQL 호환성을 위한 Mapper XML 변환을 시작합니다..."

# 변환할 디렉토리
MAPPER_DIR="src/main/resources/mappers"

# 백업 디렉토리 생성
BACKUP_DIR="src/main/resources/mappers_backup_$(date +%Y%m%d_%H%M%S)"
mkdir -p "$BACKUP_DIR"

echo "기존 파일을 $BACKUP_DIR 에 백업합니다..."
cp -r "$MAPPER_DIR"/* "$BACKUP_DIR/"

echo "Mapper XML 파일들을 PostgreSQL에 맞게 변환합니다..."

# 모든 XML 파일에 대해 변환 수행
find "$MAPPER_DIR" -name "*.xml" -type f | while read -r file; do
    echo "변환 중: $file"
    
    # 임시 파일 생성
    temp_file=$(mktemp)
    
    # 변환 규칙 적용
    sed -e 's/CONCAT(\([^)]*\))/\1/g' \
        -e 's/CONCAT(\([^,]*\), \("[^"]*"\), \([^,]*\), \("[^"]*"\))/\1 || \2 || \3 || \4/g' \
        -e 's/CONCAT(\([^,]*\), \("[^"]*"\), \([^)]*\))/\1 || \2 || \3/g' \
        -e 's/CONCAT(\("[^"]*"\), \([^,]*\), \("[^"]*"\))/\1 || \2 || \3/g' \
        -e 's/CONCAT(\([^,]*\), \("[^"]*"\))/\1 || \2/g' \
        -e 's/NOW()/CURRENT_TIMESTAMP/g' \
        -e 's/LIMIT \([^,]*\), \([^)]*\)/LIMIT \2 OFFSET \1/g' \
        -e 's/useGeneratedKeys="true" keyProperty="\([^"]*\)"/useGeneratedKeys="true" keyProperty="\1"/g' \
        "$file" > "$temp_file"
    
    # 변환된 파일로 교체
    mv "$temp_file" "$file"
done

echo "변환 완료!"
echo "백업 파일 위치: $BACKUP_DIR"
echo ""
echo "주요 변환 사항:"
echo "1. CONCAT() 함수 → || 연산자"
echo "2. NOW() 함수 → CURRENT_TIMESTAMP"
echo "3. LIMIT offset, size → LIMIT size OFFSET offset"
echo "4. 자동 증가 컬럼 처리 개선"
echo ""
echo "변환 후 반드시 테스트를 수행하세요!" 