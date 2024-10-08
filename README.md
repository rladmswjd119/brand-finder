## Brand Finder
브랜드가 다른 상품을 전체조회 혹은 브랜드 별로 조회할 수 있으며, 로그인 여부에 따라 제공하는 데이터가 다른 애플리케이션의 API 서버입니다.

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
</br>

| Stack                                                                                                        | Version           |
|:------------------------------------------------------------------------------------------------------------:|:-----------------:|
| ![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white) | Spring Boot 3.3.x |
| ![Gradle](https://img.shields.io/badge/Gradle-02303A.svg?style=for-the-badge&logo=Gradle&logoColor=white)    | Gradle 8.8       |
| ![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)    | JDK 17           |
| ![MySQL](https://img.shields.io/badge/mysql-4479A1.svg?style=for-the-badge&logo=mysql&logoColor=white)       | MySQL latest        |
| ![Redis](https://img.shields.io/badge/redis-%23DD0031.svg?style=for-the-badge&logo=redis&logoColor=white)    | Redis latest        |

</br>

## ERD
</br>

![image](https://github.com/user-attachments/assets/f8670c71-d5ce-43ef-bab4-33d1622c0aef)


</br>

## API 명세서
</br>

| 대분류 | 기능 | Http Method | API Path | 토큰 검증 여부 | 
| --- | --- | --- | --- | --- |
| 사용자 | 회원 가입 | `POST` | /api/users/join | X | 
|사용자 | 로그인 | `POST` | /api/users/login | X | 
| 상품 | 상품 목록 조회 | `GET` | /api/products | O \ X | 
| 상품 | 브랜드별 상품 목록 조회 | `GET` | /api/products | O \ X |

</br>

## 기술 선택 이유
</br>

<details><summary>로그인 여부에 따른 상품 목록 조회 처리
</summary>
	
*우선 로그인 여부에 따라서 사용자에게 보여지는 데이터가 다르기 때문에 반환 DTO를 두 가지를 생성했습니다. 처음에는 리다이렉트를 사용하려고 했으나 리다이렉트 방식은 데이터 전달에 한계가 있었습니다. 이를 해결하기 위해, 공통된 부모 객체를 반환하여 두 가지 경우를 모두 처리할 수 있도록 구현하였습니다.*

</details>

<details><summary>Error Handling
</summary>

*Spring에서는 `controller`에서 예외가 발생할 경우 기본적으로 `BasicErrorController`가 이를 처리합니다. 하지만 `BasicErrorController`는 클라이언트에게 `500 Internal Server Error`만 전달하기 때문에, 정확한 에러 원인을 알 수 없다는 문제가 있습니다. 이를 해결하기 위해 `controller` 단에서 발생할 수 있는 오류를 전역적으로 관리할 수 있도록 `@RestControllerAdvice`를 사용한 `GlobalExceptionHandler`를 정의해 예외 처리를 커스터마이즈했습니다.
</br>
또한, 정상적인 응답을 위한 `SuccessResponse`와 오류 발생 시 사용될 `ErrorResponse`를 각각 생성했습니다. `SuccessResponse`는 `controller`에서 정상 처리된 응답을 클라이언트에 전달할 때 사용되고, `ErrorResponse`는 `GlobalExceptionHandler`의 `@ExceptionHandler`로 등록된 예외가 발생했을 때 생성되어 클라이언트에 전달되도록 설정했습니다.*

</details>

<details><summary>docker-compose
</summary>
	
*애플리케이션 실행에 필요한 소프트웨어가 2개 이상이어서, 각각의 컨테이너를 따로 생성하는 대신, docker-compose를 이용해 함께 관리하기로 결정했습니다.*

</details>

<details><summary>TDD
</summary>

*기능 구현 후에 테스트 코드를 작성하는 방식에서는 종종 테스트 코드 작성을 잊거나, 코드를 검증할 방법이 부족해 리팩토링이 어려운 상황이 자주 발생했습니다. 이러한 문제를 해결하기 위해 TDD 방식을 도입했는데, 기능 구현 전에 테스트 코드를 작성하는 것이 처음에는 다소 어려웠지만, 구현이 끝나면 즉시 코드를 확인할 수 있어 장기적으로 개발 효율성을 높일 수 있었습니다.*

</details>


</br>

## 트러블 슈팅
</br>

<details><summary>역직렬화
</summary>
  
*상품 목록 조회 기능 테스트 코드 작성 과정에서 `org.springframework.web.client.RestClientException` 에러가 발생했습니다. 에러 메시지를 번역해보니 JSON 데이터를 자바 객체로 역직렬화할 수 없어서 발생한 것이었습니다.  아래가 에러 발생 코드입니다.*
```
ResponseEntity<List<ProductNoLoginResponse>> responseEntity 
                = testRestTemplate.exchange("/api/products/", HttpMethod.GET, 
                                              null, new ParameterizedTypeReference<>() {});
```
*로그인 기능 테스트 코드를 같은 방법으로 작성할 땐 발생되지 않은 에러였기 때문에 원인이 무엇인지 찾는데 시간을 많이 할애할 수 밖에 없었습니다.*
```
ResponseEntity<String> result = testRestTemplate.exchange("/api/users/login", HttpMethod.POST, 
                                                            entity, String.class);
```
*두 코드의 차이점은 `SuccessResponse`에 저장된 타입 뿐이었습니다. 그래서 기본 자료형일 때에도 Jackson을 이용해 역직렬화가 실행되는지 확인해보았는데, 기본 자료형일 경우에는 역직렬화가 실행되지 않는다는 것을 발견했습니다.*
```
ResponseEntity<SuccessResponse<List<ProductNoLoginResponse>>> responseEntity
          = testRestTemplate.exchange("/api/products/", HttpMethod.GET, null,
                						            new ParameterizedTypeReference<>() {});
```
*Jackson 라이브러리를 사용해서 역직렬화를 할 때엔 지정 객체를 정확하게 기재해야 한다는 것을 깨달았습니다. 하지만 여전히 이유는 잘 모르는 상황이었습니다. 구현을 마치고 좀 더 찾아보니 에러가 발생한 데이터의 타입이 런타임 시점에서 소거되는 List인 것이 문제였습니다. List는 제네릭 타입으로 런타임 시점에서 타입이 소거됩니다. 때문에 Jackson에게 정확한 타입 정보를 전달해야 역직렬화가 가능합니다.*

</details>


<details><summary>Could not initialize proxy - no Session
</summary>
  
*로그인을 한 상태에서 상품 목록 조회를 할 때 발생한 오류입니다. 해당 오류가 발생했을 때 `User` 엔티티는 연관 관계 객체로 `Rank`를 가지고 있었고 FetchType은 LAZY로 정의된 상황이었습니다. 아래는 에러가 발생한 메소드입니다.*
```
@Override
public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
     User user = Optional.of(userRepository.findByUsername(username))
                .orElseThrow(() -> new NullPointerException(ErrorCode.ACCOUNT_NOT_FOUND.getMessage()));
     log.info("{}의 등급은 {} 입니다.", user.getUsername(), user.getRank().getName());
     return new UserDetailsImpl(user);
}
```
*LAZY는 엔티티가 호출될 때가 아닌 해당 연관 관계 객체에 접근할 때 초기화가 됩니다. 접근 전까지는 영속성 컨텍스트에 프록시 객체로 저장됩니다. 문제는 이 영속성 컨텍스트가 트랜잭션과 생명주기 같다는 점에서 발생합니다. 오류가 발생한 코드에서 트랜잭션 범위는 `userRepository.findByUsername(username)` 메소드 입니다. 즉, `findByUsername()` 메소드가 종료되면 트랜잭션도 종료됩니다. 영속성 컨텍스트 또한 종료됩니다. 프록시 객체로 저장되었던 `Rank`도 함께 삭제되기 때문에 `log`에서 `Rank`의 필드를 호출할 때 오류가 발생했던 것입니다. 해결방법은 대표적으로 두 가지입니다. `FetchType`을 `EAGER`로 변경하거나 트랜잭션의 범위를 넓히는 것입니다. 저는 선택의 여지가 있다면 트랜잭션의 범위를 넓히는 것이 메모리 관리 방면에서 더 효율적이라고 생각했기 때문에 `loadUserByUsername()` 메소드에 `@Transational` 어노테이션을 정의해줌으로써 트랜잭션 범위를 넓혔습니다.*

</details>


<details><summary>회원가입도 Jwt filter를 거친다!
</summary>

*WebSecurityConfig에 Jwt filter를 등록해주고 회원가입과 로그인  API엔 permitAll() 메소드를 이용해 인증/인가에 상관없이 접근을 허용했습니다. 그리고 포스트맨을 실행했는데 403 Forbidden 오류가 발생했습니다. 어디서 오류가 발생했는지 정확히 알기위해 log를 이용했고, 접근을 허용한 API 또한 JWT filter를 거친다는 것을 알게되었습니다.*
</br>
*처음엔 접근을 혀용한 API는 등록한 필터를 거치지 않는다고 생각했기 때문에 회원가입 API가 Jwt filter를 거치는 게 오류라고 생각했습니다.*

```
* 오류가 발생한 코드

public String extractToken(HttpServletRequest request) {
		  String bearerToken = request.getHeader(AUTHORIZATION);
		  if(bearerToken == null || !bearerToken.startsWith(BEARER)) {
			    // 헤더에 AccessToken 없을 시 오류 발생
		  }

		  return bearerToken;
}
```
*관련 글을 찾아보고 다른 분이 구현한 JWT를 분석하면서 고민한 결과 코드 설계에 문제가 있다는 것을 발견했습니다. 결론부터 말하자면 인증/인가 없이 접근을 허용한 API라고 해서 등록된 필터를 거치지 않는 것이 아니었습니다. 다만, 접근이 허용된 API는 token이 없어도 오류가 발생하지 않는 것 뿐이었습니다. 그렇기 때문에 token이 존재하지 않을 때 오류를 발생하는 것이 아니라 아래와 같이 null를 반환해야 하는 것이었습니다.*

```
* 정상적으로 실행이 되는 코드

public String extractToken(HttpServletRequest request) {
		  String bearerToken = request.getHeader(AUTHORIZATION);
		  if(bearerToken != null && bearerToken.startsWith(BEARER)) {
			    return bearerToken;
		  }

		  return   null;
}
```

</details>

</br>
