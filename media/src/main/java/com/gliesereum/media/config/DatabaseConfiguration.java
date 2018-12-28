package com.gliesereum.media.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author yvlasiuk
 * @version 1.0
 * @since 08/10/2018
 */
@Configuration
@EnableJpaRepositories("com.gliesereum.media.model.repository")
public class DatabaseConfiguration {
}
