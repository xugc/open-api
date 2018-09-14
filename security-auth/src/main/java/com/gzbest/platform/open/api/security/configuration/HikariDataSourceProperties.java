package com.gzbest.platform.open.api.security.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "db.statement")
public class HikariDataSourceProperties {
	private boolean cachePrepStmts;
	private int prepStmtCacheSize;
	private int prepStmtCacheSqlLimit;

	public boolean isCachePrepStmts() {
		return cachePrepStmts;
	}

	public void setCachePrepStmts(boolean cachePrepStmts) {
		this.cachePrepStmts = cachePrepStmts;
	}

	public int getPrepStmtCacheSize() {
		return prepStmtCacheSize;
	}

	public void setPrepStmtCacheSize(int prepStmtCacheSize) {
		this.prepStmtCacheSize = prepStmtCacheSize;
	}

	public int getPrepStmtCacheSqlLimit() {
		return prepStmtCacheSqlLimit;
	}

	public void setPrepStmtCacheSqlLimit(int prepStmtCacheSqlLimit) {
		this.prepStmtCacheSqlLimit = prepStmtCacheSqlLimit;
	}

}
