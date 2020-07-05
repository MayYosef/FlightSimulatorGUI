# FlightSimulatorGUI
 🎮 A code where you can control an airplane simulator using a joystick or script, with a nice interface GUI.
<hr>
 <img src="https://github.com/MayYosef/FlightSimulatorGUI/blob/master/images/2.png" width="75%" />
<hr>

<h4>In this repository we'll cover the semester final project that we did in the advanced software development course,
 which deals with java programming, with emphasis on design patterns and programming principles such as SOLID and GRASP.
 As well as object-oriented architectures and the development of JavaFX desktop application.

A code where you can control an airplane simulator using a joystick or script, with a nice interface GUI.</h4>
<hr>

<h3 style="background-color:Tomato;">Server side:</h3>

  We take a look to the UML diagram of the server side:
  
<img src="https://github.com/MayYosef/FlightSimulatorGUI/blob/master/images/serverSide.png" width="75%" />

 In the server side we have the connection between the server that listening to a socket
 and the client handler that define the communication protocol.
 This communication help us to get a <ins>single responsibility</ins> of each interface.
 also to be able to solve a different Kind of problems we use an <ins>object adapter</ins>.

 Use <ins>bridge pattern</ins>:
 
<img src="https://github.com/MayYosef/FlightSimulatorGUI/blob/master/images/serverSide2.png" width="75%" />

we have searcher algorithm that can solve any search problem.
We implemented BFS [Best-First Search] but if we want we can add another algorithms without changing the design.
<hr>

<h3 style="background-color:Tomato;">Script Reader(Interpreter):</h3>

The project is a GUI of a flight simulator by which you can control the plane and get information from it.
And one of its features is running a script, basically a kind of programming language that can run and fly the plane.
So we implement custom language interpreter.
The interpreter is able to run a script with a various commands and uses the commands to control an air-craft based on simulator.
[we actually combined Command Pattern with Composite Pattern]

<img src="https://github.com/MayYosef/FlightSimulatorGUI/blob/master/images/script.png" width="75%" />

<hr>

<h3 style="background-color:Tomato;">Client side:</h3>

 We take a look to the UML diagram of the client  side:
 
 <img src="https://github.com/MayYosef/FlightSimulatorGUI/blob/master/images/clientSide.png" width="75%" />
 
The client side based on <ins>mvvm</ins> and <ins>observer pattern</ins>. 

we have the map drawer how responsible to draw the map,</br>
The joystick controler who control the logic of the joystick</br>
And last element is the view logic of the joystic.

<img src="https://github.com/MayYosef/FlightSimulatorGUI/blob/master/images/MVVM.png" width="75%" />

Now we have all the flow of the things that begins with interaction with the user. </br>
The user does something, </br>
The viewmodel notice that something is change with the data member and sends an activate function</br>
or asking for some calculation from the model.</br>
The model then sends notifications that our data or the calculation is ready. </br>
And the viewmodel again decide what to do with this information and so on...</br>
It goes back to the view and sending notifications to the view and the view shows the output to the user again.</br>
As we can see the model have the interpreter .
<hr>

<h3 style="background-color:Tomato;">This is our application:</h3>

 <img src="https://github.com/MayYosef/FlightSimulatorGUI/blob/master/images/1.png" width="75%" />
 
 <img src="https://github.com/MayYosef/FlightSimulatorGUI/blob/master/images/3.png" width="75%" />
 
 <hr>
 
 <h2 style="background-color:Tomato;">Built With:</h2>
<ul>
  <li>Eclipse - Java IDE</li>
  <li>Scene Builder - Scene Builder 8.5.0</li>
</ul>  
<hr>

<h2 style="background-color:Tomato;">Authors:</h2>
<ul>
  <li>May Yosef</li>
  <li>Yuval Butbul</li>
</ul>  
