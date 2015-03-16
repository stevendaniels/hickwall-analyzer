# 简述 #
> 在以词典匹配的中文分词算法里，词典充当一个十分重要的角色。此个Task就是为词典类编写测试用例，尽量确保词典类的正确运行，并为此后对词典的重构确立信心。


# 详述 #

**测试用例的类型:**
此次对Dictionary相关类的测试主要分使用外部数据的测试用例与不使用外部数据的测试用例。使用外部数据的测试用例指测试时需要外部的数据去初始化词典或进行测试的动作，使用外部数据的测试用例必须实现接口ResourceRequired，此接口为标记接口，不包含任何操作。使用外部数据的测试用例一般为需大量数据的测试用例。而不使用外部数据的测试用例指在测试过程中不需要从测试程序外部读取数据，可能测试用例不需要数据，或者只需少量的数据（此数据可在代码中构造），一般此种测试为小数据量的测试，主要是对被测试的对象进行各种边界的正确性检测。


**对Dictionary接口的分析:**
Dictionary定义主要方法有: insertWord(String), insertWord(List

&lt;String&gt;

), deleteWord(String), deleteWord(List

&lt;String&gt;

), match(String), print(PrintStream)。其中insertWord(String), insertWord(List

&lt;String&gt;

), deleteWord(String), deleteWord(List

&lt;String&gt;

)都可能会改变词典的状态（这种改变是不一定的）; 而match(String)不改变词典状态，只是对词典状态的一种显示或表示


**对测试用例的测试流程的简要说明:**
因为在Dictionary接口中，已存在改变状态及测试状态的操作，所以测试用例的一般流程：初始化状态->改变状态->检测状态->改变状态->检测状态......

**发现可能的问题：**
1.各个词典的初始化不一，有的不需要初始化，有的需要loadDictionary之后才算完成初始化

2.词典应否对单字进行处理，比如词典中不应包含单字词……