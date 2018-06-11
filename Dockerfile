FROM ibrahim/alpine
ADD target/projetServiceFonction.jar ws_projetServiceFonction_sf.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","ws_projetServiceFonction_sf.jar"]