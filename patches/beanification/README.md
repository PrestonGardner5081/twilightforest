# Beanification patches

Small local patches to `tamaized:beanification:1.6.109` that are applied to the
shaded, relocated copy of the library inside the shadowJar output.

## Why

Beanification's annotation processors call `Class.forName(className)` on every
class returned by its `@Component`/`@Bean`/`@Autowired`/`@Directory` scan. On a
NeoForge dedicated server, loading a class that contains any client-only type
reference (e.g. `net.minecraft.client.player.LocalPlayer`) fails with:

```
RuntimeException: Attempted to load class net/minecraft/client/player/LocalPlayer
                  for invalid dist DEDICATED_SERVER
```

The exception is thrown by `RuntimeDistCleaner` while resolving symbols during
class loading. Beanification doesn't catch it, so mod loading crashes.

## Fix

Wrap each `Class.forName(...)` call in `try { ... } catch (Throwable t) { continue; }`
(or `return null` for stream pipelines), so classes that fail to load on the
current dist are silently skipped instead of killing the whole scan.

Patched files (both `src/` and pre-compiled `.class`, post-relocation paths):

- `processors/gather/ComponentAnnotationGatherBeanProcessor`
- `processors/gather/BeanAnnotationGatherBeanProcessor`
- `processors/inspect/ComponentAnnotationInspectBeanProcessor`
- `processors/inspect/BeanAnnotationInspectBeanProcessor`
- `processors/staticinject/AutowiredAnnotationStaticInjectBeanProcessor`
- `processors/staticinject/DirectoryAnnotationStaticInjectBeanProcessor`
- `internal/ListInjector`

## How it's applied

`shadowJar` runs normally. After it produces the universal jar, a
`shadowJar.doLast` action uses `ant.jar(update: true)` to overwrite the seven
shaded `.class` entries with the copies in this directory. Paths here are
already post-relocation (`twilightforest/beanification/...`).

Drop this directory (and the build.gradle hook) once an upstream beanification
release ships the same fix.
