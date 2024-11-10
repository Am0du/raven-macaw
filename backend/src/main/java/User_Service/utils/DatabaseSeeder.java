package User_Service.utils;

import User_Service.entity.Role;
import User_Service.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class DatabaseSeeder implements CommandLineRunner {
    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        if (isDatabaseEmpty()) {
            System.out.println("Database is empty, seeding.");
            seedDatabase();
        } else {
            System.out.println("Database is not empty, skipping seeding.");
        }
    }

    public boolean isDatabaseEmpty() {
        return roleRepository.count() == 0;
    }

    public void seedDatabase(){
        Role roleAdmin = new Role();
        roleAdmin.setRole("ROLE_ADMIN");

        Role roleUser = new Role();
        roleUser.setRole("ROLE_USER");

        roleRepository.saveAll(Arrays.asList(roleAdmin, roleUser));
    }

}