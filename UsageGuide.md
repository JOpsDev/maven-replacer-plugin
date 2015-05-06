# Usage Guide #

### Parameters used in any execution ###
<table cellpadding='5' width='800' border='1'>
<tr>
<th width='200'>Parameter</th><th width='300'>Required</th><th>Description</th>
</tr>
<tr>
<td>phase</td><td>no (default: compile)</td><td>The desired phase can easily be redefined here. Typically used phases are: compile, test and prepare-package.</td></tr>
<tr>
<td>goal</td><td>yes</td><td>Always "replace".</td></tr>
<tr>
<td>outputDir</td><td>no</td><td>Destination directory relative to the baseDir for all replaced files to be written to.</td></tr>
<tr>
<td>outputBasedir</td><td>no</td><td>Destination directory relative to the execution directory for all replaced files to be written to. Use with outputDir to have files written to a specific base location.</td></tr>
<tr><td>basedir</td><td>no</td><td>Path to base relative files for replacements from. This feature is useful for multi-module projects.</td></tr>
<tr>
<td>preserveDir</td><td>no (default: true)</td><td>Preserve the directory structure of files found with the includes parameter when writing to the outputDir.</td></tr>
<tr>
<td>quiet</td><td>no (default: false)</td><td>Stops printing a summary of files that have had replacements performed upon them when true.</td></tr>
<tr>
<td>ignoreErrors</td><td>no (default: false)</td><td>Ignore any errors with missing files or any other exceptions caused by this plugin. The Maven build will continue and log an error if ignoreErrors is set to true and an error occurs.</td></tr>
<tr>
<td>maxReplacements</td><td>no</td><td>Maximum number of files allowed for replacements.</td></tr>
</table>

### Selecting which files to perform replacements upon ###
<table cellpadding='5' width='800' border='1'>
<tr>
<th width='200'>Parameter</th><th width='300'>Required</th><th>Description</th>
</tr>
<tr>
<td>includes:include</td><td>(see note below)</td><td>List of files to include for multiple (or single) replacement. In Ant format (<code>*</code>/directory/<code>*</code><code>*</code>.properties) and works in conjunction with basedir. Note: There is no longer a default for basedir and this must be an absolute path if not using a basedir. </td></tr>
<tr>
<td>excludes:exclude</td><td>no</td><td>List of files to exclude (must have some includes) for multiple (or single) replacement. In Ant format (<code>*</code><code>*</code>/directory/do-not-replace.properties). The files replaced will be derived from the list of includes and excludes. Files not found are ignored by default.</td></tr>
<tr>
<td>filesToInclude</td><td>(see note below)</td><td>List of comma separated files to include for multiple (or single) replacement. In Ant format (<code>*</code>/directory/<code>*</code><code>*</code>.properties). Files not found are ignored by default. </td></tr>
<tr>
<td>filesToExclude</td><td>no</td><td>List of comma separated files to exclude (must have some includes) for multiple (or single) replacement. In Ant format (<code>*</code><code>*</code>/directory/do-not-replace.properties). The files replaced will be derived from the list of includes and excludes.</td></tr>
<tr>
<td>file</td><td>(see note below)</td><td>Path to single file to replace tokens in. The file must be text (ascii). Based on current execution path.</td></tr>
<tr>
<td>encoding</td><td>no (default: uses maven property ${project.build.sourceEncoding})</td><td>File encoding used for reading and writing files different from the ${project.build.sourceEncoding}.</td></tr>
<tr>
<td>ignoreMissingFile</td><td>no (default: false)</td><td>Set to true to not fail build if the file is not found. First checks if file exists and exits without attempting to replace anything. Only usable with file parameter.</td></tr>
<tr>
<td>outputFile</td><td>no (default: file)</td><td>The input file is read and the final output (after replacing tokens) is written to this file. The path and file are created if it does not exist. If it does exist, the contents are overwritten. You should not use outputFile when using a list of includes.</td></tr>
<tr>
<td>inputFilePattern</td><td>no</td><td>Regular expression to match upon the name of the file being replaced. Use groups to match the fragments to make use of within outputFilePattern. This parameter will have no effect without outputFilePattern also being used.</td></tr>
<tr>
<td>outputFilePattern</td><td>no</td><td>Expression for making use of the matched groups from inputFilePattern for creating your output file's name. This parameter will have no effect without inputFilePattern also being used. Ensure you are using the Java standard's group identifies (i.e. $1, $2, etc).</td></tr>
</table>

Note: You must either supply a file, or list of includes (excludes are optional).

