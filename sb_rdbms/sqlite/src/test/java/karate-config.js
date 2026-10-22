
function fn() {

	var env = karate.env; // get system property 'karate.env'
	karate.log('karate.env system property was:', env);
	if (!env) { env = 'dev'; }

	var config = {
		env: env,
		varTime: new Date().toISOString(),
		postService: 'https://jsonplaceholder.typicode.com'
	}
	if (env == 'dev') { config.postService = 'https://jsonplaceholder.typicode.com' } else 
	if (env == 'int') { config.postService = 'https://dummyjson.com' }

	karate.configure('connectTimeout', 5000);
	karate.configure('readTimeout', 5000);
	return config;
}