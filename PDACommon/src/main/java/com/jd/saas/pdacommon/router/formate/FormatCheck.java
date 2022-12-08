package com.jd.saas.pdacommon.router.formate;

import com.jd.saas.pdacommon.router.ConstantUtil;
import com.jd.saas.pdacommon.router.beans.HostDO;
import com.jd.saas.pdacommon.router.beans.RouterProtocolDO;
import com.jd.saas.pdacommon.router.enums.HostTypeEnum;
import com.jd.saas.pdacommon.router.exception.InvokeException;

import java.util.regex.Pattern;

/**
 * @author majiheng
 */
public abstract class FormatCheck {
    //host正则格式
    private static final String ROUTER_HOST_PATTERN = "^(([a-zA-Z0-9_-])+(\\.)?([a-zA-Z0-9_-])+)+$";
    //path正则格式
    private static final String ROUTER_PATH_PATTERN = "^([A-Za-z0-9-_\\/])+$";

    /**
     * 判断下发路由地址是否正确
     *
     * @param routerUrl
     * @param existType
     * @return
     */
    public static boolean isRouterUrlError(String routerUrl, boolean existType) {
        Boolean urlError;
        RouterProtocolDO protocolDO = ProtocolFormat.routerUrlToObj(routerUrl);
        String scheme = protocolDO.getScheme();
        //scheme不区分大小写
        if (ConstantUtil.ROUTER_SCHEME.equalsIgnoreCase(scheme)) {
        } else {
            throw new InvokeException(102, "路由地址scheme标识错误");
        }
        if (existType) {
            String host = protocolDO.getHost();
            HostDO hostDO = ProtocolFormat.hostToObj(host);
            HostTypeEnum typeEnum = HostTypeEnum.enumValueOf(hostDO.getType());
            if (typeEnum == null) {
                throw new InvokeException(102, "路由地址type标识错误");
            } else {
            }
        }
        String path = protocolDO.getPath();
        boolean pathError = isPathError(path);
        if (pathError) {
            throw new InvokeException(102, "路由地址path错误");
        } else {
            urlError = Boolean.FALSE;
        }
        return urlError;
    }

    /**
     * 判断下发路由地址是否正确,existType默认为true
     *
     * @param routerUrl
     * @return
     */
    public static boolean isRouterUrlError(String routerUrl) {
        return isRouterUrlError(routerUrl, true);
    }

    /**
     * 判断path格式是否正确
     *
     * @param path
     * @return
     */
    public static boolean isPathError(String path) {
        if (path == null || path.trim().equals("")) {
            //处理错误
            throw new InvokeException(102, "path参数不能为空！");
        }
        return !Pattern.matches(ROUTER_PATH_PATTERN, path);
    }

    /**
     * 判断host格式是否正确
     *
     * @param host
     * @return
     */
    public static boolean isHostError(String host) {
        if (host == null || host.trim().length() == 0) {
            //处理错误
            throw new InvokeException(102, "host参数不能为空！");
        }
        return !Pattern.matches(ROUTER_HOST_PATTERN, host);
    }
}
