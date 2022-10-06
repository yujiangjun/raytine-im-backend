FROM xiehaijun/jdk11
MAINTAINER 余江俊
ADD build/libs/raytine-im-backend-1.0-SNAPSHOT.jar /opt/app.jar
ENTRYPOINT ["java","-jar","/opt/app.jar","&"]