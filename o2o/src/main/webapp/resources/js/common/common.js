/**
 * 
 */
function changeVerifyCode(){
	//提交给servlet生成新的验证码（web.xml中对Kaptcha的servlet配置）
	var x=document.getElementById("captcha_img");
	x.src="../Kaptcha?" + Math.floor(Math.random() * 100);
}

function getQueryString(name){
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if(r != null){
		return decodeURIComponent(r[2]);
	}
	return '';
}