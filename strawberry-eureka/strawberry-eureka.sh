
# pull alpine container
docker pull alpine:3.8

#build base jdk8 alpine
docker build -f Dockerfile-alpine-jdk8 -t alpine-jdk8:1.0 .

#maven exec
mvn clean install -pl strawberry-eureka -DskipTests -X

docker build -f Dockerfile-strawberry-eureka -t strawberry-eureka:1.0 .

docker run -p 8761:8761 -d strawberry-eureka:1.0 sh -c 'java -jar /opt/strawberry-eureka-1.0.0-SNAPSHOT.jar'