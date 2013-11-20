<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>MyYouTube: Upload your video</title>
<script language="JavaScript" type="text/JavaScript">   
	var  img=null;   
	function checkPic(upload){   
		var location=upload.video.value;   
		if(location==""){   
			alert("please select a video");   
			return false;   
		}   
		var point = location.lastIndexOf(".");   
		var type = location.substr(point);   
		if(type==".mp4"||type==".rmvb"){
			return true;   
		}   
		else{   
			alert("format is not acceptable");   
			return false;   
		}   
		return true;   
	}   
	
	function changesrc(){   
		yourpic.src=picForm.pic.value;   
	}   
</script>  
</head>
<body>


<form name="upload" method="POST" enctype="multipart/form-data" action="./ServletUpload" onsubmit="return checkPic(upload);">
  File to upload: <input type="file" name="upfile" id="video"><br/>
  <!--  Notes about the file: <input type="text" name="note"><br/>  -->
  <br/>
  <input type="submit" value="Press"> to upload the file!
</form>




</body>
</html>