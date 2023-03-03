package com.example.pafassessment.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class RedisConfig {
    
        // value redis host from appln.properties
        @Value("${spring.redis.host}")
        private String redisHost;
    
        // value redis port from appln.properties
        @Value("${spring.redis.port}")
        private Optional<Integer> redisPort;
    
        @Value("${spring.redis.username}")
        private String redisUsername;
    
        @Value("${spring.redis.password}")
        private String redisPassword;
    
        @Bean
        @Scope("singleton")
        public RedisTemplate<String, Object> redisTemplate(){
            final RedisStandaloneConfiguration config 
                    = new RedisStandaloneConfiguration();
    
            config.setHostName(redisHost);
            config.setPort(redisPort.get());
            System.out.println(redisUsername);
            System.out.println(redisPassword);
            
            if(!redisUsername.isEmpty() && !redisPassword.isEmpty()){
                config.setUsername(redisUsername);
                config.setPassword(redisPassword);
            }
            config.setDatabase(0);
    
            final JedisClientConfiguration jedisClient = JedisClientConfiguration
                                            .builder()
                                            .build();
            final JedisConnectionFactory jedisFac= 
                                new JedisConnectionFactory(config, jedisClient);
            jedisFac.afterPropertiesSet();
            RedisTemplate<String, Object> redisTemplate = 
                        new RedisTemplate<String, Object>();
            // associate with the redis connection
            redisTemplate.setConnectionFactory(jedisFac);
            redisTemplate.setKeySerializer(new StringRedisSerializer());
            // set the map key/value serialization type to String
            redisTemplate.setHashKeySerializer(new StringRedisSerializer());
            // enable redis to store java object on the value column
            RedisSerializer<Object> objSerializer 
                    = new JdkSerializationRedisSerializer(getClass().getClassLoader());
    
            redisTemplate.setValueSerializer(objSerializer);
            redisTemplate.setHashValueSerializer(objSerializer);
            
            return redisTemplate;
        }
    }
    
