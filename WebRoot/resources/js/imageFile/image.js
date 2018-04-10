/**
 * @param source    图片源的imput id
 * @param show      要展示图片的div id
 * @param imgH      处理图片的高度
 * @param ingW      处理图片的宽度
 * @param isSize    是否处理图的高宽大小
 */
function imageProcess(source, show, imgH, ingW, isSize) {
	//监听图片源表单的改变事件
	source.change(function() {
		var files = this.files;
		if (files.length) {
			var isImage = checkFile(this.files);
			if (!isImage) {
				show.html("请确保文件为图像类型");
			} else {
				var reader = new FileReader();
				reader.onload = function(e) {
					var imageSize = e.total; //图片大小
					var image = new Image();
					image.src = e.target.result;
					image.onload = function() {
						// 旋转图片
						var newImage = rotateImage(image);
						//压缩图片
						newImage = judgeCompress(newImage, imageSize);
						if(isSize){
							newImage.setAttribute('width', ingW);
							newImage.setAttribute('height', imgH);
						}else{
							newImage.setAttribute('width', '100%');
							newImage.setAttribute('height', '100%');
						}
						show.attr("title",isImage.name);
						show.html(newImage);
					}
				}
				reader.readAsDataURL(isImage);
			}
		}
	})
}

/**
 * 检查文件是否为图像类型
 * @param files         FileList
 * @returns file        File
 */
function checkFile(files) {
	var file = files[0];
	//使用正则表达式匹配判断
	if (!/image\/\w+/.test(file.type)) {
		return false;
	}
	return file;
}

/**
 * 判断图片是否需要压缩
 * @param image          HTMLImageElement
 * @param imageSize      int
 * @returns {*}          HTMLImageElement
 */
function judgeCompress(image, imageSize) {
	//判断图片是否大于500 KB
	var threshold = 500000; //阈值,可根据实际情况调整
	console.log('图片大小:' + (imageSize/1024)+" KB")
	if (imageSize > threshold) {
		var imageData = compress(image);
		var newImage = new Image()
		newImage.src = imageData
		return newImage;
	} else {
		return image;
	}
}

/**
 *压缩图片
 * @param image         HTMLImageElement
 * @returns {string}    base64格式图像
 */
function compress(image) {
	var canvas = document.createElement('canvas')
	var ctx = canvas.getContext('2d');
	var imageLength = image.src.length;
	var width = image.width;
	var height = image.height;
	canvas.width = width;
	canvas.height = height;
	ctx.drawImage(image, 0, 0, width, height);
	//压缩操作
	var quality = 0.1; //图片质量  范围：0<quality<=1 根据实际需求调正
	var imageData = canvas.toDataURL("image/jpeg", quality);
	console.log("压缩前：" + imageLength);
	console.log("压缩后：" + imageData.length);
	console.log("压缩率：" + ~~(100 * (imageLength - imageData.length) / imageLength) + "%");
	return imageData;
}


/**
 * 旋转图片
 * @param image         HTMLImageElement
 * @returns newImage    HTMLImageElement
 */
function rotateImage(image) {
	var width = image.width;
	var height = image.height;
	var canvas = document.createElement("canvas")
	var ctx = canvas.getContext('2d');
	var newImage = new Image();
	//旋转图片操作
	EXIF.getData(image, function() {
		var orientation = EXIF.getTag(this, 'Orientation');
		// orientation = 6;//测试数据
		console.log('旋转状态:' + orientation);
		switch (orientation) {
		//正常状态
		case 1:
			newImage = image;
			break;
		//旋转90度
		case 6:
			canvas.height = width;
			canvas.width = height;
			ctx.rotate(Math.PI / 2);
			ctx.translate(0, -height);
			ctx.drawImage(image, 0, 0)
			imageDate = canvas.toDataURL('Image/jpeg', 1)
			newImage.src = imageDate;
			break;
		//旋转180°
		case 3:
			canvas.height = height;
			canvas.width = width;
			ctx.rotate(Math.PI);
			ctx.translate(-width, -height);
			ctx.drawImage(image, 0, 0)
			imageDate = canvas.toDataURL('Image/jpeg', 1)
			newImage.src = imageDate;
			break;
		//旋转270°
		case 8:
			canvas.height = width;
			canvas.width = height;
			ctx.rotate(-Math.PI / 2);
			ctx.translate(-height, 0);
			ctx.drawImage(image, 0, 0)
			imageDate = canvas.toDataURL('Image/jpeg', 1)
			newImage.src = imageDate;
			break;
		//undefined时不旋转
		case undefined:
			newImage = image;
			break;
		}
	});
	return newImage;
}