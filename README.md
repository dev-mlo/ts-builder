# TypeScript Builder
This library is a Java API for generating ```.ts``` source files.

## Simple examples

Here is a simple hello World example

```java
import de.mlo.dev.tsbuilder.elements.function.TsFunction;

class HelloWorldGenerator {
    public static void main(String[] args) {
        String tsFunction = new TsFunction("printHelloWorld")
                .setExportDefault()
                .addContent("console.log('Hello world")
                .build();
        System.out.println(tsFunction);
    }
}
```

will result in

```typescript
export default function printHelloWorld () {
    console.log('Hello World');
}
```

## Features

### Compile imports

Add your imports anywhere in your building process. The function 
```buildWithImports``` will collect all declared imports and will create
a distinct list of all imports:

```java
TsFunction calculateDistance = new TsFunction("calculateDistance")
        .addParameter(TsFunctionParameter.number("from"))
        .addParameter(TsFunctionParameter.number("to"))
        .addReturnType(TsFunctionReturnType.literal("Distance"))
        .addContent("// Calculate some stuff");
calculateDistance.addImport("Distance", "./my-types");

TsFunction convertMilesToKilometer = new TsFunction("convertMilesToKilometer")
        .addParameter(TsFunctionParameter.custom("distance", "Miles"))
        .addReturnType(TsFunctionReturnType.literal("Kilometers"))
        .addContent("// Calculate some stuff");
convertMilesToKilometer.addImport("Miles", "./my-types");
convertMilesToKilometer.addImport("Kilometers", "./my-types");

String result = new TsFile("calc.ts")
        .addFunction(calculateDistance)
        .addFunction(convertMilesToKilometer)
        .buildWithImports();
```

As you can see we declare one import (Distance) at the first function and
two more imports (Miles and Kilometers) at the second function. All three 
from the same file. The Framework will compile those imports and this 
will result into:

```typescript
import {Distance, Miles, Kilometers} from './my-types';

function calculateDistance (from: number, to: number): Distance {
  // Calculate some stuff
}
function convertMilesToKilometer (distance: Miles): Kilometers {
  // Calculate some stuff
}
```

### Merge duplicates

Some elements like files, functions, methods and class can be declared more 
than once. If this happens and the elements are marked as ```mergeAllowed``` 
the content of those element will be merged together:

```java
TsFunction first = new TsFunction("foo")
        .addContent("// Comment from first 'foo' function");
TsFunction second = new TsFunction("foo")
        .addContent("// Comment from second 'foo' function");
TsFile file = new TsFile("bar")
        .addFunctions(first, second);

String result = file.build();
System.out.println(result);
```

The first and the second function have the same signature. If you add both
function to a file those function will be automatically merged together and
the result is:

```typescript
function foo () {
  // Comment from first 'foo' function
  // Comment from second 'foo' function
}
```

If you don't merge or drop duplicated function, then... well, the TypeScript
Compiler will tell you what to do.

## More examples

### Create Angular component

It is also possible to build TypeScript files for frameworks like Angular. 
Here is an example how to create a Component class for Angular:

```java
import de.mlo.dev.tsbuilder.elements.clazz.TsClass;
import de.mlo.dev.tsbuilder.elements.decorator.TsDecorator;
import de.mlo.dev.tsbuilder.elements.decorator.TsDecoratorProperty;
import de.mlo.dev.tsbuilder.elements.values.ComplexValue;

class ComponentGenerator {
    public static void main(String[] args) {
        // Creating the component decorator which
        TsDecorator componentDecorator = new TsDecorator("Component")
                .addProperty(new TsDecoratorProperty().setValue(new ComplexValue()
                        .addStringValue("selector", "app-my-component[user]")
                        .addStringValue("templateUrl", "./my.component.html")
                        .addStringArrayValue("styleUrls", "./my.component.scss")));
        componentDecorator.addImport("Component", "@angular/core");

        // Add a field which holds the current user and provide a setter function
        TsField userField = TsField.optionalCustom("user", "User")
                .addSetter(new TsDecorator("Input"));
        userField.addImport("Input", "@angular/core");

        // Define a class and add the decorator and the defined field
        TsClass clazz = new TsClass("MyComponent")
                .setExport()
                .addDecorator(componentDecorator)
                .addField(userField);

        String result = clazz.buildWithImports();

        System.out.println(result);
    }
}
```

will result in 

```typescript
import {Component, Input} from '@angular/core';

@Component(
  {
    selector: 'app-my-component[user]',
    templateUrl: './my.component.html',
    styleUrls: ['./my.component.scss']
  }
)
export class MyComponent{
  _user?: User;
  @Input()
  set user (value?: User) {
    this._user = value;
  }
}
```