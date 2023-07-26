# 카카오페이증권 기능 클론 코딩

## 개발 환경 및 프레임워크/라이브러리
- IDE: IntelliJ IDEA Ultimate
- OS: Mac OS X
- Jdk17
- Spring Boot3
- JPA
- Docker
- Flyway
- DBUnit
- RestDocs

## 구현 기능
- 실시간 순위 - 상승, 하락
- 전체 주식 리스트 조회
- 공통 조건
  - 상승, 하락에 대해서는 최대 100건까지 데이터 표출
- 주식에 대한 실시간 데이터는 '외부'에서 API요청을 통해 넣어주는 것을 가정하여 API요청 시 랜덤 데이터 생성
- 최초 서버 실행 시 PreData 설정
  - 데이터 출처 : http://data.krx.co.kr/contents/MDC/MDI/mdiLoader/index.cmd?menuId=MDC0201020201

## 설계
### 테이블
- 각 데이터의 중복 체크를 하기 위한 ts칼럼을 추가한 후 ts와 code로 중복 체크 진행하는 방향으로 테이블 설계
```sql
CREATE TABLE stock (
id BIGINT NOT NULL AUTO_INCREMENT,
name VARCHAR(255),
price DECIMAL(19,2),
ts INT,
code VARCHAR(255),
PRIMARY KEY (id),
UNIQUE KEY stockUnique (ts, code)
);
```
### API
- 상승, 하락 주식 조회에 대해 pagenation은 get parameter로 startIdx와 offset을 받아서 진행함.
- 최대 100개까지의 데이터 제공에 대한 상수는 spring의 환경변수로 관리함.
```yaml
const:
show-limit-number: 100
```
- 상승, 하락률
  - 상승과 하락에 대해서는 기본적으로 제약 조건이 존재함. 기본 데이터 외 추가 데이터가 한 번 이상 들어올 것
  - 각 상승과 하락에 대해 직전 데이터와 비교 후 반환
- 랜덤 데이터 생성
  - 랜덤 데이터 생성에 대해서는 아래 기준 및 로직으로 결정함
    - 주식의 상승, 하락율에 대해서는 추후 주식 종목이 많아질 것을 대비하여 쿼리에서 해결하는 방향으로 결정함
    - 상승, 하락율에 대해 -70% 부터 200%까지의 상승률을 랜덤으로 결정한다.
```java
for (StockPojo stockPojo : stockPojoList) {
  int randomPercentage = ThreadLocalRandom.current().nextInt(-70, 201);
  BigDecimal currentPrice = stockPojo.getPrice();

          if (randomPercentage < 0) {
              int discountPercentage = -randomPercentage;
              int discountedAmount = (int) (currentPrice.intValue() * (1 - (discountPercentage / 100.0)));
              stockList.add(new Stock(stockPojo.getName(), new BigDecimal(discountedAmount), stockPojo.getCode()));
              continue;
          }

          int increasePercentage = randomPercentage;
          int increasedAmount = (int) (currentPrice.intValue() * (1 + (increasePercentage / 100.0)));
          stockList.add(new Stock(stockPojo.getName(), new BigDecimal(increasedAmount), stockPojo.getCode()));
      }
```
### profile
- local과 prod환경에 대한 profile을 구분함.
- local
  - 로컬로 실행하는 경우에는 h2의 inmemory db를 사용함.
  ```
  datasource:
    driver-class-name: org.h2.Driver
    url: jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;MODE=MySQL;IGNORECASE=TRUE;DB_CLOSE_ON_EXIT=FALSE;
    username: user
    password:
    ```
- prod
  - 실제 환경과 유사하게 맞추기 위하여 docker로 mariadb를 띄운 후 진행함.
  - scehma, table의 생성은 flyway로 진행함.
  - Dockerfile
```dockerfile
FROM mariadb:10.6.13-focal

ENV MYSQL_ROOT_PASSWORD 1q2w3e4r!!

ADD ./mysql-init-files /docker-entrypoint-initdb.d

RUN echo [mysqld] >> /etc/mysql/conf.d/docker.cnf
RUN echo lower_case_table_names=1 >> /etc/mysql/conf.d/docker.cnf
RUN echo default-time-zone='+9:00' >> /etc/mysql/conf.d/docker.cnf
RUN echo collation-server = utf8mb4_unicode_ci >> /etc/mysql/conf.d/docker.cnf
RUN echo character-set-server = utf8mb4 >> /etc/mysql/conf.d/docker.cnf
RUN echo skip-character-set-client-handshake >> /etc/mysql/conf.d/docker.cnf
``` 
- docker mariadb container 생성 커맨드
```
docker run --name sec-mariadb -d -p 13306:3306 sec-mariadb:0.1.0
```
### 데이터 초기화
- 초기 프로세스 실행 시 BaseData를 기반으로 초기화하기 위해 @PostConstructor를 이용해 데이터 초기화를 진행함.
  - 이 과정에서 mariadb를 연결하는 경우 계속해서 데이터가 추가되는 경우를 방지하기 위해 stock_config 테이블을 추가로 생성하여 초기화 여부를 체크함.
    - 해당 데이터에 대한 컨트롤을 위해 StockConfigRepository를 구현한다.
```sql
CREATE TABLE stock_config (
                       id INT NOT NULL AUTO_INCREMENT,
                       init_yn tinyint(1) default false,
                       PRIMARY KEY (id)
);
insert into stock_config(init_yn) values(false);
```
```java
@PostConstruct
@Transactional
public void initBaseData() throws IOException {
    StockConfig stockConfig = stockConfigRepository.findStockConfig();
    if (stockConfig == null || !stockConfig.getInitYn()) {
        List<StockPojo> stockPojoList = baseDataRepository.readAll();
        stockPojoList.forEach(
                stockPojo -> stockRepository.save(
                        new Stock(
                                stockPojo.getName(),
                                stockPojo.getPrice(),
                                stockPojo.getCode())
                )
        );
        if (stockConfig != null) {
            stockConfig.initComplete();
            stockConfigRepository.save(stockConfig);
        }
    }
}
```
### 로깅
- 각 profile에 대해 로그 파일을 별도로 저장한다.
```yaml
logging:
level:
root: info
org.springframework.web: info
org.hibernate: info
org.hibernate.type.descriptor.sql: trace
```
### 테스트
- 테스트는 DBUnit와 RestDocs를 이용해 진행함.
- 컨트롤러 테스트의 결과는 RestDocs를 이용해 저장함.
  - 테스트 진행 후 build/generated-snippets 디렉토리에 각 결과 문서 파일이 생성된다.