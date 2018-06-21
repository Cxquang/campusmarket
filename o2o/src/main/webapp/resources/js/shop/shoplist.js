$(function(){
	getlist();
	function getlist(e){
		$.ajax({
			url:"/shopadmin/getshoplist",
			type:"get",
			dataType:"json",
			success: function(data){
				if(data.success){
					//渲染店铺列表，调用函数handleList
					handleList(data.shopList);
					//显示用户名，调用函数handleUser
					handleUser(data.user);
/*					$('#addShop')
					.attr('href','/shopadmin/productoperation?shopId=1');*/
				}
			}
		});
	}
	
	function handleUser(data){
		$("#user-name").text(data.name);
		/*var html = '';
		html = '你好，' 
			+ data.name 
			+ '<a class="pull-right" id="addShop" href="/shopadmin/shopoperation">增加店铺</a>'
      	$('.titleShop').html(html);*/
	}
	
	function handleList(data){
		var html = '';
		data.map(function(item,index){
			html += '<div class="row row-shop"><div class="col-40">'
				 + item.shopName + '</div><div class="col-40">'
				 + shopStatus(item.enableStatus)
				 + '</div><div class="col-20">'
				 + goShop(item.enableStatus,item.shopId) + '</div></div>';
		});
		$('.shop-wrap').html(html);
	}
	
	//将状态码以文字描述
	function shopStatus(status){
		if(status == 0){
			return '审核中';
		}else if(status == -1){
			return '店铺非法';
		}else if(status == 1){
			return '审核通过';
		}
	}
	
	function goShop(status,id){
		if(status == 1){
			return '<a href="/shopadmin/shopmanagement?shopId=' + id + '">进入</a>';
		}else{
			return '';
		}
	}
	
	$('#addShop').click(function(){
		window.location.href = '/shopadmin/shopoperation';
	});
});