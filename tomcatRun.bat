set GRADLE_OPTS=-Xdebug -Xrunjdwp:transport=dt_socket,address=9999,server=y,suspend=n
set JAVA_OPTS="-Xms512m"
gradle clean war explodedDist TomcatRunWar
