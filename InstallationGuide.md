# Installation Guide #

The maven-replacer-plugin is uploaded within the central Maven repository.
```
<build>
    <plugins>
        ...
       <plugin>
           <groupId>com.google.code.maven-replacer-plugin</groupId>
           <artifactId>maven-replacer-plugin</artifactId>
           <version>(version)</version>
           <configuration>
           ...
           </configuration>
       </plugin>
       ...
    </plugins>
    ...
</build>
```

---

**Installing manually**

Download the latest binary from [downloads](http://code.google.com/p/maven-replacer-plugin/downloads/list) and use mvn install. e.g.
```
mvn install:install-file -Dfile=/path/to/file/maven-replacer-plugin-(version).jar -DgroupId=com.google.code.maven-replacer-plugin \ 
-DartifactId=maven-replacer-plugin -Dversion=(version) -Dpackaging=jar -DpomFile=maven-replacer-plugin-(version).pom
```

To install the plugin by building locally, check out the latest copy from trunk (or a tag) and use mvn install (which should build and install the plugin).

To install into Nexus, either add the repository or upload the JAR to your 3rd party libraries.

Once the plugin is installed into your repository, you can integrate it into your build by following the [Usage Guide](UsageGuide.md).