package com.example.demo.modulologin.servicio;
import com.example.demo.modulologin.modelo.modeloodoo.Producto;
import com.example.demo.modulologin.modelo.modeloodoo.Stock;
import com.example.demo.modulologin.utils.Logs;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.springframework.stereotype.Service;

@Service
public class Servicio {
    private Conexion conection;
    private Logs logs;

    public Servicio(Conexion conection) {
        this.conection = conection;
    }

    public List<Stock> devolverStock() throws MalformedURLException, XmlRpcException, JsonProcessingException {
        int uid = conection.devolverUid();
        XmlRpcClient models = conection.crearConexion();

        List<Object> registroStock = Arrays.asList(
                (Object[]) models.execute("execute_kw", Arrays.asList(conection.getDb(), uid, conection.getPassword(),
                        "stock.quant", "search_read", Arrays.asList(Arrays.asList()), new HashMap() {
                            {
                                put("fields", Arrays.asList("product_id", "quantity"));
                            }
                        })));

        List<Stock> listaStock = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();

        for (Object elemento : registroStock) {
            @SuppressWarnings("unchecked")
            HashMap<String, Object> miMapa = objectMapper.convertValue(elemento, HashMap.class);
            @SuppressWarnings("unchecked")
            List<Object> arreglo_id = (List<Object>) miMapa.get("product_id");
            int product_id = (int) arreglo_id.get(0);
            double cantidad = (double) miMapa.get("quantity");
            if (cantidad > 0) {
                Stock stock = new Stock(product_id, cantidad);
                listaStock.add(stock);
            }
        }
        return listaStock;
    }

    public List<Producto> devolverProductos() throws MalformedURLException, XmlRpcException, JsonProcessingException {
        int uid = conection.devolverUid();
        XmlRpcClient models = conection.crearConexion();

        List<Object> registros = Arrays.asList(
                (Object[]) models.execute("execute_kw", Arrays.asList(conection.getDb(), uid, conection.getPassword(),
                        "product.template", "search_read", Arrays.asList(Arrays.asList()), new HashMap() {
                            {
                                put("fields",
                                        Arrays.asList("name", "list_price", "categ_id", "description_sale", "barcode", "image_1024"));
                            }
                        })));

        List<Producto> listaProducto = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();

        for (Object elemento : registros) {
            @SuppressWarnings("unchecked")
            HashMap<String, Object> miMapa = objectMapper.convertValue(elemento, HashMap.class);
            @SuppressWarnings("unchecked")
            List<Object> arreglo_id = (List<Object>) miMapa.get("categ_id");
            int id = (int) miMapa.get("id");
            String name = (String) miMapa.get("name");
            double list_price = (double) miMapa.get("list_price");
            String categ_id = (String) arreglo_id.get(1);
            String description_sale = obtenerString(miMapa.get("description_sale"));
            String barcode = obtenerString(miMapa.get("barcode"));
            String image_1024 = obtenerString(miMapa.get("image_1024"));
            Producto product = new Producto(id, name, list_price, categ_id, description_sale, barcode, image_1024);
            listaProducto.add(product);

        }
        return listaProducto;
    }

    public List<Producto> actualizarProductosConStock(List<Producto> listaProducto, List<Stock> listaStock, String username, String modulo) throws IOException {
        Logs logs = new Logs();
        logs.devolverLog(username, modulo);
        
        for (Stock stock : listaStock) {
            int product_id = stock.getProduct_id();
            double quantity = stock.getQuantity();
            for (Producto producto : listaProducto) {
                if (producto.getId() == product_id) {
                    producto.setStock(quantity);
                    break;
                }
            }
        }
        return listaProducto;
    }

    public List<Producto> actualizarProductosConStockDos(List<Producto> listaProducto, List<Stock> listaStock) {
        
        for (Stock stock : listaStock) {
            int product_id = stock.getProduct_id();
            double quantity = stock.getQuantity();
            for (Producto producto : listaProducto) {
                if (producto.getId() == product_id) {
                    producto.setStock(quantity);
                    break;
                }
            }
        }
        return listaProducto;
    }

    public Producto buscarProducto(int id, String username, String modulo) throws XmlRpcException, IOException {
        Logs logs = new Logs();
        logs.devolverLog(username, modulo);
        List<Producto> listaLocal = actualizarProductosConStockDos(devolverProductos(), devolverStock());
        for (Producto product : listaLocal) {
            if (product.getId() == id) {
                return product;
            }
        }
        return null;
    }

    public void actualizarStock(int id, double quantity) throws XmlRpcException, MalformedURLException {
        int uid = conection.devolverUid();
        XmlRpcClient models = conection.crearConexion();

        Object[] arregloResultado = (Object[]) models.execute("execute_kw",
                Arrays.asList(conection.getDb(), uid, conection.getPassword(), "stock.quant", "search",
                        Arrays.asList(Arrays.asList(Arrays.asList("product_id", "=", id)))));
    
        List<Object> ids= Arrays.asList(arregloResultado);
    
        models.execute("execute_kw", Arrays.asList(conection.getDb(), uid, conection.getPassword(), "stock.quant", "write",
                Arrays.asList(Arrays.asList(ids.get(0)), new HashMap<String, Object>() {{
                        put("quantity", quantity);
                    }})));
    }
    
    
    private String obtenerString(Object valor) {
        if (valor instanceof Boolean) {
            return Boolean.toString((Boolean) valor);
        } else if (valor instanceof String) {
            return (String) valor;
        } else {
            return null;
        }
    }

}