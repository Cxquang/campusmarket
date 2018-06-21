$(function(){
	var shopId = getQueryString("shopId");
	var shopInfoUrl = '/shopadmin/getshopmanagementinfo?shopId=' + shopId;
	$.getJSON(shopInfoUrl,function(data){
		if(data.redirect){
			window.location.href = data.url;
		}else{
			if(data.shopId != undefined && data.shopId != null){
				shopId = data.shopId;
			}
			$('#shopInfo')
				.attr('href','/shopadmin/shopoperation?shopId=' + shopId);
			$('#productCategoryList')
			.attr('href','/shopadmin/productcategorylist?shopId=' + shopId);
			$('#productList')
			.attr('href','/shopadmin/productoperation');
		}
	})
});