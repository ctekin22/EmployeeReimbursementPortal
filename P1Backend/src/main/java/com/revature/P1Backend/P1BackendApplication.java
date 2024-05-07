package com.revature.P1Backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
Employee Reimbursement System (ERS)
The Employee Reimbursement System (ERS) is a Java Full Stack application designed to streamline
the reimbursement process within an organization. It provides a platform for employees to submit reimbursement requests
which can then be reviewed and approved or denied by managers. The system utilizes a React-based front end
that communicates via HTTP to a Spring-based back end, with data stored in PostgreSQL database.
 */

@SpringBootApplication
// The @SpringBootApplication annotation combines @Configuration, @EnableAutoConfiguration, and @ComponentScan,
// providing a convenient way to configure Spring Boot applications. It automatically configures the Spring application
// context based on the dependencies present in the classpath.

@EntityScan("com.revature.models")
// The @EntityScan annotation specifies the base package for scanning JPA entity classes. In this case, it tells Spring
// to look for entity classes within the "com.revature.models" package and its subpackages.

@ComponentScan("com.revature")
// The @ComponentScan annotation tells Spring to scan the specified package and its subpackages for Spring-managed components
// such as controllers, services, and repositories.

@EnableJpaRepositories("com.revature.daos")
// The @EnableJpaRepositories annotation enables Spring Data JPA repositories, allowing us to use JpaRepository and other
// repository interfaces. It specifies the base package where Spring Data JPA should scan for repository interfaces.
public class P1BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(P1BackendApplication.class, args);
	}

}
