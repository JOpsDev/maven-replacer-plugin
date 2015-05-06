# Release Notes #

Note: For more details on each item, please see its Issue or the UsageGuide.

### 1.5.3 - April 16th, 2014 ###
  * Fixed base directory being null from default value. This was preventing absolute paths being used to locate files to replace ([Issue 69](https://code.google.com/p/maven-replacer-plugin/issues/detail?id=69))
  * Fixed default values ([Issue 85](https://code.google.com/p/maven-replacer-plugin/issues/detail?id=85))
  * Added warning message when not enough parameters ([Issue 90](https://code.google.com/p/maven-replacer-plugin/issues/detail?id=90))
  * Added licence to releases ([Issue 84](https://code.google.com/p/maven-replacer-plugin/issues/detail?id=84))
  * Added maxReplacements property ([Issue 86](https://code.google.com/p/maven-replacer-plugin/issues/detail?id=86))

### 1.5.2 - November 17th, 2012 ###
  * Updated copyright date ([Issue 71](https://code.google.com/p/maven-replacer-plugin/issues/detail?id=71))
  * Added help goal ([Issue 72](https://code.google.com/p/maven-replacer-plugin/issues/detail?id=72))
  * Fixed default values ([Issue 74](https://code.google.com/p/maven-replacer-plugin/issues/detail?id=74))
  * Fixed use of project.build.outputDirectory property ([Issue 75](https://code.google.com/p/maven-replacer-plugin/issues/detail?id=75))
  * Added skip goal ([Issue 77](https://code.google.com/p/maven-replacer-plugin/issues/detail?id=77))

### 1.5.1 - September 18th, 2012 ###
  * Fixed using project source encoding property as default encoding. ([Issue 68](https://code.google.com/p/maven-replacer-plugin/issues/detail?id=68)).
  * Fixed input and output file encodings being missed with some configurations. ([Issue 70](https://code.google.com/p/maven-replacer-plugin/issues/detail?id=70)).

### 1.5.0 - March 13th, 2012 ###
  * Renamed artifactId to "replacer" from "maven-replacer-plugin" due to naming policy change from the Maven team. ([Issue 62](https://code.google.com/p/maven-replacer-plugin/issues/detail?id=62)).
  * Fixed [Issue 62](https://code.google.com/p/maven-replacer-plugin/issues/detail?id=62) where tokenValueMap was not allowing absolute paths to this file.
  * Enhancement to support dynamic output file's name based on the respective input file's name. This is achieved with inputFilePattern and outputFilePattern parameters which are regular expressions for output file's name to be replaced with. ([Issue 28](https://code.google.com/p/maven-replacer-plugin/issues/detail?id=28)).

### 1.4.1 - February 3rd, 2012 ###
  * Enhancement to accept different file encodings than the system default. Effects reading and writing files. (Optional parameter for [Issue 61](https://code.google.com/p/maven-replacer-plugin/issues/detail?id=61)).

### 1.4.0 - November 22nd, 2011 ###
  * Enhancement to support X-Path locating of XML fragments to perform replacements upon. ([Issue 58](https://code.google.com/p/maven-replacer-plugin/issues/detail?id=58)).

### 1.3.9 - September 18th, 2011 ###
  * Fixed [Issue 53](https://code.google.com/p/maven-replacer-plugin/issues/detail?id=53) where tokenValueMap file was ignoring the set basedir property.
  * Fixed [Issue 56](https://code.google.com/p/maven-replacer-plugin/issues/detail?id=56) where Maven 3 reports warnings about not knowing if this plugin is thread safe.
  * Enhancement to ignore any errors (ignoreErrors) caused by the plugin and continue with the build. ([Issue 54](https://code.google.com/p/maven-replacer-plugin/issues/detail?id=54)).

### 1.3.8 - May 13th, 2011 ###
  * Fixed [Issue 51](https://code.google.com/p/maven-replacer-plugin/issues/detail?id=51) where output to an outside directory on Windows OS types was not working.
  * Minor error handling fixes.

### 1.3.7 - February 27th, 2011 ###
  * Fixed [Issue 47](https://code.google.com/p/maven-replacer-plugin/issues/detail?id=47) where 1.3.6 was only usable with Java 1.6.
  * Enhancement to allow tokenValueMap to be expressed within the pom internally. Implemented new parameter: variableTokenValueMap to support this. Note: Each token/value pair is separated by a comma instead of new lines to separate. ([Issue 48](https://code.google.com/p/maven-replacer-plugin/issues/detail?id=48)).
  * Fixed [Issue 49](https://code.google.com/p/maven-replacer-plugin/issues/detail?id=49) where it was difficult to write to outside directories. When a outputFile begins with / the output file will be written based on the system root.

### 1.3.6 - February 4th, 2011 ###
  * Enhancement to add quiet mode. Now logs a report rather than per file logging. ([Issue 43](https://code.google.com/p/maven-replacer-plugin/issues/detail?id=43)).
  * Enhancement to add optional delimiters to tokens for matching. Delimiters can either wrap a token simply or add specific prefixes and suffixes to tokens. ([Issue 44](https://code.google.com/p/maven-replacer-plugin/issues/detail?id=44)).
  * Enhancement to support optional unescaping special characters to match tokens and the replacement values. ([Issue 45](https://code.google.com/p/maven-replacer-plugin/issues/detail?id=45)).

### 1.3.5 - November 28th, 2010 ###
  * Fixed [Issue 41](https://code.google.com/p/maven-replacer-plugin/issues/detail?id=41).
  * Fixed [Issue 40](https://code.google.com/p/maven-replacer-plugin/issues/detail?id=40) - ignoreMissingFile parameter now only usable with file parameter.

### 1.3.4 - November 15th, 2010 ###
  * Enhancement to add preserving of directory structure (by default) when using includes and outputDir. Files written to the outputDir will by default preserve their parent directories found during the includes file find. ([Issue 38](https://code.google.com/p/maven-replacer-plugin/issues/detail?id=38)).
  * Enhancement to add an outputBasedir in combination with outputDir. When used, the basedir is ignored and files are written to whatever the outputBasedir appended with the outputDir. This allows the files to be written to the execution path. ([Issue 37](https://code.google.com/p/maven-replacer-plugin/issues/detail?id=37)).

### 1.3.3 - October 30th, 2010 ###
  * Enhancement to support comments (lines starting with #) and changed format of tokenValueMap to token=value (from line separated tokens and values). ([Issue 36](https://code.google.com/p/maven-replacer-plugin/issues/detail?id=36)).
  * Enhancement to support an output directory (outputDir parameter). All replaced files are written to the outputDir (flat structure and based from baseDir) when specified. ([Issue 37](https://code.google.com/p/maven-replacer-plugin/issues/detail?id=37)).

### 1.3.2 - August 22nd, 2010 ###
  * Fixed [Issue 34](https://code.google.com/p/maven-replacer-plugin/issues/detail?id=34).
  * Fixed [Issue 35](https://code.google.com/p/maven-replacer-plugin/issues/detail?id=35).

### 1.3.1 - April 17th, 2010 ###
  * Enhancement to be able to add includes and excludes as a comma separated list of files/patterns. ([Issue 29](https://code.google.com/p/maven-replacer-plugin/issues/detail?id=29)).
  * Change to tokenValueMap where now formatted with token/value pairs on separate lines to each other, so that tokens and values can contain regular expressions. ([Issue 30](https://code.google.com/p/maven-replacer-plugin/issues/detail?id=30))
  * Enhancement to be able to supply a basedir (which is by default, the basedir used by maven, i.e. "."), which will be used as the base directory for all files used for replacement ([Issue 31](https://code.google.com/p/maven-replacer-plugin/issues/detail?id=31)).

### 1.3 - February 9th, 2010 ###
  * Enhancement to supply multiple replacements per configuration (per file) ([Issue 23](https://code.google.com/p/maven-replacer-plugin/issues/detail?id=23)).
  * Fixed [Issue 26](https://code.google.com/p/maven-replacer-plugin/issues/detail?id=26) where leading and trailing new lines and white spaces were being truncated.

### 1.2 - December 31st, 2009 ###
  * Added ability to supply a list of includes/excludes for multiple file replacement. Given in the standard Ant format ([Issue 5](https://code.google.com/p/maven-replacer-plugin/issues/detail?id=5)).
  * Enhancement to list regular expression flags (from the Java Pattern class) for special handling of token matching ([Issue 21](https://code.google.com/p/maven-replacer-plugin/issues/detail?id=21)).
  * Fixed [Issue 22](https://code.google.com/p/maven-replacer-plugin/issues/detail?id=22) where new directories were not being created for output files.

### 1.1 - November 19th, 2009 ###
  * groupId changed to com.google.code.maven-replacer-plugin to comply with standard Maven repository which will enable this plugin to be uploaded to the main repo. ([Issue 17](https://code.google.com/p/maven-replacer-plugin/issues/detail?id=17))
  * Added ability to supply a tokenValueMap ([Issue 18](https://code.google.com/p/maven-replacer-plugin/issues/detail?id=18)). A tokenValueMap is a supplied file which contains a list of tokens and their respective values to replace with. This allows a file to have different tokens replaced with different values in a single execution. This feature is useful if your file contains several different properties to be replaced.
  * Now supports suppling a file containing the token to match against ([Issue 12](https://code.google.com/p/maven-replacer-plugin/issues/detail?id=12)). This is useful when the token is long or should not be shown in your project pom.
  * Now supports suppling a file containing the value to replace the token with ([Issue 12](https://code.google.com/p/maven-replacer-plugin/issues/detail?id=12)). This is useful when the value is long or should not be shown in your project pom.
  * Fixed Windows path handling ([Issue 16](https://code.google.com/p/maven-replacer-plugin/issues/detail?id=16)).
  * Fixed logging of null when no value given ([Issue 13](https://code.google.com/p/maven-replacer-plugin/issues/detail?id=13)).

### 1.0.3 - October 3rd, 2009 ###
  * Value is no longer required. If no value is supplied, the token is removed entirely  ([Issue 10](https://code.google.com/p/maven-replacer-plugin/issues/detail?id=10)).
  * Fixed inability to replace tokens spanning more than one line ([Issue 10](https://code.google.com/p/maven-replacer-plugin/issues/detail?id=10)).

### 1.0.2 - September 5th, 2009 ###
  * Enhancement to create base path for outputFile if not does not exist ([Issue 9](https://code.google.com/p/maven-replacer-plugin/issues/detail?id=9)).

### 1.0.1 - August 15th, 2009 ###
  * Added optional `<regex>[true, false]</regex>` parameter. Set to true when token contains a regular expression. Set to false if you do not want regular expressions considered within a token. (If not supplied, regex will be true by default) ([Issue 8](https://code.google.com/p/maven-replacer-plugin/issues/detail?id=8)).
  * Added optional `<outputFile>some file</outputFile>` parameter. The modified file contents are written to this file if specified. (If not supplied, replace will overwrite input file) ([Issue 7](https://code.google.com/p/maven-replacer-plugin/issues/detail?id=7)).

### 1.0.0 - July 13th, 2009 ###
  * Added optional ability to skip replacement task if input file not found (the build is continued and no replacement is made) ([Issue 4](https://code.google.com/p/maven-replacer-plugin/issues/detail?id=4)).

### 0.0.9 - March 30th, 2009 ###
  * Fixed bug where files that were replaced did not preserve line feeds. ([Issue 1](https://code.google.com/p/maven-replacer-plugin/issues/detail?id=1)).

### 0.0.8 - March 6th, 2009 ###
  * First public release.