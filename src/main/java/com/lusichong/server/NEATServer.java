package com.lusichong.server;

import com.lusichong.LogicModel;
import com.lusichong.util.Log;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

/**
 * Created by lusichong on 2017/4/27 14:09.
 */
public class NEATServer {

    public static final String TAG = NEATServer.class.getSimpleName();

    public static void main(String[] args) {
        NEATServer server = new NEATServer();
        server.start();
    }

    public void start() {
        try {
            InetSocketAddress addr = new InetSocketAddress(23333);
            HttpServer server = HttpServer.create(addr, 0);
            server.createContext("/", new NeatServerHandler());
            server.setExecutor(Executors.newCachedThreadPool());
            server.start();
            System.out.println("Server is listening on port 23333");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class NeatServerHandler implements HttpHandler {

        LogicModel model = new LogicModel();

        public void handle(HttpExchange exchange) throws IOException {
            String requestMethod = exchange.getRequestMethod();
            if (requestMethod.equalsIgnoreCase("GET")) {
                Headers responseHeaders = exchange.getResponseHeaders();
                responseHeaders.set("Content-Type", "text/plain");
                responseHeaders.add("Access-Control-Allow-Origin", "*");
                exchange.sendResponseHeaders(200, 0);

                String requestUrl = exchange.getRequestURI().toString();
                Log.i(this, "requestUrl: " + requestUrl);
                OutputStream responseBody = exchange.getResponseBody();
                if ("/flow_cluster".equals(requestUrl)) {
                    responseBody.write(model.getFlowCluster().getBytes());
                    Log.i(this, "response to /flow_cluster finished");
                } else if ("/base_cluster".equals(requestUrl)) {
                    responseBody.write(model.getBaseCluster().getBytes());
                    Log.i(this, "response to /base_cluster finished");
                } else {
                    String param = exchange.getRequestURI().getQuery();
                    Log.i(this, "request /generate_trajectory params:" + param);
                    String moCount = param.split("=")[1];
                    Log.i(this, "request /generate_trajectory , mocount:" + moCount);
                    responseBody.write(model.generateTrajectory(Integer.parseInt(moCount)).getBytes());
                    Log.i(this, "response to /generate_trajectory finished");
                }
                switch (requestUrl) {
                    case "/generate_trajectory":

                        break;
                    case "/base_cluster":

                        break;
                    case "/flow_cluster":

                        break;
                    default:

                }
                responseBody.close();
            }
        }
    }


    public String getTestNeatFlowClusterJson() {
        String res = null;
        try {
            BufferedReader br = new BufferedReader(
                    new FileReader("/Users/lusichong/Desktop/NEATHotRoute/neattestjson"));
            String line = "";
            res = "";
            while ((line = br.readLine()) != null) {
                res += line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }


}
