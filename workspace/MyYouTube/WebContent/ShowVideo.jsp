<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Show Video!</title>
</head>

<script src="http://jwpsrv.com/library/z5kcWE_PEeOD4iIACqoGtw.js"></script>

<body>
<div id="myElement">Loading the player ...</div>

<script type="text/javascript">
var urlParams;
(window.onpopstate = function () {
    var match,
        pl     = /\+/g,  // Regex for replacing addition symbol with a space
        search = /([^&=]+)=?([^&]*)/g,
        decode = function (s) { return decodeURIComponent(s.replace(pl, " ")); },
        query  = window.location.search.substring(1);

    urlParams = {};
    while (match = search.exec(query))
       urlParams[decode(match[1])] = decode(match[2]);
})();
//alert(urlParams.url);
	//var url = "http://" + urlParams.url;
	
    jwplayer("myElement").setup({
        //file: url,
        file: "rtmp://s2mmqsjxyn90z1.cloudfront.net/cfx/st/" + urlParams.url,
        height: 360,
        width: 640
    });
</script>

</body>
</html>