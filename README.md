# MirikAPI

API for my same plugins and mods

## Wiki

### How import

Kotlin DSL:
~~~
repositories {
    mavenCentral()
    maven("https://jitpack.io")
}
~~~

~~~
dependencies {
    implementation("com.github.Mirik9724:MirikAPI:0.1")
}
~~~

Grovy DSL:
~~~
repositories {
mavenCentral()
maven { url 'https://jitpack.io' }
}
~~~
~~~
dependencies {
implementation 'com.github.Mirik9724:MirikAPI:0.1'
}
~~~

### How use
u can see it in ./examples