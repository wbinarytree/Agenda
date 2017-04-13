# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/phoenix/Library/Android/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more transactions, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

-keepattributes Signature
-keepattributes *Annotation*
-keepattributes EnclosingMethod
-keepattributes InnerClasses

-keepclassmembers class com.phoenix.soft.costy.module.** {
  *;
}
# Proguard flags for consumers of the Google Play services SDK
# https://developers.google.com/android/guides/setup#add_google_play_services_to_your_project

# Keep SafeParcelable value, needed for reflection. This is required to support backwards
# compatibility of some classes.
-keep public class com.google.android.gms.common.internal.safeparcel.SafeParcelable {
    public static final *** NULL;
}

# Needed for Parcelable/SafeParcelable classes & their creators to not get renamed, as they are
# found via reflection.
-keep class com.google.android.gms.common.internal.ReflectedParcelable
-keepnames class * implements com.google.android.gms.common.internal.ReflectedParcelable
-keepclassmembers class * implements android.os.Parcelable {
  public static final *** CREATOR;
}

# Keep the classes/members we need for client functionality.
-keep @interface android.support.annotation.Keep
-keep @android.support.annotation.Keep class *
-keepclasseswithmembers class * {
  @android.support.annotation.Keep <fields>;
}
-keepclasseswithmembers class * {
  @android.support.annotation.Keep <methods>;
}

# Keep the names of classes/members we need for client functionality.
-keep @interface com.google.android.gms.common.annotation.KeepName
-keepnames @com.google.android.gms.common.annotation.KeepName class *
-keepclassmembernames class * {
  @com.google.android.gms.common.annotation.KeepName *;
}

# Keep Dynamite API entry points
-keep @interface com.google.android.gms.common.util.DynamiteApi
-keep @com.google.android.gms.common.util.DynamiteApi public class * {
  public <fields>;
  public <methods>;
}

# Needed when building against pre-Marshmallow SDK.
-dontwarn android.security.NetworkSecurityPolicy

# Needed when building against Marshmallow SDK.
-dontwarn android.app.Notification

# Ensure that proguard will not strip GcmListenerService.handleIntent()
-keepclassmembers class com.google.android.gms.gcm.GcmListenerService {
    public void handleIntent(android.content.Intent);
}

-dontwarn com.squareup.okhttp.**

-dontwarn org.joda.money.**
-keep class org.joda.money.** { *; }
