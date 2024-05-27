//
// Created by apple on 2019-10-14.
//

#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_com_apporio_demotaxiappdriver_SplashActivity_getAPIKey(JNIEnv* env, jobject /* this */) {
    std::string api_key = "AIzaSyBGcTpMjWT1ukAl3qcV4abnn677SOhuK2c";
    return env->NewStringUTF(api_key.c_str());
}