package com.jnu.student.myclass;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class DataDownload {
    public String download(String url_) {
        try {
            // 创建URL对象
            URL url = new URL(url_);

            // 打开连接
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // 设置请求方法（GET、POST等）
            connection.setRequestMethod("GET");

            // 设置连接和读取超时时间（可选）
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            // 发起连接
            connection.connect();

            // 获取响应码
            int responseCode = connection.getResponseCode();

            // 如果响应码为200表示请求成功
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // 获取字节输入流
                InputStream inputStream = connection.getInputStream();

                // 读取字节输入流中的字符数据
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                // 将分散字符整理为一个字符串
                String responseData = stringBuilder.toString();

                // 关闭输入流和连接
                reader.close();
                inputStream.close();
                connection.disconnect();

                Log.v("下载完成：",responseData);

                // 返回下载的数据
                return responseData;
            } else {
                Log.v("下载失败！", String.valueOf(responseCode));
                // 处理请求失败的情况
                // ...
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public ArrayList<ShopLocation> parseJsonObjects(String content) {

        ArrayList<ShopLocation> locations = new ArrayList<>();
        try {
            Gson gson = new Gson();
            // 准备类型
            Type shopListType = new TypeToken<ShopList>() {}.getType();
            ShopList temp = gson.fromJson(content, shopListType);
            locations = temp.getShops();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return locations;
    }

    public class ShopList {
        private ArrayList<ShopLocation> shops;

        public ArrayList<ShopLocation> getShops() {
            return shops;
        }

        public void setShops(ArrayList<ShopLocation> shops) {
            this.shops = shops;
        }
    }
}
