# Spring을 활용한 쇼핑몰 웹사이트 기능 구현

Spring을 사용해보면서 Spring이 실제로 어떻게 동작하는지를 눈으로 직접 확인하며 익히기 위한 개인 프로젝트 입니다.


첫 개인 프로젝트라 부족한 부분이 많이 존재합니다. (다음 프로젝트 시에는 이를 개선하여 프로젝트를 진행하도록 노력할 예정)

## 버전
Java 17
Springboot 3.2.3
Spring Security 6.3.1.2
MySQL 8.0
Toss API 2022.11.16

##  추가해본 기능

### 프론트엔드 (html,css,js만 사용.)
- 카테고리 기능
- get, post를 이용하여 controller와 상호작용하고, 이를 html을 통해 보여주는 코드
- 웹사이트 디자인 및 기타 js의 eventListener를 이용한 click 이벤트

### 백엔드 (Spring 사용) 
- Spring Security를 활용한 로그인 기능
- 회원 탈퇴 기능
- 장바구니 기능
- 카테고리를 통한 상품 검색 기능
- 판매자 상품 등록 기능
- 상품 할인율 적용
- 쿠폰 기능
- 결제 기능(토스 API 사용)
- 찜하기 기능
- 리뷰 작성 기능
- 주문 취소(환불) 기능
- 주문내역 검색 기능
- 상품 별점 기능
- 홈화면에 사용자 정보를 활용한 상품 추천 기능
- 검색결과 정렬 기능

### 아쉬웠던 점
- 데이터베이스 테이블을 구성할 때, 너무 생각없이 만들어서 필요할 때마다 변수를 추가하여 테이블이 비효율적으로 구성됨
- 코드 작성 시, 자신만의 규칙(변수나 함수 이름 설정 시 규칙이 통일되지 않음 ex) subString , sub_string이 혼용되서 사용됨)
- 코드의 스타일이 통일되지 않음.(같은 기능이지만 다른 모양으로 구현되는 경우가 종종 있음.)
- 관심사의 분리가 제대로 되지 않은 것 같음. (하나의 컨트롤러 메소드가 여러가지 기능을 모두 자신이 수행해버림)
- 기능을 계속 추가만 하고, 제대로된 테스트를 진행하지 않음.(이러한 버그를 다른 기능 추가시 발견하는 경우가 비일비재함)
- 배포를 해보지 않음. (다음 프로젝트는 배포까지 진행해볼 예정)

  ### 프로젝트 시 참고한 사이트
- [디자인 플랫폼 사이트](https://www.miricanvas.com/)
- [쇼핑몰 구현 블로그](https://blog.naver.com/psychozandy/222652712340)
- [JSON 데이터 출력](https://chlee21.tistory.com/156)
- [JS 특정 문자 모두 바꾸기(replaceAll)](https://gent.tistory.com/18)
- [CSS 디스플레이](https://www.daleseo.com/css-display-inline-block/)
- [2015 지마켓 ERD](https://www.slideshare.net/slideshow/db-project-gmarket/54873434#47)
- [토스 페이먼츠 API 공식 가이드](https://docs.tosspayments.com/guides/v2/get-started)
- [스프링 시큐리티 구현 관련_1](https://kimchanjung.github.io/programming/2020/07/02/spring-security-02/)
- [스프링 시큐리티 구현 관련_2](https://nahwasa.com/entry/%EC%8A%A4%ED%94%84%EB%A7%81%EB%B6%80%ED%8A%B8-30%EC%9D%B4%EC%83%81-Spring-Security-%EA%B8%B0%EB%B3%B8-%EC%84%B8%ED%8C%85-%EC%8A%A4%ED%94%84%EB%A7%81-%EC%8B%9C%ED%81%90%EB%A6%AC%ED%8B%B0)
- [스프링을 이용한 이메일 인증](https://velog.io/@rnqhstlr2297/Spring%EB%A5%BC-%EC%9D%B4%EC%9A%A9%ED%95%9C-%EC%9D%B4%EB%A9%94%EC%9D%BC-%EC%9D%B8%EC%A6%9Dfeat.-%EB%84%A4%EC%9D%B4%EB%B2%84)
- [스프링 시큐리티 구현 관련_1](https://kimchanjung.github.io/programming/2020/07/02/spring-security-02/)

- 
  ### 세부적인 프로젝트 진행 일대기
  [velog](https://velog.io/@kkuldangi3/series/%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-%EB%B0%8F-%EC%9D%BC%EA%B8%B0)

