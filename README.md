# Codex Naturalis: Software Engineering Final Project

**Codex Naturalis** Board Game is the final project of **Software Engineering** course, part of Computer Science and Engineering Bachelor's Degree at Politecnico di Milano (2023/2024).
This project involves developing a Java Application of the board game Codex Natualis, originally created by [_Cranio Creations_](https://www.craniocreations.it/prodotto/codex-naturalis).


## Group Members
* [Lorenzo Valentini](https://github.com/lorenzotini)
* [Andrea Vicedomini](https://github.com/bicix-72)
* [Toni Wang](https://github.com/wangteyi)
* [Lucrezia Vaccaro](https://github.com/lucreziavaccaro)



## Basic Functionalities
| Functionality  | Implemented |
|----------------|:-----------:|
| Full rules     |      ✅       |
| RMI           |      ✅      |
|Socket         |      ✅      |
|TUI            |      ✅      |
|GUI            |      ✅      |

## Advanced Functionalities
|Funcionality  | Implemented |
|--------------|:------------|
| Chat         | ✅           |
| Multiple Games| ✅            |
|Resilience to client disconnection| ✅          |
| Persistence of server crash| ⛔         |

## JAR Execution
**Server**

In order to execute the server, the command below has to be written in the terminal. No more arguments are required, as the port is predefined. 
```shell
 java -jar GC27.jar
```

**Client**

The default configuration for the client sets RMI as client-server communication type, TUI as User Interface and localhost as the IP address.
A different IP address of the Server can be provided, it has to be written as the first additional argument of the Jar.
```shell
> java -jar GC27 
```

Two more arguments can be specified to the client jar, their order does not affect the execution:
*  _--rmi_ or _--socket_  to specify the client-server communication type
* _--tui_ or _--gui_ to specify the User Interface type

**Client GUI**

```shell
> java -jar GC27 --gui 
```
**Client TUI**

```shell
> java -jar GC27 --tui
```
An example of a complete configuration specifying a custom server IP address, GUI, and socket communication:
```shell
> java -jar GC27 192.168.56.1 --gui --socket
```

Executing the jar on the command line followed by --help or --h parameter displays all configurations.







