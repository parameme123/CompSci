package FileEncr;
import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

public class ResourceConfig extends WebMvcConfigurerAdapter {

	@Configuration
	public class ResourcesConfig extends WebMvcConfigurerAdapter {

		public void addResourceHandlers(ResourceHandlerRegistry registry) {

			registry.addResourceHandler(new String[] { "/**" }).addResourceLocations(new String[] { "/" })
					.setCacheControl(CacheControl.maxAge(0, TimeUnit.MILLISECONDS));

		}

	}
}
