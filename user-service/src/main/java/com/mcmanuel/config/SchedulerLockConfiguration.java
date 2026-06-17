package com.mcmanuel.config;

@Configuration
public class SchedulerLockConfiguration {

    import net.javacrumbs.shedlock.provider.jdbctemplate.JdbcTemplateLockProvider;

    @Bean
    public LockProvider lockProvider(DataSource dataSource) {
        return new JdbcTemplateLockProvider(
                JdbcTemplateLockProvider.Configuration.builder()
                        .withJdbcTemplate(new JdbcTemplate(dataSource))
                        .usingDbTime() // Works on Postgres, MySQL, MariaDb, MS SQL, Oracle, DB2, HSQL and H2
                        .build()
        );
    }
}
