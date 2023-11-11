<div align="center">
  <img src="/images/logo.png" alt="Syntakts" width="100%" />

  ### Simple to use text parser and syntax highlighter for Kotlin Multiplatform
</div>

## Setup
```kotlin
implementation("xyz.wingio.syntakts:syntakts-core")
// implementation("xyz.wingio.syntakts:syntakts-compose")
// implementation("xyz.wingio.syntakts:syntakts-compose-material3")
```

## Use
Syntakts can be set up through a simple DSL:
```kotlin
val mySyntakts = syntakts<Unit> {
  rule("@([A-z])") { result, context ->
    append(result.groupValues[1]) {
      color = Color.Yellow
    }
  }
}
```

We also provide MarkdownSyntakts and BasicMarkdownSyntakts, which has some default markdown rules

### Context
Syntakts allows you to pass any class as context, this can be used to pass additional information for rendering.
If you don't need to use context you can set it to Unit

Example:
```kotlin
data class Context(
  val userMap = mapOf("1234" to "Wing")
)

val mySytankts = syntakts<Context> {
  rule("<@([0-9]+)>") { result, context ->
    val username = context.userMap[result.groupValues[1]] ?: "Unknown"
    append("@$username) {
      color = Color.Yellow
    }
  }
}
```

## Displaying

### Compose
`syntakts-compose`
Syntakts uses AnnotatedStrings in order to display rendered text in Compose

> [!NOTE]
> When creating a Syntakts instance in a composable we reccommend replacing `syntakts {}` with `rememberSyntakts {}`

Example: 
```kotlin
@Composable
fun SomeScreen() {
  val syntakts = rememberSyntakts<Unit> { /* */ }

  Text(
    text = syntakts.rememberRendered("some input")
  )
}
```

#### Clickable
Syntakts for Compose includes a ClickableText component that is neccessary in order to handle clickable text. The `syntakts-compose-material3` includes this component as well but adds support for Material 3 theming

## Attribution
Syntakts was heavily inspired by [SimpleAST](https://github.com/discord/SimpleAST), an unfortunately abandoned library that was once used in Discords android app
