# eLearn
## launching
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
    - spring.mail.username=<mail>
    - spring.mail.properties.mail.smtp.starttls.required=true
    - spring.mail.host=smtp.gmail.com
    - spring.mail.port=587
    - spring.mail.password=<mail_password>
    - spring.mail.properties.mail.smtp.auth=true
    - spring.mail.properties.mail.transport.protocol=smtp
    - spring.mail.properties.mail.smtp.starttls.enable=true
    - notification.scheduler.enabled=true (or false if you want to disable scheduler)
   
  * to launch structurizr and generate diagram:
    - create account https://structurizr.com/signup
    - create workspace
    - run class StructurizrMain with env variables:
      a) workspace.id = <workspace_id>
      b) api.key = <api_key>
      c) api.secret = <secret_key>
## current architecture

![structurizr-90179-eLearn components (2)](https://github.com/erykov1/eLearn/assets/62502523/370e0e45-8da4-453a-beec-4ce32e74f233)

## release 1.3.0
  - tracking user results
  - image link added to questions
## release 1.1.0
  - architecture for application
  - user assignations to learning object (current only to quiz)
## release 1.0.0
  - possibility to create open and closed questions
  - possibility to modify questions
  - possibility to create quizzes
  - possibility to modify quizzes
  - possibility to create account
  - separation of user roles into: ADMIN and STUDENT
  - possibility to assign questions to quizzes
