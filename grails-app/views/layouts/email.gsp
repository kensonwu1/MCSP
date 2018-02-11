<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>

<style type="text/css">

@media screen and (max-device-width:480px){
    .mailContentWrapper {width: 480px;}
}

* {
	padding: 0;
	margin: 0;
}

html,body {
	font-family: "Trebuchet MS", Arial, Helvetica, sans-serif;
	font-size: 13px;
	color: #404040;
	background: #eee;
}

a {
	cursor:pointer;
	text-decoration: none;
	color: #00a8ec;
}

a:hover {
	text-decoration:underline;
	color: #ec6a00;
}

a img {
	border: 0;
}

#mail-wrapper {
	border-collapse: collapse;
	width:750px;
	height:100%;
	background: #fff;
}

#mail-header{
    border-top: 1px solid #eee;
    border-left: 1px solid #eee;
    border-right: 1px solid #eee;
    border-bottom: 2px solid #fb4e14;
}

#logo {
	margin: 20px;
	margin-left:30px;
	height:30px;
}

#mail-content {
    border-left: 1px solid #eee;
    border-right: 1px solid #eee;
    border-bottom: 1px solid #eee;
}

#mail-content p {
	line-height: 20px;
	margin-bottom: 20px;
}

#mail-body{
	margin:30px;
}
#mail-body p{line-height:18px;}

#signature{margin:30px 0;}
#footer{
	color:#aaa;
	border-top:1px solid #eee;
	padding-top:6px;
	font-style: oblique;
}
.highlight{
    font-weight: bold;
}
</style>
</head>
<body>
	<table style="width: 100%;">
		<tr>
			<td align="center">
				<table id="mail-wrapper">
						<tr>
							<td id="mail-header">
								<div id='logo'>
                                    <img src='cid:foglightLogo' alt="Foglight" title="Foglight Logo"/>
								<!--
                                    <asset:image src='email/foglight.png' alt="Foglight" title="Foglight Logo"/>
								-->
								 </div>
							</td>
						</tr>
						<tr>
							<td id='mail-content'>
								<div id="mail-body">
									<g:pageProperty name="page.body"/>
									<p id="signature">
									    Thanks <br/>
									    Foglight Team
									</p>
									<p id="footer">
										Please do not hesitate to <a href="mailto:${contactUs}?subject=Question about Foglight">contact us</a> if you need any help, have any question or want to make any suggestions.
									</p>
							 	</div>
		                    </td>
		                </tr>
            	</table>
        	</td>
    	</tr>
	</table>
</body>
</html>
