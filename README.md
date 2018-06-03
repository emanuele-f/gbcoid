# gbcoid
Gameboy and Gameboy Color emulator for Android phones

The original project is located at `https://sourceforge.net/p/gbcoid`, but it's no more maintained.
Although, the author suggests migrating to [libretro](https://www.libretro.com/), gbcoid still remains a simple and
working emulator, which performs its job very well. I've seen many emulators having permissions problems leading to the inability
to load ROM files, whereas gbcoid works just fine.

This project is a port of the original project to the new Android Studio tools to provide a working setup as well as
fixes to some little bugs.

# APK

A freshly generated APK can be found [here](gbcoid/release). It includes the following enhancements over the original 1.8.5 apk:

- Use a more recent gui, libemudroid version
- Fix crackling noise after resuming music (libemudroid)
- Smoother DPAD movements

# Notes

- This project depends on [libemudroid](https://github.com/emanuele-f/libemudroid). After building libemudroid, you should:

  `cp ../libemudroid/build/outputs/aar/libemudroid-release.aar libemudroid/libemudroid.aar`

# Usefull Links

https://android.googlesource.com/platform/libnativehelper

https://developer.android.com/studio/projects/android-library
