package com.sass.erp.finance.cash.api_service.database.seeders;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class DatabaseSeeder extends BaseSeeder {

    protected Logger logger = LoggerFactory.getLogger(DatabaseSeeder.class);

    @Override
    public void run() {
        this.logger.info("Call Seeder: {} For: {}", DatabaseSeeder.class, UserSeeder.class);
        this.call(
                UserSeeder.class
        );
    }
}
