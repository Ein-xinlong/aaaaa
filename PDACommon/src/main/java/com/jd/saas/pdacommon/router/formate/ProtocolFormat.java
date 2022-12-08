package com.jd.saas.pdacommon.router.formate;

import com.jd.saas.pdacommon.log.Logger;
import com.jd.saas.pdacommon.router.beans.HostDO;
import com.jd.saas.pdacommon.router.beans.RouterProtocolDO;
import com.jd.saas.pdacommon.router.enums.HostTypeEnum;
import com.jd.saas.pdacommon.router.exception.InvokeException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author majiheng
 * **注意该类只处理自定义协议逻辑，其它协议不能调用该类方法
 * scheme://type.model/page1/page2?params1=xxx&params2=xxx&…
 */
public abstract class ProtocolFormat {
    //host=type+model，中间间隔符为点号
    private static final String ROUTER_DOT = ".";
    //协议字符串格式
    private static final String ROUTER_PROTOCOL_FORMAT = "%s://%s/%s";
    //host字符串格式
    private static final String ROUTER_HOST_FORMAT = "%s.%s";
    //path是否存在多个/，并进行处理成一个
    private static final String ROUTER_SPACER_PATTERN = "\\/{2,}";
    //斜杠字符串
    private static final String SPACER_STRING = "/";

    /**
     * 处理path多余斜杠
     *
     * @param pagePath
     * @return
     */
    public static String pathFormat(String pagePath) {
        if (pagePath == null) {
            throw new InvokeException(101, "路由path不能为空!");
        }
        String path = pagePath.trim();
        Pattern pattern = Pattern.compile(ROUTER_SPACER_PATTERN);
        Matcher m = pattern.matcher(path);
        String newPath = path;
        while (m.find()) {
            String val = m.group();
            newPath = newPath.replaceFirst(val, SPACER_STRING);
        }
        if (SPACER_STRING.equals(newPath)) {
            throw new InvokeException(102, "路由path格式异常!");
        }
        //处理第一位为斜杠
        String firstVal = newPath.substring(0, 1);
        if (SPACER_STRING.equals(firstVal)) {
            newPath = newPath.substring(1);
        }
        //处理最后一位为斜杠
        int length = newPath.length();
        int secondEnd = length - 1;
        String endVal = newPath.substring(secondEnd);
        if (SPACER_STRING.equals(endVal)) {
            newPath = newPath.substring(0, secondEnd);
        }
        return newPath;
    }

    /**
     * 根据参数转换为协议格式地址
     *
     * @param routerUrl
     * @return
     */
    public static String toRouterUrlNotType(String routerUrl) {
        if (routerUrl == null || "".equals(routerUrl.trim())) {
            throw new InvokeException(101, "路由地址不能为空!");
        }
        RouterProtocolDO protocolDO = routerUrlToObj(routerUrl);
        String host = protocolDO.getHost();
        HostDO hostDO = hostToObj(host);
        String type = hostDO.getType();
        return routerUrl.replaceFirst(type + ROUTER_DOT, "");
    }

    /**
     * 获取没有type的路由地址
     *
     * @param scheme
     * @param model
     * @param pagePath
     * @return
     */
    public static String toRouterUrl(String scheme, String model, String pagePath) {
        if (scheme == null || "".equals(scheme.trim())) {
            //异常处理
            throw new InvokeException(101, "scheme参数不能为空！");
        }

        //验证pagePath参数格式
        boolean pathError = FormatCheck.isPathError(pagePath);
        if (pathError) {
            throw new InvokeException(101, "path参数格式异常！");
        }
        return String.format(ROUTER_PROTOCOL_FORMAT, scheme, model, pagePath);
    }

    /**
     * 根据协议路径将地址解析成各个参数
     *
     * @param routerUrl
     * @return
     */
    public static RouterProtocolDO routerUrlToObj(String routerUrl) {
        if (routerUrl == null || "".equals(routerUrl.trim())) {
            throw new InvokeException(101, "路由地址不能为空!");
        }
        routerUrl = routerUrl.trim();
        URI uri;
        try {
            uri = new URI(routerUrl);
        } catch (URISyntaxException e) {
            Logger.e("router:",e.getMessage());
            throw new InvokeException(101, "路由地址异常!");
        }
        String scheme = uri.getScheme();
        String host = uri.getHost();
        String path = uri.getPath();
        String paramStr = uri.getQuery();
        RouterProtocolDO protocolDO = new RouterProtocolDO();
        protocolDO.setScheme(scheme);
        protocolDO.setHost(host);
        protocolDO.setPath(path);
        protocolDO.setParmas(paramStr);
        return protocolDO;
    }

    /**
     * 根据host字符串获取Host对象属性
     *
     * @param host
     * @return
     */
    public static HostDO hostToObj(String host) {
        host = host.trim();
        Logger.d("router:","=====host======" + host);
        //验证格式是否正确
        boolean hostError = FormatCheck.isHostError(host);
        HostDO hostDO;
        if (hostError) {
            throw new InvokeException(101, "host协议格式异常,请检查协!");
        } else {
            //解析host
            int length = host.length();
            int indexDot = host.indexOf(ROUTER_DOT);
            if (indexDot > 0 && indexDot < length - 1) {
                //通过截取字符串获取两个参数，type符合枚举值，model符合字符串
                String type = host.substring(0, indexDot);
                String model = host.substring(indexDot + 1);
                HostTypeEnum hostTypeEnum = HostTypeEnum.enumValueOf(type);
                if (hostTypeEnum == null) {
                    throw new InvokeException(101, "没有找到匹配的协议,请检查协类型!");
                }
                hostDO = new HostDO();
                hostDO.setType(type);
                hostDO.setModel(model);
            } else {
                throw new InvokeException(101, "host不符合协议规范，格式异常!");
            }
        }
        return hostDO;
    }

    /**
     * 根据参数转换为host字符串
     *
     * @param typeEnum
     * @param model
     * @return
     */
    public static String toHostStr(HostTypeEnum typeEnum, String model) {
        if (typeEnum == null) {
            // 处理异常
            throw new InvokeException(101, "host参数类型不能为空！");
        }
        if (model == null || "".equals(model.trim())) {
            // 处理异常
            throw new InvokeException(101, "host参数模块不能为空！");
        } else {
            model = model.trim();
        }
        String hostStr = String.format(ROUTER_HOST_FORMAT, typeEnum.toName(), model);
        Logger.d("router:",hostStr);
        return hostStr;
    }
}
