# ChatPro
A minecraft plugin that allows you to add colors to your messages by including a bukkit color code in your message, and the plugin features a word filter which you can set in the config file.

## Features
- Add color to your messages by using bukkit color codes
- Support for bukkit format codes
- Allowing players to change the color of their name
- Configuration file
- Word ignore list
- Update Checker

## Permissions
- ```chatpro.namecolor``` Allow players to change the color of their name.
- ```chatpro.colors``` Allow players to use color in their chat messages.
- ```chatpro.formats``` Allows players to use formatting in their messages.

## Tested Versions
1.8 And above.

## Compiling Jar From Source

### Prerequisites

- Java Development Kit (JDK) version 8 or higher

### Steps:

1. **Clone the Repository**:
   Clone the repository containing the ChatPro source code to your machine using Git:
   ```bash
   git clone https://github.com/Aaron2404/ChatPro.git
   ```

2. **Navigate to Project Directory**:
   Navigate to the root directory of the cloned project:
   ```bash
   cd ChatPro
   ```

3. **Compile the Source Code**:
   Use Gradle to compile the source code and generate the jar file:
   ```bash
   ./gradlew build
   ```
   or for Windows CMD:
   ```cmd
   .\gradlew build
   ```

4. **Locate the Jar File**:
   Upon successful compilation,
   the generated jar file can be found in the `build/libs/` directory within the project root.

By following these steps, you will compile the ChatPro plugin from its source code.
