# eLearn
## launching: 
  * create folder certs in resources and in this folder:
    - create file private.pem - add private key HS256
    - create file public.pem - add public key HS256
  * to launch application, add file application.properties with values:
    - spring.datasource.driver-class-name=org.postgresql.Driver
    - spring.datasource.password=<password>
    - spring.datasource.url=<url>
    - spring.datasource.username=<username>
    - spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
    - spring.liquibase.change-log=classpath:/db/changelog/changelog.xml
    - rsa.private-key=classpath:certs/private.pem
    - rsa.public-key=classpath:certs/public.pem
## release 1.0.0
  - possibility to create open and closed questions
  - possibility to modify questions
  - possibility to create quizzes
  - possibility to modify quizzes
  - possibility to create account
  - separation of user roles into: ADMIN and STUDENT
  - possibility to assign questions to quizzes
