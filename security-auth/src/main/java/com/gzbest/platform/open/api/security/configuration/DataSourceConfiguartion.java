package com.gzbest.platform.open.api.security.configuration;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableConfigurationProperties(HikariDataSourceProperties.class)
public class DataSourceConfiguartion {
	@Bean
	@ConfigurationProperties(prefix = "db")
	public DataSource dataSource(HikariDataSourceProperties properties) {
		HikariDataSource dataSource = new HikariDataSource();
		Properties p = new Properties();
		p.put("cachePrepStmts", properties.isCachePrepStmts());
		p.put("prepStmtCacheSize", properties.getPrepStmtCacheSize());
		p.put("prepStmtCacheSqlLimit", properties.getPrepStmtCacheSqlLimit());
		dataSource.setDataSourceProperties(p);
		return dataSource;
	}
}
