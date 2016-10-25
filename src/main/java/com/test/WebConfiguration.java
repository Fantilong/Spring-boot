package com.test;

import org.apache.catalina.filters.RemoteIpFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import java.util.List;

/**
 * Created by fantilong on 25/10/2016.
 */
@Configuration
public class WebConfiguration extends WebMvcConfigurerAdapter{
    /**
     * 添加Tomcat8提供的对应过滤器：RemoteIpFilter(将代理服务器发来的包含IP地址的请求转换成真正的用户Ip)
     * 通过将RemoteIpFilter加入过滤器调用链即可使用它
     * @ConponentScan 让Spring boot扫描WebConfiguration类并将它加入到程序的上下文中，因此我们在WebConfiguration中定义的Bean
     * 就跟在BookPubApplication中定义的一样
     * 当Spring boot检测到有javax.servlet.Filter的Bean时就会自动加入过滤器调用链，
     * Spring boot会一次加入这几个过滤器用于处理请求；
     * 1、characterEncodingFilter（用于处理编码问题）
     * 2、hiddenHttpMathodFilter（隐藏Http函数）
     * 3、httpPutFormContentFilter、requestContextFilter（请求上下文）
     * 4、RemoteIpFilter
     * 所有过滤器的调用顺序跟添加相反，过滤器实现的是责任链模式。
     * @return
     */
    @Bean
    public RemoteIpFilter remoteIpFilter(){
        return new RemoteIpFilter();
    }

    /**
     * Servlet过滤器属于Servlet API ，和Spring关系不大，除了使用过滤器包装Web请求Spring MVC还提供了HandlerInterceptor（拦截器）工具
     * HandlerInterceptor跟过滤器功能相似但提供了更精准的控制能力，request在响应前、响应后、及视图渲染之前、request全部结束之后。拦截器都
     * 不能修改request的内容但可以通过抛出异常（或返回false）来暂停request的执行
     * 常用拦截器：
     * 1、LocaleChangeInterceptor（用于国际化配置）
     * 2、ThemeChangeInterceptor
     * 3、可以自定义拦截器
     * 步骤：
     * 添加拦截器不仅是在WebConfiguration中定义Bean，Spring提供了基础类WebMvcConfigurerAdapter（不明白为什么要继承他）
     * 1、继承WebMvcConfigurerAdapter
     * 2、为localeChangeInterceptor添加@Bean定义，（只是添加了一个Interceptor的spring Bean，Spring不会自动将它添加到调用链中）
     * 3、拦截器需要手动添加到调用链
     * @return
     */
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor(){
        return new LocaleChangeInterceptor();//返回一个拦截器实例
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());//添加到调用链
    }

    /**
     * 订制Http消息转换器（Http输入请求格式，转换器按格式转换为JAVA对象。JAVA对象输出请求转换，按格式转换java对象）
     * 解析Http的输入流解析为JAVA对象与java对象解析为Http的输出流
     * Spring boot通过底层的HttpMessageConverters依靠Jackson库将JAVA实体类输出为JSON格式，
     * 当有多个转换器格式可用时，根据消息对象类型和需求的内容类型选择最合适的转换器。
     *
     * 有些消息转换器只支持多个数据类型，有的只支持多个输出格式。也有两者兼具的，例如：
     * 1、MappingJackson2HttpMessageConverter可以将java对象转换成application/json
     * 2、ProtobufHttpMessageConverter仅支持com.google.protobuf.Message类型的输入，
     * 但是可以输出application/json、application/xml、text/plain、application/x-protobuf
     *
     * 添加消息转换器有三种方法
     */
//    @Bean
//    public ByteArrayHttpMessageConverter byteArrayHttpMessageConverter(){
//        /**
//         * 方法一：
//         * @Bean 定义HttpMessageConverter是向项目中添加消息转换器的最简便的方法
//         * Spring扫描到HttpMessageConverter的Bean之后就会自动将它添加到调用链中，
//         * 推荐让WebConfiguration继承WebMvcConfigurerAdapter
//         */
//        return new ByteArrayHttpMessageConverter();
//    }

//    @Override
//    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//        /**
//         * 方法二：
//         * 重写configureMessageConverters方法，扩展现有的消息转换器链表
//         * 通过configureMessageConverters方法添加自定义转换器很方便，但是如果项目中存在多个WebMvcConfigurers的实例
//         * （我们自己定义的，或者Spring Boot默认提供的）,不能确保configureMessageConverters方法按照固定顺序执行。
//         */
//        converters.add(new ByteArrayHttpMessageConverter());
//    }

//    @Override
//    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
//        /**
//         * 方法三：
//         * 更精确的控制，重写extendMessageConverters方法，先清空转换器列表，再加入自定义的转换器
//         *如果需要更精细的控制：清除其他消息转换器或者清楚重复的转换器，可以通过重写extendMessageConverters完成，
//         * 仍然有这种可能：别的WebMvcConfigurer实例也可以重写这个方法，但是这种几率非常小。
//         */
//        converters.clear();
//        converters.add(new ByteArrayHttpMessageConverter());
//    }
}










