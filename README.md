# Books application with Compose and MVI
A small sample application that uses Jetpack Compose for UI with the MVI patttern. It showcases different types of states, events and side effects. 
It also implements the navigation component for Compose and showcases its usage.
### MVI core components
The MVI pattern should handle these core components the following way:
- **State** - data class that holds the state content of the corresponding screen e.g. list of User, loading status etc. The state is exposed as a Compose runtime MutableState object from that perfectly matches the use-case of receiving continuous updates with initial value.
- **Event** - plain object that is sent through callbacks from the UI to the presentation layer. Events should reflect UI events caused by the user. Events are sent into the ViewModel where they are handled.
- **SideEffect** - plain object that signals one-time side-effect actions that should impact the UI e.g. triggering a navigation action, showing a Toast, SnackBar etc. Effects are exposed as SharedFlow which behave as in each event is delivered to a single subscriber. An attempt to post an event without subscribers will suspend as soon as the channel buffer becomes full, waiting for a subscriber to appear.

![Screenshot 2024-11-20 at 15 03 16](https://github.com/user-attachments/assets/ebeff58a-a0e2-4851-9302-a27b91ef92a7)
### Navigation
The app uses the official navigation component for Compose. For this a couple of components were created:
- **NavGraph**: contains the navigation tree with the screens
- **Screen navigations**: a separate `NavGraphBuilder` extension function for each screen in the app. The reason why these were created is because this is where the `ViewModel` is injected, in case the Screens themselfs want to be snapshot tested.
- **Screens**: Composable functions for the screens themselfs without anything injected, making for an easier snapshot testing, if needed.

In case we don't need snapshot testing, the screen navigations can be omitted, we can inject the `ViewModel` inside the screen composable function and the screens can be used in the `NavGraph` directly.
