# Releasing to Central #

  * Walk through from Sonatype: https://docs.sonatype.org/display/Repository/Sonatype+OSS+Maven+Repository+Usage+Guide

  * Usage of GnuPG: http://www.sonatype.com/people/2010/01/how-to-generate-pgp-signatures-with-maven/

  * Public key name at hkp://pgp.mit.edu - A08C6B0A.

  * Use oss.sonatype.org for Nexus Central sync

Commands to build and deploy to staging OSS Sonatype:
```
mvn clean release:prepare -B -Dgpg.passphrase=(project owner password)
mvn release:perform -Dgpg.passphrase=(project owner password)
```