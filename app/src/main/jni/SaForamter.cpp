//
// Created by fengqiao on 2022/5/30.
//
#include <jni.h>
#include <string>
#include <android/log.h>
#include <iostream>
#include <string>
#include "md5.h"

static const char* salt = "95d52ed2c226747f294218293ae610ff";

extern "C"
JNIEXPORT void JNICALL
Java_com_wairdell_learnhelp_bridge_SaFormater_format(JNIEnv *env, jobject obj, jobject params) {
    jclass mapClass = env->GetObjectClass(params);
    jmethodID mapSizeMethodId = env->GetMethodID(mapClass, "size", "()I");
    int size = env->CallIntMethod(params, mapSizeMethodId);
    jobject keySet = env->CallObjectMethod(params, env->GetMethodID(env->FindClass("java/util/Map"), "keySet", "()Ljava/util/Set;"));
    jobject iterator = env->CallObjectMethod(keySet, env->GetMethodID(env->FindClass("java/util/Set"), "iterator", "()Ljava/util/Iterator;"));
    jclass iteratorClass = env->GetObjectClass(iterator);
    jmethodID hasNextMethodId = env->GetMethodID(iteratorClass, "hasNext", "()Z");
    jmethodID nextMethodId = env->GetMethodID(iteratorClass, "next", "()Ljava/lang/Object;");
    jmethodID toStringMethodId = env->GetMethodID(env->FindClass("java/lang/Object"), "toString", "()Ljava/lang/String;");

    //获取 Map 里所有的 key， 并按照 assic 码进行排序
    const char* keyArray[size];
    int len = 0;
    while(env->CallBooleanMethod(iterator, hasNextMethodId)) {
        jobject key = env->CallObjectMethod(iterator, nextMethodId);
        jstring keyString = static_cast<jstring>(env->CallObjectMethod(key, toStringMethodId));
        const char* keyChars = env->GetStringUTFChars(keyString, 0);

        len++;
        for(int i = 0; i < len; i++) {
            if(i == len - 1) {
                keyArray[i] = keyChars;
            } else if (strcmp(keyArray[i], keyChars) > 0) {
                for(int j = len; j > i; j--) {
                    keyArray[j] = keyArray[j - 1];
                }
                keyArray[i] = keyChars;
                break;
            }
        }
    }

    //按照 key=value& 的形式做字符串拼接
    jmethodID getValueMethodId = env->GetMethodID(mapClass, "get", "(Ljava/lang/Object;)Ljava/lang/Object;");
    std::string data = "";
    for(int i = 0; i < len; i ++) {
        const char* keyChars = keyArray[i];
        jobject value = env->CallObjectMethod(params, getValueMethodId, env->NewStringUTF(keyChars));
        jstring valueString = static_cast<jstring>(env->CallObjectMethod(value, toStringMethodId));
        const char* valueChars = env->GetStringUTFChars(valueString, 0);
        data.append(keyChars).append("=").append(valueChars);
        if (i != len - 1) {
            data.append("&");
        }
        //env->ReleaseStringUTFChars(keyString, keyChars);
        env->ReleaseStringUTFChars(valueString, valueChars);
    }
    data.append(salt);

    jmethodID mapPutMethodId = env->GetMethodID(mapClass, "put", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;");
    env->CallObjectMethod(params, mapPutMethodId, env->NewStringUTF("sign"), env->NewStringUTF(md5(data).c_str()));
//    __android_log_print(ANDROID_LOG_ERROR, "GL_TOOLS", "key = %s", data.c_str());
}

// 包名
static char *PACKAGE_NAME = "";
// 签名信息
static char *SIGNTURES = "";

extern "C"
JNIEXPORT void JNICALL
Java_com_wairdell_learnhelp_bridge_SaFormater_verify(JNIEnv *env, jobject thiz,
        jobject context) {

    __android_log_print(ANDROID_LOG_INFO, "JNI_TAG", "log start -----");

    // 获取包名信息
    jclass context_class = env->GetObjectClass(context);
    jmethodID methodId = env->GetMethodID(context_class, "getPackageName", "()Ljava/lang/String;");
    // context.packageName
    jstring packageName = (jstring) env->CallObjectMethod(context, methodId);
    const char *c_package_name = env->GetStringUTFChars(packageName, NULL);
    // 校验
    if (strcmp(PACKAGE_NAME, c_package_name) != 0) {
        //TODO
        return;
    }
    __android_log_print(ANDROID_LOG_INFO, "JNI_TAG", "包名一致，packageName:%s", c_package_name);

    // 获取签名信息
    methodId = env->GetMethodID(context_class, "getPackageManager",
                                "()Landroid/content/pm/PackageManager;");
    jobject package_manager = env->CallObjectMethod(context, methodId);
    // context.packageManager
    jclass package_manage_class = env->GetObjectClass(package_manager);


    methodId = env->GetMethodID(package_manage_class, "getPackageInfo",
                                "(Ljava/lang/String;I)Landroid/content/pm/PackageInfo;");
    // packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
    jobject package_info = env->CallObjectMethod(package_manager, methodId, packageName,
                                                 0x00000040);


    jclass package_info_class = env->GetObjectClass(package_info);
    jfieldID fileId = env->GetFieldID(package_info_class, "signatures",
                                      "[Landroid/content/pm/Signature;");
    // packageInfo.signatures
    jobjectArray signatures = (jobjectArray) env->GetObjectField(package_info, fileId);
    // signatures[0]
    jobject signatures_first = env->GetObjectArrayElement(signatures, 0);
    jclass signatures_first_class = env->GetObjectClass(signatures_first);
    methodId = env->GetMethodID(signatures_first_class, "toCharsString", "()Ljava/lang/String;");
    // toCharsString()
    jstring signatures_first_str = (jstring) env->CallObjectMethod(signatures_first, methodId);


    const char *c_signatures_first_str = env->GetStringUTFChars(signatures_first_str, NULL);
    __android_log_print(ANDROID_LOG_INFO, "JNI_TAG", "%s", c_signatures_first_str);
    // 校验
    if (strcmp(SIGNTURES, c_signatures_first_str) != 0) {
        //TODO
        return;
    }
    __android_log_print(ANDROID_LOG_INFO, "JNI_TAG", "签名信息一致 %s", c_signatures_first_str);
    __android_log_print(ANDROID_LOG_INFO, "JNI_TAG", "log end -----");
}
