FROM openjdk:8-jdk-alpine
ADD target/projetServiceFonction.jar ws_projetServiceFonction_sf.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","ws_projetServiceFonction_sf.jar"]