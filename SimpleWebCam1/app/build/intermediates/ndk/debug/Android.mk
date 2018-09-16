LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE := ImageProc
LOCAL_LDFLAGS := -Wl,--build-id
LOCAL_SRC_FILES := \
	F:\AndroidCode\SimpleWebCam1\app\src\main\jni\Android.mk \
	F:\AndroidCode\SimpleWebCam1\app\src\main\jni\Application.mk \
	F:\AndroidCode\SimpleWebCam1\app\src\main\jni\ImageProc.c \

LOCAL_C_INCLUDES += F:\AndroidCode\SimpleWebCam1\app\src\debug\jni
LOCAL_C_INCLUDES += F:\AndroidCode\SimpleWebCam1\app\src\main\jni

include $(BUILD_SHARED_LIBRARY)
