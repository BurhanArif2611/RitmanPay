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

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
#-dontwarn okio.**
#-dontwarn javax.annotation.Nullable
#-dontwarn javax.annotation.ParametersAreNonnullByDefault
#-dontwarn com.mapbox.mapboxsdk.plugins.locationlayer.**



-dontwarn okhttp3.**
#-dontwarn okio.**
-dontwarn javax.annotation.**
-dontwarn org.conscrypt.**
##-dontwarn retrofit2.Platform$Java8
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement



#-keep class com.squareup.okhttp.** { *; }
#-keep class retrofit.** { *; }
#-keep interface com.squareup.okhttp.** { *; }
#
#-dontwarn com.squareup.okhttp.**
#-dontwarn okio.**
#-dontwarn retrofit.**
#-dontwarn rx.**
#
#-keepclasseswithmembers class * {
#    @retrofit.http.* <methods>;
#}
#

