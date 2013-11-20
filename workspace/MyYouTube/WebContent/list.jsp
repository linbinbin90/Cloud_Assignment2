<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="com.jsp.MyYouTube.*" %>
<%@ page import="java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>List Page</title>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
.STYLE1 {font-size: 12px}
.STYLE3 {font-size: 12px; font-weight: bold; }
.STYLE4 {
	color: #03515d;
	font-size: 12px;
}
-->
</style>

<script>
var  highlightcolor='#c1ebff';
var  clickcolor='#51b2f6';
function  changeto(){
source=event.srcElement;
if  (source.tagName=="TR"||source.tagName=="TABLE")
return;
while(source.tagName!="TD")
source=source.parentElement;
source=source.parentElement;
cs  =  source.children;
//alert(cs.length);
if  (cs[1].style.backgroundColor!=highlightcolor&&source.id!="nc"&&cs[1].style.backgroundColor!=clickcolor)
for(i=0;i<cs.length;i++){
	cs[i].style.backgroundColor=highlightcolor;
}
}

function  changeback(){
if  (event.fromElement.contains(event.toElement)||source.contains(event.toElement)||source.id=="nc")
return
if  (event.toElement!=source&&cs[1].style.backgroundColor!=clickcolor)
//source.style.backgroundColor=originalcolor
for(i=0;i<cs.length;i++){
	cs[i].style.backgroundColor="";
}
}

function  clickto(){
source=event.srcElement;
if  (source.tagName=="TR"||source.tagName=="TABLE")
return;
while(source.tagName!="TD")
source=source.parentElement;
source=source.parentElement;
cs  =  source.children;
//alert(cs.length);
if  (cs[1].style.backgroundColor!=clickcolor&&source.id!="nc")
for(i=0;i<cs.length;i++){
	cs[i].style.backgroundColor=clickcolor;
}
else
for(i=0;i<cs.length;i++){
	cs[i].style.backgroundColor="";
}
}

function rateFunction(ID){
	alert(ID);
}
</script>

</head>

<body>
<%
    RDSManager DBmanager = new RDSManager();
    ArrayList<VideoInfo> movieList = DBmanager.getVideoList();
	%>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="30" background="images/tab_05.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="12" height="30"><img src="images/tab_03.gif" width="12" height="30" /></td>
        <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="46%" valign="middle"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="33%" class="STYLE1" align="center"><span class="STYLE3"></span><a href="./admin.jsp">Admin page</a></td>
                <td width="33%" class="STYLE1" align="center"><span class="STYLE3"></span><a href="./list.jsp">List page</a></td>
                <td width="34%" class="STYLE1" align="center"><span class="STYLE3"></span><a href="./upload.jsp">Upload page</a></td>
              </tr>
            </table></td>
          </tr>
          <tr>
            <td width="46%" valign="middle"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="5%"><div align="center"><img src="images/tb.gif" width="16" height="16" /></div></td>
                <td width="95%" class="STYLE1"><span class="STYLE3"></span>List Page</td>
              </tr>
            </table></td>
          </tr>
        </table></td>
        <td width="16"><img src="images/tab_07.gif" width="16" height="30" /></td>
      </tr>
    </table></td>
  </tr>
  <tr>
  
    <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        
        <td><table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="b5d6e6" onmouseover="changeto()"  onmouseout="changeback()">
          <tr>
            <td width="12%" height="22" background="images/bg.gif" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">Name</span></div></td>
            <td width="20%" height="22" background="images/bg.gif" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">UploadTime</span></div></td>
            <td width="12%" background="images/bg.gif" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">Average Rate</span></div></td>
            <td width="20%" height="22" background="images/bg.gif" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">URL</span></div></td>
            <td width="20%" height="22" background="images/bg.gif" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">Rate Score</span></div></td>
            <td width="20%" height="22" background="images/bg.gif" bgcolor="#FFFFFF" class="STYLE1"><div align="center">Rate Action</div></td>
          </tr>
          <%
			
			for(VideoInfo movie : movieList) {
		  %>
		  <form action=<%="./UpdateRatingServlet?ID="+movie.ID%> method="POST">
          <tr>
            <td height="20" bgcolor="#FFFFFF" name="name"><div align="center"><span class="STYLE1"><a href=<%="./ShowVideo.jsp?url="+movie.url%>><%=movie.name %></a></span></div></td>
            <td bgcolor="#FFFFFF"><div align="center" name="uploadTime"><span class="STYLE1"><%=movie.uploadTime %></span></div></td>
            <td height="20" bgcolor="#FFFFFF" name="avgRate"><div align="center"><span class="STYLE1"><%=movie.avgRate %></span></div></td>
            <td height="20" bgcolor="#FFFFFF" name="url"><div align="center"><span class="STYLE1"><a href=<%="rtmp://s2mmqsjxyn90z1.cloudfront.net/"+movie.url %>><%=movie.url %></a></span></div></td>
            <td height="20" bgcolor="#FFFFFF" name="rating"><div align="center">
            <select name=<%="score"+movie.ID%>>
            	<option value="5">5 point</option>
            	<option value="4">4 point</option>
            	<option value="3">3 point</option>
            	<option value="2">2 point</option>
            	<option value="1">1 point</option>
            </select>
            </div></td>
            <td height="20" bgcolor="#FFFFFF" name="Actions"><div align="center"><span class="STYLE4">
            <input type="submit" value="Rate" /></span></div></td>
          </tr>
          </form>
          <%} %>
          
        </table></td>
        <td width="8" background="images/tab_15.gif">&nbsp;</td>
      </tr>
    </table></td>
  </tr>
    </table></td>
  </tr>
</table>
</body>

</html>