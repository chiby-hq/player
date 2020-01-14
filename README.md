# Chiby Playar

Chiby Playar is a container-controller daemon that provides a playlist model for container applications.
It allows to run a sequence of applications, feed them with parameters, grant them security privileges selectively etc...

# How to use

Playar is automatically deployed via Jitpack.
To include Playar in your projects, add the following to your project POM :

    <repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://jitpack.io</url>
		</repository>
	</repositories>

	
Then refer to any of the playar modules using Jitpack GAV :

    <dependency>
        <groupId>com.github.chibyhq.playar</groupId>
        <artifactId>playar-client</artifactId>
        <version>${playar.version}</version>
    </dependency>

# Hacking Playar

## How to release

This project uses the maven gitflow plugin.

Simply execute :

    ./mvnw gitflow:release-start

Perform all required changes to refine the release, then execute :

    ./mvnw gitflow:release-finish
    
    
This will tag the code, perform a jitpack deploy.


