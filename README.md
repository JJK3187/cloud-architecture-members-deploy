
# 클라우드 아키텍처 설계 및 배포 (LV 1)

본 프로젝트는 API를 만들어 AWS 상에 배포하는 것을 연습하는 프로젝트 입니다.

---

## 1. 인프라 구축 정보
안전한 운영 환경을 위해 VPC를 설계하고 망을 분리하였습니다.

* **VPC 설계**: Public/Private Subnet 분리를 통한 보안 강화
* **EC2 배포**: Public Subnet 내 EC2 인스턴스를 생성하여 외부 접속 허용
* **접속 정보**:
    * **Public IP**: `http://13.124.241.188:8080`
<details><summary>
AWS Budgets</summary>

![budget](https://github.com/user-attachments/assets/ec807cc5-4f8c-4c5e-8251-e3f1cb9563be)

</details>
      
---

## 2. 애플리케이션 아키텍처

### 핵심 기능 (Member API)
| Method | Endpoint | Description |
| :--- | :--- | :--- |
| **POST** | `/api/members` | 팀원 정보(이름, 나이, MBTI) 저장 |
| **GET** | `/api/members/{id}` | 저장된 팀원 정보 조회 |

---

## 3. 운영 설정 전략

### Profile 분리 (Multi-Profile)
환경에 따른 유연한 설정을 위해 `application.yml`을 분리하여 관리합니다.
* **Local**: H2 In-Memory DB 사용 (개발 편의성)
* **Prod**: MySQL DB 사용 (데이터 영속성 및 환경변수 보안 적용)

### 로그 전략 및 예외 처리
* **INFO 로그**: 모든 API 요청 시 `[API - LOG] {메서드} {경로}` 형식으로 기록
* **ERROR 로그**: 시스템 예외 발생 시 전역 예외 처리기(`@RestControllerAdvice`)를 통해 **스택트레이스(Stacktrace)** 포함 기록
* **상태 모니터링**: `Spring Boot Actuator`를 도입하여 `/actuator/health` 엔드포인트 노출
