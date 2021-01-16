# Immersive-MaterialDatePicker-Sample
Immersive MaterialDatePicker sample created for https://stackoverflow.com/a/65745807/2644098

The `MaterialDatePicker` provided by the [material-components-android](https://github.com/material-components/material-components-android) package is `final`, thus it's not possible to override the dialog handler methods to create an **immersive** or **fullscreen** dialog.

This sample project demonstrates how to use `FragmentLifecycleCallbacks` to overcome this limitation and enable an immersive experience throughout an already immersive app.

The results:

Normal | Fullscreen
--|--
[![enter image description here][2]][2]|[![enter image description here][1]][1]

> These screenshots were taken on an emulator that has navigation bar normally.

  [1]: https://i.stack.imgur.com/yGHTB.png
  [2]: https://i.stack.imgur.com/ABdj5.png
