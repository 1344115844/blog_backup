# Spring缓存的使用(Cache)

> 关于缓存，我想你在开发中多多少少都会用到。可能你之间使用一个`Map`来管理，或者采用了`EhCache`或者`Guava`等框架，但是否注意到这些缓存代码是和你的业务代码混在一起的，而且一旦使用了某种缓存的话，想切换一下估计头都要大了。那么正好，本篇就是看一看Spring为我们所提供的解决方案，如何就像我们使用声明式事务一样来使用缓存。

## Spring Cache注解

Spring自从3.1版本开始提供了Cache注解，就像使用Spring所提供的事务注解一样，可以业务代码与缓存相关处理剥离，使得我们的代码更专注于业务逻辑。

Spring本身提供的Cache注解有下面几个：

1. `@Cacheable`: 该注解表示所注解的方法支持缓存。当所注解的方法被调用时，Spring首先会根据参数从缓存中查找，如果没有则执行相应的方法，否则返回缓存的值；
2. `@CacheEvict`: 该注解表示所注解的方法执行后将清空相应缓存；
3. `@CachePut`: 该注解表示所注解的方法在执行后能够将执行结果进行缓存，和`@Cacheable`注解不同的是，它所注解的方法每次都会执行，即使执行的结果在缓存中已经存在；而`@Cacheable`所注解的方法只有缓存中不存在时才会执行。比如，对于`update(User user)`这种类型的方法；
4. `@Caching`: 当我们需要在一个方法上添加多个缓存注解(如：`@CacheEvict`和`@CachePut`)，或者需要操作多个缓存时，可以使用该注解进行组合；
5. `@CacheConfig`: 该注解使用在类声明中，对该类中使用到缓存的方法统一进行配置，如，配置缓存的名称：`@CacheConfig("users")`。

另外，Spring框架从4.1版本后也支持`JCache(JSR-107)`的标准注解：

1. `@CacheResult`: 类似Spring所提供的`@Cacheable`；
2. `@CachePut`: 类似Spring所提供的`@CachePut`；
3. `@CacheRemove`: 类似Spring所提供的`@CacheEvict`，不过只支持将方法返回的结果从缓存中清除，如果要清空全部缓存需要使用下面的注解；
4. `@CacheRemoveAll`: 清空全部缓存；
5. `@CacheDefaults`: 类似Spring所提供的`@CacheConfig`。

> 虽然，Spring框架对这两种缓存注解都支持，但为了保持代码的可读性，最好在代码中只使用它们中的一个，而不要混用。

