package com.abdulaziz1928;

import com.fluffypeople.managesieve.ManageSieveClient;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.testcontainers.containers.DockerComposeContainer;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AbstractManageSieveTest {
    protected static final DockerComposeContainer<?> dockerComposeInstance =
            SharedDockerComposeContainer.getInstance();
    protected ManageSieveClient client;

    @SneakyThrows
    @BeforeAll
    void init() {
        client = new ManageSieveClient();
        client.connect("localhost", 4190);
        client.authenticate("testuser@example.com", "testpass");
    }

    protected static String getFailureMessage(String script, String errorMessage) {
        return String.format("script failed : \n %s \nError Message:\n %s", script, errorMessage);
    }

    protected static String getMimeMessage(){
        return "From: emily.summers@example.com\n" +
                "Content-Type: multipart/alternative; boundary=\"----=_Part_527_1082391.1720785600000\"\n" +
                "MIME-Version: 1.0\n" +
                "\n" +
                "------=_Part_527_1082391.1720785600000\n" +
                "Content-Type: text/plain; charset=\"UTF-8\"\n" +
                "Content-Transfer-Encoding: quoted-printable\n" +
                "\n" +
                "Hi there,\n" +
                "\n" +
                "Thanks for your message. I’m currently out of the office and will be back on June 20th.\n" +
                "\n" +
                "I’ll get back to you as soon as possible upon my return.\n" +
                "\n" +
                "Warm regards,  \n" +
                "Emily Summers\n" +
                "\n" +
                "------=_Part_527_1082391.1720785600000\n" +
                "Content-Type: text/html; charset=\"UTF-8\"\n" +
                "Content-Transfer-Encoding: quoted-printable\n" +
                "\n" +
                "<html>\n" +
                "  <body style=\"font-family: Arial, sans-serif; font-size: 14px; color: #333;\">\n" +
                "    <p>Hi there,</p>\n" +
                "    <p>Thanks for your message. I’m currently <strong>out of the office</strong> and will be back on <em>June 20th</em>.</p>\n" +
                "    <p>I’ll get back to you as soon as possible upon my return.</p>\n" +
                "    <p>Warm regards,<br/>Emily Summers</p>\n" +
                "  </body>\n" +
                "</html>\n" +
                "\n" +
                "------=_Part_527_1082391.1720785600000--\n" +
                ".\n";
    }

    @SneakyThrows
    @AfterAll
    void disconnect() {
        client.logout();
    }

}
