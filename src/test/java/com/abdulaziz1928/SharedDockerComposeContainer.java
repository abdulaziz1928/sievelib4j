package com.abdulaziz1928;

import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;

import java.io.File;
import java.time.Duration;

class SharedDockerComposeContainer {

    private static final DockerComposeContainer<?> dockerComposeContainer;

    static {
        dockerComposeContainer = new DockerComposeContainer(new File("src/test/resources/compose/docker-compose.yml"))
                .withBuild(true)
                .withExposedService("dovecot", 4190, Wait.forListeningPort().withStartupTimeout(Duration.ofSeconds(60)))
                .withStartupTimeout(Duration.ofSeconds(120));

        dockerComposeContainer.start();
        Runtime.getRuntime().addShutdownHook(new Thread(dockerComposeContainer::stop));
    }

    public static DockerComposeContainer<?> getInstance() {
        return dockerComposeContainer;
    }
}