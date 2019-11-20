package com.example.demo.Controller;

import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;
/**
 * * 通用工具类
 * *
 * * @author liufeng
 */
public class CommonUtil {

    private static Logger log = LoggerFactory.getLogger(CommonUtil.class);


    public static JSONObject httpsRequest(String token_url, String requestMethod, String outputStr) {

/*
        String requestUrl="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx22ee18ad62d60871&secret=c9e4ede5c3de4d283476dd09f5e7b541";
*/

        JSONObject jsonObject = null;
        try {
            // 创建 SSLContext 对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = {new MyX509TrustManager()};
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // 从上述 SSLContext 对象中得到 SSLSocketFactory 对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();

            URL url = new URL(token_url);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setSSLSocketFactory(ssf);

            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);

            // 设置请求方式（GET/POST）
            conn.setRequestMethod(requestMethod);

            // 当 outputStr 不为 null 时，向输出流写数据
            if (null != outputStr) {
                OutputStream outputStream = conn.getOutputStream();

                // 注意编码格式
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }

            // 从输入流读取返回内容
            InputStream inputStream = conn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            StringBuffer buffer = new StringBuffer();

            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }

            // 释放资源
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            inputStream = null;
            conn.disconnect();
            jsonObject = JSONObject.fromObject(buffer.toString());
        } catch (ConnectException ce) {
            log.error(" 连接超时：{}", ce);
        } catch (Exception e) {
            log.error("https 请求异常：{}", e);
        }

        return jsonObject;
    }
}