#Spring Testing

The intent of this application is to showcase testing code.

http://zetcode.com/springboot/annotations

	> cd c:\workspace\github\spring_testing

for gradle

	> gradle			// installs local env

	> gradle tasks
	> gradle build --info -x test
	> gradle clean test --i | findstr /i INFO:
	> gradle bootrun

goto > http://localhost:8080

final mock
	powermock of final classes only possible when adding:
	"src/test/resources/mockito-extensions/org.mockito.plugins.MockMaker" with line: mock-maker-inline
