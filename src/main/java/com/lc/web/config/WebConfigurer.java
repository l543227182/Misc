package com.lc.web.config;

import com.lc.web.converter.ResourceRegionMessageWiter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Configuration
public class  WebConfigurer implements WebFluxConfigurer {
	@Override
	public void configureHttpMessageCodecs(ServerCodecConfigurer configurer) {
		configurer.customCodecs().writer(new ResourceRegionMessageWiter());
	}

}
