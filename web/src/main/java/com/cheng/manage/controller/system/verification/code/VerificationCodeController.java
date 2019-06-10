package com.cheng.manage.controller.system.verification.code;

import com.cheng.manage.common.redis.JedisUtil;
import com.cheng.manage.controller.base.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;


/**
 * @ClassName:  VerificationCodeController
 * @Description:TODO
 * @author: cheng fei
 * @date:   2018-10-09 20:06
 */
@RestController
@Api(tags = "system.verification.code.VerificationCodeController", description = "系统-验证码模块")
@RequestMapping("/system/verification/code")
public class VerificationCodeController extends BaseController {


	@Autowired
	private JedisUtil jedisUtil;

	@Value("${verification.code.expire}")
	private int verificationCodeExpire;

	@ApiOperation(value = "加载验证码接口",httpMethod = "GET", produces = "image/png")
	@GetMapping(value="")
	public void generate(
			@ApiParam(value = "验证码储存Key", required = true) @RequestParam(required = true) String key,
			@ApiParam(value = "验证码宽度", required = false) @RequestParam(required = false, defaultValue = "70") Integer width,
			@ApiParam(value = "验证码高度", required = false) @RequestParam(required = false, defaultValue = "25") Integer height,
			@ApiParam(hidden = true) HttpServletRequest request,
			@ApiParam(hidden = true) HttpServletResponse response
	){
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		String code = drawImg(width,height, output);
		jedisUtil.set(appCacheDb, key, code, verificationCodeExpire);
		try {
			ServletOutputStream out = response.getOutputStream();
			output.writeTo(out);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String drawImg(int width,int height, ByteArrayOutputStream output){
		String code = "";
		for(int i=0; i<4; i++){
			code += randomChar();
		}

		BufferedImage bi = new BufferedImage(width,height,BufferedImage.TYPE_3BYTE_BGR);
		Font font = new Font("Times New Roman",Font.PLAIN,20);
		Graphics2D g = bi.createGraphics();
		g.setFont(font);
		Color color = new Color(66,2,82);
		g.setColor(color);
		g.setBackground(new Color(226,226,240));
		g.clearRect(0, 0, width, height);
		FontRenderContext context = g.getFontRenderContext();
		Rectangle2D bounds = font.getStringBounds(code, context);
		double x = (width - bounds.getWidth()) / 2;
		double y = (height - bounds.getHeight()) / 2;
		double ascent = bounds.getY();
		double baseY = y - ascent;
		g.drawString(code, (int)x, (int)baseY);
		g.dispose();
		try {
			ImageIO.write(bi, "jpg", output);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return code;
	}

	private char randomChar(){
		Random r = new Random();
		String s = "ABCDEFGHJKLMNPRSTUVWXYZ0123456789";
		return s.charAt(r.nextInt(s.length()));
	}

}
