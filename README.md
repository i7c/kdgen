# kdgen
kdgen is a lightweight utility for declarative test data generation, best used
with Kotlin for its syntatic sugar.

kdgen contains a collection of classes that share a common trait: they are
instantiated with little to no constructor arguments, but when evaluated (or
eval'ed), each instance yields another result. At its core, there is a class
called CountingInt. For example, if we instantiate it once, and evaluate it, it
will yield 0:

```kotlin
val x = CountingInt()
println(x.eval())
println(x.eval())
```

outputs:

```
0
0
```

If instantiated a second time, however, it will yield another value:

```kotlin
val x = CountingInt()
val y = CountingInt()
println(x.eval())
println(y.eval())
```

outputs:

```
0
1
```


A CountingInt is the most basic form of a generative primitive. Its properties
can easily be used to create other primitives:

```kotlin
println(CountingString("string").eval())
println(CountingString("string").eval())
```

outputs:

```
string0
string1
```


Another extremely simple datatype is a Modulo. It is instantiated with a list
of possible values. Internally, it uses a CountingInt to decide which one it
will yield when evaluated.

```kotlin
println(Modulo(listOf("Banana", "Apple", "Pear")).eval())
println(Modulo(listOf("Banana", "Apple", "Pear")).eval())
println(Modulo(listOf("Banana", "Apple", "Pear")).eval())
println(Modulo(listOf("Banana", "Apple", "Pear")).eval())
```

outputs:

```
Banana
Apple
Pear
Banana
```

(Note, that Modulos are best used by creating a factory using the .cycle()
method on any list. The example above is just to show that under the hood, a
modulo is just like all the other primitives.)

kdgen provided useful *simple* types similar to this Modulo, in order to
facilitate test data generation. For example, you can get an arbitrary city:

```kotlin
val hometown = City()
```

or names:

```kotlin
val firstName = FemaleFirstName()
val lastName = LastName()
```


Note, that at its very core, most types use CountingInts. This implies some
nice properties. For example, the value of a CountingInt only depends on the
underlying counter (more on that later) and the order of evaluation. This
means, that keeping the order of evaluation stable implies stable test data.

kdgen provides its own abstraction for external representation of the generated
test data. Currently, we offer json serialization, but more may follow.
Internally, we use jackson for serialization. Since jackson can deal with
structures (classes), we can create more complex types, that will be turned
into json objects:

```kotlin
class Person {
    val firstName = FirstName()
    val lastName = LastName()
    val age = CountingInt()
}
```

if serialized with the appropriate object mapper (see below), this will be
turned into something like:

```json
{
    "firstName": "Mary",
    "lastName": "Smith",
    "age": 40
}
```


Unfortunately, using a CountingInt as age might result in arbitrary high ages,
which is probably not what we want. Let's use a modulo with a more appropriate
range for ages:

```kotlin
class Person {
    companion object {
        private val nextAge = (20..100).shuffled(Random(9832)).cycle()
    }

    val firstName = FirstName()
    val lastName = LastName()
    val age = nextAge()
}
```

nextAge is a factory, that, when called, produces a Modulo. So instead of
invoking the Modulo constructor directly, we invoke the factory, with the
advantage, that all instances coming from this factory share their own
dedicated counter internally. If we used Modulo directly, all Modulos would
share a counter, which might not be what we want. Specifying Random with a seed
ensures reproducibility of the results. So, generating three Persons, will
always yield

```json
{"firstName":"Mary","lastName":"Smith","age":61}
{"firstName":"James","lastName":"Johnson","age":22}
{"firstName":"Patricia","lastName":"Williams","age":94}
```
## Generative primitive overview

* AlternatingBoolean: yields true or false
* CountingInt: monotonically increasing integer
* CountingString: a CountingInt with a string prefix and suffix
* Modulo: cycles through fixed set of values

## Simple generative types
* City: name of a city
* HouseNumber: yields seemingly random housenumbers
* ExtendedHouseNumber: like HouseNumber but with arbitrary supplements like "/2"

* FirstName: alternates between male and female first names
* FemaleFirstName: female first names
* MaleFirstName: well, I'm sure you know
* LastName: yields last names

* Text(x): yields x words of lorem ipsum nonsense text

## Pools

## Sinks

## Internals: LazyValue
