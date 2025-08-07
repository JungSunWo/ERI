# -*- coding: utf-8 -*-
"""
PDF 문서 텍스트 추출기
IBK클라우드 화면 스타트킷 개발 가이드 PDF를 텍스트로 변환
"""

import os
import sys
from pathlib import Path

def install_required_packages():
    """필요한 패키지 설치"""
    try:
        import PyPDF2
        import pdfplumber
        import fitz  # PyMuPDF
    except ImportError:
        print("필요한 패키지를 설치합니다...")
        os.system("pip install PyPDF2 pdfplumber PyMuPDF")

def extract_text_with_pypdf2(pdf_path):
    """PyPDF2를 사용한 텍스트 추출"""
    try:
        import PyPDF2
        text = ""
        with open(pdf_path, 'rb') as file:
            pdf_reader = PyPDF2.PdfReader(file)
            for page_num in range(len(pdf_reader.pages)):
                page = pdf_reader.pages[page_num]
                text += f"\n--- 페이지 {page_num + 1} ---\n"
                text += page.extract_text() + "\n"
        return text
    except Exception as e:
        print(f"PyPDF2 오류: {e}")
        return None

def extract_text_with_pdfplumber(pdf_path):
    """pdfplumber를 사용한 텍스트 추출 (한글 지원 우수)"""
    try:
        import pdfplumber
        text = ""
        with pdfplumber.open(pdf_path) as pdf:
            for page_num, page in enumerate(pdf.pages):
                text += f"\n--- 페이지 {page_num + 1} ---\n"
                page_text = page.extract_text()
                if page_text:
                    text += page_text + "\n"
        return text
    except Exception as e:
        print(f"pdfplumber 오류: {e}")
        return None

def extract_text_with_pymupdf(pdf_path):
    """PyMuPDF를 사용한 텍스트 추출"""
    try:
        import fitz
        text = ""
        doc = fitz.open(pdf_path)
        for page_num in range(len(doc)):
            page = doc.load_page(page_num)
            text += f"\n--- 페이지 {page_num + 1} ---\n"
            text += page.get_text() + "\n"
        doc.close()
        return text
    except Exception as e:
        print(f"PyMuPDF 오류: {e}")
        return None

def save_text_to_file(text, output_path):
    """추출된 텍스트를 파일로 저장"""
    try:
        with open(output_path, 'w', encoding='utf-8') as file:
            file.write(text)
        print(f"텍스트가 성공적으로 저장되었습니다: {output_path}")
        return True
    except Exception as e:
        print(f"파일 저장 오류: {e}")
        return False

def main():
    """메인 함수"""
    # PDF 파일 경로
    pdf_path = "docs/IBK클라우드 화면 스타트킷 개발 가이드_v1.2.pdf"
    
    # 출력 파일 경로
    output_path = "docs/IBK클라우드_화면_스타트킷_개발_가이드_텍스트.txt"
    
    # 파일 존재 확인
    if not os.path.exists(pdf_path):
        print(f"PDF 파일을 찾을 수 없습니다: {pdf_path}")
        return
    
    print("PDF 텍스트 추출을 시작합니다...")
    
    # 여러 방법으로 텍스트 추출 시도
    text = None
    
    # 1. pdfplumber 시도 (한글 지원 우수)
    print("pdfplumber로 텍스트 추출 중...")
    text = extract_text_with_pdfplumber(pdf_path)
    
    # 2. PyMuPDF 시도
    if not text or len(text.strip()) < 100:
        print("PyMuPDF로 텍스트 추출 중...")
        text = extract_text_with_pymupdf(pdf_path)
    
    # 3. PyPDF2 시도
    if not text or len(text.strip()) < 100:
        print("PyPDF2로 텍스트 추출 중...")
        text = extract_text_with_pypdf2(pdf_path)
    
    if text and len(text.strip()) > 100:
        # 텍스트 저장
        if save_text_to_file(text, output_path):
            print(f"\n추출된 텍스트 길이: {len(text)} 문자")
            print(f"처음 500자 미리보기:")
            print("-" * 50)
            print(text[:500])
            print("-" * 50)
        else:
            print("텍스트 저장에 실패했습니다.")
    else:
        print("텍스트 추출에 실패했습니다. PDF가 텍스트를 포함하지 않거나 보호되어 있을 수 있습니다.")

if __name__ == "__main__":
    # 필요한 패키지 설치
    install_required_packages()
    
    # 메인 실행
    main() 