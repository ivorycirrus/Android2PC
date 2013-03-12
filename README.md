Android2PC
==========

USB Socket Communication between PC and Android


Modules
=======

1. PC2UsbSocketApplication - 
  A socket server application which installed on the android devices.

2. SocketExample - 
  Socker server and socker client programs in java languages.


Usages
======

1. Install Android usb driver and ADB on your PC.

2. Connect PC and Android device using USB cable.

3. Set adb port forwarding.
   Example:> on pc's command console (shell prompt)
          $ adb forward tcp:9500 tcp:9500

4. Run "PC2UsbSocketApplication" application on android device first.

5. Compile and Run socket server in "SocketExample"
