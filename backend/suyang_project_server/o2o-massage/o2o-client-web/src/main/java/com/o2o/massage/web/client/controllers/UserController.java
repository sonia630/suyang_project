package com.o2o.massage.web.client.controllers;


import com.o2o.massage.biz.ClientLoginBiz;
import com.o2o.massage.biz.ClientUserBiz;
import com.o2o.massage.core.common.DeviceInfo;
import com.o2o.massage.core.common.WebRespResult;
import com.o2o.massage.core.exception.BizException;
import com.o2o.massage.model.request.GetVerifyCodeRequest;
import com.o2o.massage.web.client.aop.ClientUserIdentify;
import com.o2o.massage.web.client.aop.EnumRequiredUserType;
import com.o2o.massage.web.common.context.ApiRequestMethod;
import com.o2o.massage.web.common.context.ClientRequestContext;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;
import java.util.Random;

@Controller
@RequestMapping("/user")
@Api(description = "用户相关接口")
public class UserController {

    @Autowired
    private ClientLoginBiz loginBiz;

    @Autowired
    private ClientUserBiz userBiz;

    @RequestMapping(value = "/captcha", method = RequestMethod.GET)
    @ApiOperation(value = "获取图形验证码")
    public void getCapcha(HttpServletRequest request, HttpServletResponse response) throws BizException, IOException {
        //1.定义BufferedImage对象
        //2.获得Graphics
        //3.通过Random长生随机验证码信息
        //4.使用Graphics绘制图片
        //5.记录验证码信息到session中
        //6.使用ImageIO输出图片
        BufferedImage bi = new BufferedImage(68, 22, BufferedImage.TYPE_INT_RGB);
        Graphics g = bi.getGraphics();
        Color c = new Color(200, 150, 255);
        g.setColor(c);
        g.fillRect(0, 0, 68, 22);

        char[] ch = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
        Random r = new Random();
        int len = ch.length, index;
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 4; i++) {
            index = r.nextInt(len);
            g.setColor(new Color(r.nextInt(88), r.nextInt(188), r.nextInt(255)));
            g.drawString(ch[index] + "", (i * 15) + 3, 18);
            sb.append(ch[index]);
        }
        request.getSession().setAttribute("captcha", sb.toString());
        ImageIO.write(bi, "JPG", response.getOutputStream());
    }

    @RequestMapping(value = "/register/verifycode", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "获取注册短信验证码")
    public WebRespResult getRegisterVerifyCode(GetVerifyCodeRequest verifyCodeRequest,
                                               HttpServletRequest request) throws BizException {
        WebRespResult retJson = new WebRespResult();
        Object captchaObj = request.getSession().getAttribute("captcha");
        if (captchaObj == null || !StringUtils.equalsIgnoreCase(String.valueOf(captchaObj), verifyCodeRequest.getCaptcha())) {
            throw new BizException("请输入正确的图形验证码");
        }
        loginBiz.registerCode(verifyCodeRequest.getPhone());
        retJson.setData("");
        retJson.setDesc("短信验证码已经发送");
        return retJson;
    }

    /*@RequestMapping(value = "/register/do", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "快速注册")
    public WebRespResult<RegisterResult> fastRegister(@RequestBody RegisterRequest registerRequest) throws BizException {
        WebRespResult<RegisterResult> retJson = new WebRespResult();
        DeviceInfo deviceInfo = ClientRequestContext.getCurrent().getDeviceInfo();
        RegisterResult registerResult = loginBiz.fastRegister(registerRequest, deviceInfo);
        retJson.setData(registerResult);
        retJson.setDesc("注册成功");
        return retJson;
    }*/


    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "退出登录")
    @ApiRequestMethod
    @ClientUserIdentify(mustLogin = true, requiredClientUserType = EnumRequiredUserType.EITHER)
    public WebRespResult logout() throws BizException {
        WebRespResult retJson = new WebRespResult();
        String fromUid = ClientRequestContext.getCurrent().getUserId();
        DeviceInfo deviceInfo = ClientRequestContext.getCurrent().getDeviceInfo();
        loginBiz.logout(fromUid, deviceInfo);
        retJson.setDesc("退出登录成功");
        return retJson;
    }

    @RequestMapping(value = "/updateSex", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "更新用户性别")
    @ApiRequestMethod
    @ClientUserIdentify(mustLogin = true, requiredClientUserType = EnumRequiredUserType.SERVICE_PROVIDER)
    public WebRespResult updateSex(int sex) throws BizException {
        WebRespResult retJson = new WebRespResult();
        String fromUid = ClientRequestContext.getCurrent().getUserId();
        userBiz.updateSex(fromUid, sex);
        retJson.setDesc("更新性别成功");
        return retJson;
    }

    @RequestMapping(value = "/updateBirthday", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "更新用户生日")
    @ApiRequestMethod
    @ClientUserIdentify(mustLogin = true, requiredClientUserType = EnumRequiredUserType.SERVICE_PROVIDER)
    public WebRespResult updateBirthday(Date date) throws BizException {
        WebRespResult retJson = new WebRespResult();
        String fromUid = ClientRequestContext.getCurrent().getUserId();
        userBiz.updateBirthday(fromUid, date);
        retJson.setDesc("更新生日成功");
        return retJson;
    }
    @RequestMapping(value = "/updateHeadPic", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "更新用户头像")
    @ApiRequestMethod
    @ClientUserIdentify(mustLogin = true, requiredClientUserType = EnumRequiredUserType.SERVICE_PROVIDER)
    public WebRespResult updateHeadPic(String name) throws BizException {
        WebRespResult retJson = new WebRespResult();
        String fromUid = ClientRequestContext.getCurrent().getUserId();
        userBiz.updateHeadPic(fromUid, name);
        retJson.setDesc("更新头像成功");
        return retJson;
    }


    /*@RequestMapping(value = "/profile", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "用户个人信息查询")
    public WebRespResult<UserBaseInfoVO> getUserProfile(HttpServletRequest request) throws BizException {
        WebRespResult retJson = new WebRespResult();
        String fromUid = ClientRequestContext.getCurrent().getUserId();
        UserBaseInfoVO userInfo = userBiz.getBaseUserInfo(fromUid);
        retJson.setData(userInfo);
        retJson.setDesc("查询成功");
        return retJson;
    }*/

    /*@RequestMapping(value = "/login/verifycode", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "登录获取验证码")
    public WebRespResult getLoginVerifyCode(@RequestBody GetVerifyCodeRequest verifyCodeRequest, HttpServletRequest request) throws BizException {
        WebRespResult retJson = new WebRespResult();
        Object captchaObj = request.getSession().getAttribute("captcha");
        if (captchaObj == null || !StringUtils.equalsIgnoreCase(String.valueOf(captchaObj), verifyCodeRequest.getCaptcha())) {
            throw new BizException("请输入正确的图形验证码");
        }
        loginBiz.getCommonVerifyCode(verifyCodeRequest.getPhone(), VerifyCodeScenario.REGISTER_LOGIN);
        retJson.setData("");
        retJson.setDesc("验证码已经发送");
        return retJson;
    }*/

    /*@RequestMapping(value = "/changetel/verifycode", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "修改手机号获取验证码")
    public WebRespResult getChangeTelVerifyCode(@RequestBody GetVerifyCodeRequest verifyCodeRequest, HttpServletRequest request) throws BizException {
        WebRespResult retJson = new WebRespResult();
        Object captchaObj = request.getSession().getAttribute("captcha");
        if (captchaObj == null || !StringUtils.equalsIgnoreCase(String.valueOf(captchaObj), verifyCodeRequest.getCaptcha())) {
            throw new BizException("请输入正确的图形验证码");
        }
        loginBiz.getCommonVerifyCode(verifyCodeRequest.getPhone(), VerifyCodeScenario.CHANGE_TELEPHONE);
        retJson.setData("");
        retJson.setDesc("验证码已经发送");
        return retJson;
    }

    @RequestMapping(value = "/changetel/do", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "修改手机号")
    public WebRespResult changeTel(@RequestBody ProfileUpdateRequest updateRequest) throws BizException {

        WebRespResult retJson = new WebRespResult();
        String fromUid = ClientRequestContext.getCurrent().getUserId();
        userBiz.changeUserTel(fromUid, updateRequest.getPhone(), updateRequest.getVerifyCode());
        retJson.setData("");
        retJson.setDesc("手机号修改成功");
        return retJson;

    }
*/


    /*@RequestMapping(value = "/change/headUrl", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "修改头像")
    public WebRespResult changeHeadUrl(HttpServletRequest request,
                                       ProfileUpdateRequest updateRequest) throws BizException {

        WebRespResult retJson = new WebRespResult();
        String fromUid = ClientRequestContext.getCurrent().getUserId();
        userBiz.changeHeadUrl(fromUid, updateRequest.getHeadPic());
        retJson.setData("");
        retJson.setDesc("头像修改成功");
        return retJson;
    }*/

    /* @RequestMapping(value = "/forgetPassword/verifycode", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "忘记密码获取验证码")
    public WebRespResult getForgetPassVerifyCode(GetVerifyCodeRequest request) throws BizException {

        WebRespResult retJson = new WebRespResult();
        loginBiz.getCommonVerifyCode(request.getPhone(), VerifyCodeScenario.FORGET_PASSWORD);
        retJson.setData("");
        retJson.setDesc("验证码已经发送");
        return retJson;
    }

    @RequestMapping(value = "/forgetPassword/do", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "忘记密码")
    public WebRespResult forgetPassword(ForgetPasswordRequest forgetPassRequest) throws BizException {

        WebRespResult retJson = new WebRespResult();
        DeviceInfo deviceInfo = ClientRequestContext.getCurrent().getDeviceInfo();
        loginBiz.forgetPassword(forgetPassRequest, deviceInfo);
        retJson.setDesc("密码修改成功,请使用新密码登录吧.");
        return retJson;
    }*/

    /*@RequestMapping(value = "/change/password", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "修改密码")
    public WebRespResult changePassword(HttpServletRequest request,
                                        ProfileUpdateRequest updateRequest) throws BizException {
        WebRespResult retJson = new WebRespResult();
        String fromUid = ClientRequestContext.getCurrent().getUserId();
        userBiz.changePassword(fromUid, updateRequest.getOriginPassword(), updateRequest.getNewPassword());
        retJson.setData("");
        retJson.setDesc("密码修改成功");
        return retJson;
    }*/
}
