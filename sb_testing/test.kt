// cd c:\workspace\github\spring_testing
// kotlinc-native test.kt -o test // makes a test.exe

data class User(val name: String, val age: Int) {
	//
	override fun toString(): String { return "Hello user: $name" }
}
var user = User( "George", 737)

var name:String = "Martin"
var age:Int = 343

fun main() {
	//
	println("Hello World")
	println(name)
	println(age)

	var user = User( "George", 737)
	println(user)
	println( user.toString() )
	//
	val list: ArrayList<String> = arrayListOf("George")
	list.add("Martin")
	println( list )
	
	//val maps: MutableMap<Int, Int> = mutableMapOf(1 to 30, 2 to 40, 3 to 20)   
	//maps.forEach { k, v -> println("key: $k, val: $v") }
}