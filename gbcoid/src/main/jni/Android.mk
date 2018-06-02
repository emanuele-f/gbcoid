LOCAL_PATH:= $(call my-dir)/gbclib
include $(CLEAR_VARS)

LOCAL_MODULE_TAGS := user

LOCAL_ARM_MODE := arm

# This is the target being built.
LOCAL_MODULE := libgbc

# All of the source files that we will compile.
LOCAL_SRC_FILES := \
	cpu.c \
	debug.c \
	emu.c \
	events.c \
	exports.c \
	fastmem.c \
	hw.c \
	keytable.c \
	lcd.c \
	lcdc.c \
	loader.c \
	mem.c \
	palette.c \
	path.c \
	rccmds.c \
	rcfile.c \
	rckeys.c \
	rcvars.c \
	refresh.c \
	rtc.c \
	save.c \
	sound.c \
	cheats.cpp \
	split.c \
	ioapi.c \
	zip.c \
	unzip.c \
	fileio.c \

LOCAL_SRC_FILES += \
	../gbc_main.c \
	../gbcengine.cpp

LOCAL_LDLIBS := -lz -llog

LOCAL_C_INCLUDES += \
	$(LOCAL_PATH)

# Compiler flags.
LOCAL_CFLAGS += -O3 -fvisibility=hidden -w

# Don't prelink this library.  For more efficient code, you may want
# to add this library to the prelink map and set this to true. However,
# it's difficult to do this for applications that are not supplied as
# part of a system image.

LOCAL_PRELINK_MODULE := false

include $(BUILD_SHARED_LIBRARY)

# include emudroid-common
include $(LOCAL_PATH)/../emudroid-common/Android.mk