package com.canal.annotation;

import com.canal.config.CanalClientConfiguration;
import com.canal.config.CanalConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;



@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({CanalConfig.class, CanalClientConfiguration.class})
public @interface EnableCanalClient {
}
