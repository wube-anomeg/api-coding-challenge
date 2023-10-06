package com.mdbank.api.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                title = "ABC Bank API Documentation",
                contact = @Contact(
                        name = "Wube Kifle",
                        email = "wube@anomeg.com"
                ),
                license = @License()),
        servers = @Server(url = "http://localhost:8080")
)
public class OpenAPIConfiguration {
}
