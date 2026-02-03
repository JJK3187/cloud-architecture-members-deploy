
# 클라우드 아키텍처 설계 및 배포

본 프로젝트는 API를 만들어 AWS 상에 배포하는 것을 연습하는 프로젝트 입니다.

---

## 1. 인프라 구축 정보
안전한 운영 환경을 위해 VPC를 설계하고 망을 분리하였습니다.

* **VPC 설계**: Public/Private Subnet 분리를 통한 보안 강화
* **EC2 배포**: Public Subnet 내 EC2 인스턴스를 생성하여 외부 접속 허용
* **접속 정보**:
    * **Public IP**: `http://13.124.241.188:8080`
    * **/actuator/info**: http://13.124.241.188:8080/actuator/info
<details><summary>
AWS Budgets</summary>

![budget](https://github.com/user-attachments/assets/ec807cc5-4f8c-4c5e-8251-e3f1cb9563be)

</details>
<details><summary>
RDS</summary>

![RDS](https://github.com/user-attachments/assets/b08d9a7e-563a-4141-b120-8dcf224734b7)

</details>

<details><summary>
Presigned URL</summary>
https://memebers-kjj3187-files.s3.ap-northeast-2.amazonaws.com/https%3A//memebers-kjj3187-files.s3.ap-northeast-2.amazonaws.com/profiles/3def436a-e0eb-45d5-99cc-58cc6eb4926e-%25EC%258B%25A4%25EC%258A%25B56.jpg?X-Amz-Security-Token=IQoJb3JpZ2luX2VjECkaDmFwLW5vcnRoZWFzdC0yIkcwRQIhANZa%2BM0Fcf4ffB1NkxxOG1t1EHqisRx1Uh1GN5Mj8v5gAiAoKaUNK0xSHzOlnD8T1n%2FK26UUoeAyF%2Fee2aOa8XGz9yrRBQjy%2F%2F%2F%2F%2F%2F%2F%2F%2F%2F8BEAAaDDU3NDA3MDY2NTQ4OSIMji7xvOG47c4%2BHkLCKqUFAmdbIIftaw6zvlCDdO6srOQG%2Fo2QVhalEERngFy%2Bn9AxoqiP5owwTB6ADZ0qhk26mSmdqJbJL3WyD%2FtKeqR71TUQ%2FgSvxanYCqxNHlyeNGNVMf%2Bdg70xVzXcmzESyoMPxSd7%2BE2%2B4up9z0CWqIPo7J%2FhV5%2F2Ht1GrdyBna9WtM03lrvrG6DWYlUFQpKl7MEEYRYqXIuyH%2FwVy0NBgYBKFajHCQTcf96ckJB%2B8kDyFkkMoFZ%2BAMZJ7ond7kKTyta8KshuiYQtZUJGFDCD6nwWssh8gYnowcfWsyqPk%2BhWZ7ck%2FO%2B7%2BqlJToRTEUEB%2F734gDO9b1IrzCp6NCVatQuTo9f%2F%2FSsTre5d%2B774CVDDbshaP0CrlfLO%2FBo0PymYnAewV04%2BmwFkX%2FIR6fes3t11SMj3Qw%2FYSj6HKvzgRj94XqGVlBEe%2Bj7vPosLhK%2FYvgBKETs0SzXNgx8%2FoUOILwdaB%2BSTyGNL1FFlWQPhd0Y1orDwZHb6UD1LGotBRO0SFzDVmKEoEW%2FhFoe%2FKLf012HnfurFBAUuXQD%2Bu37r75vtXZjKRsjL9ZyK3l2a%2F450TzowD6X%2B0iv83wirtncFdBAaDWVgbhVuvAuDUir%2BFDKwVBvR3lDzHp2npHt9Q2JzAKQDgoURHrpeTlxkc1zhcJYpkGOxbvo0iXV%2FZs5PU5bmRFLsYKHb6q92eCczpcIt41czFMfVLL9slicMhjmR3iu6PWEsyxYUc2w4rjU3KTjGBHyARLsb%2BHSyuFCcaAiEsqsBeOBvltUyGfH8e6CzUi057R6TNEZB2BOn44KgKl0%2FPxnTqpsnj8Xud3oJ8yQKTb%2B%2FOybPd02rxSLgzuFpd%2F8VGeKVHF1zS1tCgupVj0I1%2FKqndvKGBMYTiPnUzsXG6LQhIMr64UUwjoSFzAY6sQGzeVl0zLTj%2B1%2FjqlQDgdJT%2BA1kfCQoJk97nSrY%2FTkqvxWQAUvfE0lrWa1ZhFu2ay5RunmMfIFuyY2MlFG3VveOGx6J8cE8qLiVwhoLzwoGJ2vzo%2F44kM3sMvJxY4YfxlUypj4XaRSsJXd%2BzYtap7an8BK9vfHjRxbblv1Qmd1DPGM4DJ5S%2FCk7xbYfhgkingcS62DiHGekqxbYkC5OFz5YcOJj3YNhALey2XusPjHg88Q%3D&X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20260203T013625Z&X-Amz-SignedHeaders=host&X-Amz-Credential=ASIAYLKKHGEI4563JWXD%2F20260203%2Fap-northeast-2%2Fs3%2Faws4_request&X-Amz-Expires=604800&X-Amz-Signature=269f3e0701d2fbae86fd2cb8db5b95669c0930e173df2998b212adefdcec7e33

</details> 
expiration: "2026-02-10 01:36:25"
---

## 2. 애플리케이션 아키텍처

### 핵심 기능 (Member API)
| Method | Endpoint | Description |
| :--- | :--- | :--- |
| **POST** | `/api/members` | 팀원 정보(이름, 나이, MBTI) 저장 |
| **GET** | `/api/members/{id}` | 저장된 팀원 정보 조회 |
| **POST** | `/api/members/{id}/profile-image` | 프로필 이미지 저장 |
| **GET** | `/api/members/{id}/profile-image` | 프로필 이미지 Presigned URL 조회 |

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

---

## 4. CI/CD 자동화 및 배포 전략
Docker 기반의 지속적 통합 및 배포(CI/CD) 환경을 구축

---

### 5. 외부 설정 관리 (AWS Parameter Store)
보안이 중요한 설정값(DB URL, 계정 정보 등)은 AWS Systems Manager의 Parameter Store에서 관리하며, 애플리케이션 구동 시점에 주입받도록 설계
* **적용 방식**: io.awspring.cloud:spring-cloud-aws-starter-parameter-store 의존성 활용
* **장점**: 소스 코드에 민감 정보가 노출되지 않으며, 인프라 레벨에서 설정을 통합 관리 가능
