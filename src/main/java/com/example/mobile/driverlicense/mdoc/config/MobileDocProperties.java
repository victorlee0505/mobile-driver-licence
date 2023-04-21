package com.example.mobile.driverlicense.mdoc.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "mobile-doc")
@Getter
@Setter
public class MobileDocProperties {
    boolean ageInYears;
    boolean ageBirthYear;
    List<Integer> ageOverNn;
}