### Parameters for replacing text ###
<table cellpadding='5' width='800' border='1'>
<tr>
<th width='200'>Parameter</th><th width='300'>Required</th><th>Description</th>
</tr>
<tr>
<td>token</td><td>(see note below)</td><td>The text to replace within the given file. This may or may not be a regular expression (see regex notes above).</td></tr>
<tr>
<td>tokenValueMap</td><td>(see note below)</td><td>A file containing tokens and respective values to replace with. This file may contain multiple entries to support a single file containing different tokens to have replaced. Each token/value pair should be in the format: "token=value" (without quotations). If your token contains ='s you must escape the = character to \=. e.g. tok\=en=value</td></tr>
<tr>
<td>variableTokenValueMap</td><td>(see note below)</td><td>Similar to tokenValueMap but incline configuration inside the pom. Token/Value pairs are separated by a comma (",") instead of new lines. This parameter may contain multiple entries to support a single file containing different tokens to have replaced. Each token/value pair should be in the format: "token1=value1,token2=value2" (without quotations). </td></tr>
<tr>
<td>commentsEnabled</td><td>no (default: true)</td><td>Enables comments within the tokenValueMap (denoted by '#'). If your token starts with an '#', then you must supply the commentsEnabled parameter and with a value of false.</td></tr>
<tr>
<td>tokenFile</td><td>(see note below)</td><td>A file containing the token to be replaced. May be multiple words or lines. This is useful if you do not wish to expose the token within your pom or the token is long.</td></tr>
<tr>
<td>value</td><td>no (default: empty)</td><td>The text to be written over any found tokens. If no value is given, the tokens found are replaced with an empty string (effectively removing any tokens found). You can also reference grouped regexp matches made in the token here by $1, $2, etc.</td></tr>
<tr>
<td>valueFile</td><td>no</td><td>A file containing a value to replace the given token with. May be multiple words or lines. This is useful if you do not wish to expose the value within your pom or the value is long.</td></tr>
<tr>
<td>regex</td><td>no (default: true)</td><td>Indicates if the token should be located with regular expressions. This should be set to false if the token contains regex characters which may miss the desired tokens or even replace the wrong tokens.</td></tr>
<tr>
<td>regexFlags:regexFlag</td><td>no (list cannot be empty if used)</td><td>List of standard Java regular expression Pattern flags (see <a href='http://java.sun.com/j2se/1.5.0/docs/api/java/util/regex/Pattern.html#field_summary'>Java Doc</a>). May contain multiple flags. May only be of the following: CANON_EQ, CASE_INSENSITIVE, COMMENTS, DOTALL, LITERAL, MULTILINE, UNICODE_CASE, UNIX_LINES.</td></tr>
<tr>
<td>replacements:replacement</td><td>no (list of token/value pairs to replace within the given file)</td><td>Each replacement element to contain sub-elements as token/value pairs. Each token within the given file will be replaced by it's respective value.</td></tr>
<tr>
<td>unescape</td><td>no (does not unescape by default)</td><td>Unescapes tokens and values which contain escaped special characters. e.g. token\n123 would match tokens: token(new line)123.</td></tr>
<tr>
<td>delimiters:delimiter</td><td>no</td><td>Add a list of delimiters which are added on either side of tokens to match against. e.g. @ would match @token@. You may also use the '<b>' character to place the token in the desired location for matching. e.g. ${</b>} would match ${token}.</td></tr>
<tr>
<td>xpath</td><td>no</td><td>Use X-Path to locate a document node to perform replacements upon. This parameter takes an X-Path expression. Since 1.4.0 - <a href='https://code.google.com/p/maven-replacer-plugin/issues/detail?id=58'>Issue 58</a>.</td></tr>
</table>

Note: Either a token, tokenFile or tokenValueMap is required. An execution should not contain more than one of these.


---

### Examples ###

Complete examples can be found in the plugin's test utility. http://code.google.com/p/maven-replacer-plugin/source/browse/test-plugin-use/pom.xml

**Single regex file replacement with output file:**
```
<build>
    <plugins>
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
                <ignoreMissingFile>true</ignoreMissingFile>
                <file>target/someapp/jsp/helloWorld.jsp</file>
                <outputFile>
                    target/someapp/jsp/helloWorld-updated.jsp
                </outputFile>
                <regex>false</regex>
                <token>$BUILD_NUMBER$</token>
                <value>${buildNumber}</value>
            </configuration>
        </plugin>
        ...
    </plugins>
</build>
```

**Using regular expression flags:**
```
<build>
    <plugins>
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
                <file>target/someapp/jsp/helloWorld.jsp</file>
                <token>ToKeN</token>
                <value>value</value>
                <regexFlags>
                    <regexFlag>CASE_INSENSITIVE</regexFlag>
                    <regexFlag>MULTILINE</regexFlag>
                </regexFlags>
            </configuration>
        </plugin>
        ...
    </plugins>
</build>
```

**Multiple file replacement:**
```
<build>
    <plugins>
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
                <includes>
                    <include>target/**/replace-me.*</include>
                </includes>
                <excludes>
                    <exclude>target/**/do-not-replace-me.*</exclude>
                </excludes>
                <token>TOKEN</token>
                <value>VALUE</value>
            </configuration>
        </plugin>
        ...
    </plugins>
</build>
```

**Single file replacement using a tokenValueMap:**
```
<build>
    <plugins>
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
                <file>target/classes/database.properties</file>
                <tokenValueMap>etc/${environment}/database.conf</tokenValueMap>
            </configuration>
        </plugin>
        ...
    </plugins>
</build>
```

**Multiple token/value replacements per file:**
```
<build>
    <plugins>
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
                <file>target/classes/database.properties</file>
                <replacements>
                    <replacement>
                        <token>token1</token>
                        <value>value1</value>
                    </replacement>
                    <replacement>
                        <token>token2</token>
                        <value>value2</value>
                    </replacement>
                </replacements>                        
            </configuration>
        </plugin>
        ...
    </plugins>
</build>
```

Notes on some difficulties integrating maven-replacer-plugin into your build:

The phase and time of execution depends largely on the desired use and the type of packaging. There can be issues with WAR packaging and replacing content at the right time to be included up by the WAR (See [Usage with other plugins](UsageWithOtherPlugins.md)).