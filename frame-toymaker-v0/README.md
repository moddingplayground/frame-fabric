# Frame Toymaker (V0)

Contains utilities to generate JSON assets/data for mods.

## Entrypoint

```java
public class ModToymaker implements ToymakerEntrypoint {
    @Override
    public void onInitializeToymaker() {
        // register to generator stores
    }
}
```

### Generator Stores

Each type of data generator has a generator store. This is essentially a registry of generator `Supplier` instances. These should be registered to using the Toymaker entrypoint.
- `AdvancementGeneratorStore`
- `ItemModelGeneratorStore`
- `StateModelGeneratorStore`
- `LootGeneratorStore`
- `RecipeGeneratorStore`
- `TagGeneratorStore`

## Generators

Every subclass of `AbstractGenerator` must contain a `generate` method. Each generator type has a default `add` method, using the types contained in its map, which may be supported by utility methods, seen below.

### `AbstractAdvancementGenerator (Identifier, Advancement.Task)`

- `String, Advancement.Task`

### `AbstractItemModelGenerator (Item, ModelGen)`

Includes utility methods such as stairs, slabs, walls, waxed blocks, and spawn eggs.

- `ItemConvertible, Function<Item, ModelGen>`
- `ItemConvertible, Function<Item, Identifier>, Function<Item, ModelGen>`
- `Item...` - infers `this#generatedItem` as factory
- `Block...` - infers `InheritingModelGen#inherit` as factory

### `AbstractStateModelGenerator (Block, StateGen)`

- `Block, Function<Block, StateGen>`
- `Block...` - infers `this#cubeAll` as factory

### `AbstractLootTableGenerator (Identifier, LootTable.Builder)`

Includes utility methods such as builder templates, number providers, and conditions.

- `T, Function<T, LootTable.Builder>`
- `T, LootTable.Builder`

#### `AbstractBlockLootTableGenerator (Block)`

Implementation of `AbstractLootTableGenerator` as `Block`. Includes utility methods for block loot tables such as enchantments, slabs, ores etc.

#### `AbstractEntityTypeLootTableGenerator (EntityType<?>)`

Implementation of `AbstractLootTableGenerator` as `EntityType<?>`. Only exists to verify that entities being added are subclasses of `LivingEntity`.

### `AbstractRecipeGenerator (Identifier, CraftingRecipeJsonFactory)`

- `String, CraftingRecipeJsonFactory` - infers an `Identifier` from the generator's mod id

### `AbstractTagGenerator (Identifier, TagEntryFactory<T>)`

- `Tag<T>, T...`
- `Tag<T>, RegistryKey<T>...`
- `Tag<T>, Tag<T>...`

## Gradle

```gradle
loom {
  runs {
    data {
      server()
      vmArgs(
              "-Dtoymaker.datagen=true",
              "-Dtoymaker.datagen.modid=${project.mod_id}",
              "-Dtoymaker.datagen.path=\"${project.file("src/generated/resources")};${project.file("$buildDir/resources/main")}\""
      )
    }
  }
}

sourceSets.main.resources { srcDirs += "$projectDir/src/generated/resources" }
```
