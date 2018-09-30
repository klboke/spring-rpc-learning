package com.spring.rpc.ws.provider;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpPrincipal;

import java.net.URI;
import java.util.Base64;

/**
 * Created by kl on 2018/9/30.
 * Content :身份认证 ip白名单 + 用户名密码
 */

public class Authenticator extends com.sun.net.httpserver.Authenticator {

    private String ipList = "127.0.0.1";
    private String userName = "";
    private String passWord = "";

    @Override
    public Result authenticate(HttpExchange httpExchange) {
        String ip =  httpExchange.getRemoteAddress().getAddress().getHostAddress();
        if(ipList.contains(ip)){
            URI uri = httpExchange.getRequestURI();
            if(uri.toString().contains("?WSDL") || uri.toString().contains("?wsdl")){
                return new Success(new HttpPrincipal("",""));
            }else {
                Headers headers = httpExchange.getRequestHeaders();
                String authorization = headers.getFirst("Authorization");
                int var4 = authorization.indexOf(32);
                if (var4 != -1 && authorization.substring(0, var4).equals("Basic")) {
                    byte[] var5 = Base64.getDecoder().decode(authorization.substring(var4 + 1));
                    String var6 = new String(var5);
                    int var7 = var6.indexOf(58);
                    String username = var6.substring(0, var7);
                    String password = var6.substring(var7 + 1);
                    if (userName.equals(username) && passWord.equals(password)) {
                        return new Success(new HttpPrincipal(username, password));
                    } else {
                        return new Failure(401);
                    }
                } else {
                    return new Failure(401);
                }
            }
        }else {
            return new Failure(403);
        }
    }

    public void setIpList(String ipList) {
        this.ipList = ipList;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
}
