maven-replacer-plugin:replacer is a build plugin to replace tokens within a file with a given value and fully supports regular expressions.

**Plugin artifactId was renamed to "replacer" from "maven-replacer-plugin" due to a naming policy change from the Maven team**

**April 16th, 2014: Version 1.5.3 has been released. (see [Release Notes](ReleaseNotes.md))**

This plugin is typically used to change database or network configuration within a project during a maven build.
It also can perform some advanced text replacement functions, such as providing a separate token/value file to keep your build script concise, writing resulting text after replacement to a separate file and token matching with regular expressions. There is even support for making exact document node replacements via X-Path. (See the [Usage Guide](UsageGuide.md) for more).

Please read the [Installation guide](InstallationGuide.md) and [Usage Guide](UsageGuide.md) on how to integrate maven-replacer-plugin into your build.

Simple example usage:
```
<build>
    <plugins>
        ...
       <plugin>
           <groupId>com.google.code.maven-replacer-plugin</groupId>
           <artifactId>replacer</artifactId>
           <version>1.5.3</version>
           <executions>
               <execution>
                   <phase>prepare-package</phase>
                   <goals>
                       <goal>replace</goal>
                   </goals>                   
               </execution>
           </executions>
           <configuration>
               <file>target/${project.artifactId}/somefile.txt</file>
               <replacements>
                   <replacement>
                       <token>SOME TOKEN</token>
                       <value>SOME VALUE</value>
                   </replacement>         
               </replacements>
           </configuration>
       </plugin>
    </plugins>
</build>
```

_If you have suggestions for enhancements or have found issues, please submit an Issue for consideration._

<table cellpadding='3' width='400' border='0'>
<tr>
<td>
<a href='https://bakersoftware.ci.cloudbees.com'><img src='http://web-static-cloudfront.s3.amazonaws.com/images/badges/BuiltOnDEV.png' /></a>
</td><td></td>
</tr>
</table>