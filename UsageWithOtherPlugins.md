# Usage when building WARs #

Currently, the replacer plugin needs access to a target resource **before** it is packaged into an archive. However, standard WAR packaging does not expose web resources (anything under src/main/webapp) for use in other plugins and runs as a single execution.
Fortunately, we can invoke war:exploded to copy these resources earlier in the build lifecycle so that they can be used by the maven-replacer-plugin. The standard package usage will then use the modified web resources when creating the WAR artifact.

```
<build>
...
<plugin>
    <groupId>com.google.code.maven-replacer-plugin</groupId>
    <artifactId>replacer</artifactId>
    <version>(version)</version>
    <executions>
        <execution>
            <phase>prepare-package</phase>
            <goals>
                <goal>replace</goal>
            </goals>            
        </execution>
    </executions>
    <configuration>
        <file>target/${project.build.finalName}/WEB-INF/helloWorld.jsp</file>
        <token>TOKEN</token>
        <value>VALUE</value>
    </configuration>
</plugin>
```

There needs to be a hooking spot for running replacements on files before they are packaged into a war. Make sure you add this before the maven-replacer-plugin if they both execute in the same phase (i.e. prepare-package). Make sure you use version 2.0.1 or below of the war plugin since the commonly used 2.1.0+ copies resources twice which overrides replacements.
Use the following war plugin configuration to allow the files to be modified before being packaged:
```
<build>
...
<plugin>
   <groupId>org.apache.maven.plugins</groupId>
   <artifactId>maven-war-plugin</artifactId>
   <version>2.0.1</version>
   <executions>
     <execution>
       <id>prepare-war</id>
       <phase>prepare-package</phase>
       <goals>
         <goal>exploded</goal>
       </goals>
     </execution>
   </executions>
</plugin>
```
Otherwise use `<useCache>true</useCache>` for the war plugin 2.1.0+ configuration.


---


Sometimes there is issues caused by other post build steps. Here is a complete example which works in most cases for including a build number:
```
<plugin>
    <groupId>org.codehaus.mojo</groupId>
    <artifactId>buildnumber-maven-plugin</artifactId>
    <version>1.0</version>
    <executions>
        <execution>
            <phase>generate-resources</phase>
            <goals>
                <goal>create</goal>
            </goals>
        </execution>
    </executions>
    <configuration>
        <doCheck>false</doCheck>
        <doUpdate>false</doUpdate>
    </configuration>
</plugin>

<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-war-plugin</artifactId>
    <version>2.1.1</version>
    <executions>
        <execution>
            <id>prepare-war</id>
            <phase>prepare-package</phase>
            <goals>
                <goal>exploded</goal>
            </goals>
        </execution>
        <execution>
            <id>default-war</id>
            <phase>package</phase>
            <goals>
                <goal>war</goal>
            </goals>
            <configuration>
                <warSourceDirectory>${project.build.directory}/${project.build.finalName}</warSourceDirectory>
            </configuration>
        </execution>
    </executions>
</plugin>

<plugin>
    <groupId>com.google.code.maven-replacer-plugin</groupId>
    <artifactId>replacer</artifactId>
    <version>(version)</version>
    <executions>
        <execution>
            <phase>prepare-package</phase>
            <goals>
                <goal>replace</goal>
            </goals>
        </execution>
    </executions>
    <configuration>
        <file>target/${project.build.finalName}/WEB-INF/YOUR_FILE</file>
        <token>YOUR_FILES_TOKEN</token>
        <value>${buildNumber}</value>
    </configuration>
</plugin>
```