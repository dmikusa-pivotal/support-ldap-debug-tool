# Support LDAP Debug Tool

This is a simple tool for testing LDAP filters through Java. 

It is often easier to test LDAP filters using a tool like Apache Directory Studio or `ldapsearch`. However, there are some times where this tool can come in handy:

1. If your LDAP server is not reachable from your desktop or somewhere that you can install other tools.
2. If your query works tested with one of these tools, but not running on CF.
3. If you need to adjust JVM & Spring specific LDAP settings to mimic the way UAA or a Spring app reacts to LDAP.

## Usage

1. Build with `./mvnw package`.
2. You can run the resulting JAR, `java -jar target/support-ldap-debug-tool-0.0.1-SNAPSHOT.jar`.

As mentioned in the `application.properties` file, you may configure this via system property or env variables or in the properties file itself.

Ex:

```
SPRING_LDAP_USERNAME=<foo> SPRING_LDAP_PASSWORD=<bar> SPRING_LDAP_URLS=<blah> SPRING_LDAP_BASE=<baz> LDAP_FILTER=<stuff> java -jar target/support-ldap-debug-tool-0.0.1-SNAPSHOT.jar`
```
