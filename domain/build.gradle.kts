plugins{
    id(BuildPlugins.kotlin)
}

dependencies {
    implementation(Libraries.kotlinStdLib)
    implementation(Libraries.koinKotlin)
    implementation(Libraries.retrofit)
    implementation(Libraries.gson)
    implementation(Libraries.converterGson)
    implementation(Libraries.okHttp)
}