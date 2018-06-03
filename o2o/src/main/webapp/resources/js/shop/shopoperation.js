/**
 * 
 */
$(function(){
	//由于在pom.xml中定义jetty访问的路径，所以这里的url不用加o2o项目名
	var initUrl = '/shopadmin/getshopinitinfo';
	var registerShopUrl = '/shopadmin/registershop';
	/*
	 * 去后台调取区域信息以及店铺类别信息，并加载到前端的店铺类别和区域类别控件当中
	 */
	//alert(initUrl);
	getShopInitInfo();
	function getShopInitInfo(){
		$.getJSON(initUrl,function(data){
			if(data.success){
			//	console.log('成功调用getShopInitInfo');
				var tempHtml = '';
				var tempAreaHtml = '';
				data.shopCategoryList.map(function(item,index){
					tempHtml += '<option data-id="' + item.shopCategoryId + '">'
					+ item.shopCategoryName + '</option>';
				});
				data.areaList.map(function(item,index){
					tempAreaHtml += '<option data-id="' + item.areaId + '">'
					+ item.areaName + '</option>';
				});
				$('#shop-category').html(tempHtml);
				$('#area').html(tempAreaHtml);
			}
			else{
				console.log('调用失败');
			}
		});
	}	
		$('#submit').click(function(){
			var shop = {};
			shop.shopName = $('#shop-name').val();
			shop.shopAddr = $('#shop-addr').val();
			shop.phone = $('#shop-phone').val();
			shop.shopDesc = $('#shop-desc').val();
			shop.shopCategory = {
					shopCategoryId:$('#shop-category').find('option').not(function(){
						return !this.selected;
					}).data('id')
			};
			shop.area = {
					areaId:$('#area').find('option').not(function(){
						return !this.selected;
					}).data('id')
			};
			//不太清楚为什么使用下面这条语句就无法添加到formData对象，而且使用Ajax发送不成功？
			var shopImg = $('#shop-img')[0].files[0];
			//var shopImg = document.getElementById("shop-img").files;
			var formData = new FormData();
			formData.append('shopImg',shopImg);
			formData.append('shopStr',JSON.stringify(shop));
			//检查验证码并将其传入后台进行验证
			var verifyCodeActual = $('#j_captcha').val();
			console.log(verifyCodeActual);
			if(!verifyCodeActual){
				$.toast("请输入验证码!");
				return;
			}
			formData.append('verifyCodeActual',verifyCodeActual);
			$.ajax({
				url : registerShopUrl,
				type:'POST',
				data : formData,
				contentType : false,
				processData : false,
				cache:false,
				error: function (jqXHR, textStatus, errorThrown) {
		            //弹出jqXHR对象的信息
		            console.log(jqXHR.responseText);
		            console.log(jqXHR.status);
		            console.log(jqXHR.readyState);
		            console.log(jqXHR.statusText);
		            //弹出其他两个参数的信息
		            console.log(textStatus);
		            console.log(errorThrown);
		        },
				success: function(data){
					if(data.success){
						$.toast('提交成功!');
					}else{
						$.toast('提交失败!' + data.errMsg);
					}
				}
			});
		});
})