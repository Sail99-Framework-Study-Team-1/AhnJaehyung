# 1주차
2024년 8월 11일~2024년 8월 17일

## 한 일
- DTO, entity간 변환 코드 추가
- 테스트 코드에 샘플 데이터 입력 로직 추가
- Optional 이용하여 에러 처리(orElseThrow)

## 배운 것
- Gradle
  - 전까지는 강의에서 본 대로 따라만 했지만, 직접 패키지를 추가해보면서 사용법을 익혀보았다
- DTO
  - 객체 전달을 위해 존재하는, 특히 java같은 type language에서는 필수적이라고 봐야 하는 것 같다
  - js에서는 있어도 그만 없어도 그만이었다(물론 없으면 매우 불편했음, ts짱짱)
  - dto와 엔트리 변환 위치에 대한 고민은 사람마다 [이견](https://velog.io/@eunsiver/DTO는-어디서-변환하는-것이-좋을까)이 있는 것 같았다
  - 나는 DTO를 Service의 파라미터와 리턴값에 넣고, Service 내부에서 entity로 변환하는 방법을 선택했다
    - 코드 변환의 편의성을 위해 변환 코드는 dto에 생성했다(생성자에 entity를 넣으면 값이 복사되게끔+entity로 변환시켜주는 함수)
- ResponseStatusException
  - Service에서 발생하는 에러를 어떻게 처리해야 적당한 응답을 보낼 수 있을 지 고민했었다
  - Service에서 ResponseStatusException을 throw해주면 spring에서 자동으로 그에 맞는 response를 보내주더라
