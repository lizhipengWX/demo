package com.example.demo;

import com.example.demo.Controller.CommonUtil;
import com.example.demo.pojo.Template;
import com.example.demo.pojo.TemplateParam;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Test {
    private static String token;

    public static void main(String[] args) {
        Template tem = new Template();
        tem.setTemplateId("w04t2KwaRVx3Be60CCtHIgettbmdWsKKxUbX8HRRago");
        tem.setTopColor("#00DD00");
        tem.setToUser("oBM7GvybXmfpCunq64r5aRMpbQZk");
        tem.setUrl("");
        List<TemplateParam> paras = new ArrayList<TemplateParam>();
        paras.add(new TemplateParam("first", "我们已收到您的货款，开始为您打包商品，请耐心等待: )", "#FF3333"));
        paras.add(new TemplateParam("orderMoneySum", "¥20.00", "#0044BB"));
        paras.add(new TemplateParam("orderProductName", "火烧牛干巴", "#0044BB"));
        paras.add(new TemplateParam("Remark", "感谢你对我们商城的支持!!!!", "#AAAAAA"));
        tem.setTemplateParamList(paras);
        System.out.println(tem.getTemplateParamList().get(0).getValue());
        boolean result = sendTemplateMsg(token, tem);
    }

    public static boolean sendTemplateMsg(String token, Template template) {
        boolean flag=false;
        String requestUrl = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";
        requestUrl=requestUrl.replace("ACCESS_TOKEN","27_XFGvTuhR4W8vYM6iGULkeNM8WYrqT0fHXxUXEjJJml4-jnvLiGTYaY5cF8Rc0aE-iDKiFs9ylnfufGnqa38wcsMhhTBaUiSVU5taB4ixDo3C_0pkYb9gGYaCh_H3oD3P8kJzjCu7V4PqDeTSKSQaACAXFD");
        System.out.println(template.getTemplateParamList().get(0).getValue());
        JSONObject jsonResult=CommonUtil.httpsRequest(requestUrl, "GET", template.toJSON());
        if (jsonResult != null) {
            System.out.println(jsonResult);
            flag = true;
        } else {
            String errorCode = jsonResult.getString("errcode");
            String errorMessage = jsonResult.getString("errmsg");
            System.out.println("消息发送失败:" + errorCode + "," + errorMessage);
            flag = false;
        }
        return flag;

       /* boolean flag = false;

        String requestUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx22ee18ad62d60871&secret=c9e4ede5c3de4d283476dd09f5e7b541";
        requestUrl = requestUrl.replace("ACCESS_TOKEN", token);

        JSONObject jsonResult = CommonUtil.httpsRequest(requestUrl, "GET", template.toJSON());
        if (jsonResult != null) {
            System.out.println(jsonResult);
            flag = true;
        } else {
            String errorCode = jsonResult.getString("errcode");
            String errorMessage = jsonResult.getString("errmsg");
            System.out.println("消息发送失败:" + errorCode + "," + errorMessage);
            flag = false;
        }
        return flag;*/
    }
}
