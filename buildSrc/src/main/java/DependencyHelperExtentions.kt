import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.implementation(depName: String) {
    add("implementation", depName)
}

fun DependencyHandler.testImplementation(depName: String) {
    add("testImplementation", depName)
}

fun DependencyHandler.kapt(depName: String) {
    add("kapt", depName)
}

@Suppress("unused")
fun DependencyHandler.compileOnly(depName: String) {
    add("compileOnly", depName)
}

@Suppress("unused")
fun DependencyHandler.api(depName: String) {
    add("api", depName)
}