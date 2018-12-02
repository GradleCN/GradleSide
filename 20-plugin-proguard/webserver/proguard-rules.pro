-obfuscationdictionary 'directory.txt'
-classobfuscationdictionary 'directory.txt'
-packageobfuscationdictionary 'directory.txt'
#
-overloadaggressively

# 不警告
-dontwarn

# 不理会警告，否则混淆失败
-ignorewarnings

# 不压缩
-dontshrink

# 不优化
-dontoptimize

# 一个类中的成员不使用重复的命名，如Student类混淆后不能出现a属性和a方法。
-useuniqueclassmembernames

# 不混淆注解
-keepattributes *Annotation*

# 不混淆泛型
-keepattributes Signature

# 保留程序入口
-keep @org.springframework.boot.autoconfigure.SpringBootApplication class * {*;}

# 不混淆枚举 
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}