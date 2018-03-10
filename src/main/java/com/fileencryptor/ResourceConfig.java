package com.fileencryptor;
import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
/**
 * Tells the server where to look for resources like images or boxes. 
 * This also sets a timer where there isnt cache for the HTML files
 * @author Ricky
 *
 */
@Configuration
public class ResourceConfig extends WebMvcConfigurerAdapter {
	@Configuration
	public class ResourcesConfig extends WebMvcConfigurerAdapter {
		public void addResourceHandlers(ResourceHandlerRegistry registry) {
			registry.addResourceHandler(new String[] { "/**" }).addResourceLocations(new String[] { "/" })
					.setCacheControl(CacheControl.maxAge(0, TimeUnit.MILLISECONDS));
		}
	}
}
