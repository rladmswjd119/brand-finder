## Brand Finder

브랜드별 상품을 조회하고 저장할 수 있는 서비스 입니다.

>기간 : 2024.09.12 ~ 2024.09.20

</br>

## 목차
> 1. [프로젝트 환경](#프로젝트-환경)
> 2. [ERD](#ERD)
> 3. [API 명세서](#API-명세서)
> 4. [기술 선택 이유](#기술-선택-이유)
> 5. [트러블 슈팅](#트러블-슈팅)

</br>

## 프로젝트 환경
| Stack                                                                                                        | Version           |
|:------------------------------------------------------------------------------------------------------------:|:-----------------:|
| ![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white) | Spring Boot 3.3.x |
| ![Gradle](https://img.shields.io/badge/Gradle-02303A.svg?style=for-the-badge&logo=Gradle&logoColor=white)    | Gradle 8.8       |
| ![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)    | JDK 17           |
| ![MySQL](https://img.shields.io/badge/mysql-4479A1.svg?style=for-the-badge&logo=mysql&logoColor=white)       | MySQL latest        |
| ![Redis](https://img.shields.io/badge/redis-%23DD0031.svg?style=for-the-badge&logo=redis&logoColor=white)    | Redis latest        |

</br>

## ERD
![image](https://github.com/user-attachments/assets/536ec17e-4686-446b-b542-5a05bfff9906)


</br>

## API 명세서

| 대분류   | 기능                  | Http Method | API Path                               | 토큰 검증 여부 |
|----------|-----------------------|-------------|----------------------------------------|------|
| 사용자   |  회원 가입              | `POST`        | /api/users/join                            | X    |
| 사용자   | 로그인                 | `POST`        | /api/users/login                       | X    |
| 상품   | 상품 목록 조회          | `GET`         | /api/products                            | O \ X    |
| 상품   | 브랜드별 상품 목록 조회         | `GET`         | /api/products                            | O \ X |

</br>

## 기술 선택 이유

<details><summary>로그인 여부에 따른 상품 목록 조회 처리
</summary>


*우선 로그인 여부에 따라서 사용자에게 보여지는 데이터가 다르기 때문에 반환 DTO를 두 가지를 생성했습니다. 처음에는 리다이렉트를 사용하려고 했으나 리다이렉트 방식은 데이터 전달에 한계가 있었습니다. 이를 해결하기 위해, 공통된 부모 객체를 반환하여 두 가지 경우를 모두 처리할 수 있도록 구현하였습니다.*
</details>


</br>

## 트러블 슈팅
