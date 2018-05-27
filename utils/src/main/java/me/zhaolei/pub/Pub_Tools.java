package me.zhaolei.pub;

import me.zhaolei.demo.base.BaseMessage;
import me.zhaolei.demo.base.SuperBootCode;
import me.zhaolei.demo.base.SuperBootStatus;
import me.zhaolei.utils.ReflectionUtils;
import me.zhaolei.utils.SnowflakeIdWorker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * <b> 项目共用的方法 </b>
 * <p>
 * 功能描述:
 * </p>
 *
 * @author jesion
 * @date 2017/9/6
 * @time 11:48
 * @Path org.superboot.Pub_Tools
 */
@Component
public class Pub_Tools {
    /**
     * 工作中心ID 0-31
     */
    @Value("${Snowflake.workerId}")
    private  long workerId;

    /**
     * 数据中心ID 0-31
     */
    @Value("${Snowflake.datacenterId}")
    private  long datacenterId;

    @Resource
    Pub_LocalTools local;

    public  long genUUID() {
        SnowflakeIdWorker idWorker0 = new SnowflakeIdWorker(workerId, datacenterId);
        return idWorker0.nextId();
    }


    /**
     * 获取对象方法上的注解信息
     *
     * @param o               对象信息
     * @param annotationClass 注解类
     * @param methodName      方法名称
     * @return
     */
    public  Annotation getMothodAnnotationByAnnotation(Object o, Class annotationClass, String methodName) {
        Method[] methods = o.getClass().getDeclaredMethods();
        for (Method method : methods) {
            //获取传入的方法
            if (method.getName().toLowerCase().equals(methodName.toLowerCase())){

                //判断是否为ID的注解方法
                if (null != method.getAnnotation(annotationClass)) {
                    return method.getAnnotation(annotationClass);
                }
            }
        }
        return null;
    }

    /**
     * 获取字段上的注解信息
     *
     * @param o               对象信息
     * @param annotationClass 注解类
     * @param fieldName       字段名称
     * @return
     */
    public  Annotation getFieldAnnotationByAnnotation(Object o, Class annotationClass, String fieldName) {
        Field[] fields = o.getClass().getDeclaredFields();
        for (Field field : fields) {
            //获取传入的字段
            if (field.getName().toLowerCase().equals(fieldName.toLowerCase())){

                //判断是否为ID的注解方法
                if (null != field.getAnnotation(annotationClass)) {
                    return field.getAnnotation(annotationClass);
                }
            }
        }
        return null;
    }


    /**
     * 通过反射的方式设置字段的值
     *
     * @param fieldName 字段名称
     * @param value     字段值
     * @param o         实体对象
     * @return
     */
    public Object setFieldValue(String fieldName, Object value, Object o) throws IllegalAccessException {
        Field[] fields = o.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.getName().endsWith(fieldName)) {
                Object val = ReflectionUtils.getFieldValue(o, fieldName);
                if (null == val) {
                    ReflectionUtils.setFieldValue(o, fieldName, value);
                }
            }
        }
        return o;
    }


    /**
     * 生成消息相应信息
     *
     * @param code    响应码
     * @param status  响应状态
     * @param message 消息提示
     * @param data    消息体
     * @return
     */
    public BaseMessage genMsg(int code, int status, String message, Object data) {
        BaseMessage basemessage = new BaseMessage();
        basemessage.setCode(code);
        basemessage.setStatus(status);
        basemessage.setMessage(message);
        basemessage.setData(data);
        return basemessage;
    }


    /**
     * 生成成功消息相应信息
     *
     * @param data 消息体
     * @return
     */
    public BaseMessage genOkMsg(Object data) {
        return genMsg(
                SuperBootCode.OK.getCode(),
                SuperBootStatus.OK.getCode(),
                local.getMessage(SuperBootCode.OK.getCode()),
                data);
    }

    /**
     * 生成成功消息相应信息
     *
     * @param code 提示码
     * @param data 消息体
     * @return
     */
    public BaseMessage genOkMsg(int code, Object data) {
        return genMsg(
                code,
                SuperBootStatus.OK.getCode(),
                local.getMessage(code),
                data);
    }

    /**
     * 生成成功消息相应信息
     *
     * @param code    响应状态
     * @param message 响应结果
     * @param data    数据体
     * @return
     */
    public BaseMessage genOkMsg(int code, String message, Object data) {
        return genMsg(
                code,
                SuperBootStatus.OK.getCode(),
                message,
                data);
    }


    /**
     * 生成失败消息相应信息
     *
     * @param code    响应码
     * @param message 消息提示
     * @param data    消息体
     * @return
     */
    public BaseMessage genNoMsg(int code, String message, Object data) {
        return genMsg(
                code,
                SuperBootStatus.NO.getCode(),
                message,
                data);
    }

    /**
     * 生成失败消息相应信息,使用国际化配置显示错误信息
     *
     * @param code 响应码
     * @param data 消息体
     * @return
     */
    public BaseMessage genNoMsg(int code, Object data) {
        return genMsg(
                code,
                SuperBootStatus.NO.getCode(),
                local.getMessage(code),
                data);
    }

    /**
     * 生成失败消息相应信息,使用国际化配置显示错误信息
     *
     * @param sc 异常信息
     * @return
     */
    public BaseMessage genNoMsg(SuperBootCode sc) {
        String message = local.getMessage(sc.getCode());
        return genMsg(
                sc.getCode(),
                SuperBootStatus.NO.getCode(),
                message,
                message);
    }

    /**
     * 生成失败消息相应信息,使用国际化配置显示错误信息
     *
     * @param sc 异常信息
     * @param data 消息体
     * @return
     */
    public BaseMessage genNoMsg(SuperBootCode sc,Object data) {
        String message = local.getMessage(sc.getCode());
        return genMsg(
                sc.getCode(),
                SuperBootStatus.NO.getCode(),
                message,
                data);
    }



    /**
     * 特殊字符转义，实现对一些特殊字符的转义操作，防止处理的时候出错
     *
     * @param text
     * @return
     */
    public static String transfString(String text) {

        if (null != text) {
            text = text.replace(">", "&gt;");
            text = text.replace("<", "&lt;");
            text = text.replace(" ", "&nbsp;");
            text = text.replace("\"", "&quot;");
            text = text.replace("\'", "&#39;");
            text = text.replace("\\", "\\\\");
            text = text.replace("\n", "\\n");
            text = text.replace("\r", "\\r");
        }

        return text;
    }


    /**
     * 转义还原，在RSA解密的时候转义的字符需要还原，否则会解密出错
     *
     * @param text
     * @return
     */
    public static String transfBack(String text) {

        if (null != text) {
            text = text.replace("&gt;", ">");
            text = text.replace("&lt;", "<");
            text = text.replace("&nbsp;", " ");
            text = text.replace("&quot;", "\"");
            text = text.replace("&#39;", "\'");
            text = text.replace("\\\\", "\\");
            text = text.replace("\\n", "\n");
            text = text.replace("\\r", "\r");
        }
        return text;
    }
}
