package com.playtomic.tests;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(profiles = "test")
public class AcceptanceTest {

    @LocalServerPort
    private Integer port = 0;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
        beforeEach();
    }

    protected void beforeEach() {
    }
}
