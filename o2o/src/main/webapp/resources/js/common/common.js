/**
 * 
 */
function changeVerifyCode(){
	//提交给servlet生成新的验证码（web.xml中对Kaptcha的servlet配置）
	var x=document.getElementById("captcha_img");
	x.src="../Kaptcha?" + Math.floor(Math.random() * 100);
}