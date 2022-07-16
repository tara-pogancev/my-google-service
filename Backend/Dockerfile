FROM adoptopenjdk/maven-openjdk11 as build
COPY src /home/app/src
COPY pom.xml /home/app
COPY images /home/app/images
RUN mvn -f /home/app/pom.xml clean package -DskipTests



FROM openjdk:11-jre
ENV PATH="/opt/exiftool:/opt/am/bin:$PATH"
#exiftool
RUN mkdir -p /opt/exiftool \
  && cd /opt/exiftool \
  && EXIFTOOL_VERSION=`curl -s https://exiftool.org/ver.txt` \
  && EXIFTOOL_ARCHIVE=Image-ExifTool-${EXIFTOOL_VERSION}.tar.gz \
  && curl -O https://exiftool.org/$EXIFTOOL_ARCHIVE \
  && CHECKSUM=`curl -s https://exiftool.org/checksums.txt | grep SHA1\(${EXIFTOOL_ARCHIVE} | awk -F'= ' '{print $2}'` \
  && echo "${CHECKSUM}  ${EXIFTOOL_ARCHIVE}" | /usr/bin/sha1sum -c  - \
  && tar xzf $EXIFTOOL_ARCHIVE --strip-components=1 \
  && rm -f $EXIFTOOL_ARCHIVE \
  && exiftool -ver
COPY --from=build /home/app/target/*.jar /usr/local/lib/target.jar
COPY --from=build /home/app/images /images
ENTRYPOINT ["java","-jar","/usr/local/lib/target.jar"]