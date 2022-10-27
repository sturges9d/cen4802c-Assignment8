FROM openjdk:11
EXPOSE 8080
ADD target/cen4802c-Assignment8.jar cen4802c-Assignment8.jar
ENTRYPOINT ["java","-jar","/cen4802c-Assignment8.jar"]