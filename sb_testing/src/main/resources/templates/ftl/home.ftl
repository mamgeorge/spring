<!DOCTYPE html>
<html><head><title>home</title>
<meta charset = "UTF-8">
<link rel = "stylesheet" href = "style.css">
<style>
body, h3,h5 form { font-family: verdana, monospace; margin: auto; text-align: center; display: inline; }
fieldset { background-color: white; width: 90%; }
</style>
</head>

<body><center>
<br /><h3>home</h3>
<br /><h5>FreeMarker: ${ .now?datetime?iso_local }</h5>

<br /><br />
<table style = "width: 60%;" border = "1" >

<tr class = "prp" ><td colspan = "2" style = "background-color: green;"></td></tr>

<tr class = "prp" ><td>
	<a href = "/"					>/(root)		</a> |
	<a href = "/home"				>/home			</a> |
	<a href = "/timer"				>/timer			</a> |
	<a href = "/utils"				>/utils			</a> |
	<a href = "/login"				>/login 		</a> |
	<a href = "/logins"				>/logins		</a> <br />
	<a href = "/cities"				>/cities		</a> |
	<a href = "/cityIds?id=5"		>/cityIds?id=5	</a> |
	<a href = "/cityPth/5"			>/cityPth/5		</a> |
	<a href = "/cityBean"			>/cityBean		</a> <br />
	<a href = "/countries"			>/countries		</a> |
	<a href = "/countryIds?id=5"	>/countryIds?id=5</a>|
	<a href = "/countryPth/5"		>/countryPth/5	</a>
	</fieldset>
	</td></tr>

<tr class = "prp" ><td colspan = "2" style = "background-color: blue;"></td></tr>

<tr class = "prp" ><td>
	<a href = "/actuator/health/"	> /actuator/health  </a> |
	<a href = "/actuator/info/"		>  /actuator/info   </a>
	<br />
	<br /><fieldset><legend>All these will fail if the ServerHttpSecurity is not found</legend>
	<a href = "/actuator/beans/"		> beans			</a> |
	<a href = "/actuator/conditions/"	> conditions	</a> |
	<a href = "/actuator/configprops/"	> configprops	</a> |
	<a href = "/actuator/env/"			> env			</a> |
	<a href = "/actuator/health/"		> health		</a> |
	<a href = "/actuator/heapdump/"		> heapdump		</a> |
	<a href = "/actuator/info/"			> info			</a> |
	<a href = "/actuator/loggers/"		> loggers		</a> |
	<a href = "/actuator/metrics/"		> metrics		</a> |
	<a href = "/actuator/scheduledtasks/"> scheduledtasks </a> |
	<a href = "/actuator/threaddump/"	> threaddump	</a>
	</fieldset>

	<br /><fieldset><legend>All these require additional configuration</legend>
	<a href = "/actuator/auditevents/"	> auditevents	</a> |
	<a href = "/actuator/flyway/"		> flyway		</a> |
	<a href = "/actuator/liquibase/"	> liquibase		</a> |
	<a href = "/actuator/logfile/"		> logfile		</a> |
	<a href = "/actuator/prometheus/"	> prometheus	</a> |
	<a href = "/actuator/sessions/"		> sessions		</a> |
	<a href = "/actuator/shutdown/"		> shutdown		</a>
	</fieldset>
	</td></tr>

<tr class = "prp" ><td colspan = "2" style = "background-color: red;"></td></tr>

<tr class = "prp" ><td>
	<a href = "/swagger-ui/"     > /swagger-ui/</a> |
	<a href = "/swagger-ui.html" > /swagger-ui.html</a> |
	<a href = "/h2-console"      > /h2-console</a>
	</td></tr>
</table>

<br /><hr /><form class = "grn" style = "width: 50%;" action = "/cityIds" method = "GET"> id for:
<input type = "text" name = "id" value = "7" />
<input type = "submit" /></form>

<br /><hr /><form class = "blu" style = "width: 50%;" action = "/countryIds" method = "GET"> id for:
<input type = "text" name = "id" value = "7" />
<input type = "submit" /></form>

<hr /><br />
<a class = "red" href = "/exits"	>/exits		</a> |
<a class = "red" href = "/errors"	>/errors	</a> |
<a class = "red" href = "/error"	>/error		</a>

</center></body>
</html>
</html>
