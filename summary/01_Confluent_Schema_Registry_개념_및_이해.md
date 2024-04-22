1. Schema
    * Data Structure
    * 데이터를 만들어내는 Producer와 데이터를 사용하는 Consumer 간의 계약으로 사용
    * 스키마가 없으면 시간이 지남에 따라, 제어된 방식으로 데이터 구조를 발전시킬 수단이 없게 됨
    * 데이터 구조는 항상 비즈니스에 따라서 진화하는데, 이것을 schema evolution이라고 함

2. Schema Evolution
    * 비즈니스가 변경되거나 더 많은 애플리케이션이 동일한 데이터를 활용하기를 원함에 따라 기존 데이터 구조가 진화할 필요성 발생

3. Avro
    * Apache Open Source Software 프로젝트
    * 데이터 Serialization 제공
    * Java를 포함한 많은 프로그래밍 언어에서 지원
    * 데이터 구조 형식 제공
    * Avro 데이터는 바이너리이므로 데이터를 효율적으로 저장
    * 장점
        * 압축, 고성능, binary 포맷
        * java를 포함한 많은 프로그래밍 언어에서 지원
        * Avro 데이터가 파일에 저장되면 해당 스키마가 함꼐 저장도므로 나중에 모든 프로그램에서 파일 처리 가능
        * Avro 스키마는 JSON으로 정의되므로, 이미 JSON 라이브러리가 있는 언어에서 구현이 용이
        * 데이터의 타입을 알 수 있음
        * Confluent Schema Registry에서 사용 가능
    * 단점
        * Binary 형태로 Serialization 되기 떄문에 데이터를 쉽게 보고 해석하기 어려움 디버깅, 개발시 불편

4. Schema Evolution
    * ![img](../../../../../Documents/kafka/img/img63.png)

5. Schema 설계 고려사항
    * 삭제될 가능성이 있는 필드이면 default value를 반드시 지정
    * 추가되는 필드라면 default value를 지정
    * 필드의 이름을 변경하지 않음

6. Confluent Schema Registry
    * Confluent Schema Registry는 스키마의 중앙 집중식 관리를 제공
        - 모든 스키마의 버전 기록을 저장
        - Avro 스키마 저장 및 검색을 위한 RESTful 인터페이스 제공
        - 스키마를 확인하고 데이터가 스키마와 일치하지 않으면 예외를 throw
        - 호환성 설정에 따라 스키마 진화 가능
    * 각 메시지와 함꼐 Avro 스키마를 보내는 것은 비효율적
        - 대신 Avro 스키마를 나타내는 Global Unique Id각 각 메시지와 함꼐 전송
    * Schema Registry는 특별한 Kafka Topic에 스키마 정보를 저장
        - "_schemas" Topic
        - kafkastore.topic 파라미터로 변경 가능
* ![img](../../../../../Documents/kafka/img/img64.png)