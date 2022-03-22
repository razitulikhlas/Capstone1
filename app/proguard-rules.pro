# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
# Untuk mempertahankan suatu Class supaya tidak di-obfuscate


# Supaya line number tetep ada saat di debugging
-keepattributes SourceFile,LineNumberTable

# Gunakan ini untuk mengganti nama file
-renamesourcefileattribute SourceFile
-keepnames class androidx.navigation.fragment.NavHostFragment
-keep class * extends androidx.fragment.app.Fragment{}

-dontwarn androidx.databinding.**
-keep class androidx.databinding.** { *; }