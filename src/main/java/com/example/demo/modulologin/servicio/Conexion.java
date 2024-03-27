package com.example.demo.modulologin.servicio;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;


@Component
public class Conexion {
    private final XmlRpcClient client = new XmlRpcClient();
    private final XmlRpcClientConfigImpl common_config = new XmlRpcClientConfigImpl();

    @Value("${odoo.url}")
    private String url;

    @Value("${odoo.db}")
    private String db;

    @Value("${odoo.username}")
    private String username;

    @Value("${odoo.password}")
    private String password;


    public int devolverUid() throws XmlRpcException, MalformedURLException{
        common_config.setServerURL(new URL(String.format("%s/xmlrpc/2/common", url)));
        int uid = (int)client.execute(common_config, "authenticate", Arrays.asList(db, username, password, Collections.emptyMap()));
        return uid;
    }

    public XmlRpcClient crearConexion() throws MalformedURLException{
        final XmlRpcClient models = new XmlRpcClient() {{
            setConfig(new XmlRpcClientConfigImpl() {{
                setServerURL(new URL(String.format("%s/xmlrpc/2/object", url)));
            }});
        }};
        return models;
    }

    public String getUrl() {
        return url;
    }

    public String getDb() {
        return db;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
