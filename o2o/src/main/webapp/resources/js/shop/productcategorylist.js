$(function(){
	var shopId = getQueryString("shopId");
	var getproductcategorylistUrl = "/shopadmin/getproductcategorylist?shopId=" + shopId;
	var addUrl = '/shopadmin/addproductcategorys';
	var deleteUrl = '/shopadmin/removeproductcategory';
	getProductCategroyList();
	function getProductCategroyList(e){
		$.ajax({
			url:getproductcategorylistUrl,
			type:"get",
			dataType:"json",
			success: function(data){
				if(data.success){
					handleProductCategoryList(data.productCategoryList);
				}
			}
		});
	}
	
	function handleProductCategoryList(data){
		var html = '';
		data.map(function(item,index){
			html += '<div class="row row-shop now"><div class="col-33">'
				 + item.productCategoryName + '</div><div class="col-33">'
				 + item.priority
				 + '</div><div class="col-33"><a href="#" class="button delete" data-id="' 
				 + item.productCategoryId + '">删除</a></div></div>';
		});
		$('.shop-wrap').html(html);
	}
	
	$('#new').click(function(){
		var tempHtml = '<div class="row row-shop temp">'
			  + '<div class="col-33"><input class="category-input category" type="text" placeholder="分类名"></div>'
			  + '<div class="col-33"><input class="category-input priority" type="text" placeholder="优先级"></div>'
			  + '<div class="col-33"><a href="#" class="button delete">删除</a></div></div>';
		$('.shop-wrap').append(tempHtml);
	});
	
	$('#submit').click(function(){
		var tempArr = $('.temp');
		var productCategoryList = [];
		tempArr.map(function(index,item){
			var tempObj = {};
			tempObj.productCategoryName = $(item).find('.category').val();
			tempObj.priority = $(item).find('.priority').val();
			if(tempObj.productCategoryName && tempObj.priority)	{
				productCategoryList.push(tempObj);
			}
		});
		$.ajax({
			url : addUrl,
			type : 'POST',
			data : JSON.stringify(productCategoryList),
			contentType: 'application/json',
			success : function(data){
				if(data.success){
					$.toast('提交成功');
					getProductCategroyList();
				}else{
					$.toast('提交失败');
				}
			}
		})
	});		
	
	$('.shop-wrap').on('click','.row-shop.temp .delete',
			function(e){
				console.log($(this).parent().parent());
				$(this).parent().parent().remove();
	});
	
	$('.shop-wrap').on('click','.row-shop.now .delete',
			function(e){
				var target = e.currentTarget;
				$.confirm('确定删除么？',function(){
					$.ajax({
						url : deleteUrl,
						type: 'POST',
						data : {
							//这里将ProductCategoryId设置到data-id中保存
							productCategoryId : target.dataset.id
						},
						dataType : 'json',
						success : function(data){
							if(data.success){
								$.toast('删除成功 !');
								getProductCategroyList();
							}else{
								$.toast('删除失败 !');
							}
						}
					});
				});
	});
});