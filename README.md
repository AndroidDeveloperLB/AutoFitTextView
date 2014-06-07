AutoFitTextView
===============

A TextView that automatically fit its font and line count based on its available size and content

This code is heavily based on [**this StackOverflow thread**][1].

The sample shows how the library can handle various parameters being changed on the TextView: width, height, number of lines allowed, and the content (text) itself. You can play with the various properties to see how the library handle them.

Note that even though the sample is of API 16, it should work fine on most cases for much older versions.

This is not a requirement though. It's more of a suggestion, because older versions might have issues as a cause of [**this known bug**][2], which affects API 12-15 (including).


  [1]: http://stackoverflow.com/questions/16017165/auto-fit-textview-for-android/21851239
  [2]: https://code.google.com/p/android/issues/detail?id=22493
