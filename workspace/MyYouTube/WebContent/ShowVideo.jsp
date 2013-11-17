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
    jwplayer("myElement").setup({
        file: "https://s3.amazonaws.com/cloudcompute/3+-+1+-+Analysis+of+Algorithms+Introduction+(8-14).mp4",
        height: 360,
        width: 640
    });
</script>

</body>
</html>