# CC UTF-8 Compat

CC UTF-8 Compat adds UTF-8 compatibility patches for [CC:Tweaked](https://modrinth.com/mod/cc-tweaked) on Minecraft 1.20.1 Forge.

The mod improves UTF-8 text handling in CC:Tweaked terminals and related peripherals. It is intended for modpacks and servers that need non-ASCII text support, including Russian, Chinese, accented characters, and other Unicode text.

## Features

- UTF-8 text rendering in computer terminals
- UTF-8 keyboard input and clipboard paste
- UTF-8 computer labels
- UTF-8 terminal synchronization between server and client
- UTF-8 rendering on monitors
- UTF-8 support for pocket computers
- UTF-8 support for printers and printed pages
- CraftOS Lua compatibility patches for selected programs and APIs

## Compatibility

| CC UTF-8 Compat | Minecraft | Loader | CC:Tweaked |
| --- | --- | --- | --- |
| **1.1.1-beta.1** | **1.20.1** | **Forge** | **1.122.0 or newer** |
| 1.1.0-beta.1 | 1.20.1 | Forge | 1.121.0 |
| 1.0.1-beta.1 (legacy) | 1.20.1 | Forge | 1.116.1 |

Version 1.1.1-beta.1 targets CC:Tweaked 1.122.0 and was tested with Forge 47.4.10. Newer CC:Tweaked versions are allowed, but compatibility is not guaranteed until tested. For CC:Tweaked 1.121.0, use CC UTF-8 Compat 1.1.0-beta.1.

## Requirements

- Minecraft **1.20.1**
- Minecraft **Forge** (tested with **47.4.10**)
- **CC:Tweaked 1.122.0 or newer for Forge** (tested with **1.122.0**)

For multiplayer, install CC UTF-8 Compat on both the client and server.

**Important:** CC:Tweaked is no longer updated on CurseForge. Download the required version from [Modrinth](https://modrinth.com/mod/cc-tweaked/versions), selecting Minecraft 1.20.1 and Forge.

## Configuration

After the first launch, the configuration file is created at:

```text
config/cc_utf8_compat-common.toml
```

The UTF-8 compatibility layer can be enabled or disabled:

```toml
ccUtf8Compat = true
```

Set it to `false` to disable the patches without removing the mod.

## Security note

CC:Tweaked **1.121.0** introduced a security fix for its HTTP API. **CC UTF-8 Compat does not itself fix that vulnerability**; updating the CC:Tweaked dependency is required.

Legacy releases targeting vulnerable CC:Tweaked versions are not recommended for production servers.

See the [official CC:Tweaked security advisory](https://github.com/cc-tweaked/CC-Tweaked/security/advisories/GHSA-g5h5-fp5g-fmx4).

## Known limitations

CC UTF-8 Compat patches internal CC:Tweaked classes using mixins and replaces selected CraftOS Lua files. Changes to CC:Tweaked internals can break compatibility. The minimum dependency version is not a guarantee of compatibility with future releases.

This release is a beta. Test it in your modpack before deploying it to a production server.

## License

This project is licensed under the [MIT License](LICENSE).
