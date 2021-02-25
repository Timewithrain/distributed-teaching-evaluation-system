package com.watermelon.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class AccessFilter extends ZuulFilter {

    @Override
    public String filterType() {
        //请求路由前执行
        return "pre";
    }

    @Override
    public int filterOrder() {
        //顺序执行
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        //true保证调用run()
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        HttpSession session = request.getSession();
        Object userName = session.getAttribute("UserName");
        if (userName!=null){
            context.setSendZuulResponse(true); // 对该请求进行路由
            return null;
        }
        context.setSendZuulResponse(false); // 不对其进行路由转发
        return null;
    }
}
