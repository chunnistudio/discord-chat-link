# DISCORD CHAT LINK

This mod was originally born to be used for [Chunni Studio's Cobblemon server](https://cobblemon.chunni.studio/)

At the moment it just works with Fabric loader 0.17.2 and for the 1.21.1 Minecraft version, it may or may not work with newer versions,
but you're free to send patches to make it work :)

## How to compile

You need Java 21+

Run the following command

```
./gradlew build
```
The final jar is located in
```
build/libs
```

## How to use the mod

Drop the mod in your server's mod folder. After executing the server for the first time, a file will appear in the following path
inside your server folder

```
config/discord-link.properties
```
Open the file and modify it by copying your Bot token and Channel Id
```
bot.token=your-token-here
channel.id=discord-channel-id-here
```
To get a bot token, you will need to setup your own private bot. Also, to get the channel Id of the text-channel you want to use
[follow these instructions](docs/SETUP.md).

