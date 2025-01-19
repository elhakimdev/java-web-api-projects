package com.sass.erp.finance.cash.api_service.database.seeders;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Component
public abstract class BaseSeeder {
    private final Set<Class<? extends BaseSeeder>> calledSeeders = new HashSet<>();

    @Autowired
    private ApplicationContext applicationContext;

    @SafeVarargs
    public final void call(Class<? extends BaseSeeder>... seederClasses) {
        for (Class<? extends BaseSeeder> seederClass : seederClasses) {
            if (!calledSeeders.contains(seederClass)) {
                try {
                    // Use ApplicationContext to get the bean instance
                    BaseSeeder seeder = applicationContext.getBean(seederClass);
                    seeder.run();
                    calledSeeders.add(seederClass);
                } catch (Exception e) {
                    throw new RuntimeException("Failed to execute seeder: " + seederClass.getName(), e);
                }
            }
        }
    }

    public abstract void run();
}
