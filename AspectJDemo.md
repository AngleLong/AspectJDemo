其实人最大悲哀莫过于知道自己想要什么，却不知道怎么坚持！最近迷恋上了[死侍](https://baike.baidu.com/item/%E6%AD%BB%E4%BE%8D/9080435?fr=aladdin) 其实和我平时的状态差不多，以一个混子的心态去做任何事情，往往成功的概率会更大！！！

一张图片镇楼！！！

![](https://user-gold-cdn.xitu.io/2018/8/11/165278007d9d29d4?w=600&h=345&f=jpeg&s=30709)

上文说到了AspectJ的集成问题，如果没有看过上一篇文章的小伙伴可以看看本系列的第一篇文章。

[AOP埋点从入门到放弃（一）](https://juejin.im/post/5b6da3426fb9a04fbe130bce)

这篇文章充分的讲解了关于AspectJ的集成问题，接下来我们讲讲怎么更好的使用AspectJ来唯我所用。。。

# 1. 一些乱七八糟东西的解释
> 其实我感觉这个东西说起来是最难的，因为要记住一大堆概念！其实记忆力是我的最烦的东西，但是我是一只猿，一只牛逼的猿！所以当背课文了...

**先来看一段代码**
```
@Aspect
public class TraceAspect {
    private static final String TAG = "hjl";

    @Before("execution(* android.app.Activity.on*(..))")
    public void onActivityMethodBefore(JoinPoint joinPoint) {
        String key = joinPoint.getSignature().toString();
        Log.e(TAG, "onActivityMethodBefore: 切面的点执行了！" + key);
    }
}
```

先说一下这段代码实现了什么？主要实现了在Activity中所有以**on**开头的方法前面打印一段**Log.e(TAG, "onActivityMethodBefore: 切面的点执行了！" + key);**代码！接下来我们来逐一讲解！

## 1.1 顶部@Aspect的含义
> 关于@Aspect这个注解，是下面所有内容的基础，如果没有这个注解AspectJ没有相应的入口，就不会有相应的切面了！AspectJ会找到所有@Aspect注解，然后


## 1.2 通配符的概念(Pointcut语法)(难点)
> 首先说一下通配符的大体格式：(我也不知道理解的对不对，但是项目中使用的时候没有发现什么不对的地方)

| @注解 | 访问权限 | 返回值类型 | 类名 | 函数名 | 参数 |
| ------ | ------ | ------ | ------ | ------ | ------ |

大体上的通配符都是这个格式的，我们用上面的一个通配符去说明一下：

```
execution(* android.app.Activity.on*(..))
```

execution 一般指定方法的执行,在往后因为没有注解和访问权限的限制，所以这里什么也没写，返回值用\*代替，说明可以用任何返回值，android.app.Activity.on*代表函数名称的全路径，后面的一个型号代表on后面可以接任何东西，后面的(..)代表其参数可以为任何值。

上面就是一段通配符的含义了！其实学习AspectJ的时候，我觉得最难懂的就是相应的操作符了，如果操作符弄明白了的话，真的就很简单了！但是如果之前做过后台的话，这个应该就很简单了，就是Spring框架中的AOP是一样的都是用的Pointcut语法。因为自己不是java后台开发人员，所以解释的可能不到位，你可以去找你们java后台组的人去问问，学习一下！应该比我讲的强很多，因为我真是第一次接触这个东西！

因为平时没接触过，所以这里就写一些常用的吧！


**分类**
| JPoint | 说明 | Pointcut语法说明 |
| :-----:| :------: | :------: |
| method execution | 一般指定方法的执行 | execution(MethodSignnature) |
| method call | 函数被调用 | call(MethodSignnature) |
| constructor call | 构造函数被调用 | call(ConstructorSignature) |
| constructor execution | 构造函数执行内部 | execution(ConstructorSignature) |
| field get | 读变量 | get(FieldSIgnature) |
| field set | 写变量 | set(FieldSIgnature) |
| handler | 异常处理 | handler(TypeSignature) 注意：只能和@Before()配合使用，不支持@After、@Around等|
| advice execution | advice执行 | adciceexectuin() |

**Signature参考**
| Sigbature |语法(间隔一个空格)|
| :-----:| :------: |
| MethodSignature | @注解 访问权限 返回值类型 类名.函数名(参数) |
| ConstructorSignature | @注解 访问权限 类名.new(参数) |
| FieldSignature | @注解 访问权限 变量类型 类名.类成员变量名 |

**Signature语法明细**
| Sigbature语法明细 |解释|
| :-----:| :------: |
| @注解 | @完整类名，如果没有则不写 |
| 访问权限 | public/private/portect，以及static/final，如果没有则不写 **注意**：如果只写public则匹配出来的是全部，如果写public final则匹配的是所有public final开头的内容|
| 返回值类型 | 如果不限定类型，使用通配符*表示 |
| 类名.函数名 | 可以使用的通配符，包括\*和..以及+号。其中\*号用于陪陪除.号之外的任意字符，而..则表示任意字package，+号表示子类 **注意**：1.ConstructorSignature的函数名只能为new 2.(.函数名可以不写)，重用和注解一起使用 3.不能以..开头|
| 变量类型 | 成员变量类型，*代表任意类型 |
| 类名.成员变量名 | 类名可以使用通配符，与函数。函数名类似 |

**Advice内容**
| Advice | 说明 |
| :------:| :------: |
| @Before(Pointcut) | 执行在jPoint之前 |
| @After(Pointcut) | 执行在jPoint之后 |
| @Around(Pointcut) | 替代原来的代码，如果要执行原来的代码，需要使用proceedingJoinPoint.proceed(); **注意**：不可以和@After和@Before等一起使用 |

上面这写表的你先简单看一下，估计你一会还是会回来看的！！！

## 1.2.1 method->call的示例：
```
    @Pointcut("call(* com.jinlong.aspectjdemo.MainActivity.callMethod(..))")
    public void callMethod() {
        //为了演示call方法的使用
    }

    @Before("callMethod()")
    public void beforeCallMethod(JoinPoint joinPoint) {
        Log.e(TAG, "call方法的演示");
    }
```

说一下上面代码：@Pointcut是来注解方法的，call后面添加了一系列的通配符，简单说就是一个方法的地址，\*代表没有返回值，@Before是说明在切片前执行！这里千万别把com.jinlong.aspectjdemo.MainActivity.callMethod这个地址写错了就行！

其实上面这段代码可以简化为
```
    @Before("call(* com.jinlong.aspectjdemo.MainActivity.callMethod(..))")
    public void beforeCallMethod(JoinPoint joinPoint){
        Log.e(TAG, "call方法的演示");
    }
```

如果你把上面的@Before换为@After，那么就会在方法之后打印！！！

再来看一段代码：
```
    @Around("call(* com.jinlong.aspectjdemo.MainActivity.callMethod(..))")
    public void aroundCallMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        joinPoint.proceed();
        long endTime = System.currentTimeMillis();
        Log.e(TAG, "方法执行的时间为" + (endTime - startTime));
    }
```
这段代码是统计方法执行时间的，这里着重讲两个内容
- joinPoint.proceed();还原你原来的代码，如果没有这句，你原来的代码就没了！没了！
- @Around 替代原来的代码，加上上面这句就可以还原了之前的代码了！

所以这里就能计算出方法的执行时间了！！！也就是@Around的用法了！

## 1.2.2 method->execution 的示例：
> 之前的时候，我总觉得execution和call是一样的，但是后来我知道了，他们最大的区别是这样的！！！

比如你有一个方法：callMethod()对吧！
然后使用call是这个样子滴~

```
call的相应方法();
callMethod();
```

但是如果是execution的话就编程这个样子滴了~

```
callMethod(){
   execution的相应方法();
}
```
其他的就没有什么区别了，也就不在这里举例说明了。

### 1.2.3 构造方法的操作
> 先说下这个东西是构造方法上用的，也就是说针对于相应的构造方法进行相应切面操作的！**但是我一只有一个疑问不明白，如果我按照上面方法的通配符进行操作的话，按照常理说应该也是能在相应切面进行操作的才对啊！编译不报错，但就是怎么也打印不出来结果，还请明白的大神帮我解答一下！**

按照上面的表格还有一种方案解决相应构造方法的问题

```
    @Before("execution(com.jinlong.aspectjdemo.Person.new(..))")
    public void beforeConstructorExecution(JoinPoint joinPoint) {
        //这个是显示Constructor的
        Log.e(TAG, "before->" + joinPoint.getThis().toString() + "#" + joinPoint.getSignature().getName());
    }
```

这段代码我尝试过了，可以打印出结果。也就是以**ConstructorSignature	@注解 访问权限 类名.new(参数)**这种方式就可以打印出相应结果来！不过我劝你把那个@Before换成@After否则你打印出来的内容可能是一个空！

### 1.2.4 关于相应成员变量的问题
> 就是相当你可以修改类的成员变量，不管你怎么设置最终返回的都是你设定的值！

看下面这段代码：

这里是正常的一个类：
```
public class Person {
    private String name;
    private String age;

    public Person() {
    }

    public Person(String name, String age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
```

这才是核心代码！

```
    @Around("get(String com.jinlong.aspectjdemo.Person.age)")
    public String aroundFieldGet(ProceedingJoinPoint joinPoint) throws Throwable {
        // 执行原代码
        Object obj = joinPoint.proceed();
        String age = obj.toString();
        Log.e(TAG, "age: " + age);
        return "100";
    }
```

这里用到的的是上面**FieldSignature @注解 访问权限 变量类型 类名.类成员变量名**上面这段代码的含义是这样滴，在每次用到age这个内容的时候，都会被修改，但是有一个问题，就是你不能重写类的**toString()**方法，如果你重写了这个方法的话，obj返回的就是一个空！我还真不知道为什么，**还请明白的告知一二！**，这里面get是一个关键字，就这么理解吧！因为没有设置访问权限和注解，所以这里直接就省返回的变量类型(String类型)和类成员变量名的全路径了！

在看下面这段代码：
```
    @Around("set(String com.jinlong.aspectjdemo.Person.age)")
    public void aroundFieleSet(ProceedingJoinPoint joinPoint) {
        Log.e(TAG, "aroundFieleSet: " + joinPoint.getTarget().toString() +
                joinPoint.getSignature().getName());
    }
```

这个可以对相应的age属性进行设置的方法，也就是当发生**赋值**操作的时候都会被修改！这里和大家说明一下，上面这段代码没有**joinPoint.proceed();**代码，所以之前的代码中执行的内容就会失效了！也就是说被打印这段话替换了！其实上面这段代码你运行的时候你会发现一件事，Log被打印了两次，为什么呢？你想啊！this.age = age;在set方法中出现一次，而且还在构造方法中出现一次呢。仔细看看，所以这里要排除构造方法总的那一次，怎么处理呢？就要用到 **withincode**了！

### 1.2.5 withincode排除内容
> 怎么理解这个东西呢？表示某个构造方法或函数中涉及到的JPoint。不理解吧！没关系，看一段代码你就理解了！

```
    @Around("set(String com.jinlong.aspectjdemo.Person.age)&&!withincode(com.jinlong.aspectjdemo.Person.new(..))")
    public void aroundFieleSet(ProceedingJoinPoint joinPoint) throws Throwable {
        //设置相应的成员变量
        joinPoint.proceed();
    }
```

在1.2.4上面说到set会在两个地方都有，但是其实我是不想要构造方法中的那个的，怎么把他排除呢？那就是后面添加的这句代码**com.jinlong.aspectjdemo.Person.new(..))**就是把构造方法中的内容排除！其实很好理解，就是排除相应的构造方法，可以简单理解withincode就是带着某个内容，但是由于取反了，所以就是不带着这个东西了！！！就酱紫了。。。

### 1.2.5 handler的异常的捕捉
> 这个相比较之下就简单一点了，直接上代码：

这是在代码中的一个异常，很简单的一个异常，如果这个方法走了相应的catch，那么就能捕获相应的异常了！

```
    private void catchMethod() {
        try {
            int sum = 100 / 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
```

关键代码来了。。。

```
    @Before("handler(java.lang.Exception)")
    public void handlerMethod() {
        Log.e(TAG, "handlerMethod: 异常产生了");
    }
```

是不是很简单，使用一个handler关键字，加上一个异常的全路径，ok搞定，但是这里一定要注意，前面的注解只能是@Before，切记！！！

### 1.2.6 关于注解的使用
> 有许多第三方你是不知道具体方法名称的，但是你还想使用的话怎么办？那就是注解了，因为注解可以很好的解决这种需求。

再来看一段代码：

定义一段注解内容：

```
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.CONSTRUCTOR, ElementType.METHOD})
public @interface DebugTrace {
}
```

因为注解的这些内容不是本篇文章的重点，所以这里不准备讲解了。感兴趣的你可以百度一下，这个注解主要是在编译完成后也会起作用，并且是方法和成员变量都可以使用！

在加上下面这段代码就可以进行相应切面的操作了！

```
    @Pointcut("execution(@com.jinlong.aspectjdemo.DebugTrace * *(..))")
    public void DebugTraceMethod() {
    }

    @Before("DebugTraceMethod()")
    public void beforeDebugTraceMethod(JoinPoint joinPoint) {
        String key = joinPoint.getSignature().toString();
        Log.e(TAG, "注解这个方法执行了: ");
    }
```

看到在这段代码大家应该不怎么陌生了，就是在方法内容添加相应的切面操作。最后在使用的方法的上面添加相应的注解就可以了！就这么简单！

```
    @DebugTrace
    private void mothod1() {
        Log.e(TAG, "方法1执行了");
    }
```

***
上面基本上包含了我们APP使用中，能用到一些内容，如果有什么讲的不到位的地方还请指出。因为是第一次接触这个东西，可能有些细节讲解的不是很到位，还请谅解！！！

想看源码吗？想看链接吗？[点这里]()




