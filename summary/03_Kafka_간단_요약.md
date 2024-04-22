1. Kafka 활용사례
    * 데이터 파이프라인
        - 중간에 사람의 개입 없이 데이터 오염, 중복, 유실과 같은 결함을 최소화 하고 수집, 저장 ETL(Extract, Transform, Load)가 가능하도록 일련의 흐름을 만들어 주는 과정
    * 시스템 모니터링
        - Application에서 Kafka에 Event를 보내고 Es/Kinaba에서 관리가 가능하고 Slink Connector를 통해 S3, HDFS에 보관도 가능
    * 메시징 허브
        - Msa 구성 특성상 특정 application에 많은 요청이 가능 경우가 있다. 그리고 배포주기에 따라 아주 가끔 에러가 발생하기도 하는데 이를 kafka를 사용한다면 많은 트래픽, 에러도 개선할 수 있다.