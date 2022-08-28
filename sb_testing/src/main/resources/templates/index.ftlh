<!DOCTYPE html>
<html><head><title>index</title>
<meta charset = "UTF-8">
<link rel = "stylesheet" href = "style.css">
<style>
body, h3,h5 form { font-family: verdana, monospace; margin: auto; text-align: center; display: inline; }
fieldset { background-color: white; width: 90%; }
</style>
</head>

<body><center>
<br /><h3>index</h3>
<br /><h5>FreeMarker: ${ .now?datetime?iso_local }</h5>

<br /><br />
<table style = "width: 50%;" border = "1" >
<tr class = "gra" ><td><a href = "/"					>/(root)		</a></td></tr>
<tr class = "gra" ><td><a href = "/logins"				>/logins		</a></td></tr>
<tr class = "grn" ><td><a href = "/cities"				>/cities		</a></td></tr>
<tr class = "grn" ><td><a href = "/cityIds?id=5"		>/cityIds?id=5	</a></td></tr>
<tr class = "grn" ><td><a href = "/cityPth/5"			>/cityPth/5	    </a></td></tr>
<tr class = "grn" ><td><a href = "/cityBean"			>/cityBean  	</a></td></tr>
<tr class = "blu" ><td><a href = "/countries"			>/countries		</a></td></tr>
<tr class = "blu" ><td><a href = "/countryIds?id=5"	>/countryIds?id=5	</a></td></tr>
<tr class = "blu" ><td><a href = "/countryPth/5"		>/countryPth/5	</a></td></tr>
<tr class = "prp" ><td><a href = "/timer"				>/timer			</a></td></tr>
<tr class = "prp" ><td><a href = "/utils"				>/utils			</a></td></tr>

<tr class = "prp" ><td colspan = "2" style = "background-color: green;"></td></tr>
<tr class = "prp" ><td>
	<a href = "http://localhost:8080/actuator/health/"  > /actuator/health  </a>
	<a href = "http://localhost:8080/actuator/info/"    >  /actuator/info   </a>
	<br />
	<br /><fieldset><legend>All these will fail if the ServerHttpSecurity is not found</legend>
	<a href = "http://localhost:8080/actuator/beans/"		> beans			</a>
	<a href = "http://localhost:8080/actuator/conditions/"	> conditions	</a>
	<a href = "http://localhost:8080/actuator/configprops/"	> configprops	</a>
	<a href = "http://localhost:8080/actuator/env/"			> env			</a>
	<a href = "http://localhost:8080/actuator/health/"		> health		</a>
	<a href = "http://localhost:8080/actuator/heapdump/"	> heapdump		</a>
	<a href = "http://localhost:8080/actuator/info/"		> info			</a>
	<a href = "http://localhost:8080/actuator/loggers/"		> loggers		</a>
	<a href = "http://localhost:8080/actuator/metrics/"		> metrics		</a>
	<a href = "http://localhost:8080/actuator/scheduledtasks/"> scheduledtasks </a>
	<a href = "http://localhost:8080/actuator/threaddump/"	> threaddump	</a>
	</fieldset>

	<br /><fieldset><legend>All these require additional configuration</legend>
	<a href = "http://localhost:8080/actuator/auditevents/"	> auditevents	</a>
	<a href = "http://localhost:8080/actuator/flyway/"		> flyway		</a>
	<a href = "http://localhost:8080/actuator/liquibase/"	> liquibase		</a>
	<a href = "http://localhost:8080/actuator/logfile/"		> logfile		</a>
	<a href = "http://localhost:8080/actuator/prometheus/"	> prometheus	</a>
	<a href = "http://localhost:8080/actuator/sessions/"	> sessions		</a>
	<a href = "http://localhost:8080/actuator/shutdown/"	> shutdown		</a>
	</fieldset>
	</td></tr>
<tr class = "prp" ><td><a href = "http://localhost:8080/swagger-ui/"     > /swagger-ui/</a></td></tr>
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
