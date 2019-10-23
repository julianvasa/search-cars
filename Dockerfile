FROM openjdk:8
ADD target/search-cars-1.0.jar search-cars-1.0.jar
ENTRYPOINT ["java","-jar","search-cars-1.0.jar"]
