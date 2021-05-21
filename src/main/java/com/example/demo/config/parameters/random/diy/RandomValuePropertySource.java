package com.example.demo.config.parameters.random.diy;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.util.Random;
import java.util.UUID;

public class RandomValuePropertySource extends PropertySource<Random> {

    /**
     * Name of the random {@link PropertySource}.
     */
    public static final String RANDOM_PROPERTY_SOURCE_NAME = "random";
    //定义前缀
    private static final String PREFIX = "random.";

    private static final Log logger = LogFactory.getLog(RandomValuePropertySource.class);

    //构造函数初始了一个 Random 对象
    public RandomValuePropertySource(String name) {
        super(name, new Random());
    }

    //默认无参的构造函数
    public RandomValuePropertySource() {
        this(RANDOM_PROPERTY_SOURCE_NAME);
    }

    //最核心的方法，以 random 为前缀配置为例，这里的 name 就是 random. 后面的值，比如int、int(10)
    @Override
    public Object getProperty(String name) {
        //不是指定的前缀直接返回空
        if (!name.startsWith(PREFIX)) {
            return null;
        }
        if (logger.isTraceEnabled()) {
            logger.trace("Generating random property for '" + name + "'");
        }
        //调用方法处理各种各种类型的随机值
        return getRandomValue(name.substring(PREFIX.length()));
    }

    private Object getRandomValue(String type) {
        //处理默认int随机值
        if (type.equals("int")) {
            return getSource().nextInt();
        }
        //处理默认long随机值
        if (type.equals("long")) {
            return getSource().nextLong();
        }
        //处理默认指定大小范围int随机值，比如int(10),int(10,100)
        String range = getRange(type, "int");
        if (range != null) {
            return getNextIntInRange(range);
        }
        range = getRange(type, "long");
        if (range != null) {
            return getNextLongInRange(range);
        }
        //生成uuid
        if (type.equals("uuid")) {
            return UUID.randomUUID().toString();
        }
        return getRandomBytes();
    }

    //解析各种类型的范围
    private String getRange(String type, String prefix) {
        if (type.startsWith(prefix)) {
            int startIndex = prefix.length() + 1;
            if (type.length() > startIndex) {
                return type.substring(startIndex, type.length() - 1);
            }
        }
        return null;
    }

    //int指定范围随机值的具体处理方法
    private int getNextIntInRange(String range) {
        String[] tokens = StringUtils.commaDelimitedListToStringArray(range);
        int start = Integer.parseInt(tokens[0]);
        if (tokens.length == 1) {
            return getSource().nextInt(start);
        }
        return start + getSource().nextInt(Integer.parseInt(tokens[1]) - start);
    }

    //long指定范围随机值的具体处理方法
    private long getNextLongInRange(String range) {
        String[] tokens = StringUtils.commaDelimitedListToStringArray(range);
        if (tokens.length == 1) {
            return Math.abs(getSource().nextLong() % Long.parseLong(tokens[0]));
        }
        long lowerBound = Long.parseLong(tokens[0]);
        long upperBound = Long.parseLong(tokens[1]) - lowerBound;
        return lowerBound + Math.abs(getSource().nextLong() % upperBound);
    }

    private Object getRandomBytes() {
        byte[] bytes = new byte[32];
        getSource().nextBytes(bytes);
        return DigestUtils.md5DigestAsHex(bytes);
    }

    //添加到当前的环境，实际自定义的时候可能不会用到此方法
    public static void addToEnvironment(ConfigurableEnvironment environment) {
        environment.getPropertySources().addAfter(
                StandardEnvironment.SYSTEM_ENVIRONMENT_PROPERTY_SOURCE_NAME,
                new RandomValuePropertySource(RANDOM_PROPERTY_SOURCE_NAME));
        logger.trace("RandomValuePropertySource add to Environment");
    }
}