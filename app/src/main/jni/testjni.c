//
// Created by admin on 2017/11/6.
//
#include <jni.h>
#include "com_winguo_testbugly_jni_NdkJniUtils.h"
#include <string.h>
#include <assert.h>
#include "./utils/android_log_print.h"
#include "./local_logic_c/easy_encrypt.h"

JNIEXPORT jstring JNICALL native_generate_key(JNIEnv *env,jobject obj, jstring name){
    //声明局部量
    char key[KEY_SIZE] = {0};
    memset(key,0,sizeof(key));

    char temp[KEY_NAME_SIZE] = {0};

    //将java传入的name转换为本地utf的char*
    const char* pName = (*env)->GetStringUTFChars(env, name, NULL);
    if(NULL != pName){
        strcpy(temp,pName);
        strcpy(key,generateKeyRAS(temp));
        (*env)->ReleaseStringUTFChars(env,name,pName);
    }

    return (*env)->NewStringUTF(env,key);
}

JNIEXPORT jint JNICALL addCalc(JNIEnv *env,jobject obj, jint i1,jint i2){
    //声明局部量
    int i = 3;
    return i1+i2+i;
}

//参数映射表
static JNINativeMethod methods[] = {
        {"nativeGenerateKey", "(Ljava/lang/String;)Ljava/lang/String;", (void*)native_generate_key},
        {"addCalc","(II)I", (void*)addCalc},
        {"startMonitor","()V", (void*)startMonitor},
        {"stopMonitor","()V", (void*)stopMonitor}
        //这里可以有很多其他映射函数
};

//自定义函数，为某一个类注册本地方法，调运JNI注册方法
static int registerNativeMethods(JNIEnv* env , const char* className , JNINativeMethod* gMethods, int numMethods)
{
    jclass clazz;
    clazz = (*env)->FindClass(env, className);
    if(clazz == NULL){
        return JNI_FALSE;
    }
    if((*env)->RegisterNatives(env,clazz,gMethods,numMethods)<0){
        return JNI_FALSE;
    }
    //JNI函数，参见系列教程2
    return JNI_TRUE;
}

//自定义函数
static int registerNatives(JNIEnv* env)
{
    const char* kClassName = "com/winguo/testbugly/jni/NdkJniUtils";//指定要注册的类
    return registerNativeMethods(env, kClassName, methods,  sizeof(methods) / sizeof(methods[0]));
};

JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM* vm, void* reserved)
{
    LOGD("customer---------------------------JNI_OnLoad-----into.\n");
    JNIEnv* env = NULL;
    jint result = -1;

    if ((*vm)->GetEnv(vm, (void**) &env, JNI_VERSION_1_4) != JNI_OK)
    {
        return -1;
    }
    assert(env != NULL);

    //动态注册，自定义函数
    if (!registerNatives(env))
    {
        return -1;
    }

    return JNI_VERSION_1_4;
}


//模拟压力传感其传递数据
int getPressure(){
    return rand()%101;
}
//用于控制循环的开关
int monitor;
JNIEXPORT void JNICALL startMonitor
(JNIEnv * env, jobject obj){
    monitor = 1;
    int pressure;
    jclass clazz;
    jmethodID methodid;
    while(monitor){

        //本地方法获取传感器数据
        pressure = getPressure();
        //使用反射调用java方法刷新界面显示
        //jclass      (*FindClass)(JNIEnv*, const char*);
        char* kClassName = "com/winguo/testbugly/jni/MonitorActivty";
        clazz = (*env)->FindClass(env, kClassName);
        //jmethodID   (*GetMethodID)(JNIEnv*, jclass, const char*, const char*);
        methodid = (*env)->GetMethodID(env, clazz, "show", "(I)V");
        // void        (*CallVoidMethod)(JNIEnv*, jobject, jmethodID, ...);
        (*env)->
        CallVoidMethod(env, obj, methodid, pressure);
        sleep(1);
    }

}

JNIEXPORT void JNICALL stopMonitor
(JNIEnv * env, jobject obj){
    //结束循环
    monitor = 0;
}