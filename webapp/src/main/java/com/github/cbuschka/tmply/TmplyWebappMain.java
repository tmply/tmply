
package com.github.cbuschka.tmply;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

@EnableAutoConfiguration
@Configuration
@ComponentScan(basePackageClasses = {TmplyWebappMain.class})
@PropertySource(value = {"classpath:application.properties", "classpath:application.${env}.properties"})
@EnableScheduling
@EnableJpaRepositories
@EnableTransactionManagement
@EnableWebMvc
public class TmplyWebappMain extends SpringBootServletInitializer
{
	public static void main(String[] args)
	{
		SpringApplication app = new SpringApplication(TmplyWebappMain.class);
		app.run(args);
	}

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException
	{
		super.onStartup(servletContext);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application)
	{
		return application.sources(TmplyWebappMain.class);
	}
}
