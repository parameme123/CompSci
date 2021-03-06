package com.fileencryptor;

import java.io.File;
import java.util.concurrent.Executors;

import javax.servlet.MultipartConfigElement;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.handler.HandlerWrapper;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;
import org.springframework.boot.autoconfigure.web.MultipartAutoConfiguration;
import org.springframework.boot.autoconfigure.web.MultipartProperties;
import org.springframework.boot.context.embedded.jetty.JettyEmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.jetty.JettyServerCustomizer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;

/**
 * 
 *Java Config for Java Beans 
 */


@SuppressWarnings("unused")
@Configuration
@ComponentScan("com.fileencryptor")
@Import({ThymeleafAutoConfiguration.class, DispatcherServlet.class, StandardServletMultipartResolver.class })
@EnableWebMvc
public class AppConfig extends WebMvcConfigurerAdapter implements ApplicationContextAware {

	private ApplicationContext applicationContext;

	/** max file size increased from 1mb to 25mb to apply to gmail size limit
	 * 
	 * @return MultipartConfigElement multipartConfigElement
	 */

	@Bean
	public MultipartConfigElement multipartConfigElement() {
		MultipartProperties multipartProperties = new MultipartProperties();
		multipartProperties.setMaxFileSize("25MB");
		multipartProperties.setMaxRequestSize("25MB");
		multipartProperties.setFileSizeThreshold("25MB");
		return multipartProperties.createMultipartConfig();
	}

	/**
	 * Creates a ViewResolver allows HTML to be render and viewed.
	 * 
	 * @return ViewResolver
	 */

	@Bean
	public ViewResolver viewResolver() {
		ThymeleafViewResolver resolver = new ThymeleafViewResolver();
		resolver.setTemplateEngine(templateEngine());

		return resolver;
	}



	@Bean
	public TaskScheduler taskExecutor () {
		return new ConcurrentTaskScheduler(Executors.newScheduledThreadPool(3));
	}

	/**
	 * Tells the server to user Spring template Engine
	 * 
	 * @return TemplateEngine
	 */

	@Bean
	public TemplateEngine templateEngine() {
		SpringTemplateEngine engine = new SpringTemplateEngine();
		engine.setEnableSpringELCompiler(true);
		engine.setTemplateResolver(templateResolver());

		return engine;
	}

	/**
	 * Creates a JettyEmbededServer to expose endpoints e.g /file or /gmail
	 * 
	 * @return JettyEmbeddedServletContainerFactory
	 */

	@Bean
	public JettyEmbeddedServletContainerFactory jetty() {
		JettyEmbeddedServletContainerFactory factory = new JettyEmbeddedServletContainerFactory();
		factory.setPort(80);
		factory.addServerCustomizers(new JettyServerCustomizer() {

			@Override
			public void customize(Server server) {
				 SslContextFactory sslContextFactory = new SslContextFactory();
                 sslContextFactory.setKeyStorePath("jetty.pkcs12");

                 sslContextFactory.setKeyStorePassword("marcopolo123");
                 sslContextFactory.setKeyStoreType("PKCS12");

                 sslContextFactory.setKeyStorePassword("**********");
                 sslContextFactory.setKeyStoreType("PKCS12");


                 ServerConnector sslConnector = new ServerConnector(	server, sslContextFactory);
                 sslConnector.setPort(443);
                 server.setConnectors(new Connector[] { sslConnector });
				
				
				setHandlerMaxHttpPostSize(2000 * 1024 * 1024, server.getHandlers());
				
			}
			
			
			 private void setHandlerMaxHttpPostSize(int maxHttpPostSize,
                    Handler... handlers) {
                for (Handler handler : handlers) {
                    if (handler instanceof ContextHandler) {
                        ((ContextHandler) handler)
                                .setMaxFormContentSize(maxHttpPostSize);
                    }
                    else if (handler instanceof HandlerWrapper) {
                        setHandlerMaxHttpPostSize(maxHttpPostSize,
                                ((HandlerWrapper) handler).getHandler());
                    }
                    else if (handler instanceof HandlerCollection) {
                        setHandlerMaxHttpPostSize(maxHttpPostSize,
                                ((HandlerCollection) handler).getHandlers());
                    }
                }
            }
			
		});
		factory.setDocumentRoot(new File("web"));
		return factory;
	}


	/**
	 * Tells the resolver to user HTML parsing *reading the html source*
	 * @return  ITemplateResolver
	 */

	@Bean
	public ITemplateResolver templateResolver() {
		SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
		resolver.setApplicationContext(applicationContext);
		resolver.setCacheable(false);
		resolver.setTemplateMode(TemplateMode.HTML);
		return resolver;
	}

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

		this.applicationContext = applicationContext;

	}
}
